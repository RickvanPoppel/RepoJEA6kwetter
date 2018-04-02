package Dao.Memory.Interfaces;

import Domain.Hashtag;

import java.util.List;

public interface HashtagDAOcoll {
    Hashtag save(Hashtag hashtag);
    boolean delete(long id);
    Hashtag get(long id);
    List<Hashtag> getAll();
    Hashtag getByContent(String content);
    List<Hashtag> getMatchesByContent(String Content);
}
