package com.mindvalley.app.data.model.api;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("profile_image")
    private ProfileImage profileImage;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return
                "User{" +
                        "profile_image = '" + profileImage + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}