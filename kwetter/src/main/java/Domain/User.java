package Domain;

import javax.persistence.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="table_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column
    private String profilePhoto;

    @Column
    private String bio;

    @Column
    private String eMail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
    private List<Kweet> kweets;

    @ManyToMany(mappedBy = "mentions", cascade = CascadeType.MERGE)
    private List<Kweet> mentions;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "table_user_user_followers"
            , joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
            , inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id", nullable = false))
    private List<User> followers;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "table_user_user_following"
            , joinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id", nullable = false)
            , inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false))
    private List<User> following;

    public User(){
        kweets = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        mentions = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String hashstring = null;
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if(hex.length() == 1)
                        hexString.append('0');
                    hexString.append(hex);
                }

                hashstring = hexString.toString();
            }
            catch (Exception x) {
                System.out.println(x);
            }
        this.password = (hashstring == null || hashstring.isEmpty()) ? password : hashstring;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public List<Kweet> getMentions() {
        return mentions;
    }

    public void setMentions(List<Kweet> mentions) {
        this.mentions = mentions;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void addKweet(Kweet kweet) {
        if (kweet != null && kweets != null && !kweets.contains(kweet)) {
            kweets.add(kweet);
            if (kweet.getOwner() != this)
                kweet.setOwner(this);
        }
    }

    public void removeKweet(Kweet kweet) {
        Kweet f = null;
        for(Kweet k : kweets){
            if(k.getId() == kweet.getId()){
                f = k;
            }
        }
        if (kweet != null && kweets != null && kweets.contains(f)) {
            kweets.remove(f);
            if (f.getOwner() != this)
                f.setOwner(null);
        }
    }

    public void addFollower(User follower) {
        if (follower != null && followers != null && !followers.contains(follower)) {
            followers.add(follower);
            if (!follower.getFollowing().contains(this))
                follower.addLeader(this);
        }
    }

    public void removeFollower(User follower) {
        if (follower != null && followers != null && followers.contains(follower)) {
            followers.remove(follower);
            if (follower.getFollowing().contains(this))
                follower.removeLeader(this);
        }
    }

    public void addLeader(User leader) {
        if (leader != null && following != null && !following.contains(leader)) {
            following.add(leader);
            if (!leader.getFollowers().contains(this))
                leader.addFollower(this);
        }
    }

    public void removeLeader(User leader) {
        if (leader != null && following != null && following.contains(leader)) {
            following.remove(leader);
            if (leader.getFollowers().contains(this))
                leader.removeFollower(this);
        }
    }

    public void addMention(Kweet mention) {
        if (mention != null && mentions != null && !mentions.contains(mention)) {
            mentions.add(mention);
            if (!mention.getMentions().contains(this))
                mention.addMention(this);
        }
    }
}
