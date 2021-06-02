package com.tobiasekman.client;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static com.tobiasekman.utils.MyScanner.intScanner;

public class ClientMain {

    public static void main(String[] args) {

        while (true) {
            try(Socket socket = new Socket("localhost", 5000)) {

                socket.setSoTimeout(10000);

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                ClientManager clientManager = new ClientManager();

                System.out.println("\n<------------ Cool things to do ----------->");
                System.out.println("[1] Send message");
                System.out.println("[2] Show all messages");
                System.out.println("[3] Show message by id");
                System.out.println("[0] Exit");

                int pick = intScanner();

                if(pick == 1) {
                    output.println(clientManager.createMessage());
                } else if (pick == 2) {
                    output.println("GET /messages HTTP/1.1\r\n\r\n");
                    String json = input.lines().toList().get(4);
                    System.out.println(clientManager.printALLMessages(json));
                } else if (pick == 3) {
                    output.println(clientManager.getIdParam());
                    String json = input.lines().toList().get(4);
                    System.out.println(clientManager.printALLMessages(json));
                } else {
                    input.close();
                    output.close();
                    socket.close();
                    break;
                }

            } catch (SocketException e) {
                System.out.println("The socket timed out");
                break;
            } catch (IOException e) {
                System.out.println("Client Error: " + e.getMessage());
            }
        }

    }

}
