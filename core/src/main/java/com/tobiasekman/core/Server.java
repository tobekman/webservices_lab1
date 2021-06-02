package com.tobiasekman.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobiasekman.db.Message;
import com.tobiasekman.db.MessageHandler;
import com.tobiasekman.utils.HttpUtility;
import com.tobiasekman.utils.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void runServer() {

        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            while(true) {

                Socket socket = serverSocket.accept();

                executorService.submit(() -> handleConnection(socket));

            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    private static void handleConnection(Socket client) {
        try {
            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));
            Request request = HttpUtility.parseHttpRequest(inputFromClient.readLine());

            var outputToClient = client.getOutputStream();
            //Routing
            switch (request.getUrl()) {
                case "/index.html" -> sendFileResponse(outputToClient, "index.html");
                case "/duck.png" -> sendFileResponse(outputToClient, "duck.png");
                case "/add" -> saveMessage(inputFromClient);
                case "/messages" -> sendJsonResponse(outputToClient);
                default -> outputToClient.write("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n".getBytes());
            }


            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMessage(BufferedReader inputFromClient) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler messageHandler = new MessageHandler();
        List<String> strings = new ArrayList<>();

        inputFromClient.lines().forEach(strings::add);

        Message message = objectMapper.readValue(strings.get(1), Message.class);
        messageHandler.add(message);

    }

    private static void sendFileResponse(OutputStream outputToClient, String filename) throws IOException {

        String header = "";
        byte[] data = new byte[0];

        File f = Path.of("core","target","web",filename).toFile();
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

        outputToClient.write(header.getBytes());
        outputToClient.write(data);

        outputToClient.flush();
    }

    private static void sendJsonResponse(OutputStream outputToClient) throws IOException {

        MessageHandler messageHandler = new MessageHandler();
        List<Message> messages = messageHandler.getAll();

        String json = new ObjectMapper().writeValueAsString(messages);

        byte[] data = json.getBytes(StandardCharsets.UTF_8);

        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length +"\r\n\r\n";

        outputToClient.write(header.getBytes());
        outputToClient.write(data);
        //outputToClient.print("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
        outputToClient.flush();
    }

}
