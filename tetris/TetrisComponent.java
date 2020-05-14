package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.AbstractMap;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener {

    /**
     * Constants for the sizes of the squares in the GUI and the margin between squares.
     */
    public static final int SQUARE_LENGTH = 30;
    private final static int MARGIN = 4;

    private Board board;
    private AbstractMap<SquareType,Color> colormap;

    public TetrisComponent(Board board) {
        colormap = new EnumMap<SquareType,Color>(SquareType.class);
        colormap.put(SquareType.I, Color.CYAN);
        colormap.put(SquareType.J, Color.BLUE);
        colormap.put(SquareType.L, Color.ORANGE);
        colormap.put(SquareType.O, Color.YELLOW);
        colormap.put(SquareType.S, Color.GREEN);
        colormap.put(SquareType.T, Color.MAGENTA);
        colormap.put(SquareType.Z, Color.RED);
        colormap.put(SquareType.EMPTY, Color.darkGray);
        this.board = board;
        this.setKeyBindings();
    }

    public Dimension getPreferredSize() {
        return new Dimension(board.getWidth() * SQUARE_LENGTH + MARGIN, board.getHeight() * SQUARE_LENGTH + MARGIN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        Poly poly = board.getFalling();
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(0, 0, board.getWidth() * SQUARE_LENGTH + MARGIN, board.getHeight() * SQUARE_LENGTH + MARGIN);
        for(int x=0; x < board.getHeight();x++){
            for(int y=0; y < board.getWidth(); y++){
                if (onFallingPoly(x,y,board,poly) ){
                    int xpos = x -board.getFallingX();
                    int ypos = y- board.getFallingY();
                    g2d.setColor(colormap.get(poly.getSquare(xpos, ypos)));
                }else{
                    g2d.setColor(colormap.get(board.getSquare(x, y)));
                }
                g2d.fillRect(y * SQUARE_LENGTH + MARGIN,x*SQUARE_LENGTH + MARGIN,SQUARE_LENGTH - MARGIN,SQUARE_LENGTH - MARGIN);
            }
        }
    }

    public static boolean onFallingPoly(int row, int column, Board board, Poly poly){
        int xpos = row - board.getFallingX();
        int ypos = column - board.getFallingY();
        return ((row - board.getFallingX()>= 0) &&
                (row- board.getFallingX() < poly.getPolys().length) &&
                (column - board.getFallingY()>=0) &&
                column - board.getFallingY() < poly.getPolys().length &&
                poly.getSquare(xpos, ypos) != SquareType.EMPTY);
    }

    @Override
    public void boardChanged() {
        this.repaint();
    }

    private Action moveRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.movePoly(Move.RIGHT);
        }
    };

    private Action moveLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.movePoly(Move.LEFT);
        }
    };

    private Action descend = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.movePoly(Move.DOWN);
        }
    };

    private Action rotateRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e){
            board.rotate(true);
        }
    };

    public void setKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
        this.getActionMap().put("moveLeft", moveLeft);

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");
        this.getActionMap().put("moveRight", moveRight);

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "rotateRight");
        this.getActionMap().put("rotateRight",rotateRight);

        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "descend");
        this.getActionMap().put("descend",descend);

    }
}
