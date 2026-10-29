package com.calcolatrice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    ClientHandler(Socket socket){
        this.socket = socket;
    }



    @Override
    public void run() {
        
        try{
            System.out.println("Client connesso: " + socket.getInetAddress() + ":" + socket.getPort());


            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);



            String richiesta = input.readLine();

            if (richiesta != null) {
                output.println(calcola(richiesta));
            }


        }
        
        catch(Exception e){
            System.out.println(e.getMessage());
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
