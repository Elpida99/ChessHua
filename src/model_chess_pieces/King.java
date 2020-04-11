/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import algorithm.ChessMove;
import java.util.List;
import model_board.Board;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class King extends ChessPiece {

    private final int value = 10;

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

    public King(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    public int getValue() {
        return value;
    }

    public boolean isMovePossible(ChessMove move, Board board) {
        boolean answer = false; //answer: is the move possiple or not
        String color = this.getColor().toString(); // colour of pawn
        System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        int curRow = this.getPiecePosition().getRow(); // piece's current row , x !!
        int curCol = this.getPiecePosition().getCol(); // piece's current column, y !!
        System.out.println("old coordinates: " + move.getCurrent().getRow() + "," + move.getCurrent().getCol());

	int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
	int col = move.getNewPos().getCol();
	System.out.println("new position: " + row + "," + col); // check that they are right

        if (board.isFieldOccupied(curRow, curCol)) { //if piece exists on the field
            System.out.println("piece exists on this field and it can be moved\n");
            
            if (ChessMove.isValid(row, col)) { //if the new position is valid (row&col <8)
		System.out.print("New position is valid, coordinates exist on board : ");
                
                if (board.isFieldOccupied(row, col) && !(board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is DIFFERENT from the one that the king has 
                    System.out.println("field occupied-the colour of the pawn (pioni) needs to be checked ");
                    answer = true;
                }else if (board.isFieldOccupied(row, col) && (board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is the SAME from the one that the king has 
                    System.out.println("field occupied-the colour of the pawn (pioni) is the SAME with the colour of the king ");
                    answer = false;
                }              
                else if ( ! ( board.isFieldOccupied(row, col) ) ){ //FIELD EMPTY
                    System.out.println("position available-FIELD NOT OCCUPIED/EMPTY  --- Let's check if the new move meets the \"criteria\"");
                    //row = new x, col = new y 
                    //curRow = old x, curCol = old y
                    if //VERTICAL 
                            (col == curCol && (  (row - curRow ) == 1 ||  ( row - curRow == -1)   )   ) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX = 1 or -1
                        System.out.println("VERTICAL move is possible");
                        System.out.println(Math.abs((row - curRow)));
                        answer = true;
                    }//HORIZONTAL 
                    else if (row == curRow && (  ( col - curCol ) == 1  || (col - curCol ) == -1 )  ) {   //if row(Xposition)is the same and newY - oldY = 1 or -1
                        System.out.println("HORIZONTAL move is possible");
                        System.out.println((row - curRow ));
                        answer = true;
                    }//DIAGONAL 
                    else if ( ( ( (row - curRow ) == 1 || (row - curRow ) == -1 ) && ( (col - curCol) == 1 || (col - curCol) == -1 ) )) {    //newX - oldX = 1 or -1 AND newY - oldY = 1 or -1
                        System.out.println("DIAGONAL  move is possible");
                        System.out.println((row - curRow));
                        answer = true;
                    } else {
                        System.out.println("piece exists on this field -- move not possible");
                        answer = false;
                    }
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

}
