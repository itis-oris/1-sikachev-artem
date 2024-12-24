package model;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Setter;
import lombok.Getter;

@Setter@Getter
public class News {
    private Long id;
    private String title;
    private String content;
    private Timestamp publishDate;
    private String imageUrl;

    public News(){}

    public News(Long id, String title, String content, Timestamp publishDate, String imageUrl){
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.imageUrl = imageUrl;
    }

    public News(String title, String content, Timestamp publishDate, String imageUrl){
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.imageUrl = imageUrl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getPublishDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(getId(), news.getId()) && Objects.equals(getTitle(), news.getTitle()) && Objects.equals(getContent(), news.getContent()) && Objects.equals(getPublishDate(), news.getPublishDate());
    }
}

