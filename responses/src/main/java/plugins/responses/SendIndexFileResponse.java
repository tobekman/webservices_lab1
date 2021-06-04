package plugins.responses;

import spi.response.Address;
import spi.response.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Address("/index.html")
public class SendIndexFileResponse implements Response {

    @Override
    public void sendResponse(OutputStream output, Map<String, String> params, BufferedReader input) throws IOException {

        String header = "";
        byte[] data = new byte[0];

        File f = Path.of("core","target","web","index.html").toFile();
        if (!(f.exists() && !f.isDirectory())) {
            header = "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n";
        }
        else
        {
            try(FileInputStream fileInputStream = new FileInputStream(f)){
                data = new byte[(int) f.length()];
                fileInputStream.read(data);
                var contentType = Files.probeContentType(f.toPath());

                header = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-length: " + data.length +"\r\n\r\n";
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }

        output.write(header.getBytes());
        output.write(data);
        output.flush();

    }

}
