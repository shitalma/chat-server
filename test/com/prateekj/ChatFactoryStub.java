package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;

import static org.mockito.Mockito.mock;

public class ChatFactoryStub extends ChatFactory{
    public ServerSocket serverSocket = mock(ServerSocket.class);
    public UserInputReader userInputReader = mock(UserInputReader.class);
    public MessageServer messageServer = mock(MessageServer.class);
    public UserDisplay userDisplay = mock(UserDisplay.class);

    @Override
    ServerSocket createServerSocket() throws IOException {
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
