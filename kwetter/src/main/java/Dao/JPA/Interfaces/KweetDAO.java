package Dao.JPA.Interfaces;

import Domain.Kweet;

import java.util.List;

public interface KweetDAO extends GenericDAO<Kweet> {
    List<Kweet> getMatchesByContent(String Content);
    List<Kweet> getKweetByHashtagId(long id);
    List<Kweet> getKweetsByMentionId(long id);
    List<Kweet> getKweetsByUserId(long id);
    List<Kweet> getRecentOwnKweetsByUserId(long id);
}
