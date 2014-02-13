package com.prateekj;

public class ChatClient implements UserInputReaderObserver, MessageClientObserver {
    private final UserInputReader inputReader;
    private ChatFactory factory;
    private MessageClient messageClient;

    public ChatClient(ChatFactory factory) {
        this.factory = factory;
        inputReader = factory.createUserInputReader(this);
    }

    public void run() {
        messageClient = factory.connectTo("127.0.0.1", this);
        inputReader.start();
    }

    @Override
    public void onInput(String text) {
        messageClient.send(text);
    }
}
