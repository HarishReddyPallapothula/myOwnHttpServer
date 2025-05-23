package org.harry;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;

public class HttpServerService {

    protected void handleRequest(HttpRequest request, PrintStream response, String directory) throws IOException {
        HttpResponse serverResponse = this.handleEndpoint(request, directory);
        response.println(serverResponse.httpResponse);
    }

    private HttpResponse handleEndpoint(HttpRequest request, String fileDirectory) throws IOException {
        String endPoint = request.requestTarget;
        if(endPoint.equals("/user-agent")){
            return new HttpResponse(200, "OK", request.requestHeaders.get("User-Agent"));
        } else if (endPoint.startsWith("/echo/")) {
            String[] splitBody = request.requestTarget.split("/");
            if(splitBody.length < 3){
                return new HttpResponse(400, "NOT_FOUND");
            }
            String body = splitBody[2];
            return new HttpResponse(200, "OK", body);
        } else if (endPoint.equals("/")) {
            return new HttpResponse(200, "OK");
        }else if(endPoint.startsWith("/files/")){
            String fileName = endPoint.substring(7);
            File file = new File(fileDirectory, fileName);
            if(file.exists() && file.isFile()){
                byte[] content = Files.readAllBytes(file.toPath());
                return new HttpResponse(new ResponseHeader("application/octet-stream", String.valueOf(content.length)),new String(content),200,"OK");
            }else{
                return new HttpResponse(404, "Not Found");
            }

        } else {
            return new HttpResponse(400, "NOT_FOUND");
        }
    }

}
