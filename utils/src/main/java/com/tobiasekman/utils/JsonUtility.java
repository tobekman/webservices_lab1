package com.tobiasekman.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtility {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String createMessageJsonString(String name, String message) {

       ObjectNode newMessage = objectMapper.createObjectNode();

       newMessage.put("name", name);
       newMessage.put("message", message);

       return newMessage.toString();

    }

    public static String makeItLookCool(String json) throws JsonProcessingException {

        Object newJson = objectMapper.readValue(json, Object.class);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newJson);

    }

}
