package org.harry;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(4222);
            System.out.println("Waiting for the connection");
            serverSocket.setReuseAddress(true);

            Socket clientSocket;
            while(true) {
                 clientSocket = serverSocket.accept();
                System.out.println("Accepted the connection");
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}




