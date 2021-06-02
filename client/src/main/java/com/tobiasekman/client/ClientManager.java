package com.tobiasekman.client;

import com.fasterxml.jackson.core.JsonProcessingException;

import static com.tobiasekman.utils.JsonUtility.createMessageJsonString;
import static com.tobiasekman.utils.JsonUtility.makeItLookCool;
import static com.tobiasekman.utils.MyScanner.stringScanner;

public class ClientManager {

    public String createMessage() {

        System.out.print("Name: ");
        String name = stringScanner();

        System.out.println("Message: ");
        String message = stringScanner();

        return "POST /add HTTP/1.1\r\n\r\n" + createMessageJsonString(name, message);

    }

    public String printALLMessages(String json) {
        try {
            return makeItLookCool(json);
        } catch (JsonProcessingException e) {
            return "Something went wrong";
        }
    }

}
