package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class ClientApp {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8085;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientApp() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientApp clientApp = new ClientApp();
        clientApp.start();
    }

    public void start() {

    }


}
