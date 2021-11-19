package com.king.service;

import com.sun.net.httpserver.HttpExchange;

public interface ServiceExecutor {
    void execute(HttpExchange exchange);
}
