package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket connection;
    DataOutputStream out;
    BufferedReader inputServer;
    Scanner inputKeyboard;
    int min = 1;
    int max = 100;

    public Client(String ip, Integer port) throws IOException {
        connection = new Socket(ip, port);
        System.out.println("Connection Started");
        out = new DataOutputStream(connection.getOutputStream());
        inputServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        inputKeyboard = new Scanner(System.in);
    }

    public void start() throws IOException {
        String userInput = getUserInput();
        out.writeBytes(userInput + '\n');
        int response = getServerResponse();
        while (response != 3) {
            if (response == 1) {
                System.out.println("Numero Troppo Grande");
                max = Integer.parseInt(userInput);
            } else if (response == 2) {
                System.out.println("Numero Troppo Piccolo");
                min = Integer.parseInt(userInput);
            }

            userInput = getUserInput();
            out.writeBytes(userInput + '\n');
            response = getServerResponse();
        }

        // get attempts
        int attempts = getServerResponse();

        System.out.println("Hai indivinato il numero in " + attempts + " tentativi");
    } 
    // get server response and then convert it to int
    private Integer getServerResponse() throws IOException {
        String response = inputServer.readLine();
        return Integer.parseInt(response);
    }

    private String getUserInput() {
        System.out.println("Inserisci un numero da " + min + " a " + max + ": ");
        String userInput = inputKeyboard.nextLine();
        return userInput;
    }
    
}
