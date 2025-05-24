package org.harry;

import java.io.*;
import java.nio.file.Files;

public class HttpServerService {

    protected void handleRequest(HttpRequest request, PrintStream response, String directory) throws IOException {
        HttpResponse serverResponse = this.handleEndpoint(request, directory);
        response.println(serverResponse.httpResponse);
    }

    private HttpResponse handleEndpoint(HttpRequest request, String fileDirectory) throws IOException {
        String endPoint = request.requestTarget;
        if(request.method == HttpRequest.Method.GET) {
            System.out.println("Requested Method GET");
            if (endPoint.equals("/user-agent")) {
                return new HttpResponse(200, "OK", request.requestHeaders.get("User-Agent"));
            } else if (endPoint.startsWith("/echo/")) {
                String[] splitBody = request.requestTarget.split("/");
                if (splitBody.length < 3) {
                    return new HttpResponse(400, "NOT_FOUND");
                }
                String body = splitBody[2];
                return new HttpResponse(200, "OK", body);
            } else if (endPoint.equals("/")) {
                return new HttpResponse(200, "OK");
            } else if (endPoint.startsWith("/files/")) {
                String fileName = endPoint.substring(7);
                File file = new File(fileDirectory, fileName);
                if (file.exists() && file.isFile()) {
                    byte[] content = Files.readAllBytes(file.toPath());
                    return new HttpResponse(new ResponseHeader("application/octet-stream", String.valueOf(content.length)), new String(content), 200, "OK");
                } else {
                    return new HttpResponse(404, "Not Found");
                }

            } else {
                return new HttpResponse(404, "Not found");
            }
        } else if (request.method == HttpRequest.Method.POST) {
            System.out.println("Requested Method POST");
            String fileName = endPoint.substring(7);
            File file = new File(fileDirectory, fileName);
            file.getParentFile().mkdirs();
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter buf = new BufferedWriter(fw);
                buf.write(request.requestBody);  // This assumes requestBody is a String
                buf.close();

                return new HttpResponse(
                        new ResponseHeader("application/octet-stream", String.valueOf(request.requestBody.length())),
                        request.requestBody,
                        201,
                        "Created"
                );
            } catch (IOException e) {
                e.printStackTrace();
                return new HttpResponse(500, "Internal Server Error");
            }
        }else {
            return new HttpResponse(404, "Not found");
        }
    }

}
