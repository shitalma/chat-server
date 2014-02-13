package com.prateekj;

public class ChatClient implements UserInputReaderObserver, MessageClientObserver {
    private final UserInputReader inputReader;
    private final UserDisplay userDisplay;
    private ChatFactory factory;
    private MessageClient messageClient;

    public ChatClient(ChatFactory factory) {
        this.factory = factory;
        inputReader = factory.createUserInputReader(this);
        userDisplay = factory.createUserDisplay();
    }

    public void run() {
        messageClient = factory.connectTo("127.0.0.1", this);
        inputReader.start();
    }

    @Override
    public void onInput(String text) {
        if(text.equals("quit")) {
            messageClient.stop();
            userDisplay.exit();
        }
        else
            messageClient.send(text);
    }

    @Override
    public void onError(MessageClient client, Exception e) {
        throw new RuntimeException(e);
    }

    @Override
    public void onMessage(MessageClient client, String message) {
        userDisplay.show(message);
    }

    @Override
    public void onConnectionClosed(MessageClient client) {
        userDisplay.exit();
    }
}
