package com.mindvalley.app.data.model.api;

import com.google.gson.annotations.SerializedName;

public class Pinboard {

    @SerializedName("urls")
    private Urls urls;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("liked_by_user")
    private boolean likedByUser;

    @SerializedName("user")
    private User user;

    @SerializedName("likes")
    private int likes;

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Urls getUrls() {
        return urls;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return
                "Pinboard{" +
                        "urls = '" + urls + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",liked_by_user = '" + likedByUser + '\'' +
                        ",user = '" + user + '\'' +
                        ",likes = '" + likes + '\'' +
                        "}";
    }
}