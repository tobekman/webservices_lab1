package com.tobiasekman.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Request {
    private HTTPType type;
    private String url;
    private Map<String,String> urlParams = new HashMap<>();
}

enum HTTPType {
    GET,
    HEAD,
    POST
}