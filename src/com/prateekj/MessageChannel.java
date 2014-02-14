package com.prateekj;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MessageChannel {
    private final Socket socket;
    private MessageChannelObserver observer;
    private boolean keepRunning = false;
    private Thread readThread;
    private final Runnable infiniteReadLoop = new Runnable() {
        @Override
        public void run() {
            while (keepRunning)
                checkForMessage();
        }
    };

    public MessageChannel(Socket socket) {
        this.socket = socket;
    }

    public void startListeningForMessages(MessageChannelObserver observer) {
        waitForStoppingExistingThread();
        keepRunning = true;
        this.observer = observer;
        readThread = new Thread(infiniteReadLoop);
        readThread.start();
    }

    private void checkForMessage() {

        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            observer.onMessage(this, objectInputStream.readObject());
        } catch (SocketTimeoutException ste) {
        } catch (SocketException se) {
            if (se.getMessage().contains("Connection reset"))
                observer.onConnectionClosed(this);
        } catch (EOFException eofe) {
            observer.onConnectionClosed(this);
        } catch (IOException e) {
            observer.onError(this, e);
        } catch (ClassNotFoundException e) {
            observer.onError(this, e);
        }
    }

    public void send(Object message) {
        try {
            new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException("Could not send message ", e);
        }
    }

    private void waitForStoppingExistingThread() {
        if (keepRunning) {
            keepRunning = false;
            try {
                readThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        waitForStoppingExistingThread();
        try {
            socket.close();
        } catch (IOException e) {
            observer.onError(this, e);
        }
    }
}
