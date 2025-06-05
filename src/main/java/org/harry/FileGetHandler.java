package org.harry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileGetHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) throws IOException {
        String fileName = request.requestTarget.substring(7); // remove /files/
        File file = new File(fileDirectory, fileName);

        if (file.exists() && file.isFile()) {
            byte[] content = Files.readAllBytes(file.toPath());
            ResponseHeader header = new ResponseHeader.Builder()
                    .contentType("application/octet-stream")
                    .contentLength(String.valueOf(content.length))
                    .build();

            return new HttpResponse.Builder()
                    .code(200)
                    .reason("OK")
                    .responseHeader(header)
                    .responseBody(new String(content))
                    .build();
        }

        return new HttpResponse.Builder()
                .code(404)
                .reason("Not Found")
                .build();
    }
}
