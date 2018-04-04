package DTO;

import Domain.Hashtag;

public class HashtagDTO {

    private long id;
    private String content;

    public void generateDTO(Hashtag hashtag){
        this.id = hashtag.getId();
        this.content = hashtag.getContent();
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
}
