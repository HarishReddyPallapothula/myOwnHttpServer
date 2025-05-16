package org.harry;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    public ResponseHeader(String contentType, String contentLength) {
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.headerMap.put("Content-Type",contentType);
        this.headerMap.put("Content-Length", contentLength);
    }


    String contentType;
    String contentLength;
    Map<String, String> headerMap = new HashMap<>();
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }
}
