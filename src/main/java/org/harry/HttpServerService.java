package org.harry;

import org.harry.requestHandlers.EndpointHandler;

import java.io.*;

public class HttpServerService {

    protected void handleRequest(HttpRequest request, PrintStream response, String fileDirectory) throws IOException {
        /*HttpResponse serverResponse = this.handleEndpoint(request, fileDirectory);
        response.println(serverResponse.getHttpResponse());*/

        RequestRouter router = new RequestRouter();
        EndpointHandler handler = router.route(request);
        response.println(handler.handle(request, fileDirectory).getHttpResponse());
    }

    //helps to understand how easily we can understand code if it is modular and not like below.

    /*private HttpResponse handleEndpoint(HttpRequest request, String fileDirectory) throws IOException {
        String endPoint = request.requestTarget;
        if (request.method == HttpRequest.Method.GET) {
            System.out.println("Requested Method GET");
            if (endPoint.equals("/user-agent")) {
                return new HttpResponse.Builder()
                        .code(200)
                        .reason("OK")
                        .responseBody(request.requestHeaders.get("User-Agent"))
                        .build();
                //return new HttpResponse(200, "OK", request.requestHeaders.get("User-Agent"));
            } else if (endPoint.startsWith("/echo/")) {
                String[] splitBody = request.requestTarget.split("/");
                if (splitBody.length < 3) {
                    return new HttpResponse.Builder()
                            .code(404)
                            .reason("Not Found")
                            .build();
                    //return new HttpResponse(404, "Not Found");
                }
                String body = splitBody[2];
                return new HttpResponse.Builder()
                        .code(200)
                        .reason("OK")
                        .responseBody(body)
                        .build();
                //return new HttpResponse(200, "OK", body);
            } else if (endPoint.startsWith("/files/")) {
                String fileName = endPoint.substring(7);
                File file = new File(fileDirectory, fileName);
                if (file.exists() && file.isFile()) {
                    byte[] content = Files.readAllBytes(file.toPath());
                    ResponseHeader responseHeader = new ResponseHeader.Builder()
                            .contentType("application/octet-stream")
                            .contentLength(String.valueOf(content.length))
                            .build();

                    return new HttpResponse.Builder()
                            .responseHeader(responseHeader)
                            .reason("OK")
                            .code(200)
                            .responseBody(new String(content))
                            .build();

                    //return new HttpResponse(new ResponseHeader("application/octet-stream", String.valueOf(content.length)),new String(content),200,"OK");
                } else {
                    return new HttpResponse.Builder()
                            .code(404)
                            .reason("Not Found")
                            .build();
                    //return new HttpResponse(404, "Not Found");
                }

            } else if (endPoint.equals("/")) {
                return new HttpResponse.Builder()
                        .code(200)
                        .reason("OK")
                        .build();
            } else {
                return new HttpResponse.Builder()
                        .code(404)
                        .reason("Not Found")
                        .build();
            }
        } else if (request.method == HttpRequest.Method.POST && endPoint.startsWith("/files/")) {
            System.out.println("Requested Method POST");
            String fileName = endPoint.substring(7);
            File file = new File(fileDirectory, fileName);
            file.getParentFile().mkdirs();
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter buf = new BufferedWriter(fw);
                buf.write(request.requestBody);  // This assumes requestBody is a String
                buf.close();

                ResponseHeader responseHeader = new ResponseHeader.Builder()
                        .contentType("application/octet-stream")
                        .contentLength(String.valueOf(request.requestBody.length()))
                        .build();

                return new HttpResponse.Builder()
                        .responseHeader(responseHeader)
                        .reason("Created")
                        .code(201)
                        .responseBody(request.requestBody)
                        .build();

                    *//*return new HttpResponse(
                            new ResponseHeader("application/octet-stream", String.valueOf(request.requestBody.length())),
                            request.requestBody,
                            201,
                            "Created"
                    );*//*
            } catch (IOException e) {
                e.printStackTrace();
                return new HttpResponse.Builder()
                        .code(500)
                        .reason("Internal Server Error")
                        .build();
                //return new HttpResponse(500, "Internal Server Error");
            }
        } else {
            return new HttpResponse.Builder()
                    .code(404)
                    .reason("Not Found")
                    .build();
            //return new HttpResponse(404, "Not found");
        }
    }*/
}
