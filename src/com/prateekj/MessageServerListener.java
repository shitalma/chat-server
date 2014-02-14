package com.prateekj;

public interface MessageServerListener {
    void onNewConnection(MessageChannel channel);
    void onError(Exception e);
}
