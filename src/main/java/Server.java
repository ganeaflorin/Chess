import game.Board;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static final int PORT = 8100;

    public Server() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            accept(serverSocket);
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
        } finally {
            serverSocket.close();
        }
    }

    public void accept(ServerSocket serverSocket) throws IOException {
        while (true) {
            System.out.println("Waiting for players...");
            Board board = new Board();
//
//            Runnable firstClient = new ClientThread(serverSocket.accept(), board, 0);
//            Runnable secondClient = new ClientThread(serverSocket.accept(), board, 1);
//            Thread firstClientThread = new Thread(firstClient);
//            Thread secondClientThread = new Thread(secondClient);
//            firstClientThread.start();
//            secondClientThread.start();
            Runnable gameRun = new ClientThread(serverSocket.accept(), serverSocket.accept(), board);
            Thread game = new Thread(gameRun);
            game.start();
        }
    }
}

