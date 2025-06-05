package org.harry;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, String> headerMap;

    private ResponseHeader(Builder builder) {
        this.headerMap = builder.headerMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getContentType() {
        return headerMap.get("Content-Type");
    }

    public String getContentLength() {
        return headerMap.get("Content-Length");
    }

    public static class Builder {
        private final Map<String, String> headerMap = new HashMap<>();

        public Builder contentType(String contentType) {
            headerMap.put("Content-Type", contentType);
            return this;
        }

        public Builder contentLength(String contentLength) {
            headerMap.put("Content-Length", contentLength);
            return this;
        }

        public Builder addHeader(String key, String value) {
            headerMap.put(key, value);
            return this;
        }

        public ResponseHeader build() {
                // Fallbacks for required headers
                headerMap.putIfAbsent("Content-Type", "text/plain");
                headerMap.putIfAbsent("Content-Length", "0");
                return new ResponseHeader(this);
        }
    }
}
