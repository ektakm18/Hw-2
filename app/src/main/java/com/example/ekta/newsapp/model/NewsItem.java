package com.example.ekta.newsapp.model;

/**
 * Created by Kumar on 6/17/2017.
 */

public class NewsItem {

    private String title;
    private String description;
    private String imageUrl;
    private String url;
    private String author;
    private String publishedAt;

    public NewsItem(String title, String description, String imageUrl, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
