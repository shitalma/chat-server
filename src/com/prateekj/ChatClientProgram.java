package com.prateekj;


import java.io.IOException;

public class ChatClientProgram {
    public static void main(String[] args) throws IOException {
        new ChatClient(new ChatFactory()).run();
    }
}
