package lyc;

import twitter4j.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TwitConnection implements Connection {
    private Twitter twitter;
    private UserBase user;

    /**
     * The constructor of TwitConnection. An instance can only be instanced by the factory
     * @param t the new instance
     */
    protected TwitConnection(Twitter t) {
        twitter = t;
    }

    @Override
    public UserBase getCurrentUser(){
        if(user != null)
            return user;

        try {
            AccountSettings account = twitter.getAccountSettings();
            User twit_user = twitter.showUser(account.getScreenName());
            this.user = TwitUserFacotry.getInstance().getOrCreateUser(twit_user.getId(), twit_user.getName(), twit_user.getScreenName());

            return this.user;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Item> SearchPost(String keyword, int max_cnt) {
        final int finalMax_cnt;

        ArrayList<Item> results = new ArrayList<>();
        Query query = new Query(keyword);
        try {
            QueryResult q_result = twitter.search(query);
            List<Status> statuses = q_result.getTweets();

            int i = 0;
            for (Status status : statuses) {
                UserBase user = TwitUserFacotry.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText());

                results.add(result);
                if (++i == max_cnt)
                    break;
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }

    @Override
    public List<Item> getSelfHomeLine() {
        ArrayList<Item> results = new ArrayList<>();

        try {
            List<Status> statues = twitter.getHomeTimeline();
            for (Status status : statues) {
                UserBase user = TwitUserFacotry.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText());
                results.add(result);
            }

        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }

    @Override
    public List<Item> SearchPost(String keyword) {
        return SearchPost(keyword, -1);
    }

    @Override
    public List<Item> getHomeLineById(long user_id) {
        ArrayList<Item> results = new ArrayList<>();

        try {
            List<Status> statues = twitter.getUserTimeline(user_id);
            for (Status status : statues) {
                UserBase user = TwitUserFacotry.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText());
                results.add(result);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }


}
