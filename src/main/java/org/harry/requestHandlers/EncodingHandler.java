package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;
import org.harry.ResponseHeader;

import java.io.IOException;

public class EncodingHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) throws IOException {
        {
            if(request.getRequestHeaders().get("Accept-Encoding").equals("gzip")){
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
}
