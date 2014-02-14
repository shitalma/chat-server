package com.prateekj;

public interface MessageServerObserver{
    void onNewConnection(MessageChannel channel);
    void onError(Exception e);
}
