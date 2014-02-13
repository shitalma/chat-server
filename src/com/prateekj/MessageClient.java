package com.prateekj;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MessageClient {
    private final Socket socket;
    private final MessageClientObserver observer;
    private boolean keepRunning = true;

    public MessageClient(Socket socket, MessageClientObserver observer) {

        this.socket = socket;
        this.observer = observer;

    }

    public void startReadThread() {
        Runnable infiniteReadLoop = new Runnable() {
            @Override
            public void run() {
                while (keepRunning)
                    checkForMessage();
            }
        };
        new Thread(infiniteReadLoop).start();
    }

    private void checkForMessage() {
        Message message;
        try {
            socket.setSoTimeout(100);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            message = (Message) objectInputStream.readObject();
            if(message.name.equals("data"))
                observer.onMessage(this,message.content);
        }catch (SocketTimeoutException ste){
        }
        catch (SocketException se){
            if(se.getMessage().contains("Connection reset"))
                observer.onConnectionClosed(this);
        }
        catch (IOException e) {
            observer.onError(this,e);
        } catch (ClassNotFoundException e) {
            observer.onError(this,e);
        }
    }

    public void send(String content) {
        Message message = new Message();
        message.name = "data";
        message.content = content;
        try {
            new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException("Could not send message ", e);
        }
    }

    public void stop() {
        keepRunning = false;
    }
}
