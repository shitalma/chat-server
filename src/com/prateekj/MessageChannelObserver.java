package com.prateekj;

public interface MessageChannelObserver {
    void onError(MessageChannel client, Exception e);

    void onMessage(MessageChannel client, Object message);

    void onConnectionClosed(MessageChannel client);
}
