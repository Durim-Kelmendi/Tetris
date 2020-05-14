package se.liu.ida.durke636.tdde30.tetris;

import javax.swing.*;
import java.awt.*;

public class OldTetrisViewer extends JFrame {

    private Board board;
    private JTextArea textArea;
    private JFrame frame = new JFrame("Tetris");

    public OldTetrisViewer(Board board) {
        this.board = board;
        this.textArea = new JTextArea(board.getWidth(), board.getHeight());
        this.frame = frame;
    }

    public void showWindow() {
        textArea.setText(BoardToTextConverter.convertToText(board));
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(textArea, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
