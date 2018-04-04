package DTO;

import Domain.Hashtag;

import java.util.ArrayList;
import java.util.List;

public class detHashtagDTO extends HashtagDTO{

    private List<KweetDTO> kweetDTOList;

    public detHashtagDTO(){
        this.kweetDTOList = new ArrayList<>();
    }

    @Override
    public void generateDTO(Hashtag hashtag){
        super.generateDTO(hashtag);
        DTOConvert.generateKweetDTOList(hashtag.getKweets(), kweetDTOList);
    }

    public List<KweetDTO> getKweetDTOList() {
        return kweetDTOList;
    }

    public void setKweetDTOList(List<KweetDTO> kweetDTOList) {
        this.kweetDTOList = kweetDTOList;
    }
}
