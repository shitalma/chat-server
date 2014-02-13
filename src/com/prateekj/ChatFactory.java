package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ChatFactory {

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
    private MessageClient createMessageClient(MessageClientObserver observer, Socket socket) {
        MessageClient messageClient = new MessageClient(socket, observer);
        messageClient.startReadThread();
        return messageClient;
    }
    public MessageClient connectTo(String serverAddress, MessageClientObserver observer){
        return createMessageClient(observer, connectTo(serverAddress, 9090));
    }

    public MessageClient acceptFrom(ServerSocket serverSocket, MessageClientObserver observer){
        try {
            serverSocket.setSoTimeout(100);
            return createMessageClient(observer, serverSocket.accept());
        }
        catch (SocketTimeoutException se){
            return null;
        }
        catch (IOException e) {
            throw new RuntimeException("Could not accept",e);
        }
    }

    public SocketServer createSocketServer(SocketServerObserver observer, MessageClientObserver clientObserver) {
        return new SocketServer(this,observer,clientObserver);
    }

    public ServerSocket createServerSocket() {
        try {
            return new ServerSocket(9090);
        } catch (IOException e) {
            throw new RuntimeException("Could not create ",e);
        }
    }
}