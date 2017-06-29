package com.example.news_app.Model;

/**
 * Created by Leonard on 6/29/2017.
 */

public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urltToImage;
    private String publishedAt;

    public NewsItem(String author, String title, String description, String url, String urltToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urltToImage = urltToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrltToImage() {
        return urltToImage;
    }

    public void setUrltToImage(String urltToImage) {
        this.urltToImage = urltToImage;
    }

    public String getPublishDate() {
        return publishedAt;
    }

    public void setPublishDate(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}