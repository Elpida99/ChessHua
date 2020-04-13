/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import algorithm.ChessMove;
import java.util.ArrayList;
import java.util.List;
import model_board.Board;
import model_board.Field;
import model_board.Position;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class King extends ChessPiece {

    private final int value = 10;
    private boolean answer = false; //answer: is the move possiple or not

    Board board;
    ChessMove move;
       
    private final int[][] directions = {
        {-1, -1},
        {1, 1},
        {1, -1},
        {-1, 1},
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    public King(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    public int getValue() {
        return value;
    }
    
    public List<Field> getPossibleMovesForKing(ChessMove move, Board board) {
         
        int curRow = 0;
        int curCol = 0;
        int row = 0;
        int col = 0;
        String color = null;
    
        answer = false; //answer: is the move possiple or not
        color = this.getColor().toString(); // colour of pawn
        System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        curRow = this.getPiecePosition().getRow(); // piece's current row , x !!
        curCol = this.getPiecePosition().getCol(); // piece's current column, y !!
        System.out.println("old coordinates: " + move.getCurrent().getRow() + "," + move.getCurrent().getCol());

        row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        col = move.getNewPos().getCol();
        System.out.println("new position: " + row + "," + col); // check that they are right

        List<Field> possibleFields = new ArrayList<>();

        addPossibleMove(possibleFields, curRow, curCol, directions[0][0], directions[0][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[1][0], directions[1][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[2][0], directions[2][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[3][0], directions[3][1], color);

        addPossibleMove(possibleFields, curRow, curCol, directions[4][0], directions[4][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[5][0], directions[5][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[6][0], directions[6][1], color);
        addPossibleMove(possibleFields, curRow, curCol, directions[7][0], directions[7][1], color);

        return possibleFields;
    }

    private void addPossibleMove(List<Field>  possibleMoves, int curRow,int curCol,int row, int col,String color) {

        if (isMovePossible(move,board, curRow,curCol, row, col,color))
            possibleMoves.add(board.getField(curRow + row, curCol+col ));        


    }


    
    public boolean isMovePossible(ChessMove move, Board board, int curRow,int curCol,int row, int col, String color) {
        
        if (board.isFieldOccupied(curRow, curCol)) { //if piece exists on the field
            System.out.println("piece exists on this field and it can be moved\n");

            if (ChessMove.isValid(row, col)) { //if the new position is valid (row&col <8)
                System.out.print("New position is valid, coordinates exist on board, (row&col <8) ");

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
                (col == curCol && ((row - curRow) == 1 || (row - curRow == -1))) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX = 1 or -1
            System.out.println("VERTICAL move is possible");
            System.out.println(Math.abs((row - curRow)));
            answer = true;
        }//HORIZONTAL 
        else if (row == curRow && ((col - curCol) == 1 || (col - curCol) == -1)) {   //if row(Xposition)is the same and newY - oldY = 1 or -1
            System.out.println("HORIZONTAL move is possible");
            System.out.println((row - curRow));
            answer = true;
        }//DIAGONAL 
        else if ((((row - curRow) == 1 || (row - curRow) == -1) && ((col - curCol) == 1 || (col - curCol) == -1))) {    //newX - oldX = 1 or -1 AND newY - oldY = 1 or -1
            System.out.println("DIAGONAL  move is possible");
            System.out.println((row - curRow));
            answer = true;
        } else {
            System.out.println("piece exists on this field -- move not possible");
            answer = false;
        }
    }
}
