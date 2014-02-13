package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatFactory {
    ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(9090);
    }

    public UserInputReader createUserInputReader(UserInputReaderObserver observer) {
        return new UserInputReader(new SystemInputScanner(),observer);
    }

    public MessageServer createMessageServer(MessageServerObserver observer) {
        return new MessageServer(observer);
    }
    public UserDisplay createUserDisplay(){
        return new UserDisplay();
    }
}