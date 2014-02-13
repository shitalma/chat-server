package com.prateekj;

public interface MessageClientObserver {
    void onError(MessageClient client, Exception e);

    void onMessage(MessageClient client, String message);

    void onConnectionClosed(MessageClient client);
}
