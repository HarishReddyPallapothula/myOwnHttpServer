package org.harry;

import java.io.IOException;


public interface EndpointHandler {
    HttpResponse handle(HttpRequest request, String fileDirectory) throws IOException;
}
