package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatFactory {
    ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(9090);
    }

    public UserInputReader createUserInputReader(UserInputReaderObserver observer) {
        return new UserInputReader(this,new SystemInputScanner(),observer);
    }

    public MessageServer createMessageServer(MessageServerObserver observer) {
        return new MessageServer(observer);
    }
    public UserDisplay createUserDisplay(){
        return new UserDisplay();
    }

    private Socket connectTo(String serverAddress, int port) {
        try {
            return new Socket(serverAddress,port);
        } catch (IOException e) {
            throw new RuntimeException("could not connect to "+serverAddress+" at "+port,e);
        }
    }
    public MessageClient connectTo(String serverAddress, MessageClientObserver observer){
        return new MessageClient(connectTo(serverAddress,9090),observer);
    }
}