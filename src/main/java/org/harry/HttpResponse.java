package org.harry;

import java.security.cert.CRL;
import java.util.Map;

public class HttpResponse {
    private static final String DEFAULT_VERSION = "HTTP/1.1";
    private static final String DEFAULT_CONTENT_TYPE = "text/plain";
    private static final String DEFAULT_CONTENT_LENGTH = "0";
    static String contentType = "text/plain";
    private static final String CRLF = "\r\n";

    ResponseHeader responseHeader;
    String responseBody;
    String httpResponse;
    int code;
    String reason;

    public HttpResponse(ResponseHeader responseHeader, String responseBody, int code, String reason) {
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.code = code;
        this.reason = reason;
        this.httpResponse = buildResponse(DEFAULT_VERSION, String.valueOf(code), reason, responseHeader, responseBody);
    }

    private String buildResponse(String version, String code, String reason, ResponseHeader responseHeader, String responseBody){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(version).append(' ')
                .append(code).append(' ')
                .append(reason).append(CRLF);

        for(Map.Entry<String, String> entry : responseHeader.getHeaderMap().entrySet()){
            stringBuilder.append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append(CRLF);
        }

        stringBuilder.append(CRLF);
        stringBuilder.append(responseBody);
        return stringBuilder.toString();
    }

    public HttpResponse(int code, String reason, String body, ResponseHeader responseHeader) {
        this(responseHeader,body, code, reason);
    }

    public HttpResponse(int code, String reason, String body) {
        this(new ResponseHeader(DEFAULT_CONTENT_TYPE, String.valueOf(body.length())),body,code, reason);
    }

    public HttpResponse(int code, String reason) {
        this(new ResponseHeader(DEFAULT_CONTENT_TYPE, DEFAULT_CONTENT_LENGTH),"",code, reason);
    }
    
}
