package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;
import org.harry.ResponseHeader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePostHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory, boolean connectionClose) {
        String fileName = request.getRequestTarget().substring(7);
        File file = new File(fileDirectory, fileName);
        file.getParentFile().mkdirs();

        try (BufferedWriter buf = new BufferedWriter(new FileWriter(file))) {
            buf.write(request.getRequestBody());

            ResponseHeader header = new ResponseHeader.Builder()
                    .contentType("application/octet-stream")
                    .contentLength(String.valueOf(request.getRequestBody().length()))
                    .build();

            return new HttpResponse.Builder()
                    .code(201)
                    .reason("Created")
                    .responseHeader(header)
                    .responseBody(request.getRequestBody())
                    .build(connectionClose);

        } catch (IOException e) {
            return new HttpResponse.Builder()
                    .code(500)
                    .reason("Internal Server Error")
                    .build(connectionClose);
        }
    }
}
