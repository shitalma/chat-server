package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;

public class MessageServer {
    private final ChatFactory chatFactory;
    private MessageServerObserver observer;
    private ServerSocket serverSocket;
    private boolean keepRunning = false;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            infiniteAcceptLoop();
        }
    };
    private Thread acceptThread;

    private void infiniteAcceptLoop() {
        try {
            while (keepRunning) {
                MessageChannel channel = chatFactory.acceptFrom(serverSocket);
                if(channel!=null)
                    observer.onNewConnection(channel);
            }
        } catch (Exception e) {
            observer.onError(e);
        }
    }

    public MessageServer(ChatFactory chatFactory) {
        this.chatFactory = chatFactory;
        serverSocket = chatFactory.createServerSocket();
    }

    public void startListeningForConnections(MessageServerObserver observer) {
        waitForStoppingExistingThread();
        keepRunning = true;
        this.observer = observer;
        startAcceptThread();
    }

    private void startAcceptThread() {
        acceptThread = new Thread(runnable, "accept");
        acceptThread.start();
    }

    private void waitForStoppingExistingThread() {
        if (keepRunning) {
            keepRunning = false;
            try {
                acceptThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        waitForStoppingExistingThread();
        try {
            serverSocket.close();
        } catch (IOException e) {
            observer.onError(e);
        }
    }
}