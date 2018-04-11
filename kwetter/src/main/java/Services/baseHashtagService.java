package Services;

import Dao.JPA.Interfaces.AnnJPA;
import Dao.JPA.Interfaces.HashtagDAO;
import Domain.Hashtag;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@AnnJPA @Stateless
public class baseHashtagService implements Serializable {

    @Inject @AnnJPA
    private HashtagDAO hashtagDAO;


    public baseHashtagService(){

    }

    public List<Hashtag> getHashtags(){
        return hashtagDAO.getAll();
    }
    public Hashtag getHashtag(long id){
        return hashtagDAO.find(id);
    }
    public Hashtag addHashtag(String content){
        Hashtag hashtag = getExactlyMatchingHashtag(content);
        if(hashtag == null){
            hashtag = new Hashtag();
            hashtag.setContent(content);
            hashtag = hashtagDAO.update(hashtag);
        }
        return hashtag;
    }
    public Hashtag updateHashtag(long id, String content){
        Hashtag hashtag = getHashtag(id);
        hashtag.setContent(content);
        return hashtagDAO.update(hashtag);
    }

    public void deleteHashtag(long id) {
        hashtagDAO.delete(id);
    }

    public Hashtag getExactlyMatchingHashtag(String content) {
        return hashtagDAO.getByContent(content);
    }

    public List<Hashtag> getMatchingHashtags(String content) {
        return hashtagDAO.getMatchesByContent(content);
    }
}
