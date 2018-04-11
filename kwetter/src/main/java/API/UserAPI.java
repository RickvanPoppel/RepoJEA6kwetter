package API;

import DTO.DTOConvert;
import DTO.UserDTO;
import DTO.detUserDTO;
import Domain.User;
import Services.KwetterService;
import WebSocket.SessionLister;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
public class UserAPI {

    private KwetterService Ks;

    @Inject
    public UserAPI(KwetterService ks) {
        this.Ks = ks;
    }

    @GET
    @Path("/get/roleforuser/{name}")
    @Produces(APPLICATION_JSON)
    public String getRole(@PathParam("name") String name, @Context HttpServletResponse r) {
        r.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUserByName(name);
        return user.getRole();
    }

    @GET
    @Path("/get/one/id/{id}")
    @Produces(APPLICATION_JSON)
    public detUserDTO getUserById(@PathParam("id") long id, @Context HttpServletResponse r) {
        r.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUser(id);
        detUserDTO udto = new detUserDTO();
        if (user != null) {
            udto.generateDTO(user);
        }
        return udto;
    }

    private List<UserDTO> userListToDTO(List<User> UserList) {
        List<UserDTO> UserDTOList = new ArrayList<>();
        DTOConvert.generateUserDTOList(UserList, UserDTOList);
        return UserDTOList;
    }

    @GET
    @Path("/get/more")
    @Produces(APPLICATION_JSON)
    public List<UserDTO> getAllUsers(@Context HttpServletResponse r) {
        r.setHeader("Access-Control-Allow-Origin", "*");
        return userListToDTO(Ks.getBaseUserService().getUsers());
    }

    @GET
    @Path("/get/one/name/{name}")
    @Produces(APPLICATION_JSON)
    public detUserDTO getByname(@PathParam("name") String name, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUserByName(name);
        detUserDTO udto = new detUserDTO();
        if (user != null) {
            udto.generateDTO(user);
        }
        return udto;

    }

    @GET
    @Path("/get/more/userId/following/{id}")
    @Produces(APPLICATION_JSON)
    public List<UserDTO> getUserFollowingById(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return userListToDTO(Ks.getBaseUserService().getFollowing(id));
    }

    @GET
    @Path("/get/more/userId/followers/{id}")
    @Produces(APPLICATION_JSON)
    public List<UserDTO> getUserFollowerById(@PathParam("id") long id, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        return userListToDTO(Ks.getBaseUserService().getFollowers(id));
    }

    @POST
    @Path("/post/insertUser")
    @Produces(APPLICATION_JSON)
    public detUserDTO addUser(
            @FormParam("name") String name
            , @FormParam("photo") String photo
            , @FormParam("bio") String bio
            , @FormParam("email") String email
            , @FormParam("role") String roleTitle
            , @FormParam("password") String password, @Context HttpServletResponse response) {
                response.setHeader("Access-Control-Allow-Origin" , "*");
                User user = new User();
                user.setUserName(name);
                user.setProfilePhoto(photo);
                user.setBio(bio);
                user.seteMail(email);
                user.setPassword(password);
                user.setRole(roleTitle);
                user = Ks.getBaseUserService().saveUser(user);
                detUserDTO udto = new detUserDTO();
                if(user != null){
                    udto.generateDTO(user);
                }
                return udto;
    }

    @POST
    @Path("/post/switchRole")
    @Produces(APPLICATION_JSON)
    public detUserDTO switchRole(
            @FormParam("name") String name, @FormParam("role") String role, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        User user = Ks.getBaseUserService().getUserByName(name);

        if (user != null) {
            user.setRole(role);
            Ks.getBaseUserService().updateUser(user);
        }

        detUserDTO udto = new detUserDTO();
        if (user != null) {
            udto.generateDTO(user);
        }
        return udto;
    }

    @POST
    @Path("/post/follow")
    @Produces(APPLICATION_JSON)
    public detUserDTO addFollower(@FormParam("nameFollower") String nameFollower, @FormParam("nameLeader") String nameLeader, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        detUserDTO udto = new detUserDTO();
        User follower = Ks.getBaseUserService().getUserByName(nameFollower);
        User leader = Ks.getBaseUserService().getUserByName(nameLeader);
        if(follower != null && leader != null){
            Ks.getBaseUserService().addFollower(follower.getId(), leader.getId());
            udto.generateDTO(follower);
        }
        return udto;
    }
    @POST
    @Path("/post/stopFollow")
    @Produces(APPLICATION_JSON)
    public detUserDTO delFollower(@FormParam("nameFollower") String nameFollower, @FormParam("nameLeader") String nameLeader, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        detUserDTO udto = new detUserDTO();
        User follower = Ks.getBaseUserService().getUserByName(nameFollower);
        User leader = Ks.getBaseUserService().getUserByName(nameLeader);
        if(follower != null && leader != null){
            Ks.getBaseUserService().removeFollower(follower.getId(), leader.getId());
            udto.generateDTO(follower);
        }
        return udto;
    }
    @POST
    @Path("/post/changeprofilepicture")
    @Produces(APPLICATION_JSON)
    public detUserDTO changeProfilePhoto(@FormParam("id") int id, @FormParam("newPic") String pic, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUser(id);
        detUserDTO udto = new detUserDTO();
        if (user != null) {
            Ks.changeProfilePhoto(id, pic);
            udto.generateDTO(user);
        }
        return udto;

    }
    @POST
    @Path("/post/changeBio")
    @Produces(APPLICATION_JSON)
    public detUserDTO changeBio(@FormParam("id") int id, @FormParam("newbio") String newBio, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUser(id);
        detUserDTO udto = new detUserDTO();
        if (user != null) {
            Ks.changeBio(id, newBio);
            udto.generateDTO(user);
        }
        return udto;

    }
    @POST
    @Path("/post/login")
    @Produces(APPLICATION_JSON)
    public detUserDTO login(@FormParam("name") String name, @FormParam("password") String password, @Context HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin" , "*");
        String hashstring = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            hashstring = hexString.toString();
        }
        catch (Exception x) {
            System.out.println(x);
        }
        String hashedPassword = (hashstring == null || hashstring.isEmpty()) ? password : hashstring;
        if(Ks.getBaseUserService().login(name, hashedPassword)){
            User user = Ks.getBaseUserService().getUserByName(name);

            if (!SessionLister.getInstance().getActiveUsers().contains(name))
                SessionLister.getInstance().getActiveUsers().add(name);

            detUserDTO kdto = new detUserDTO();
            if (user != null)
                kdto.generateDTO(user);
            return kdto;
        }
        return null;
    }
    @POST
    @Path("/post/logout")
    @Produces(APPLICATION_JSON)
    public void uitloggen(@FormParam("name") String name, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = Ks.getBaseUserService().getUserByName(name);
        if (user != null && SessionLister.getInstance().getActiveUsers().contains(name)) {
            SessionLister.getInstance().getActiveUsers().remove(name);
        }
    }

}
