package com.prateekj;

import java.util.Scanner;

public class SystemInputScanner implements InputScanner {
    private final Scanner scanner;

    SystemInputScanner() {
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
