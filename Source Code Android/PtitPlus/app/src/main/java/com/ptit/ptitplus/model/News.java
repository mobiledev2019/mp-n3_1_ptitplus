package com.ptit.ptitplus.model;

public class News {
    private String title;
    private String time;
    private String link;
    private int imageResource;

    public News(String title, String time, String link, int imageResource) {
        this.title = title;
        this.time = time;
        this.link = link;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
