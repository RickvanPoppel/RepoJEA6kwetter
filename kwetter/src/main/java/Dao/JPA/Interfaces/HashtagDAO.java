package Dao.JPA.Interfaces;

import Domain.Hashtag;

import java.util.List;

public interface HashtagDAO extends GenericDAO<Hashtag> {
    Hashtag getByContent(String content);
    List<Hashtag> getMatchesByContent(String content);
}
