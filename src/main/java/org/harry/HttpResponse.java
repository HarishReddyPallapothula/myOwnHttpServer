package org.harry;

import java.util.Map;

public class HttpResponse {
    private static final String DEFAULT_VERSION = "HTTP/1.1";
    private static final String DEFAULT_CONTENT_TYPE = "text/plain";
    private static final String CRLF = "\r\n";

    private final ResponseHeader responseHeader;
    private final String responseBody;
    private final int code;
    private final String reason;
    private final String httpResponse;

    private HttpResponse(Builder builder) {
        this.responseHeader = builder.responseHeader;
        this.responseBody = builder.responseBody;
        this.code = builder.code;
        this.reason = builder.reason;
        this.httpResponse = buildResponse(DEFAULT_VERSION, String.valueOf(code), reason, responseHeader, responseBody);
    }

    private String buildResponse(String version, String code, String reason,
                                 ResponseHeader responseHeader, String responseBody) {
        StringBuilder sb = new StringBuilder();
        sb.append(version).append(' ').append(code).append(' ').append(reason).append(CRLF);
        for (Map.Entry<String, String> entry : responseHeader.getHeaderMap().entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(CRLF);
        }
        sb.append(CRLF);
        sb.append(responseBody);
        return sb.toString();
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public static class Builder {
        private ResponseHeader responseHeader;
        private String responseBody = "";
        private int code = 200;
        private String reason = "OK";

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder responseBody(String body) {
            this.responseBody = body;
            return this;
        }

        public Builder responseHeader(ResponseHeader header) {
            this.responseHeader = header;
            return this;
        }

        public HttpResponse build() {
            // If responseHeader is not provided, create a default one
            if (this.responseHeader == null) {
                String contentLength = String.valueOf(responseBody.length());
                this.responseHeader = new ResponseHeader.Builder()
                        .contentLength(contentLength)
                        .contentType(DEFAULT_CONTENT_TYPE)
                        .build();
                //this.responseHeader = new ResponseHeader(DEFAULT_CONTENT_TYPE, contentLength);
            }
            return new HttpResponse(this);
        }
    }
}
