package com.accenture.business.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String requestBody = new String(body, StandardCharsets.UTF_8);

        String str1 = String.format("%n Method: %s %n Header: %s %n url: %s %n requestBody: %s",
                request.getMethod().name(),
                request.getHeaders().toString(),
                request.getURI(),
                requestBody);
        logger.info(str1);
        ClientHttpResponse response = execution.execute(request, body);
        String str2 = String.format("%n Method: %s %n Header: %s %n responseBody: %s",
                request.getMethod().name(),
                response.getHeaders().toString(),
                response.getBody());
        logger.info(str2);
        return response;
    }
}