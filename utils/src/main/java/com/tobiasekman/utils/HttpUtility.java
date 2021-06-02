package com.tobiasekman.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtility {

    public static Request parseHttpRequest(String input) {

        var request = new Request();
        request.setType(parseHttpRequestType(input));
        request.setUrl(parseUrl(input));
        if(request.getUrl().contains("?")) {
            request.setUrlParams(parseParams(request.getUrl()));
        }
        return request;
    }

    private static String parseUrl(String input) {

        String[] result = input.split(" ");
        return result[1];
    }

    private static HTTPType parseHttpRequestType(String input) {
        if (input.startsWith("G"))
            return HTTPType.GET;
        else if (input.startsWith("H"))
            return HTTPType.HEAD;
        else if (input.startsWith("PO"))
            return HTTPType.POST;

        throw new RuntimeException("Invalid type");
    }

    public static Map<String, String> parseParams(String input) {

        String[] param = input.split("\\?")[1].split("=");
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(param[0], param[1]);
        return requestParams;

    }



}
