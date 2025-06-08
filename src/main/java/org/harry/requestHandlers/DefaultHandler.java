package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;

public class DefaultHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory, boolean connectionClose) {
        return new HttpResponse.Builder()
                .code(404)
                .reason("Not Found")
                .build(connectionClose);
    }
}
