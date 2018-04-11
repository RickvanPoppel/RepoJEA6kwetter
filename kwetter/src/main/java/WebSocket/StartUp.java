package WebSocket;

import Domain.Kweet;
import Domain.User;
import Services.KwetterService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup @Singleton
public class StartUp {

    @Inject
    private KwetterService Ks;

    public StartUp(){

    }

    @PostConstruct
    private void initData(){

        //create users
        User user1 = new User();
        user1.setUserName("Rick");
        user1.setProfilePhoto("rick.png");
        user1.setBio("the best");
        user1.seteMail("rick@rick.nl");
        user1.setPassword("12345");
        user1.setRole("admin");
        user1 = Ks.getBaseUserService().saveUser(user1);

        User user2 = new User();
        user2.setUserName("Dennis");
        user2.setProfilePhoto("dennis.jpg");
        user2.setBio("bietje rechts");
        user2.seteMail("dennis@dennis.nl");
        user2.setPassword("123452");
        user2.setRole("user");
        user2 = Ks.getBaseUserService().saveUser(user2);

        User user3 = new User();
        user3.setUserName("Nick");
        user3.setProfilePhoto("nick.bmp");
        user3.setBio("niet dennis");
        user3.seteMail("nick@nick.nl");
        user3.setPassword("312414");
        user3.setRole("user");
        user3 = Ks.getBaseUserService().saveUser(user3);

        //create kweets
        long id = Ks.getBaseUserService().getUserByName("Nick").getId();
        String content1 = "this is a kweet";
        Ks.sendKweet(id, content1);

        long id1 = Ks.getBaseUserService().getUserByName("Dennis").getId();
        String content2 = "this also is a kweet";
        Ks.sendKweet(id1, content2);
    }
}
