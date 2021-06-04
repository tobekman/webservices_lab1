package plugins.responses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobiasekman.db.Message;
import com.tobiasekman.db.MessageHandler;
import spi.response.Address;
import spi.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Address("/messages")
public class SendMessagesResponse implements Response {

    @Override
    public void sendResponse(OutputStream output, Map<String, String> params, BufferedReader input) throws IOException {

        MessageHandler messageHandler = new MessageHandler();
        List<Message> messages = new ArrayList<>();
        if(!params.isEmpty()) {
            messages.add(messageHandler.getById(Integer.parseInt(params.get("id"))));
        } else {
            messages = messageHandler.getAll();
        }

        String json = new ObjectMapper().writeValueAsString(messages);

        byte[] data = json.getBytes(StandardCharsets.UTF_8);

        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length +"\r\n\r\n";

        output.write(header.getBytes());
        output.write(data);
        output.flush();

    }
}
