package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter@Setter
public class Advertisement {
    private Integer id;
    private Integer sender_id;
    private String title;
    private String content;
    private Timestamp publishDate;
    private String imageUrl;
    private String status;

    public Advertisement(){}

    public Advertisement(Integer id, Integer sender_id, String title, String content, Timestamp publishDate, String imageUrl, String status){
        this.id = id;
        this.sender_id = sender_id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Advertisement(String title, Integer sender_id, String content, Timestamp publishDate, String imageUrl){
        this.sender_id = sender_id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.imageUrl = imageUrl;
    }

    public Advertisement(String title, Integer sender_id, String content, Timestamp publishDate, String imageUrl, String status){
        this.sender_id = sender_id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSender_id(), getTitle(), getContent(), getPublishDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement news = (Advertisement) o;
        return Objects.equals(getId(), news.getId()) && Objects.equals(getTitle(), news.getTitle()) && Objects.equals(getContent(), news.getContent()) && Objects.equals(getPublishDate(), news.getPublishDate());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append(" from ").append(sender_id.toString());
        sb.append(" published on ").append(publishDate);
        return sb.toString();
    }
}
