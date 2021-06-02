package com.tobiasekman.utils;

public class HttpUtility {

    public static Request parseHttpRequest(String input) {

        var request = new Request();
        request.setType(parseHttpRequestType(input));
        request.setUrl(parseUrl(input));
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



}
