import game.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket1 = null;
    private Socket socket2 = null;
    private boolean running = true;
    private Board board;
    private BufferedReader in1;
    private PrintWriter out1;
    private BufferedReader in2;
    private PrintWriter out2;
    private int turn;

    public ClientThread(Socket socket1, Socket socket2, Board board) throws IOException {
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.board = board;
        in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        out1 = new PrintWriter(socket1.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        out2 = new PrintWriter(socket2.getOutputStream(), true);
    }

    public void changeTurn() {
        if (turn == 0)
            turn = 1;
        else turn = 0;
    }

    //    public void run() {
//        try {
//            out.println(id);
//            while (true) {
//                out.println(Game.Board.getTurn());
//                synchronized (board) {
//                    while (board.getTurn() != id) {
//                        board.wait();
//                    }
//                    out.println(board.getBoard());
//                    board.setBoard(in.readLine());
//                    Game.Board.changeTurn();
//                    board.notifyAll();
//                }
//
//            }
//        } catch (IOException | InterruptedException ioException) {
//            ioException.printStackTrace();
//        }
//    }
    public void run() {
//        try {
//            out1.println(0);
//            out2.println(1);
//            while (true) {
//                out1.println(turn);
//                out2.println(turn);
//                if (turn == 0) {
//                    out1.println(board.getBoard());
//                    board.setBoard(in1.readLine());
//
//                } else {
//                    out2.println(board.getBoard());
//                    board.setBoard(in2.readLine());
//                }
//                if (!board.getBoard().equals("test"))
//                    continue;
//                changeTurn();
//            }
//        } catch (IOException e) {
//            System.out.printf("error!");
//        }
    }
}
