package Dao.JPA.Implementations;

import Dao.JPA.Interfaces.AnnJPA;
import Dao.JPA.Interfaces.KweetDAO;
import Domain.Kweet;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;

@Stateless
@AnnJPA
public class KweetDAOJPAimp extends GenericDAOJPAimp<Kweet> implements KweetDAO {
    public KweetDAOJPAimp() {

    }

    @Override
    public List<Kweet> getMatchesByContent(String Content) {
        if(Content == null){
            return new ArrayList<>();
        }
        return getListByQuery("select k from Kweet k where k.content LIKE '%" + Content + "%'");
    }

    @Override
    public List<Kweet> getKweetByHashtagId(long id) {
        if (id >= 0)
            return getListByQuery("select k from Kweet k where k.id = (select x.kweet_hashtag_id from table_hashtag x where x.hashtag_hashtag_id = " + id + ")");
        return new ArrayList<>();
    }

    @Override
    public List<Kweet> getKweetsByMentionId(long id) {
        if (id >= 0) {
            return getListByQuery("select k from Kweet k where k.content LIKE '%@ (select user.username from table_user user where user.id = " + id + ") %'");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Kweet> getKweetsByUserId(long id) {
        if (id >= 0) {
            return getListByQuery("select k from Kweet k where k.owner.id = " + id);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Kweet> getRecentOwnKweetsByUserId(long id) {
        if (id >= 0) {
            return getListByQuery("select k from Kweet k where k.owner.id = " + id + " order by k.date desc");
        }
        return new ArrayList<>();
    }

    public List<Kweet> getListByQuery(String query) {
        if (query == null || query.isEmpty())
            return new ArrayList<>();

        try {
            return (List<Kweet>) em.createQuery(query).getResultList();
        }
        catch (Exception x) {
            System.out.println(x.getMessage());
            return new ArrayList<>();
        }
    }
}
