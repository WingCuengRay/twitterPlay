package lyc;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Connection{
    /**
     * Search the most recent posts by keyword
     *
     * @param keyword the keyword that you use to search posts
     * @return a list of item as result, null if failure
     */
    public List<Item> SearchPost(String keyword);

    /**
     * Search the most recent posts by keyword and the number of posts will be limited by max_cntg
     *
     * @param keyword the keyword that you use to search posts
     * @param max_cnt maximum number of posts returned
     * @return  a list of item as results, null if failure
     */
    public List<Item> SearchPost(String keyword, int max_cnt);

    /**
     * Get the homeline of current user/account
     *
     * @return the list of post that in the user's homeline, null if failure
     */
    public List<Item> getSelfHomeLine();

    /**
     * Get the homeline of specific user by id
     *
     * @param id the user's id
     * @return a list of posts of this user, null if failure
     */
    public List<Item> getHomeLineById(long id);

    /**
     * Get the user that create this connection
     *
     * @return the user corresponding to auths
     */
    public UserBase getCurrentUser();
}

