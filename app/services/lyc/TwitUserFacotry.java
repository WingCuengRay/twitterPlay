package lyc;

import java.util.HashMap;

public class TwitUserFacotry {
    private static TwitUserFacotry ourInstance = new TwitUserFacotry();
    public static TwitUserFacotry getInstance() {
        return ourInstance;
    }

    private TwitUserFacotry() {
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

    private HashMap<Long, UserBase> cache;

}
