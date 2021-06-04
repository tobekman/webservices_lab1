package spi.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface Response {
    void sendResponse(OutputStream output, Map<String, String> params, BufferedReader input) throws IOException;
}
