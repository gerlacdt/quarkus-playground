package org.acme;

import com.google.common.base.MoreObjects;

public class GetSingleUserResponse {
    private Long id;
    private String username;
    private String email;
    private short age;
    private boolean premium;

    public GetSingleUserResponse() {
    }

    public GetSingleUserResponse(User u) {
        this.id = u.id;
        this.username = u.username;
        this.email = u.email;
        this.age = u.age;
        this.premium = u.premium;
    }

    public User toUser() {
        var u = new User();
        u.username = this.getUsername();
        u.email = this.getEmail();
        u.age = this.getAge();
        u.premium = this.isPremium();
        return u;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("email", email)
                .add("age", age)
                .add("premium", premium)
                .toString();
    }
}
