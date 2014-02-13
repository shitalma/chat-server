package com.prateekj;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(serverAddress,9090);

        while(scanner.hasNext() ){
            String message = scanner.nextLine();
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os,true);
            pw.println(message);
        }
    }
}
