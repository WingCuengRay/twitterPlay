package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static play.libs.Scala.asScala;
import lyc.*;


@Singleton
public class WidgetController extends Controller {
    private final CompletableFuture<Connection> twitter;
    private CompletableFuture<List<Item>> tweets;
    private final Form<WidgetData> form;

    @Inject
    public WidgetController(FormFactory formFactory) {
        this.form = formFactory.form(WidgetData.class);
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

    public Result index() {
        return ok(views.html.index.render(form, asScala(tweets.join())));
    }


    public Result search() {
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

        return redirect(routes.WidgetController.index());
    }

    public Result userProfile(long user_id){
        UserBase user = TwitUserFactory.getInstance().getUserById(user_id);

        CompletableFuture<List<Item>> results = twitter.thenApply(
                (Connection conn) -> conn.getHomeLineById(user_id)
        );

        if(user!=null)
            return ok(views.html.userProfile.render(user, asScala(results.join())));
        else
            return ok(views.html.error.render("User profile not found"));
    }

    public CompletableFuture<Result> testReturn(){
        return CompletableFuture.supplyAsync( () ->
             ok(views.html.error.render("User profile not found")) );
    }
}
