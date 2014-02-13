package com.prateekj;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChatServerTest {
    ChatFactoryStub stub = new ChatFactoryStub();
    ChatServer chatServer = new ChatServer(stub);
    @Test
    public void startsMessageServerAndInputReaderWhenRun() {

        chatServer.run();
        verify(stub.messageServer, times(1)).start();
        verify(stub.userInputReader, times(1)).start();
    }
    @Test
    public void showsNewMessageToUser(){
         chatServer.onMessage("new message");
        verify(stub.userDisplay,times(1)).show("new message");
    }
    @Test(expected = RuntimeException.class)
    public void throwsExceptionOnError(){
        chatServer.onError(new Exception());
    }

    @Test
    public void stopsMessageServerAndExitsUserDisplayOnQuit(){
        chatServer.onQuit();
        verify(stub.messageServer,times(1)).stop();
        verify(stub.userDisplay,times(1)).exit();
    }
}