package Services;

import Dao.JPA.Interfaces.KweetDAO;
import Domain.Kweet;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class baseKweetService {
    private KweetDAO kweetDAO;

    @Inject
    public baseKweetService(KweetDAO kDAO){
        this.kweetDAO = kDAO;
    }
    public List<Kweet> getKweets() {
        return kweetDAO.getAll();
    }

    public Kweet getKweet(long id) {
        return kweetDAO.find(id);
    }

    public List<Kweet> getMatchesByContent(String content) {
        return kweetDAO.getMatchesByContent(content);
    }

    public List<Kweet> getKweetByHashtagId(long id) {
        return kweetDAO.getKweetByHashtagId(id);
    }

    public List<Kweet> getKweetsByMentionId(long id) {
        List<Kweet> kweets = kweetDAO.getKweetsByMentionId(id);
        return kweets;
    }

    public List<Kweet> getRecenteEigenKweetsByUserId(long id) {
        List<Kweet> kweets = kweetDAO.getRecentOwnKweetsByUserId(id);
        Collections.reverse(kweets);
        return kweets;
    }

    public void deleteKweet(long id) {
        kweetDAO.delete(id);
    }

    public Kweet saveKweet(Kweet kweet) {
        return kweetDAO.create(kweet);
    }

    public Kweet updateKweet(Kweet kweet) {
        return kweetDAO.update(kweet);
    }

    public List<Kweet> getKweetsByUserId(long id) {
        return kweetDAO.getKweetsByUserId(id);
    }
}

