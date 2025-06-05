package org.harry;

public class DefaultHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) {
        return new HttpResponse.Builder()
                .code(404)
                .reason("Not Found")
                .build();
    }
}
