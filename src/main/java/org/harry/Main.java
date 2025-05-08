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
            Socket clientSocket= serverSocket.accept();
            System.out.println("Accepted the connection");

            PrintStream printStreamOut = new PrintStream(clientSocket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String plainRequestPath = bufferedReader.readLine();
            String[] splitRequestPath = plainRequestPath.split(" ");

            if(splitRequestPath[1].equals("/")){
                printStreamOut.println(HttpStatus.OK.getResponse());
                System.out.println("sent response to the client : "+HttpStatus.OK.getResponse());
            }else {
                printStreamOut.println(HttpStatus.NOT_FOUND.getResponse());
                System.out.println("sent response to the client : "+HttpStatus.NOT_FOUND.getResponse());
            }
            serverSocket.close();
            clientSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

enum HttpStatus{
    OK("HTTP/1.1 200 OK\r\n\r\n"),
    NOT_FOUND("HTTP/1.1 404 Not Found\r\n\r\n");

    private final String response;

    HttpStatus(String response){
        this.response = response;
    }


    public String getResponse(){
        return response;
    }
}