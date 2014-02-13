package com.prateekj;

import java.net.ServerSocket;

import static org.mockito.Mockito.mock;

public class ChatFactoryStub extends ChatFactory{
    public ServerSocket serverSocket = mock(ServerSocket.class);
    public UserInputReader userInputReader = mock(UserInputReader.class);
    public MessageServer messageServer = mock(MessageServer.class);
    public UserDisplay userDisplay = mock(UserDisplay.class);
    public MessageClient client = mock(MessageClient.class);
    private SocketServer socketServer = mock(SocketServer.class);


    @Override
    public MessageClient connectTo(String serverAddress, MessageClientObserver observer) {
        return client;
    }

    @Override
    public SocketServer createSocketServer(SocketServerObserver observer, MessageClientObserver clientObserver) {
        return socketServer;
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
    public MessageServer createMessageServer(MessageServerObserver observer) {
        return messageServer;
    }

    @Override
    public UserDisplay createUserDisplay() {
        return userDisplay;
    }
}
