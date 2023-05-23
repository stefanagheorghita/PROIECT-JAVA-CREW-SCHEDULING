package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerApplication
     //   implements CommandLineRunner
{

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//      //  startServer();
//    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(8085);
            System.out.println("Server started on port 8085.");
            listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForClients() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clientThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}