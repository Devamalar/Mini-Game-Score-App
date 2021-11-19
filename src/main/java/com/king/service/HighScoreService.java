package com.king.service;

import com.king.dto.Score;
import com.king.server.ServerApp;
import com.king.util.ResponseUtils;
import com.sun.net.httpserver.HttpExchange;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service to get the first 15 top scorers along with respective user id
 */
public class HighScoreService implements ServiceExecutor {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(HighScoreService.class));

    @Override
    public void execute(HttpExchange exchange) {
        String extractedId = exchange.getRequestURI().toString().split("/")[1];
        int levelId = Integer.parseInt(extractedId);

        List<Score> scoreList = ServerApp.scoreMap.get(levelId);
        if (scoreList == null) {
            LOGGER.log(Level.INFO, "No scores found for the level");
            ResponseUtils.writeResponse(exchange, 200, "");
        } else {
            Map<Integer, Integer> tempMap = new HashMap<>();
            for(Score score: scoreList) {
                int scoreNumber = score.getScoreNumbers().stream().max(Comparator.naturalOrder()).get();
                tempMap.put(score.getUserId(), scoreNumber);
            }
            Map<Integer, Integer> finalMap = tempMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(15)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            ResponseUtils.writeResponse(exchange, 200, finalMap.toString());
        }
    }
}
