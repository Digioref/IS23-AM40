package it.polimi.ingsw.am40.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Logger implements Runnable {
    private BufferedReader in;
    public Logger(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            String line = null;
            try {
                line = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            print(line);
        }
    }

    public synchronized void print(String s) {
        System.out.println(s);
        System.out.flush();
    }
}
