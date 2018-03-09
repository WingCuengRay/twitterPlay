package lyc;

import java.util.List;

public class TwitUserImpl extends UserBase {
    public TwitUserImpl(long user_id, String user_name, String user_screenName) {
        super(user_id, user_name, user_screenName);

        user_link = "www.twitter.com/" + user_screenName;
    }

}
