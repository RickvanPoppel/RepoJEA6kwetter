package Dao.Memory.Interfaces;

import Domain.User;

import java.util.List;

public interface UserDAOcoll {
    User save(User user);
    boolean delete(long id);
    User get(long id);
    List<User> getAll();
    User getByUsername(String userName);
    void addFollower(long id, long idLeader);
    void register(String userName, String password);
    boolean login(String userName, String password);
}
