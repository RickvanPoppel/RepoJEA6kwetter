package Services;

import Dao.JPA.Interfaces.AnnJPA;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

import static java.util.Comparator.comparing;

@Stateless
public class KwetterService implements Serializable{

    @Inject @AnnJPA
    private baseHashtagService baseHashtagService;
    @Inject @AnnJPA
    private baseUserService baseUserService;
    @Inject @AnnJPA
    private baseKweetService baseKweetService;

    public KwetterService(){

    }

    //data methods
    public void changeProfilePhoto(long id, String picURL){
        User user = baseUserService.getUser(id);
        user.setProfilePhoto(picURL);
        baseUserService.updateUser(user);
    }
    public void changeUsername(long id, String username){
        User user = baseUserService.getUser(id);
        user.setUserName(username);
        baseUserService.updateUser(user);
    }
    public void changeBio(long id, String bio){
        User user = baseUserService.getUser(id);
        user.setBio(bio);
        baseUserService.updateUser(user);
    }
    public void changeRole(long id, String newRole){
        User user = baseUserService.getUser(id);
        user.setRole(newRole);
        baseUserService.updateUser(user);
    }
    public Kweet sendKweet(long id, String content){
        Kweet kweet = new Kweet();
        kweet.setContent(content);
        kweet.setOwner(baseUserService.getUser(id));
        kweet.setDate(new Date());
        baseKweetService.saveKweet(kweet);

        List<Hashtag> hashtags = findHashtags(content);
        hashtags.forEach(h -> {
            if(h != null){
                kweet.addHashtag(h);
            }
        });
        List<User> mentions = findMentions(content);
        mentions.forEach(m -> {
            if(m != null){
                kweet.addMention(m);
            }
        });
        return baseKweetService.updateKweet(kweet);
    }
    public List<Kweet> getOwnAndFollowingKweets(long id){
        List<Kweet> kweets = new ArrayList<>();
        List<User> leaders = baseUserService.getFollowing(id);
        for(int i = 0; i < leaders.size(); i++){
            User user = leaders.get(i);
            List<Kweet> kweetList = user.getKweets();
            kweets.addAll(kweetList);
        }

        kweets.addAll(baseUserService.getKweets(id));
        int count = kweets.size();
        if(count > 20){
            count = 20;
        }
        kweets.sort(comparing(Kweet::getDate));
        Collections.reverse(kweets);
        return kweets.subList(0, count);
    }
    public List<Kweet> getHashtagTrends(String hashtagContent){
        Hashtag hashtag = baseHashtagService.getExactlyMatchingHashtag(hashtagContent);
        List<Kweet> trends = new ArrayList<>();
        if(hashtag != null){
            trends = baseKweetService.getKweetByHashtagId(hashtag.getId());
        }
        return trends;
    }
    public void deleteKweet(long id){
        baseUserService.deleteKweet(baseKweetService.getKweet(id).getOwner().getId(), id);
        baseKweetService.deleteKweet(id);
    }

    //service get
    public baseHashtagService getBaseHashtagService() {
        return baseHashtagService;
    }
    public baseKweetService getBaseKweetService(){
        return baseKweetService;
    }
    public baseUserService getBaseUserService(){
        return baseUserService;
    }


    //functional methods
    public List<Hashtag> findHashtags(String content) {
        List<Hashtag> hashtags = new ArrayList<>();
        int count = content.length() - content.replace("#", "").length();
        for (int i = 0; i < count; i++) {
            if (content.contains("#")) {
                int startPos = content.indexOf('#');
                content = content.substring(startPos);
                int endPos = content.substring(1).indexOf(' ');
                String hashtagContent;
                if (endPos > -1) {
                    hashtagContent = content.substring(0, endPos + 1);
                    content = content.substring(endPos + 1);
                } else {
                    hashtagContent = content;
                    content = "";
                }
                if (baseHashtagService.getExactlyMatchingHashtag(hashtagContent.substring(1)) == null)
                    baseHashtagService.addHashtag(hashtagContent.substring(1));

                if (hashtags.stream().filter(h->h.getId() == baseHashtagService.getExactlyMatchingHashtag(hashtagContent.substring(1)).getId()).findAny().orElse(null) == null)
                    hashtags.add(baseHashtagService.getExactlyMatchingHashtag(hashtagContent.substring(1)));

            }
        }
        return hashtags;
    }
    public List<User> findMentions(String content){
        List<User> mentions = new ArrayList<>();
        int count = content.length() - content.replace("@", "").length();
        for (int i = 0; i < count; i++) {
            if (content.contains("@")) {
                int startPos = content.indexOf('@');
                content = content.substring(startPos);
                int endPos = content.substring(1).indexOf(' ');
                String mentionName;
                if (endPos > -1) {
                    mentionName = content.substring(0, endPos + 1);
                    content = content.substring(endPos + 1);
                } else {
                    mentionName = content;
                    content = "";
                }
                mentions.add(baseUserService.getUserByName(mentionName.substring(1)));
            }
        }
        return mentions;
    }
}
