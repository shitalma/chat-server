package com.prateekj;

import java.net.ServerSocket;

public class SocketServer {
    private final MessageClientObserver clientObserver;
    private ChatFactory chatFactory;
    private SocketServerObserver observer;
    private ServerSocket serverSocket;
    private boolean keepRunning = true;

    public SocketServer(ChatFactory chatFactory, SocketServerObserver observer, MessageClientObserver clientObserver) {
        this.chatFactory = chatFactory;
        this.observer = observer;
        this.clientObserver = clientObserver;
    }

    public void start() {
        this.serverSocket = chatFactory.createServerSocket();
        startAcceptThread();
    }

    private void startAcceptThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (keepRunning) {
                        MessageClient messageClient = chatFactory.acceptFrom(serverSocket, clientObserver);
                        if(messageClient!=null)
                            observer.onNewConnection(messageClient);
                    }
                    serverSocket.close();
                } catch (Exception e) {
                    observer.onError(e);
                }

            }
        };
        new Thread(runnable, "accept").start();
    }

    public void stop() {
        keepRunning = false;
    }
}