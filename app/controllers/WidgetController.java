package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.libs.concurrent.HttpExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Scala.asScala;
import lyc.*;


@Singleton
public class WidgetController extends Controller {
    private final CompletableFuture<Connection> twitter;
    private CompletableFuture<List<Item>> tweets;
    private final Form<WidgetData> form;

    private HttpExecutionContext httpExecutionContext;

    /**
     * Constructor of WidgetController, initializes all the member variables.
     *
     * @param formFactory
     */
    @Inject
    public WidgetController(FormFactory formFactory, HttpExecutionContext ec) {
        this.form = formFactory.form(WidgetData.class);
        this.httpExecutionContext = ec;

        this.tweets = CompletableFuture.supplyAsync(() -> new ArrayList<Item>());  // initialize

        String []auths = new String[]{
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };
        AccountFactory factory = new TwitterAccountFactory();
        this.twitter = CompletableFuture.supplyAsync( () -> factory.createAccount(auths) );
    }

    /**
     * Reponse of 'Get /' request.
     * This method will render the main page with search form and search results.
     *
     * @return a future object of the main page rendered by index.scala.html
     */
    public CompletionStage<Result> index() {
        return tweets.thenApplyAsync(results -> 
            ok(views.html.index.render(form, asScala(results))), httpExecutionContext.current()
            );
        //return ok(views.html.index.render(form, asScala(tweets.join())));
    }


    /**
     * Reponse of 'Post /search' request.
     * This method will get the data of the input box and search tweets according to the keyword asynchronously
     *
     * @return a future oject of the main page. In other word, it call the index() method agian.
     */
    public CompletionStage<Result> search() {
        final Form<WidgetData> boundForm = form.bindFromRequest();
        WidgetData data = boundForm.get();

        tweets = twitter.thenApply(
                (Connection conn) -> conn.SearchPost(data.getKeyword(), 10)
        );

        //Logger message
        //play.Logger.ALogger logger = play.Logger.of(getClass());
        //logger.error(data.getKeyword());
        //logger.error(String.valueOf(twitter==null));
        //flash("info", "Widget Search!");

        return CompletableFuture.completedFuture(redirect(routes.WidgetController.index()));
    }

    /**
     * Reponse of `Get /userProfile/:id' request. It will fetch the user's homeline and generate a user profile web page.
     *
     * @param user_id the uesr's id
     * @return a future object of the user profile page rendered by userProfile.scala.html or error page if the id doesn't exist.
     */
    public CompletionStage<Result> userProfile(long user_id){
        UserBase user = TwitUserFactory.getInstance().getUserById(user_id);

        CompletableFuture<List<Item>> f_results = twitter.thenApply(
                (Connection conn) -> conn.getHomeLineById(user_id)
        );

        if(user!=null)
            return f_results.thenApplyAsync( results -> 
                ok(views.html.userProfile.render(user, asScala(results))), httpExecutionContext.current()
                );
        else
            return CompletableFuture.completedFuture( ok(views.html.error.render("User profile not found")) );
    }

}
