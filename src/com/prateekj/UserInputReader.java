package com.prateekj;

public class UserInputReader {
    private final InputScanner scanner;
    private UserInputReaderObserver observer;

    public UserInputReader(InputScanner scanner, UserInputReaderObserver observer) {

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
    private void infiniteReadingLoop() {
        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            if (text.equals("quit"))
                observer.onQuit();
        }
    }
}
