package com.prateekj;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageClient {
    private final Socket socket;
    private final MessageClientObserver observer;

    public MessageClient(Socket socket, MessageClientObserver observer) {

        this.socket = socket;
        this.observer = observer;
    }

    public void send(String message) {

        OutputStream stream = null;
        try {
            System.out.println("input stream available "+socket.getInputStream().available());
            stream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Could not send message ",e);
        }
        new PrintWriter(stream,true).println(message);
    }
}
