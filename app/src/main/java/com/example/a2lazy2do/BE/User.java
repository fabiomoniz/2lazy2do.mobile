package com.example.a2lazy2do.BE;

public class User {

    private String userEmail, userName, tokenId;

    public User() {
    }

    public User(String userEmail, String userName, String tokenId) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.tokenId = tokenId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
