package DTO;

import Domain.Kweet;
import Domain.User;

import java.util.Date;

public class KweetDTO {

    private long id;
    private String content;
    private Date date;
    private UserDTO owner;

    public void generateDTO(Kweet kweet){
        this.id = kweet.getId();
        this.content = kweet.getContent();
        this.date = kweet.getDate();
        User user = kweet.getOwner();
        UserDTO uDTO = new UserDTO();
        uDTO.generateDTO(user);
        this.owner = uDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }
}
