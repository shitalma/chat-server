package com.prateekj;

import java.util.ArrayList;
import java.util.List;

public class MessageServer implements MessageClientObserver, SocketServerObserver {
    private final ChatFactory chatFactory;
    private final SocketServer socketServer;
    private MessageServerObserver observer;
    List<MessageClient> clients = new ArrayList<>();


    public MessageServer(MessageServerObserver observer, ChatFactory chatFactory) {
        this.chatFactory = chatFactory;
        this.observer = observer;
        this.socketServer = chatFactory.createSocketServer(this, this);
    }

    public MessageServer(MessageServerObserver observer) {
        this(observer, new ChatFactory());
    }

    public void start() {
        socketServer.start();
    }

    public void stop() {
        for (MessageClient client : clients) client.stop();
        socketServer.stop();
    }

    public void send(String message) {
        for (MessageClient client : clients) client.send(message);
    }

    @Override
    public void onNewConnection(MessageClient messageClient) {
        clients.add(messageClient);
    }

    @Override
    public void onError(Exception e) {
        observer.onError(e);
    }

    @Override
    public void onError(MessageClient client, Exception e) {
        observer.onError(e);
    }

    @Override
    public void onMessage(MessageClient client, String message) {
        observer.onMessage(message);
    }

    @Override
    public void onConnectionClosed(MessageClient client) {
        client.stop();
        clients.remove(client);
    }
}