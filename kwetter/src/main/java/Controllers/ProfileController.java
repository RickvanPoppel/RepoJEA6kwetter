package Controllers;

import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Services.KwetterService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Comparator.comparing;

@ManagedBean(name = "ProfileController", eager = true)
@ViewScoped
public class ProfileController {
    private KwetterService ks;
    private User user;
    private String newKweetContent;
    private List<Kweet> kweetList;
    private String kweetSearchString;
    private String getRequestUsername;
    private String getRequestUserID;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ProfileController(){
        //bean
    }

    public void onload(){
        try{
            if(getRequestUserID != null && !getRequestUserID.isEmpty()){
                long UserID = Long.parseLong(getRequestUserID);
                user = ks.getBaseUserService().getUser(UserID);
            }
            if(getRequestUsername != null && !getRequestUsername.isEmpty()){
                user = ks.getBaseUserService().getUserByName(getRequestUsername);
            }
            if(user == null){
                user = ks.getBaseUserService().getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
                getTimeline();
            }
        }
        catch(Exception ex){
            logger.severe("Profile loading error: " + ex.getMessage());
        }
    }

    @Inject
    public void setKwetterService(KwetterService ks){this.ks = ks;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNewKweetContent() {
        return newKweetContent;
    }

    public void setNewKweetContent(String newKweetContent) {
        this.newKweetContent = newKweetContent;
    }

    public Kweet getLastKweet(){
        List<Kweet> kweets = user.getKweets();
        kweets.sort(comparing(Kweet::getDate));
        return kweets.get(kweets.size()-1);
    }
    public void getTimeline(){kweetList = ks.getOwnAndFollowingKweets(user.getId());}

    public void getMentionKweetList(){
        kweetList = ks.getBaseKweetService().getKweetsByMentionId(user.getId());
    }

    public List<Hashtag> getAllTrends(){
        List<Hashtag> hashtags = ks.getBaseHashtagService().getHashtags();
        return hashtags;
    }

    public void sendKweet(){
        ks.sendKweet(user.getId(), newKweetContent);
    }

    public void followUser(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String userIDString = params.get("userID");
        logger.info(userIDString);
        long userID = Long.parseLong(userIDString);
        User loggedInUser = ks.getBaseUserService().getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        ks.getBaseUserService().addFollower(loggedInUser.getId(), userID);
    }

    public List<Kweet> getRecentKweets(){
        List<Kweet> kweets = ks.getBaseKweetService().getRecenteEigenKweetsByUserId(user.getId());
        return kweets;
    }

    public void setKweetList(List<Kweet> kweetList) {
        this.kweetList = kweetList;
    }

    public String getKweetSearchString() {
        return kweetSearchString;
    }

    public void setKweetSearchString(String kweetSearchString) {
        this.kweetSearchString = kweetSearchString;
    }

    public void searchKweets(){
        List<Kweet> kweets = ks.getBaseKweetService().getMatchesByContent(kweetSearchString);
        setKweetSearchString(null);
        kweetList = kweets;
    }

    public String getGetRequestUsername() {
        return getRequestUsername;
    }

    public void setGetRequestUsername(String getRequestUsername) {
        this.getRequestUsername = getRequestUsername;
    }

    public String getGetRequestUserID() {
        return getRequestUserID;
    }

    public void setGetRequestUserID(String getRequestUserID) {
        this.getRequestUserID = getRequestUserID;
    }
}
