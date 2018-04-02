package Dao.Memory.Implementations;

import Dao.Memory.Interfaces.KweetDAOcoll;
import Domain.Kweet;

import javax.enterprise.inject.Alternative;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@RequestScoped
@Alternative
public class KweetDAOcollImpl implements KweetDAOcoll {

    memCollectionObject im = memCollectionObject.getInstance();

    public KweetDAOcollImpl() {

    }

    @Override
    public Kweet save(Kweet kweet) {
        //Invalid, return.
        if (kweet == null || kweet.getContent() == null || kweet.getContent().isEmpty())
            return null;

        //Non existing, add id.
        if (kweet.getId() == 0L)
            kweet.setId(im.useKweetId());

        //Existing, update.
        if (kweet.getId() > 0L && im.getKweets().stream().filter(k->k.getId() == kweet.getId()).count() == 0) {
            im.getKweets().add(kweet);
            return kweet;
        }

        //Failed.
        return null;
    }

    @Override
    public boolean delete(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return false;

        Kweet kweet = im.getKweets().stream().filter(k->k.getId() == id).findAny().orElse(null);
        if (kweet != null)
            im.getKweets().remove(kweet);

        return true;
    }

    @Override
    public Kweet get(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return null;

        //Search and return, else return null.
        return im.getKweets().stream().filter(k->k.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<Kweet> getAll() {
        //Return all.
        return im.getKweets();
    }

    @Override
    public List<Kweet> getMatchesByContent(String content) {
        List<Kweet> kweets = new ArrayList<>();
        im.getKweets().forEach(k->{
            if (k.getContent().contains(content))
                kweets.add(k);
        });
        return kweets;
    }

    @Override
    public List<Kweet> getKweetByHashtagId(long id) {
        List<Kweet> kweets = new ArrayList<>();
        getAll().forEach(k->k.getHashtags().forEach(h->{
            if (h.getId() == id && !kweets.contains(k))
                kweets.add(k);
        }));
        return kweets;
    }

    @Override
    public List<Kweet> getKweetsByMentionId(long id) {
        List<Kweet> kweets = new ArrayList<>();
        getAll().forEach(k->k.getMentions().forEach(m->{
            if (m.getId() == id && !kweets.contains(k))
                kweets.add(k);
        }));
        return kweets;
    }

    @Override
    public List<Kweet> getKweetsByUserId(long id) {
        List<Kweet> kweets = new ArrayList<>();
        getAll().forEach(k->{
            if (k.getOwner().getId() == id && !kweets.contains(k))
                kweets.add(k);
        });
        return kweets;
    }

    @Override
    public List<Kweet> getRecentOwnKweetsByUserId(long id) {
        List<Kweet> kweets = getKweetsByUserId(id);
        int count = kweets.size();
        if(count > 10)
            count = 10;
        kweets.sort(comparing(k1 -> k1.getDate()));
        return kweets.subList(0, count);
    }
}
