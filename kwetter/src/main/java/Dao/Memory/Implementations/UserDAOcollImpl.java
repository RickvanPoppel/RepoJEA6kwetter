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
        // Empty constructor for dependency injection purposes.
    }

    @Override
    public User save(User kwetteraar) {
        //Invalid, return.
        if (kwetteraar == null || kwetteraar.getUserName() == null || kwetteraar.getUserName().isEmpty())
            return null;

        //Non existing, add id.
        if (kwetteraar.getId() == 0L)
            kwetteraar.setId(im.useUserId());

        //Existing, update.
        if (kwetteraar.getId() > 0L && im.getUsers().stream().filter(k->k.getId() == kwetteraar.getId()).count() == 0) {
            im.getUsers().add(kwetteraar);
            return kwetteraar;
        }

        //Failed.
        return null;
    }

    @Override
    public boolean delete(long id) {
        //Invalid id, return.
        if (id < 0L || id == 0L)
            return false;

        User kwetteraar = im.getUsers().stream().filter(k->k.getId() == id).findAny().orElse(null);
        if (kwetteraar != null)
            im.getUsers().remove(kwetteraar);

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
    public void addFollower(long id, long idLeider) {
        get(idLeider).addFollower(get(id));
    }

    @Override
    public void register(String Username, String password) {
        User kwetteraar = new User();
        kwetteraar.setUserName(Username);
        kwetteraar.setPassword(password);
        save(kwetteraar);
    }

    @Override
    public boolean login(String username, String wachtwoord) {
        if(getByUsername(username) != null)
            return getByUsername(username).getPassword().equals(wachtwoord);
        return false;
    }
}
