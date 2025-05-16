package org.harry;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HttpServerService {

    protected void handleRequest(HttpRequest request, PrintStream response) throws IOException {
        HttpResponse serverResponse = this.handleEndpoint(request);
        response.println(serverResponse.httpResponse);
    }

    private HttpResponse handleEndpoint(HttpRequest request) {
        String endPoint = request.requestTarget;
        if(endPoint.equals("/user-agent")){
            return new HttpResponse(200, "OK", request.requestHeaders.get("User-Agent"));
        } else if (endPoint.startsWith("/echo/")) {
            String body = request.requestTarget.split("/")[2];
            return new HttpResponse(200, "OK", body);
        } else if (endPoint.equals("/")) {
            return new HttpResponse(200, "OK");
        } else {
            return new HttpResponse(400, "NOT_FOUND");
        }
    }

}
