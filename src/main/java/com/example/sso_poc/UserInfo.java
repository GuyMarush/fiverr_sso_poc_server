package com.example.sso_poc;

public class UserInfo {
    private final String email;
    private final Boolean email_verified;
    private final String username;
    private final String profileImg;
    private final String name;

    public UserInfo(String email, Boolean email_verified, String username, String profileImg, String name) {
        this.email = email;
        this.email_verified = email_verified;
        this.username = username;
        this.profileImg = profileImg;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmail_verified() {
        return email_verified;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getName() {
        return name;
    }
}
