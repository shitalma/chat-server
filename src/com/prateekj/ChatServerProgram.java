package com.prateekj;

public class ChatServerProgram {
    public static void main(String[] args)   {
        new ChatServer(new ChatFactory()).run();
    }
}