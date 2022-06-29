package com.gfa.users.models;

import java.util.Date;

public class User {

    private String email;
    private Date verifiedAt;

    public User(String email) {
        this.email = email;
        this.verifiedAt = null;
    }

    public User(String email, Date verifiedAt) {
        this.email = email;
        this.verifiedAt = verifiedAt;
    }

    public String getEmail() {
        return email;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }
}
