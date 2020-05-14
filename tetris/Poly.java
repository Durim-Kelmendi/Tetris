package tetris;

public class Poly {

    private SquareType[][] polys;
    private boolean falling = true;

    public Poly(final SquareType[][] polys) {
        this.polys = polys;
    }

    public SquareType[][] getPolys() {
        return polys;
    }

    public boolean isFalling() {
        return falling;
    }

    public SquareType getSquare(int x,int y) {
        return polys[x][y];
    }

    public void setFalling(final boolean fallingStatus) {
        this.falling = fallingStatus;
    }

    public Poly rotate(boolean direction) {
        if (direction) {
            return rotateRight();
        } else {
            return rotateLeft();
        }
    }

    private Poly rotateRight() {
        int size = polys.length;
        Poly newPoly = new Poly(new SquareType[size][size]);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++){
                newPoly.polys[c][size-1-r] = this.polys[r][c];
            }
        }
        return newPoly;
    }

    private Poly rotateLeft() {
        int size = polys.length;
        Poly newPoly = new Poly(new SquareType[size][size]);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++){
                newPoly.polys[size-1-r][c] = this.polys[c][r];
            }
        }

        return newPoly;
    }
}
