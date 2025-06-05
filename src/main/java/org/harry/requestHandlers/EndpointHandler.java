package org.harry.requestHandlers;

import org.harry.HttpRequest;
import org.harry.HttpResponse;

import java.io.IOException;


public interface EndpointHandler {
    HttpResponse handle(HttpRequest request, String fileDirectory) throws IOException;
}
