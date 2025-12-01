package com.example.servervagasrest;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;


public class RequestResponseLoggingFilter implements Filter {

    public static final int MAX_LOGS = 100;
    public static final Deque<RequestResponseLog> logs = new ConcurrentLinkedDeque<>();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res);

        if (req.getRequestURI().startsWith("/admin/dashboard") || req.getRequestURI().startsWith("/h2-console") || req.getRequestURI().contains("/swagger") || req.getRequestURI().contains("/dashboard.html")) {
            res.copyBodyToResponse();
            return;
        }

        String requestBody = new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("----------------------------------");
        System.out.println("---- [REQUEST] ----|");
        System.out.println(req.getMethod() + " " + req.getRequestURI());
        System.out.println("Body: " + requestBody);

        System.out.println("---- [RESPONSE] ----");
        System.out.println("Status: " + res.getStatus());
        System.out.println("Body: " + responseBody);
        System.out.println("---------------------------------\n");

        RequestResponseLog log = new RequestResponseLog(
                req.getMethod(),
                req.getRequestURI(),
                requestBody,
                res.getStatus(),
                responseBody,
                new Date()
        );
        logs.addFirst(log);
        if (logs.size() > MAX_LOGS) {
            logs.removeLast();
        }

        res.copyBodyToResponse();
    }

    public static class RequestResponseLog {
        public String method;
        public String uri;
        public String requestBody;
        public int status;
        public String responseBody;
        public Date timestamp;

        public RequestResponseLog(String method, String uri, String requestBody, int status, String responseBody, Date timestamp) {
            this.method = method;
            this.uri = uri;
            this.requestBody = requestBody;
            this.status = status;
            this.responseBody = responseBody;
            this.timestamp = timestamp;
        }
    }
}

