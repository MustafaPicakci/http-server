package com.application.model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private String contentType;
    private Map<Object,Object> header=new HashMap<>();

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<Object, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<Object, Object> header) {
        this.header = header;
    }
}
