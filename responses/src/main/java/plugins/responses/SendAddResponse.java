package plugins.responses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobiasekman.db.Message;
import com.tobiasekman.db.MessageHandler;
import spi.response.Address;
import spi.response.Response;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Address("/add")
public class SendAddResponse implements Response {

    @Override
    public void sendResponse(OutputStream output, Map<String, String> params, BufferedReader input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler messageHandler = new MessageHandler();
        List<String> strings = new ArrayList<>();

        input.lines().forEach(strings::add);

        Message message = objectMapper.readValue(strings.get(1), Message.class);
        messageHandler.add(message);

        String header = "HTTP/1.1 200 OK\r\nContent-length: 0\r\n\r\n";
        output.write(header.getBytes());
    }


}
