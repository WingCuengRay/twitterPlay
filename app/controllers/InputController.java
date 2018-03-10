package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import views.html.*;
import lyc.*;
import java.util.List;
import java.lang.String;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InputController extends Controller {
    private Connection twitter;
    private final Form<WidgetData> keywordForm;

    @Inject
    public InputController(FormFactory formFactory){
        this.keywordForm = formFactory.form(WidgetData.class);

        String []auths = {
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };

        AccountFactory factory = new TwitterAccountFactory();
        twitter = factory.createAccount(auths);


    }


    public Result index(){
        return ok(views.html.index.render(keywordForm));
    }

    public Result search() {
        final Form<WidgetData> boundForm = keywordForm.bindFromRequest();

        WidgetData keyword = keywordForm.get();
    	List<Item> results = twitter.SearchPost("hello", 10);

        return redirect(routes.InputController.index());
    }
}