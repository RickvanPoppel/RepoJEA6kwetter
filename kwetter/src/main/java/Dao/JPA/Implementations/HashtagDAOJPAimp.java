package Dao.JPA.Implementations;

import Dao.JPA.Interfaces.HashtagDAO;
import Domain.Hashtag;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class HashtagDAOJPAimp  extends GenericDAOJPAimp<Hashtag> implements HashtagDAO {

    public HashtagDAOJPAimp() {
        
    }

    @Override
    public Hashtag getByContent(String content) {
        if (content == null || content.isEmpty())
            return null;

        try {
            return (Hashtag) em.createQuery("select h from Hashtag h where h.content = '" + content + "'").getSingleResult();
        }
        catch (Exception x) {
            System.out.println(x.getMessage());
            return null;
        }
    }

    @Override
    public List<Hashtag> getMatchesByContent(String content) {
        if (content == null || content.isEmpty())
            return new ArrayList<>();

        try {
            return (List<Hashtag>) em.createQuery("select h from Hashtag h where h.content like '%" + content + "%'").getResultList();
        }
        catch (Exception x) {
            System.out.println(x.getMessage());
            return new ArrayList<>();
        }
    }
}
