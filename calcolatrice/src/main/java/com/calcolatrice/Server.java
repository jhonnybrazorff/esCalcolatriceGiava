package com.calcolatrice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader input = new BufferedReader(
                             new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter output = new PrintWriter(
                             clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connesso: "
                            + clientSocket.getInetAddress());

                    String richiesta = input.readLine();

                    if (richiesta == null) {
                        continue;
                    }

                    output.println(calcola(richiesta));
                } catch (IOException e) {
                    System.err.println("Errore nella comunicazione: "
                            + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Impossibile avviare il server: "
                    + e.getMessage());
        }
    }

    private static String calcola(String richiesta) {
        String[] parti = richiesta.trim().split("\\s+");

        if (parti.length != 3) {
            return "Errore: formato atteso, per esempio 10 + 5";
        }

        try {
            double primo = Double.parseDouble(parti[0]);
            double secondo = Double.parseDouble(parti[2]);
            String operatore = parti[1];

            double risultato;

            switch (operatore) {
                case "+":
                    risultato = primo + secondo;
                    break;
                case "-":
                    risultato = primo - secondo;
                    break;
                case "*":
                    risultato = primo * secondo;
                    break;
                case "/":
                    if (secondo == 0) {
                        return "Errore: divisione per zero";
                    }
                    risultato = primo / secondo;
                    break;
                default:
                    return "Errore: operatore non riconosciuto";
            }

            return "Risultato: " + risultato;
        } catch (NumberFormatException e) {
            return "Errore: inserire due numeri validi";
        }
    }
}
