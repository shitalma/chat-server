package com.prateekj;

import java.net.ServerSocket;

import static org.mockito.Mockito.mock;

public class ChatFactoryStub extends ChatFactory{
    public ServerSocket serverSocket = mock(ServerSocket.class);
    public UserInputReader userInputReader = mock(UserInputReader.class);
    public MessageServer messageServer = mock(MessageServer.class);
    public UserDisplay userDisplay = mock(UserDisplay.class);
    public MessageChannel channel = mock(MessageChannel.class);


    @Override
    public MessageChannel connectTo(String serverAddress, MessageChannelObserver observer) {
        return channel;
    }

    @Override
    public ServerSocket createServerSocket() {
        return serverSocket;
    }

    @Override
    public UserInputReader createUserInputReader(UserInputReaderObserver observer) {
        return userInputReader;
    }

    @Override
    public MessageServer createMessageServer() {
        return messageServer;
    }

    @Override
    public UserDisplay createUserDisplay() {
        return userDisplay;
    }
}
