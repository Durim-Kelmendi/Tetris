package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {


    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private List<BoardListener> boardlisteners;
    private Move directionOfPolyMovement = null;
    private Random rnd = new Random();

    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling;
    private int fallingX, fallingY;
    public boolean gameover = false;
    private final static int OFFSET = 2;
    private final static int FRAME = 4;

    public Board(final int height, final int width) {
        this.height = height;
        this.width = width;
        this.falling = tetrominoMaker.getPoly();
        this.fallingX = 0;
        this.fallingY = 0;
        this.squares = new SquareType[height + FRAME][width + FRAME];
        for (SquareType[] row : squares) {
            Arrays.fill(row, SquareType.OUTSIDE);
        }
        clearBoard(); // Creates new empty board, with OUTSIDE enum at the invisible frame
        this.boardlisteners = new ArrayList<BoardListener>();
        this.notifyListeners();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SquareType getSquare(int x, int y) {
        return squares[x + OFFSET][y + OFFSET];
    }

    public Poly getFalling() {
        return falling;
    }

    public int getFallingX() {
        return fallingX;
    }

    public int getFallingY() {
        return fallingY;
    }

    public int shuffle() {
        return rnd.nextInt(7);
    }

    public boolean polyWasDescending() {
        return directionOfPolyMovement == Move.DOWN;
    }

    public void addBoardListener(BoardListener bl) {
        boardlisteners.add(bl);
    }

    public void notifyListeners() {
        for (BoardListener listener : boardlisteners) {
            listener.boardChanged();
        }
    }

    public void clearBoard() {
        for (int row = OFFSET; row < squares.length - OFFSET; row++) {
            Arrays.fill(squares[row], OFFSET, width + OFFSET, SquareType.EMPTY);
        }
    }

    public boolean hasCollision() {
        Poly falling = getFalling();
        int fallingX = getFallingX();
        int fallingY = getFallingY();
        for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
            for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
                if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
                        this.getSquare(row, col) != SquareType.EMPTY) {
                    notifyListeners();
                    return true;
                }
            }
        }
        notifyListeners();
        return false;
    }

    public SquareType getSquareAt(int y, int x) {
        if (this.falling == null) {
            return squares[y][x];
        }
        int polySize = falling.getPolys().length - 1;

        if (this.fallingX <= x && x <= fallingX + polySize &&
                this.fallingY <= y && y <= this.fallingY + polySize) {
            if (falling.getPolys()[x - fallingX][y - fallingY] == SquareType.EMPTY) {
                notifyListeners();
                return getSquare(x, y);
            }
            notifyListeners();
            return falling.getPolys()[x - fallingX][y - fallingY];
        }
        notifyListeners();
        return getSquare(x, y);
    }

    /**
     * Fills the array with randomized Polyominos that take 1 square each.
     * Purely for testing reason to see if all the colours on the polys work.
     */
    public void randomizeBoard() {
        SquareType[] squareArray = SquareType.values();
        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                squares[x][y] = squareArray[rnd.nextInt(squareArray.length)];
            }
        }
        notifyListeners();
    }

    /**
     * Spawns a random poly at the top of the screen.
     */
    public void spawnPoly() {
        falling = tetrominoMaker.getPoly();
        fallingX = 0;
        fallingY = 5;
    }

    public void movePoly(Move dir) {
        int preX = fallingX;
        int preY = fallingY;
        directionOfPolyMovement = dir;
        if (falling.isFalling()) {
            fallingX += dir.deltaX;
            fallingY += dir.deltaY;
            if (hasCollision()) {
                if(!polyWasDescending()) {
                    fallingX = preX;
                    fallingY = preY;
                }
                if (polyWasDescending()) {
                    fallingX -= 1;
                    insertSetBlock();
                    falling.setFalling(false);
                }
            }
        }
        notifyListeners();
    }

    public void rotate(boolean direction) {
        Poly preRotation = falling;
        if(falling.isFalling()) {
            falling = falling.rotate(direction);
            if (hasCollision()) {
                falling = preRotation;
            }
        }
        notifyListeners();
    }

    /**
     * Active game tick. What the game loops over in BoardTest file.
     * Handles spawning poly, collisions and if the game is over.
     */
    public void tick() {
        if(!gameover) {
            if (falling.isFalling()) {
                movePoly(Move.DOWN);
            } else {
                completeRow();
                spawnPoly();
                if(hasCollision()) {
                    gameover = true;
                }
            }
            notifyListeners();
        } else {
            System.exit(0);
        }
    }

    /**
     * Inserts the block that has landed, so it doesen't dissapear.
     */
    public void insertSetBlock() {
        for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
            for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
                if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY) {
                    if (squares[row + OFFSET][col + OFFSET] != SquareType.OUTSIDE) {
                        squares[row + OFFSET][col + OFFSET] = falling.getSquare(row - fallingX, col - fallingY);
                        notifyListeners();
                    }
                }
            }
        }
    }

    public void deleteRow(int index) {
        for (int row = index; row > OFFSET; row--) {
            for (int column = OFFSET; column < width + OFFSET; column++) {
                squares[row][column] = squares[row - 1][column];
            }
        }
    }

    public void completeRow() {
        for (int row = OFFSET; row < height + OFFSET; row++) {
            boolean foundRow = true;
            for (SquareType c : squares[row]) {
                if (c == SquareType.EMPTY) {
                    foundRow = false;
                }
            }
            if (foundRow) {
                deleteRow(row);
            }
        }
        notifyListeners();
    }

}
