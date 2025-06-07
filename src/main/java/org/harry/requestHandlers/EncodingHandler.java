package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;
import org.harry.ResponseHeader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EncodingHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) throws IOException {
        List<String> encodingTypes = Arrays.asList(request.getRequestHeaders().get("Accept-Encoding").trim().split("\\s*,\\s*"));
        if(encodingTypes.contains("gzip")){
                ResponseHeader responseHeader = new ResponseHeader.Builder()
                        .addHeader("Content-Encoding", "gzip").build();

                return new HttpResponse.Builder()
                        .responseHeader(responseHeader)
                        .code(200)
                        .reason("OK").build();
            }else{
                return new HttpResponse.Builder()
                        .code(200)
                        .reason("OK").build();
            }
    }
}
