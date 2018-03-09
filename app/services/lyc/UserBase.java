package lyc;

import java.util.List;

public abstract class UserBase {

    protected long user_id;
    protected String user_link;
    protected String user_name;
    protected String user_screenName;

    public UserBase(long user_id, String user_name, String user_screenName) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_screenName = user_screenName;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_link() {
        return user_link;
    }

    public void setUser_link(String user_link) {
        this.user_link = user_link;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_screenName() {
        return user_screenName;
    }

    public void setUser_screenName(String user_screenName) {
        this.user_screenName = user_screenName;
    }

}
