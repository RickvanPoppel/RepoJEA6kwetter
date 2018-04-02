package Dao.JPA.Interfaces;

import Domain.User;

public interface UserDAO extends GenericDAO<User> {
    User getByUsername(String username);
    void addFollower(long id, long idLeader);
    void removeFollower(long id, long idLeader);
    void register(String username, String password);
    boolean login(String username, String password);
}
