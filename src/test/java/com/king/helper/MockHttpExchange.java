package com.king.helper;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.HashMap;

public class MockHttpExchange extends HttpExchange {
    public MockHttpExchange() {

    }
    @Override
    public Headers getRequestHeaders() {
        return null;
    }

    @Override
    public Headers getResponseHeaders() {
        return new Headers();
    }

    @Override
    public URI getRequestURI() {
        return URI.create("/1/test");
    }

    @Override
    public String getRequestMethod() {
        return "GET";
    }

    @Override
    public HttpContext getHttpContext() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public InputStream getRequestBody() {
        return null;
    }

    @SneakyThrows
    @Override
    public OutputStream getResponseBody() {
        return new FileOutputStream("test.txt");
    }

    @Override
    public void sendResponseHeaders(int rCode, long responseLength) throws IOException {

    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getResponseCode() {
        return 0;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {

    }

    @Override
    public HttpPrincipal getPrincipal() {
        return null;
    }
}
