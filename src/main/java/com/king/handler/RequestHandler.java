package com.king.handler;

import com.king.service.AddScoreService;
import com.king.service.HighScoreService;
import com.king.service.LoginService;
import com.king.service.ServiceExecutor;
import com.king.util.ResponseUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Request Handler to route the requests based on http method and request uri
 */
public class RequestHandler implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(RequestHandler.class));

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ServiceExecutor serviceExecutor = null;
        if ("GET".equals(exchange.getRequestMethod())) {
            if (exchange.getRequestURI().toString().split("/")[2].equals("login")) {
                serviceExecutor = new LoginService();
            } else if (exchange.getRequestURI().toString().split("/")[2].equals("highscorelist")) {
                serviceExecutor = new HighScoreService();
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            if (exchange.getRequestURI().toString().split("/")[2].equals("score")) {
                serviceExecutor = new AddScoreService();
            }
        }
        if (serviceExecutor != null) {
            serviceExecutor.execute(exchange);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid Path");
            ResponseUtils.writeResponse(exchange, 404, "Page Not Found");
        }
    }
}
