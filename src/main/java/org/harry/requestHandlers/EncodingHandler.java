package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;
import org.harry.ResponseHeader;
import org.harry.compressions.Compressor;
import org.harry.compressions.GZIP_Compressor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EncodingHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory, boolean connectionClose) throws IOException {

        List<String> encodingTypes = Arrays.asList(request.getRequestHeaders().get("Accept-Encoding").trim().split("\\s*,\\s*"));
        if(encodingTypes.contains("gzip")){
            String[] splitBody = request.getRequestTarget().split("/");
            if (splitBody.length < 3) {
                return new HttpResponse.Builder()
                        .code(404)
                        .reason("Not Found")
                        .build(connectionClose);
            }
            String body = splitBody[2];
            Compressor gzipCompressor = new GZIP_Compressor();
            byte[] compressedBody = gzipCompressor.compress(body);
            ResponseHeader responseHeader = new ResponseHeader.Builder()
                    .addHeader("Content-Encoding", "gzip")
                    .contentLength(String.valueOf(compressedBody.length)).build();

            return new HttpResponse.Builder()
                    .responseHeader(responseHeader)
                    .code(200)
                    .reason("OK")
                    .compressedResponseBody(compressedBody).build(connectionClose);
        } else{
            return new HttpResponse.Builder()
                    .code(200)
                    .reason("OK").build(connectionClose);
        }
    }
}
