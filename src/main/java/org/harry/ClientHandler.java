package org.harry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            while (true) {
                HttpRequest httpRequest = HttpRequest.parseRequest(in);
                HttpServerService serverService = new HttpServerService();
                serverService.handleRequest(httpRequest, out, fileDirectory);
                String connectionHeader = httpRequest.requestHeaders.get("Connection");
                if (connectionHeader != null && connectionHeader.equalsIgnoreCase("close")) {
                    System.out.println("Client requested to close the connection.");
                    clientSocket.close();// close client socket
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
