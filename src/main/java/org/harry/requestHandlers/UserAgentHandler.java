package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;

public class UserAgentHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory, boolean connectionClose) {
        return new HttpResponse.Builder()
                .code(200)
                .reason("OK")
                .responseBody(request.getRequestHeaders().get("User-Agent"))
                .build(connectionClose);
    }
}
