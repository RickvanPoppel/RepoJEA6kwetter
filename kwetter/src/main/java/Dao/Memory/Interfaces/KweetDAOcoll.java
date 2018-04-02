package Dao.Memory.Interfaces;

import Domain.Kweet;

import java.util.List;

public interface KweetDAOcoll {
    Kweet save(Kweet kweet);
    boolean delete(long id);
    Kweet get(long id);
    List<Kweet> getAll();
    List<Kweet> getMatchesByContent(String content);
    List<Kweet> getKweetByHashtagId(long id);
    List<Kweet> getKweetsByMentionId(long id);
    List<Kweet> getKweetsByKwetteraarId(long id);
    List<Kweet> getRecentOwnKweetsByUserId(long id);
}
