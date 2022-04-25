package com.application;

import com.application.model.HttpRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        ObjectMapper mapper = new ObjectMapper();


        HttpRequest httpRequest = new HttpRequest();

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(inputStreamReader);

        //code to read and print headers
        StringBuilder header = new StringBuilder();
        String headerLine;
        while (StringUtils.isNotBlank(headerLine = br.readLine())) {
            if (headerLine.split(":").length > 1) {
                String key = headerLine.split(":")[0];
                String value = headerLine.split(":")[1];
                httpRequest.getHeader().put(key, value);
            }
        }
        System.out.println(httpRequest);

        //code to read the post payload data
        StringBuilder body = new StringBuilder();
        while (br.ready()) {
            char c = ((char) br.read());
            if (c == CR) {
                c = (char) br.read();
                if (c == LF) {
                }
            } else {
                if (StringUtils.isNotBlank(String.valueOf(c))) {
                    body.append(c);
                }
            }
        }
        if (body.length() > 0) {
            String[] fields= String.valueOf(body).split(",");
           Map<Object,Object> mapBody= mapper.readValue(String.valueOf(body), HashMap.class);

        }
        System.out.println(httpRequest);
        System.out.println(body);

        OutputStream clientOutput = socket.getOutputStream();
        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write(("ContentType: text/html\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write("<b>It works!</b>".getBytes());
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        socket.close();
    }
}
