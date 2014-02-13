package com.prateekj;

public interface MessageServerObserver{
    void onMessage(String message);
    void onError(Exception e);
}
