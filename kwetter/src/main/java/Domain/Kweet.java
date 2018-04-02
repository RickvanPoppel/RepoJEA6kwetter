package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="table_kweet")
public class Kweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date date;

    @ManyToMany
    @JoinTable(name = "table_hashtag"
            ,   joinColumns = @JoinColumn(name = "kweet_hashtag_id", referencedColumnName = "id")
            ,   inverseJoinColumns = @JoinColumn(name = "hashtag_hashtag_id", referencedColumnName = "id"))
    private List<Hashtag> hashtags;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "t_kweet_user_mentions"
            , joinColumns = @JoinColumn(name = "kweet_mention_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "user_mention_id", referencedColumnName = "id"))
    private List<User> mentions;

    public Kweet(){
        hashtags = new ArrayList<>();
        mentions = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getMentions() {
        return mentions;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }

    public void addHashtag(Hashtag hashtag){
        if (hashtag != null && hashtags != null && !hashtags.contains(hashtag)) {
            hashtags.add(hashtag);
            if (!hashtag.getKweets().contains(this))
                hashtag.addKweet(this);
        }
    }
    public void addMention(User mention) {
        if (mention != null && mentions != null && !mentions.contains(mention)) {
            mentions.add(mention);
            if (!mention.getMentions().contains(this))
                mention.addMention(this);
        }
    }
}
