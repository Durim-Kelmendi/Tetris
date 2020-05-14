package tetris;

import java.util.Arrays;
import java.util.Random;

public class TetrominoMaker {

    private SquareType[][] emptyBlock(SquareType[][] polys) {
        for (SquareType[] row : polys) {
            Arrays.fill(row, SquareType.EMPTY);
        }
        return polys;
    }

    public int getNumberOfTypes(){
        return SquareType.values().length;
    }

    public Poly getPoly() {
        Random rnd = new Random();
        int n = rnd.nextInt(getNumberOfTypes() - 2);
        SquareType poly = SquareType.values()[n];
        switch (poly) {
            case I:
                SquareType[][] i = emptyBlock(new SquareType[4][4]);
                Arrays.fill(i[1], SquareType.I);
                return new Poly(i);
            case O:
                SquareType[][] o = emptyBlock(new SquareType[2][2]);
                for (SquareType[] row : o) {
                    Arrays.fill(row, SquareType.O);
                }
                return new Poly(o);
            case T:
                SquareType[][] t = emptyBlock(new SquareType[3][3]);
                t[0][1] = SquareType.T;
                Arrays.fill(t[1], SquareType.T);
                return new Poly(t);
            case S:
                SquareType[][] s = emptyBlock(new SquareType[3][3]);
                Arrays.fill(s[0], 1, 3, SquareType.S);
                Arrays.fill(s[1], 0, 2, SquareType.S);
                return new Poly(s);
            case Z:
                SquareType[][] z = emptyBlock(new SquareType[3][3]);
                Arrays.fill(z[0], 0, 2, SquareType.Z);
                Arrays.fill(z[1], 1, 3, SquareType.Z);
                return new Poly(z);
            case J:
                SquareType[][] j = emptyBlock(new SquareType[3][3]);
                Arrays.fill(j[1], SquareType.J);
                j[0][0] = SquareType.J;
                return new Poly(j);
            case L:
                SquareType[][] l = emptyBlock(new SquareType[3][3]);
                Arrays.fill(l[1], SquareType.L);
                l[0][2] = SquareType.L;
                return new Poly(l);
            default:
                throw new IllegalArgumentException("Invalid index: " + n);
        }
    }

}
