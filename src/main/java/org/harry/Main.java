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
            HttpRequest httpRequest = HttpRequest.parseRequest(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            HttpServerService serverService = new HttpServerService();
            serverService.handleRequest(httpRequest, printStreamOut);

//            String plainRequestPath = bufferedReader.readLine();
//            String[] splitRequestPath = plainRequestPath.split("\r\n");
//
//            if(splitRequestPath[1].equals("/")){
//                printStreamOut.println(HttpStatus.OK.getResponse());
//                System.out.println("sent response to the client : "+HttpStatus.OK.getResponse());
//            }else {
//                printStreamOut.println(HttpStatus.NOT_FOUND.getResponse());
//                System.out.println("sent response to the client : "+HttpStatus.NOT_FOUND.getResponse());
//            }
            serverSocket.close();
            clientSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}




