package org.harry;

import org.harry.requestHandlers.*;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {

    private final Map<String, EndpointHandler> getHandlers = new HashMap<>();
    private final EndpointHandler defaultHandler = new DefaultHandler();

    public RequestRouter() {
        getHandlers.put("/user-agent", new UserAgentHandler());
        getHandlers.put("/echo", new EchoHandler());
        getHandlers.put("/files", new FileGetHandler());
    }

    public EndpointHandler route(HttpRequest request) {
        if (request.method == HttpRequest.Method.GET) {
            if(request.requestHeaders.containsKey("Accept-Encoding")) return getHandlers.get("Accept-Encoding");
            else if (request.requestTarget.equals("/user-agent")) return getHandlers.get("/user-agent");
            else if (request.requestTarget.startsWith("/echo/")) return getHandlers.get("/echo");
            else if (request.requestTarget.startsWith("/files/")) return getHandlers.get("/files");
            else if (request.requestTarget.equals("/")) {
                return (req, dir) -> new HttpResponse.Builder().code(200).reason("OK").build();
            }
        } else if (request.method == HttpRequest.Method.POST && request.requestTarget.startsWith("/files/")) {
            return new FilePostHandler();
        }

        return defaultHandler;
    }
}
