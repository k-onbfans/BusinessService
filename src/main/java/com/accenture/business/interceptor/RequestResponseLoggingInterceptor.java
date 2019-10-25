package com.accenture.business.interceptor;

import com.accenture.business.handler.log.RequestBodyLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestBodyLogger.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String requestBody = new String(body, "UTF-8");
        logger.info(
                request.getMethod().name() + "\n" +
                request.getHeaders().toString() + "\n" +
                requestBody + "\n" +
                body.length + "\n" +
                "External call request:" + request.getURI()
        );
        ClientHttpResponse response = execution.execute(request, body);
        MediaType contentType = response.getHeaders().getContentType();
        logger.info(
                request.getMethod().name() + "\n" +
                StringUtils.EMPTY + "\n" +
                response.getHeaders().toString() + "\n" +
                -1 + "\n" +
                String.format(
                        "External call response: %s " +
                                "The response content type is %s. " +
                                "According to the config RESTTEMPLATE_DISABLE_BODY_LOG_CONTENT_TYPE, this response body and its size won't be logged.",
                        request.getURI(),
                        contentType.toString()
                )
        );
        return response;
    }
}