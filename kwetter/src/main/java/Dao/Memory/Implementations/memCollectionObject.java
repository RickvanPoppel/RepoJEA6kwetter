package Dao.Memory.Implementations;

import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;

import javax.faces.bean.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class memCollectionObject {

    private static memCollectionObject im = new memCollectionObject();
    protected List<Kweet> kweets;
    protected List<Hashtag> hashtags;
    protected List<User> users;
    protected long kweetId;
    protected long hashtagId;
    protected long userId;

    private memCollectionObject () {
        clearMemory();
    }

    protected static memCollectionObject getInstance(){
        return im;
    }

    protected List<Kweet> getKweets() {
        return kweets;
    }

    protected List<Hashtag> getHashtags() {
        return hashtags;
    }

    protected List<User> getUsers() {
        return users;
    }

    protected long useHashtagId() {
        long temp = hashtagId;
        hashtagId++;
        return temp;
    }

    protected long useKweetId() {
        long temp = kweetId;
        kweetId++;
        return temp;
    }

    protected long useUserId() {
        long temp = userId;
        userId++;
        return temp;
    }

    protected void clearMemory() {
        kweets = new ArrayList<>();
        hashtags = new ArrayList<>();
        users = new ArrayList<>();
        kweetId = 1;
        hashtagId = 1;
        userId = 1;
    }
}
