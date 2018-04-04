package DTO;

import Domain.User;

import java.util.ArrayList;
import java.util.List;

public class detUserDTO extends UserDTO {
    private List<UserDTO> followers;
    private List<UserDTO> leaders;
    private List<KweetDTO> kweets;
    private List<KweetDTO> mentions;

    public detUserDTO() {
        this.followers = new ArrayList<>();
        this.leaders = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.kweets = new ArrayList<>();
    }

    @Override
    public void generateDTO(User user){
        super.generateDTO(user);
        DTOConvert.generateUserDTOList(user.getFollowers(), followers);
        DTOConvert.generateUserDTOList(user.getFollowing(), leaders);
        DTOConvert.generateKweetDTOList(user.getKweets(), kweets);
        DTOConvert.generateKweetDTOList(user.getMentions(), mentions);
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }

    public List<UserDTO> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<UserDTO> leaders) {
        this.leaders = leaders;
    }

    public List<KweetDTO> getKweets() {
        return kweets;
    }

    public void setKweets(List<KweetDTO> kweets) {
        this.kweets = kweets;
    }

    public List<KweetDTO> getMentions() {
        return mentions;
    }

    public void setMentions(List<KweetDTO> mentions) {
        this.mentions = mentions;
    }
}
