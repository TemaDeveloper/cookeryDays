package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("profileImg")
    private String profileImg;

    @SerializedName("password")
    @Expose
    private String password;

    public User(String name, String email, String profileImg, String password) {
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getPassword() {
        return password;
    }
}
