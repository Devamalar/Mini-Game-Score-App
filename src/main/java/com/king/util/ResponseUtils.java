package com.king.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseUtils {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ResponseUtils.class));

    public static void writeResponse(HttpExchange exchange, int code, String response) {
        OutputStream outputStream = exchange.getResponseBody();
        try {
            if(response == null) {
                exchange.sendResponseHeaders(code, -1);
            }
            else {
                exchange.sendResponseHeaders(code, response.length());
                outputStream.write(response.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
