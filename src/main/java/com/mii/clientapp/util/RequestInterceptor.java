package com.mii.clientapp.util;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class RequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!request.getURI().getPath().equals("/api/login")) {
            request.getHeaders().add("Authorization", "Basic " +
                    BasicHeader.createToken(
                            authentication.getName(),
                            authentication.getCredentials().toString())
            );
        }

        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }
}
