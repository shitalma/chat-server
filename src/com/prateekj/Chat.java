package com.prateekj;

public class Chat {
    public static void main(String[] args) {
        ChatFactory factory = new ChatFactory();
        if (args.length > 0) {
            new ChatClient(factory).run(args[0]);
        } else
            new ChatServer(factory).run();
    }
}
