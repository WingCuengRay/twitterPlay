package lyc;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TwitterAccountFactoryTest {
    @Test
    public void TestCreateMethod() {
        String []valid_auths = new String[]{
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };
        String []invalid_auths = new String[]{
                "123",
                "4556",
                "789",
                "890"
        };


        TwitterAccountFactory t_factory = new TwitterAccountFactory();
        Connection valid_account = t_factory.createAccount(valid_auths);
        Connection invalid_account = t_factory.createAccount(invalid_auths);

        assertNotNull(valid_account);
        assertNull(invalid_account);
    }
}