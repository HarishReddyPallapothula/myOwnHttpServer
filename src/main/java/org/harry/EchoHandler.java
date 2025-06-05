package org.harry;

public class EchoHandler implements EndpointHandler {
    @Override
    public HttpResponse handle(HttpRequest request, String fileDirectory) {
        String[] splitBody = request.requestTarget.split("/");
        if (splitBody.length < 3) {
            return new HttpResponse.Builder()
                    .code(404)
                    .reason("Not Found")
                    .build();
        }
        String body = splitBody[2];
        return new HttpResponse.Builder()
                .code(200)
                .reason("OK")
                .responseBody(body)
                .build();
    }
}
