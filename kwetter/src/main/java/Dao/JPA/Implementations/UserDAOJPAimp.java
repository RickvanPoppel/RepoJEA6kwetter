package Dao.JPA.Implementations;

import Dao.JPA.Interfaces.AnnJPA;
import Dao.JPA.Interfaces.UserDAO;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless
@AnnJPA
public class UserDAOJPAimp  extends GenericDAOJPAimp<User> implements UserDAO {

    public UserDAOJPAimp(){

    }

    @Override
    public User getByUsername(String username) {
        if(username == null || username.isEmpty()){
            return null;
        }
        try{
            return (User) em.createQuery("select k from User k where k.userName = '"+ username + "'").getSingleResult();
        }catch(Exception x){
            System.out.println(x.getMessage());
            return null;
        }
    }

    @Override
    public void addFollower(long id, long idLeader) {
        if(id >= 0 && idLeader >= 0){
            try{
                User F = find(id);
                User L = find(idLeader);
                L.addFollower(F);
                update(F);
                update(L);
            }catch(Exception x){
                System.out.println(x.getMessage());
            }
        }
    }

    @Override
    public void removeFollower(long id, long idLeader) {
        if(id >= 0 && idLeader >= 0){
            try{
                User F = find(id);
                User L = find(idLeader);
                L.removeFollower(F);
                update(F);
                update(L);
            }catch(Exception x){
                System.out.println(x.getMessage());
            }
        }
    }

    @Override
    public void register(String username, String password) {
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
        }
    }

    @Override
    public boolean login(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        try {
            User u = (User) em.createQuery("select k from User k where k.userName = '" + username + "' and k.password = '" + password + "'").getSingleResult();
            if (u != null) {
                return true;
            }
            return false;
        }
        catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }
}
