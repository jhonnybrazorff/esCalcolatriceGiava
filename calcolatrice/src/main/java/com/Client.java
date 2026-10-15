package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                Socket socket = new Socket(HOST, PORT);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            System.out.println("================================");
            System.out.println("       CALCOLATRICE CLIENT");
            System.out.println("================================");

            while (true) {
                System.out.println();
                System.out.println("--------------------------------");
                System.out.print("Primo numero (o 'exit' per uscire): ");
                String primo = scanner.nextLine().trim();

                if (primo.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.print("Operatore (+, -, *, /): ");
                String operatore = scanner.nextLine().trim();

                if (operatore.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.print("Secondo numero (o 'exit' per uscire): ");
                String secondo = scanner.nextLine().trim();

                if (secondo.equalsIgnoreCase("exit")) {
                    break;
                }

                output.println(primo + " " + operatore + " " + secondo);

                String risposta = input.readLine();

                if (risposta == null) {
                    System.err.println("Il server ha chiuso la connessione.");
                    break;
                }

                System.out.println();
                System.out.println("Risultato");
                System.out.println("  " + primo + " " + operatore + " " + secondo
                        + " = " + risposta);
            }

            System.out.println();
            System.out.println("Client terminato. Arrivederci!");

        } catch (IOException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
        }
    }

}
