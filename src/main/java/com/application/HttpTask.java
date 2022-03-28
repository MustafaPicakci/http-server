package com.application;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpTask implements Runnable {
    private final Socket client;
    private static final int SP = 0x20;//32 space
    private static final int CR = 0x0D;//13 \r
    private static final int LF = 0x0A;//10 \n

    public HttpTask(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            handle(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(Socket socket) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(inputStreamReader);

        //code to read and print headers
        StringBuilder header = new StringBuilder();
        String headerLine;
        while (StringUtils.isNotBlank(headerLine = br.readLine())) {
            System.out.println(headerLine);
        }
        System.out.println(header);

        //code to read the post payload data
        StringBuilder payload = new StringBuilder();
        while (br.ready()) {
            char c = ((char) br.read());
            if (c == CR) {
                c = (char) br.read();
                if (c == LF) {
                }
            } else {
                if (StringUtils.isNotBlank(String.valueOf(c))) {
                    payload.append(c);
                }
            }
        }
        System.out.println(payload);

        OutputStream clientOutput = socket.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + 500).getBytes());
        clientOutput.write(("ContentType: " + "contentType" + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(("It Works").getBytes());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        socket.close();
    }
}
