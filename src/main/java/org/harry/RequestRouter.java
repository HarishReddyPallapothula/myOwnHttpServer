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
            if (request.requestTarget.equals("/user-agent")) return getHandlers.get("/user-agent");
            if (request.requestTarget.startsWith("/echo/")) return getHandlers.get("/echo");
            if (request.requestTarget.startsWith("/files/")) return getHandlers.get("/files");
            if (request.requestTarget.equals("/")) {
                return (req, dir) -> new HttpResponse.Builder().code(200).reason("OK").build();
            }
        }

        if (request.method == HttpRequest.Method.POST && request.requestTarget.startsWith("/files/")) {
            return new FilePostHandler();
        }

        return defaultHandler;
    }
}
