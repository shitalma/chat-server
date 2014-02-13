package com.prateekj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageServer {
    private final ChatFactory chatFactory;
    private MessageServerObserver observer;
    private ServerSocket serverSocket;
    List<Socket> sockets = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();

    public MessageServer(MessageServerObserver observer, ChatFactory chatFactory) {
        this.chatFactory = chatFactory;
        this.observer = observer;
    }

    public MessageServer(MessageServerObserver observer) {
        this(observer, new ChatFactory());
    }

    private void infiniteReadLoop(Socket socket){
        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            observer.onError(e);
        }
        while(scanner.hasNext())
            observer.onMessage(scanner.nextLine());
    }
    private void infiniteAcceptLoop(){

        do{
            try {
                Socket client = serverSocket.accept();
                sockets.add(client);
                createReadThread(client);
            } catch (IOException e) {
                observer.onError(e);
            }
        }while(true);

    }

    private void createReadThread(final Socket client) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                infiniteReadLoop(client);
            }
        });
        thread.start();
        threads.add(thread);
    }

    public void start(){

        try {
            serverSocket = chatFactory.createServerSocket();

        } catch (IOException e) {
            throw new RuntimeException("Could not start server",e);
        }
        createAcceptThread();
    }

    private ServerSocket createServerSocket() throws IOException {
        return chatFactory.createServerSocket();
    }

    private void createAcceptThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                infiniteAcceptLoop();
            }
        });
        thread.start();
        threads.add(thread);
    }

    public void stop() {
        try {
            for (Thread thread : threads) {
                thread.stop();
            }
            for (Socket socket : sockets)
                socket.close();

            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not close",e);
        }
    }
}
