package com.server;

import java.net.Socket;

public class ClientThread extends Thread {

    private Socket clientSocket;

    private ServerApplication serverApplication;

    public ClientThread(Socket clientSocket, ServerApplication gameServer) {
        this.clientSocket = clientSocket;
        this.serverApplication = gameServer;
    }


    @Override
    public void run() {
        super.run();
    }

}
