package com.king.server;

import com.king.dto.Score;
import com.king.handler.RequestHandler;
import com.king.dto.Session;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ServerApp.class));

    public static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, List<Score>> scoreMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            //ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

            int numOfCores = Runtime.getRuntime().availableProcessors();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numOfCores, numOfCores*100,
                    30L, TimeUnit.SECONDS, new SynchronousQueue<>());;
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8081), 0);
            server.createContext("/", new RequestHandler());
            server.setExecutor(threadPoolExecutor);
            server.start();
            LOGGER.log(Level.INFO, "Server started on port 8081");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private int getNumOfThreads() {
        Runtime runtime = Runtime.getRuntime();
        int numOfCores = runtime.availableProcessors();
        //long freeMemory = runtime.freeMemory();
        return numOfCores * 100;
    }
}
