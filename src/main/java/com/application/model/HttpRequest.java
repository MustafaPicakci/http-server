package com.application.model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private Map<Object,Object> header=new HashMap<>();

    public Map<Object, Object> getBody() {
        return body;
    }

    public void setBody(Map<Object, Object> body) {
        this.body = body;
    }

    private Map<Object,Object> body =new HashMap<>();

    public Map<Object, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<Object, Object> header) {
        this.body = header;
    }
}
