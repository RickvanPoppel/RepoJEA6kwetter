package DTO;

import Domain.Kweet;

import java.util.ArrayList;
import java.util.List;

public class detKweetDTO extends KweetDTO{

    private List<UserDTO> mentions;
    private List<HashtagDTO> hashtags;

    public detKweetDTO(){
        this.mentions = new ArrayList<>();
        this.hashtags = new ArrayList<>();
    }

    @Override
    public void generateDTO(Kweet kweet){
        super.generateDTO(kweet);
        DTOConvert.generateUserDTOList(kweet.getMentions(), mentions);
        DTOConvert.generateHashtagDTOList(kweet.getHashtags(), hashtags);
    }

    public List<UserDTO> getMentions() {
        return mentions;
    }

    public void setMentions(List<UserDTO> mentions) {
        this.mentions = mentions;
    }

    public List<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }
}
