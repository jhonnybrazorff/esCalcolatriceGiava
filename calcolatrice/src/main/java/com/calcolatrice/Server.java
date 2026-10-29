package com.calcolatrice;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            

            while(true){

                Socket clientSocket = serverSocket.accept();
                
                ClientHandler ch = new ClientHandler(clientSocket);
                Thread th = new Thread(ch);
                th.start();
            }

        } catch (IOException e) {
            System.err.println("Impossibile avviare il server: "
                    + e.getMessage());
        }

    }


}
