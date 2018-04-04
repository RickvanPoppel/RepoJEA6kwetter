package DTO;

import Domain.User;

public class UserDTO {

    private long id;
    private String username;
    private String profilePhoto;
    private String bio;
    private String email;
    private String role;

    public UserDTO() {
    }

    public void generateDTO(User user){
        this.id = user.getId();
        this.username = user.getUserName();
        this.profilePhoto = user.getProfilePhoto();
        this.bio = user.getBio();
        this.email = user.geteMail();
        this.role = user.getRole();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
