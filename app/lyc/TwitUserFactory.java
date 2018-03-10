package lyc;

import java.util.HashMap;

public class TwitUserFactory {
    private static TwitUserFactory ourInstance = new TwitUserFactory();
    public static TwitUserFactory getInstance() {
        return ourInstance;
    }

    private TwitUserFactory() {
        cache = new HashMap<>();
    }

    /**
     * Get a user from cache or create on if it doesn't exist.
     *
     * @param user_id the user's id
     * @param user_name the user's name
     * @param user_screenName the user's screenName, always as same as the user name
     * @return a TwitUserInstance
     */
    public UserBase getOrCreateUser(long user_id, String user_name, String user_screenName){
        UserBase user = cache.get(user_id);
        if(user != null)
            return user;

        user = new TwitUserImpl(user_id, user_name, user_screenName);
        cache.put(user_id, user);

        return user;
    }

    public UserBase getUserById(long user_id){
        UserBase user = cache.get(user_id);

        return user;
    }

    private HashMap<Long, UserBase> cache;

}
