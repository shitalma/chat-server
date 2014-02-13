package com.prateekj;

public class ChatServer implements MessageServerObserver, UserInputReaderObserver{

    private final UserInputReader userInputReader;
    private final MessageServer messageServer;
    private final UserDisplay userDisplay;

    public ChatServer(ChatFactory factory) {
        this.userInputReader = factory.createUserInputReader(this);
        this.messageServer = factory.createMessageServer(this);
        this.userDisplay = factory.createUserDisplay();
    }

    public void run(){

        userInputReader.start();
        messageServer.start();
        System.out.println("started server:");
    }

    @Override
    public void onMessage(String message) {
        userDisplay.show(message);
    }

    @Override
    public void onError(Exception e) {
        throw new RuntimeException("Gaya Paani Taalab mein",e);
    }

    @Override
    public void onQuit() {
        messageServer.stop();
        userDisplay.exit();
    }
}
