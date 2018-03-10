package lyc;

public class Item {
    public Item(UserBase user, String text){
        this.user = user;
        this.text = text;
    }

    public long getUser_id(){
        return user.getUser_id();
    }

    public String getUser_name(){
        return user.getUser_name();
    }

    public String getUser_link(){
        return user.getUser_link();
    }


    public String getText() {
        return text;
    }

    public String getRef_user() {
        return ref_user;
    }

    public void setText(String text){
        this.text = text;
    }
    public void setRef_user(String ref_user){
        this.ref_user = ref_user;
    }

    public UserBase getUser_profile() {
        return user;
    }

    private UserBase user;
    private String text;
    private String ref_user;
}
