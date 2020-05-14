package se.liu.ida.durke636.tdde30.tetris;

public class BoardToTextConverter {

    public static String convertToText(Board board){
        StringBuilder boardText = new StringBuilder();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                insertTypeToString(boardText, board.getSquareAt(x, y));
            }
            boardText.append("\n");
        }
        return boardText.toString();
    }

    private static StringBuilder insertTypeToString(StringBuilder boardText, SquareType square) {
        switch(square) {
            case EMPTY:
                boardText.append("_ ");
                break;
            case I:
                boardText.append("I ");
                break;
            case O:
                boardText.append("O ");
                break;
            case T:
                boardText.append("T ");
                break;
            case S:
                boardText.append("S ");
                break;
            case Z:
                boardText.append("Z ");
                break;
            case J:
                boardText.append("J ");
                break;
            case L:
                boardText.append("L ");
                break;
            default:
                boardText.append("NULL ");
        }
        return boardText;
    }
}
