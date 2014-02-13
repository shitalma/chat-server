package com.prateekj;

public class UserInputReader {
    private final InputScanner scanner;
    private final ChatFactory factory;
    private UserInputReaderObserver observer;
    boolean keepRunning = true;

    public UserInputReader(ChatFactory factory, InputScanner scanner, UserInputReaderObserver observer) {

        this.factory = factory;
        this.scanner = scanner;
        this.observer = observer;
    }

    public void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                infiniteReadingLoop();
            }
        }).start();
    }

    public void stop() {
        keepRunning = false;
    }

    private void infiniteReadingLoop() {
        while (keepRunning && scanner.hasNext()) {
            observer.onInput(scanner.nextLine());
        }
    }

}
