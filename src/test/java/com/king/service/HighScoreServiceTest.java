package com.king.service;

import com.king.dto.Score;
import com.king.helper.MockHttpExchange;
import com.king.server.ServerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class HighScoreServiceTest {

    private final HighScoreService underTest = new HighScoreService();

    @Test
    public void testExecute() {
        ServerApp.scoreMap.put(1, List.of(Score.builder().userId(123).scoreNumbers(List.of(1,2,3)).build()));
        underTest.execute(new MockHttpExchange());
    }

    @Test
    public void testExecuteWhenMapEmpty() {
        underTest.execute(new MockHttpExchange());
    }
}