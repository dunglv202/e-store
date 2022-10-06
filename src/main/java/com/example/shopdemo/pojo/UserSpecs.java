package com.example.shopdemo.pojo;

import java.util.Set;

public class UserSpecs {
    private String keyword;
    private Set<String> authorities;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
