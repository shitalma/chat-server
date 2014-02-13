package com.prateekj;

/**
 * Created by shitalma on 2/13/14.
 */
public interface SocketServerObserver {
    void onNewConnection(MessageClient messageClient);

    void onError(Exception e);
}
