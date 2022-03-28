package com.application;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

    public void start(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket client = socket.accept();
                    executorService.execute(new HttpTask(client));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
