package com.king.service;

import com.king.dto.Session;
import com.king.server.ServerApp;
import com.king.util.ResponseUtils;
import com.sun.net.httpserver.HttpExchange;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service to generate session key and store it to the session map
 */
public class LoginService implements ServiceExecutor {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(LoginService.class));

    @Override
    public void execute(HttpExchange exchange) {
        String extractedId = exchange.getRequestURI().toString().split("/")[1];

        try {
            int userId = Integer.parseInt(extractedId);
            UUID uuid = UUID.randomUUID();
            String sessionKey = uuid.toString();

            ResponseUtils.writeResponse(exchange, 200, sessionKey);

            ServerApp.sessionMap.put(sessionKey, Session.builder().userId(userId).keyGenTime(LocalDateTime.now()).build());

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "Session key generation failed", e);
            ResponseUtils.writeResponse(exchange, 500, "Session key generation failed");
        }

    }
}
