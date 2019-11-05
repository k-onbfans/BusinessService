package com.accenture.business.handler.log;

import com.accenture.business.response.SimpleHttpInputMessage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class RequestBodyLogger extends RequestBodyAdviceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestBodyLogger.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        RequestMapping isHTTPRequest = methodParameter.getMethodAnnotation(RequestMapping.class);
        return isHTTPRequest != null;
    }

    public RequestBodyLogger() {
        super();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] body = IOUtils.toByteArray(inputMessage.getBody());
        String strBody = new String(body, StandardCharsets.UTF_8);
        logger.info(parameter.getMethod().getName() + "\ntargetType:" + targetType.getTypeName() + "\nparameter:" + parameter.getParameterName() + "\n" + strBody);
        return new SimpleHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(body));
    }


    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.warn("This RequestBody is empty;");
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}
