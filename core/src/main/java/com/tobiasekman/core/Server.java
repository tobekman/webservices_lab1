package com.tobiasekman.core;

import com.tobiasekman.utils.HttpUtility;
import com.tobiasekman.utils.Request;
import spi.response.Address;
import spi.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.nonNull;

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
            var input = new BufferedReader(new InputStreamReader((client.getInputStream())));
            Request request = HttpUtility.parseHttpRequest(input.readLine());
            OutputStream output = client.getOutputStream();
            String url = request.getUrl();
            Map<String, String> params = request.getUrlParams();
            String requestUrl = url.split("\\?")[0];
            boolean notFound = true;

            ServiceLoader<Response> responses = ServiceLoader.load(Response.class);
            for (Response response : responses) {
                Address annotation = response.getClass().getAnnotation(Address.class);
                if (nonNull(annotation) && annotation.value().equals(requestUrl)) {
                    response.sendResponse(output, params, input);
                    notFound = false;
                }
            }

            if(notFound) {
                output.write("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n".getBytes());
            }

            input.close();
            output.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
