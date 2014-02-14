package com.prateekj;

public interface MessageChannelListener {
    void onError(MessageChannel client, Exception e);
    void onMessage(MessageChannel client, Object message);
    void onConnectionClosed(MessageChannel client);
}
