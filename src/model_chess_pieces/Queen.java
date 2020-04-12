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

    public boolean isDiagonalMovePossible(ChessMove move, Board board) { //In case Queen is moving diagonally, queen moves the same as bishop

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

            System.out.println("bishop exists on the field and can be moved");

            if (board.isFieldOccupied(newRow, newCol)) {

                System.out.println("New move is not possible - Field occupied.");
                return false;

            } else {

                System.out.println("Position available");
                Field nextField;

                for (int i = 1; i <= Math.abs(deltaX); i++) {

                    nextField = board.getField(curRow + i * dx, curCol + i * dy);

                    if (nextField.getChessPiece() != null && (i != Math.abs(deltaX) || nextField.getChessPiece().getColor() == this.getColor())) {
                        return false;
                    }

                }

            }

        }
        return true;
    }

    public boolean isVertHorMovePossible(ChessMove move, Board board) { //If queen moves vertically/horizontically, she moves like Rook

        int curRow = 0;
        int curCol = 0;
        int row = 0;
        int col = 0;

        this.answer = false; //answer: is the move possiple or not
        String color = this.getColor().toString(); // colour of pawn
        System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        curRow = this.getPiecePosition().getRow(); // piece's current row , x !!
        curCol = this.getPiecePosition().getCol(); // piece's current column, y !!
        System.out.println("old coordinates: " + move.getCurrent().getRow() + "," + move.getCurrent().getCol());

        row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        col = move.getNewPos().getCol();
        System.out.println("new position: " + row + "," + col); // check that they are right

        if (board.isFieldOccupied(curRow, curCol)) { //if piece exists on the field
            System.out.println("piece exists on this field and it can be moved\n");

            if (ChessMove.isValid(row, col)) { //if the new position is valid (row&col <8)
                System.out.print("New position is valid, coordinates exist on board (row&col <8) ");

                if (board.isFieldOccupied(row, col) && !(board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is DIFFERENT from the one that the king has 
                    System.out.println("field occupied-the colour of the pawn (pioni) needs to be checked ");

                    check_col_row(col, curCol, row, curRow);

                } else if (board.isFieldOccupied(row, col) && (board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is the SAME from the one that the king has 
                    System.out.println("field occupied-the colour of the pawn (pioni) is the SAME with the colour of the king ");
                    answer = false;
                } else if (!(board.isFieldOccupied(row, col))) { //FIELD EMPTY
                    System.out.println("position available-FIELD NOT OCCUPIED/EMPTY  --- Let's check if the new move meets the \"criteria\"");

                    check_col_row(col, curCol, row, curRow);
                }

            } else {
                // not valid, out of bounds
                System.out.println("new field must exist on board!");
                answer = false;
                //+while loop for checking a new entry 
            }
        } else {
            System.out.println("piece DOES NOT exists on this field -- move not possible");
            System.out.println("This field " + curRow + " " + curCol + " does not contain a piece");
            answer = false;
        }
        return answer;
    }

    private void check_col_row(int col, int curCol, int row, int curRow) {
        //row = new x, col = new y 
        //curRow = old x, curCol = old y
        if //VERTICAL 
                (col == curCol) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX = 1 or -1
            System.out.println("VERTICAL move is possible");
            System.out.println(Math.abs((row - curRow)));
            answer = true;
        }//HORIZONTAL 
        else if (row == curRow) {   //if row(Xposition)is the same and newY - oldY = 1 or -1
            System.out.println("HORIZONTAL move is possible");
            System.out.println((row - curRow));
            answer = true;
        } else {
            System.out.println("piece exists on this field -- move not possible");
            answer = false;
        }
        //++++++CHECK IF THERE IS AN OTHER PAWN IN THE COLUMN OR ROW THAT THE ROOK CAN MOVE!!!
    }

}
