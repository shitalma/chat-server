package com.prateekj;

public class ChatClient implements UserInputReaderObserver, MessageChannelListener {
    private final UserInputReader inputReader;
    private final UserDisplay userDisplay;
    private ChatFactory factory;
    private MessageChannel channel;

    public ChatClient(ChatFactory factory) {
        this.factory = factory;
        inputReader = factory.createUserInputReader(this);
        userDisplay = factory.createUserDisplay();
    }

    public void run(String serverAddress) {
        channel = factory.connectTo(serverAddress, this);
        channel.startListeningForMessages(this);
        inputReader.start();
    }

    @Override
    public void onInput(String text) {
        if(text.equals("quit")) {
            channel.stop();
            userDisplay.exit();
        }
        else
            channel.send(text);
    }

    @Override
    public void onError(MessageChannel client, Exception e) {
        throw new RuntimeException(e);
    }

    @Override
    public void onMessage(MessageChannel client, Object message) {
        userDisplay.show((String) message);
    }

    @Override
    public void onConnectionClosed(MessageChannel channel) {
        channel.stop();
        userDisplay.exit();
    }
}
