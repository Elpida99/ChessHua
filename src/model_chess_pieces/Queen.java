package model_chess_pieces;

import algorithm.ChessMove;
import exceptions.InvalidMoveException;
import java.util.ArrayList;
import java.util.List;
import model_board.Board;
import model_board.Field;

/**
 * it21735 , it21754, it21762
 *
 */
public class Queen extends ChessPiece {

    private final int value = 9;
    private boolean answer = false;

    private int[][] directions = {
        {-1, -1},
        {1, 1},
        {1, -1},
        {-1, 1},
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}

    };

    public Queen(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

    public boolean isMoveLikeBishop(ChessMove move, Board board) { //In case Queen is moving diagonally, queen moves the same as bishop

        String color = this.getColor().toString(); // colour of pawn
        System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        int curRow = move.getCurrent().getRow(); //get bishop's current coordinates 
        int curCol = move.getCurrent().getCol();

        int newRow = move.getNewPos().getRow(); //get bishop's new coordinates 
        int newCol = move.getNewPos().getCol();

        if (move.getCurrent() == null || move.getNewPos() == null) { //check
            System.out.println("One of the piece's positions is null");
            return false;
        }

        int deltaX = newRow - curRow;
        int deltaY = newCol - curCol;

        int dx = deltaX / Math.abs(deltaX);
        int dy = deltaY / Math.abs(deltaY);

        System.out.println(newRow + " " + newCol); //check that they are right

        //check that field is actually occupied
        if (board.isFieldOccupied(move.getCurrent().getRow(), move.getCurrent().getCol())) {

            System.out.println("queen exists on the field and can be moved");

            if (board.isFieldOccupied(newRow, newCol)) {

                System.out.println("New move is not possible - Field occupied.");
                return false;

            } else {

                System.out.println("Position available");
                Field nextField;

                for (int i = 1; i <= Math.abs(deltaX); i++) {

                    nextField = board.getField(curRow + i * dx, curCol + i * dy);
                    if (nextField == null) {
                        System.out.println("null");
                    }
                    if (nextField.getChessPiece() != null && (i != Math.abs(deltaX) || nextField.getChessPiece().getColor() == this.getColor())) {
                        return false;
                    }

                }

            }

        }
        return true;
    }

    public boolean isMoveLikeRook(ChessMove move, Board board) { //If queen moves vertically/horizontically, she moves like Rook

        String color = this.getColor().toString(); // colour of pawn
        System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        int curRow = move.getCurrent().getRow(); //get bishop's current coordinates 
        int curCol = move.getCurrent().getCol();

        int newRow = move.getNewPos().getRow(); //get bishop's new coordinates 
        int newCol = move.getNewPos().getCol();

        if (move.getCurrent() == null || move.getNewPos() == null) { //check
            System.out.println("One of the piece's positions is null");
            return false;
        }

        int deltaX = newRow - curRow;
        int deltaY = newCol - curCol;

        /*IF EXPLANATION: deltaX !=0 && deltaY !=0 is true IF the move is not horizontal OR vertical
                          newRow == curRow && newCol==curRow is true IF the queen stays on the same position*/
        if (deltaX != 0 && deltaY != 0 || (newRow == curRow && newCol == curCol)) {
            System.out.println("Invalid Move");
            return false;
        }

        int dx = setDerivativeChange(deltaX);
        int dy = setDerivativeChange(deltaY);

        int delta = Math.abs(Math.max(Math.abs(deltaX), Math.abs(deltaY)));

        System.out.println(newRow + " " + newCol); //check that they are right

        //check that field is actually occupied
        if (board.isFieldOccupied(move.getCurrent().getRow(), move.getCurrent().getCol())) {

            System.out.println("queen exists on the field and can be moved");

            if (board.isFieldOccupied(newRow, newCol)) {

                System.out.println("New move is not possible - Field occupied.");
                return false;

            } else {

                System.out.println("Position available");
                Field nextField;

                for (int i = 1; i <= delta; i++) {

                    nextField = board.getField(curRow + i * dx, curCol + i * dy);

                    if (nextField.getChessPiece() != null && (i != delta || nextField.getChessPiece().getColor() == this.getColor())) {
                        return false;
                    }

                }

            }

        }
        return true;
    }

    private int setDerivativeChange(int delta) {
        if (delta == 0) {
            return 0;
        } else {
            return delta / Math.abs(delta);
        }
    }

    

}
