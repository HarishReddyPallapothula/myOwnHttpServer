package org.harry;

public class UserAgentHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) {
        return new HttpResponse.Builder()
                .code(200)
                .reason("OK")
                .responseBody(request.requestHeaders.get("User-Agent"))
                .build();
    }
}
