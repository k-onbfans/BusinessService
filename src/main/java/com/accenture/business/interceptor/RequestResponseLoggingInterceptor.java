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
        logger.info(
                "\n" +
                "Method: " + request.getMethod().name() + "\n" +
                "Header: " + request.getHeaders().toString() + "\n" +
                "url: " + request.getURI() + "\n" +
                "requestBody: " + requestBody
        );
        ClientHttpResponse response = execution.execute(request, body);
        logger.info("\n" +
                "Method: " + request.getMethod().name() + "\n" +
                "Header: " + response.getHeaders().toString() + "\n" +
                "responseBody: " + response.getBody()
        );
        return response;
    }
}