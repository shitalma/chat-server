package com.prateekj;

import java.util.ArrayList;
import java.util.List;

public class ChatServer implements MessageServerListener, MessageChannelListener, UserInputReaderObserver {

    private final UserInputReader userInputReader;
    private final MessageServer messageServer;
    private final UserDisplay userDisplay;
    List<MessageChannel> channels = new ArrayList<>();

    public ChatServer(ChatFactory factory) {
        this.userInputReader = factory.createUserInputReader(this);
        this.messageServer = factory.createMessageServer();
        this.userDisplay = factory.createUserDisplay();
    }

    public void run() {
        userInputReader.start();
        messageServer.startListeningForConnections(this);
        System.out.println("started server:");
    }

    @Override
    public void onNewConnection(MessageChannel channel) {
        channel.startListeningForMessages(this);
        channels.add(channel);
    }

    @Override
    public void onError(Exception e) {
        throw new RuntimeException("message server error", e);
    }

    private void quit() {
        for (MessageChannel channel : channels) channel.stop();
        userInputReader.stop();
        messageServer.stop();
        userDisplay.exit();
    }

    @Override
    public void onInput(String text) {
        if (text.equals("quit")) quit();
        else
            for (MessageChannel channel : channels)
                channel.send("server:" + text);
    }

    @Override
    public void onError(MessageChannel channel, Exception e) {
        throw new RuntimeException("error on ChatClient", e);
    }

    @Override
    public void onMessage(MessageChannel channel, Object message) {
        userDisplay.show((String) message);
    }

    @Override
    public void onConnectionClosed(MessageChannel channel) {
        channel.stop();
        channels.remove(channel);
    }
}