package org.harry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public enum Method {GET, POST, PUT, DELETE, PATCH}

    String version;
    Method method;
    Map<String, String> requestHeaders;
    String requestTarget;
    String requestBody;

    public String getVersion() {
        return version;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public HttpRequest(Method method, String version, String requestTarget, Map<String, String> requestHeaders, String requestBody) {
        this.method = method;
        this.version = version;
        this.requestTarget = requestTarget;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }




    static HttpRequest parseRequest(InputStream request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request));
        Map<String, String> requestHeaders = new HashMap<>();
        String plainRequestLine = bufferedReader.readLine();
        String[] requestLineParts = plainRequestLine.split(" ");
        Method method = Method.valueOf(requestLineParts[0]);
        String version = requestLineParts[1];
        String target = requestLineParts[2];


        String line;
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            int index = line.indexOf(':');
            if (index > 0) {
                String name = line.substring(0, index).trim();
                String value = line.substring(index + 1).trim();
                requestHeaders.put(name, value);
            }
        }

        StringBuilder requestBody = new StringBuilder();
        int ch;
        int contentLength = Integer.parseInt(requestHeaders.getOrDefault("Content-Length", "0"));
        for (var i = 0; i < contentLength; i++) {
            ch = bufferedReader.read();
            requestBody.append((char)ch);
        }
        return new HttpRequest(method, target, version, requestHeaders, requestBody.toString());
    }
}