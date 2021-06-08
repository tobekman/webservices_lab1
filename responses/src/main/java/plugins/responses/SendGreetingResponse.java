package plugins.responses;


import spi.response.Address;
import spi.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Address("/greeting")
public class SendGreetingResponse implements Response {

    @Override
    public void sendResponse(OutputStream output, Map<String, String> params, BufferedReader input) throws IOException {

        String content = "Hello " + URLDecoder.decode(params.get("name"), StandardCharsets.UTF_8);
        String header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-length: " + content.getBytes().length +"\r\n\r\n";

        output.write(header.getBytes());
        output.write(content.getBytes());
        output.flush();

    }


}
