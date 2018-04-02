package Dao.Memory.Implementations;

import Dao.Memory.Interfaces.UserDAOcoll;
import Domain.User;

import javax.enterprise.inject.Alternative;
import javax.faces.bean.RequestScoped;
import java.util.List;

@RequestScoped
@Alternative
public class UserDAOcollImpl implements UserDAOcoll {
    memCollectionObject im = memCollectionObject.getInstance();

    public UserDAOcollImpl() {

    }

    @Override
    public User save(User user) {
        //Invalid, return.
        if (user == null || user.getUserName() == null || user.getUserName().isEmpty())
            return null;

        //Non existing, add id.
        if (user.getId() == 0L)
            user.setId(im.useUserId());

        //Existing, update.
        if (user.getId() > 0L && im.getUsers().stream().filter(k->k.getId() == user.getId()).count() == 0) {
            im.getUsers().add(user);
            return user;
        }

        //Failed.
        return null;
    }

    @Override
    public boolean delete(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return false;

        User user = im.getUsers().stream().filter(k->k.getId() == id).findAny().orElse(null);
        if (user != null)
            im.getUsers().remove(user);

        return true;
    }

    @Override
    public User get(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return null;

        //Search and return, else return null.
        return im.getUsers().stream().filter(k->k.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<User> getAll() {
        //Return all.
        return im.getUsers();
    }

    @Override
    public User getByUsername(String name) {
        return im.getUsers().stream().filter(k->k.getUserName().equals(name)).findAny().orElse(null);
    }

    @Override
    public void addFollower(long id, long idLeader) {
        get(idLeader).addFollower(get(id));
    }

    @Override
    public void register(String Username, String password) {
        User user = new User();
        user.setUserName(Username);
        user.setPassword(password);
        save(user);
    }

    @Override
    public boolean login(String username, String password) {
        if(getByUsername(username) != null)
            return getByUsername(username).getPassword().equals(password);
        return false;
    }
}
