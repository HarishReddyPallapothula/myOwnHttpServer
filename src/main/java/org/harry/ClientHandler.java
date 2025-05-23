package org.harry;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{

    Socket clientSocket;

    ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            PrintStream printStreamOut = new PrintStream(clientSocket.getOutputStream(), true);
            HttpRequest httpRequest = HttpRequest.parseRequest(clientSocket.getInputStream());


            HttpServerService serverService = new HttpServerService();
            serverService.handleRequest(httpRequest, printStreamOut);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
