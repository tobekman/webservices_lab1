package com.tobiasekman.utils;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private HTTPType type;
    private String url;
    private Map<String,String> urlParams = new HashMap<>();

    public HTTPType getType() {
        return type;
    }

    public void setType(HTTPType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public int getId() {
        return Integer.parseInt(getUrlParams().get("id"));
    }
}

enum HTTPType {
    GET,
    HEAD,
    POST
}