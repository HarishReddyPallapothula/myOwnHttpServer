package org.harry;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{

    Socket clientSocket;
    String fileDirectory;

    ClientHandler(Socket clientSocket, String fileDirectory) throws IOException {
        this.clientSocket = clientSocket;
        this.fileDirectory = fileDirectory;
    }

    @Override
    public void run() {
        try {

            PrintStream printStreamOut = new PrintStream(clientSocket.getOutputStream(), true);
            HttpRequest httpRequest = HttpRequest.parseRequest(clientSocket.getInputStream());


            HttpServerService serverService = new HttpServerService();
            serverService.handleRequest(httpRequest, printStreamOut, fileDirectory);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
