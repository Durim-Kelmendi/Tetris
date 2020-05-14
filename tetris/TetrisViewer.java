package tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer extends JFrame {

    private Board board;
    private JFrame frame;

    public TetrisViewer(Board board) {
        this.board = board;
        this.frame = new JFrame("Tetris");
        final TetrisComponent tetrisArea = new TetrisComponent(board);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        board.addBoardListener(tetrisArea);
        this.setLayout(new BorderLayout());
        this.add(tetrisArea, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
/*
    public void showWindow() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        board.addBoardListener(tetrisArea);
        frame.setLayout(new BorderLayout());
        frame.add(tetrisArea, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }*/

}
