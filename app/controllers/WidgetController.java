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

import static play.libs.Scala.asScala;
import lyc.*;
/**
 * An example of form processing.
 *
 * https://playframework.com/documentation/latest/JavaForms
 */
@Singleton
public class WidgetController extends Controller {
    private Connection twitter;
    private final Form<WidgetData> form;
    private List<Item> tweets;

    @Inject
    public WidgetController(FormFactory formFactory) {
        this.form = formFactory.form(WidgetData.class);
        this.tweets = new ArrayList<Item>();

        String []auths = new String[]{
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };
        AccountFactory factory = new TwitterAccountFactory();
        twitter = factory.createAccount(auths);
    }

    public Result index() {
        return ok(views.html.index.render(form, asScala(tweets)));
    }


    public Result search() {


        final Form<WidgetData> boundForm = form.bindFromRequest();
        WidgetData data = boundForm.get();

        tweets = twitter.SearchPost(data.getKeyword(), 10);

        //Logger message
        //play.Logger.ALogger logger = play.Logger.of(getClass());
        //logger.error(data.getKeyword());
        //logger.error(String.valueOf(twitter==null));
        //flash("info", "Widget Search!");

        return redirect(routes.WidgetController.index());
    }

    public Result userProfile(long user_id){
        UserBase user = TwitUserFactory.getInstance().getUserById(user_id);
        List<Item> results = twitter.getHomeLineById(user_id);

        if(user!=null)
            return ok(views.html.userProfile.render(user, asScala(results)));
        else
            return ok(views.html.error.render("User profile not found"));
    }
}
