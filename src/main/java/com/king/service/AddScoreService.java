package com.king.service;

import com.king.dto.Score;
import com.king.dto.Session;
import com.king.server.ServerApp;
import com.king.util.ResponseUtils;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service to add the score of the user to the Score map based on level
 */
public class AddScoreService implements ServiceExecutor {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(AddScoreService.class));

    @Override
    public void execute(HttpExchange exchange) {
        String extractedId = exchange.getRequestURI().toString().split("/")[1];
        int levelId = Integer.parseInt(extractedId);
        String request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                .lines().collect(Collectors.joining("\n"));
        int newScore = Integer.parseInt(request);
        String sessionKey = exchange.getRequestURI().toString().split("sessionkey=")[1];

        LocalDateTime currentTime = LocalDateTime.now();
        Session session = ServerApp.sessionMap.get(sessionKey);
        if (session != null) {
            LocalDateTime keyGenTime = session.getKeyGenTime();
            if (currentTime.minus(10, ChronoUnit.MINUTES).isBefore(keyGenTime)) {
                List<Score> scoreList = ServerApp.scoreMap.getOrDefault(levelId, new ArrayList<>());

                scoreList.add(Score.builder().userId(session.getUserId())
                        .scoreNumbers(getUpdatedScoreNumList(scoreList, session.getUserId(), newScore)).build());
                ServerApp.scoreMap.put(levelId, scoreList);
                LOGGER.log(Level.INFO, "Score list for the users with scores: " + scoreList);
                LOGGER.log(Level.INFO, "Score map for the level with score: " + ServerApp.scoreMap);
                ResponseUtils.writeResponse(exchange, 200, null);
            } else {
                LOGGER.log(Level.SEVERE, "Session expired");
                ResponseUtils.writeResponse(exchange, 401, "Session expired");
            }
        } else {
            LOGGER.log(Level.SEVERE, "User session not found");
            ResponseUtils.writeResponse(exchange, 401, "User session not found");
        }

    }

    /**
     * Get the updated list of score numbers for the given user id and remove the entry of the user from the list
     *
     * @param scoreList the list of score objects(user id to score numbers)
     * @param userId    User id of the scorer
     * @param newScore  the new score to be updated to the list
     * @return Updated score number list
     */
    private List<Integer> getUpdatedScoreNumList(List<Score> scoreList, int userId, int newScore) {
        List<Integer> updatedList = new ArrayList<>();
        Optional<Score> scoreOptional = scoreList.stream()
                .filter(score -> score.getUserId() == userId).findFirst();
        if (scoreOptional.isPresent()) {
            updatedList = scoreOptional.get().getScoreNumbers();
            scoreList.remove(scoreOptional.get());
        }
        updatedList.add(newScore);
        return updatedList;
    }
}
