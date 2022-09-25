package com.example.shopdemo.pojo;

import org.springframework.http.HttpStatus;

import java.util.*;

public class RestResponse {
    private Integer status;
    private Set<String> messages = new HashSet<>();
    private Map<String, Object> details = new HashMap<>();

    private RestResponse() {}

    public static RestResponse build() {
        return new RestResponse();
    }

    public RestResponse status(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public RestResponse messages(Collection<String> messages) {
        this.messages.addAll(messages);
        return this;
    }

    public RestResponse messages(String... messages) {
        this.messages.addAll(Arrays.asList(messages));
        return this;
    }

    public RestResponse detail(String key, Object val) {
        this.details.put(key, val);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
