package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="table_hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @ManyToMany(mappedBy = "hashtags", cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    private List<Kweet> kweets;

    public Hashtag() {
        kweets = new ArrayList<>();
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

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public void addKweet(Kweet kweet){
        if (kweet != null && kweets != null && !kweets.contains(kweet)) {
            kweets.add(kweet);
            if (!kweet.getHashtags().contains(this))
                kweet.addHashtag(this);
        }
    }
}
