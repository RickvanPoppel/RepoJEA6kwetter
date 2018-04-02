package Dao.Memory.Implementations;

import Dao.Memory.Interfaces.HashtagDAOcoll;
import Domain.Hashtag;

import javax.enterprise.inject.Alternative;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Alternative
public class HashtagDAOcollImpl implements HashtagDAOcoll {

    memCollectionObject im = memCollectionObject.getInstance();

    public HashtagDAOcollImpl () {

    }

    @Override
    public Hashtag save(Hashtag hashtag) {
        //Invalid hashtag, return.
        if (hashtag == null || hashtag.getContent() == null || hashtag.getContent().isEmpty())
            return null;

        //Non existing hashtag, add id.
        if (hashtag.getId() == 0L)
            hashtag.setId(im.useHashtagId());

        //Existing hashtag, update.
        if (hashtag.getId() > 0L && im.getHashtags().stream().filter(h->h.getId() == hashtag.getId()).count() == 0) {
            im.getHashtags().add(hashtag);
            return hashtag;
        }

        //Failed.
        return null;
    }

    @Override
    public boolean delete(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return false;

        Hashtag hashtag = im.getHashtags().stream().filter(h->h.getId() == id).findAny().orElse(null);
        if (hashtag != null)
            im.getHashtags().remove(hashtag);

        return true;
    }

    @Override
    public Hashtag get(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return null;

        //Search and return, else return null.
        return im.getHashtags().stream().filter(h->h.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<Hashtag> getAll() {
        //Return all.
        return im.getHashtags();
    }

    @Override
    public Hashtag getByContent(String inhoud) {
        return im.getHashtags().stream().filter(h->h.getContent().equals(inhoud)).findAny().orElse(null);
    }

    @Override
    public List<Hashtag> getMatchesByContent(String inhoud) {
        List<Hashtag> hashtags = new ArrayList<>();
        im.getHashtags().forEach(h->{
            if (h.getContent().contains(inhoud))
                hashtags.add(h);
        });
        return hashtags;
    }
}
