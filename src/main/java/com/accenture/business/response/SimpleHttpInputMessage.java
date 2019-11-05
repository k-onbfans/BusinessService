package com.accenture.business.response;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

public class SimpleHttpInputMessage implements HttpInputMessage {
    private InputStream inputStream;
    private HttpHeaders httpHeaders;

    public SimpleHttpInputMessage(HttpHeaders httpHeaders, InputStream inputStream) {
        this.inputStream = inputStream;
        this.httpHeaders = httpHeaders;
    }

    @Override
    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    @Override
    public InputStream getBody() throws IOException {
        return inputStream;
    }
}