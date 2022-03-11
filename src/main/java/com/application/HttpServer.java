package com.application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {


    public void start(int port) throws IOException {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (true) {
                try (Socket client = socket.accept()) {
                    handle(client);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handle(Socket socket) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder requestBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null && !"".equals(line)) {
            requestBuilder.append(line + "\r\n");
        }


        String request = requestBuilder.toString();
        System.out.println(request);

        OutputStream clientOutput = socket.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + 500).getBytes());
        clientOutput.write(("ContentType: " + "contentType" + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(("It Works").getBytes());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        socket.close();

       /* BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.append("It Works!");
        bufferedWriter.append("...");
        bufferedWriter.flush();
        socket.close();*/
    }

}
