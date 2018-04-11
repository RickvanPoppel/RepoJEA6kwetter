package Services;

import Dao.JPA.Interfaces.AnnJPA;
import Dao.JPA.Interfaces.UserDAO;
import Domain.Kweet;
import Domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@AnnJPA @Stateless
public class baseUserService implements Serializable {

    @Inject @AnnJPA
    private UserDAO userDAO;

    public baseUserService(){

    }
    public List<User> getUsers(){
        return userDAO.getAll();
    }
    public User getUser(long id){
        return userDAO.find(id);
    }
    public User getUserByName(String name){
        return userDAO.getByUsername(name);
    }
    public void deleteUser(long id){
        userDAO.delete(id);
    }
    public List<Kweet> getKweets(long id){
        return userDAO.find(id).getKweets();
    }
    public List<User> getFollowers(long id){
        return userDAO.find(id).getFollowers();
    }
    public List<User> getFollowing(long id){
        return userDAO.find(id).getFollowing();
    }
    public void addFollower(long id, long idLeader){
        if(id != idLeader){
            userDAO.addFollower(id, idLeader);
        }
    }
    public void removeFollower(long id, long idLeader){
        if(id != idLeader){
            userDAO.removeFollower(id, idLeader);
        }
    }
    public void register(String username, String password){
        userDAO.register(username,password);
    }
    public boolean login(String username, String password){
        return userDAO.login(username,password);
    }
    public User saveUser(User user){
        return userDAO.create(user);
    }
    public User updateUser(User user){
        return userDAO.update(user);
    }
    public void deleteKweet(long id, long kweetId){
        getKweets(id).remove(getKweets(kweetId).stream().filter(k->k.getId() == kweetId).findAny().orElse(null));
    }
}
