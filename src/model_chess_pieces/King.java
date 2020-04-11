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

    @Override
    public int getValue() {
        return value;
    }

    public boolean isMovePossible(ChessMove move, Board board) {
        boolean answer = false; //answer: is the move possiple or not
        move.setOldXY(getPiecePosition()); // piece's current position-->set it to class ChessMove
        int row = move.getNewXY().getX(); // coordinates of desired move are the directions of the user
        int col = move.getNewXY().getY();
        System.out.println(row + " " + col); //check that they are right

        if (board.isFieldOccupied(move.getOldXY().getX(), move.getOldXY().getY())) { //if piece exists on the field
            System.out.println("piece exists on this field and it can be moved");
            if (board.isFieldOccupied(row, col)) { //if new position is occupied, the pawn cannot move to that position
                System.out.println("move not possible-field occupied");
                answer = false;
            } else {
                System.out.println("position available-field not occupied  --- Let's check if the new move meets the \"criteria\"");
                //row = new x 
                if //VERTICAL 
                        (col == getPiecePosition().getY() && (  (row - getPiecePosition().getX() ) == 1 ||  ( row - getPiecePosition().getX() == -1)   )   ) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX = 1 or -1
                    System.out.println("VERTICAL move is possible");
                    System.out.println(Math.abs((row - getPiecePosition().getX())));
                    answer = true;
                }//HORIZONTAL 
                else if (row == getPiecePosition().getX() && (  ( col - getPiecePosition().getY() ) == 1  || (col - getPiecePosition().getY() ) == -1 )  ) {   //if row(Xposition)is the same and newY - oldY = 1 or -1
                    System.out.println("HORIZONTAL move is possible");
                    System.out.println(Math.abs((row - getPiecePosition().getX())));
                    answer = true;
                }//DIAGONAL 
                else if ( ( ( (row - getPiecePosition().getX()) == 1 || (row - getPiecePosition().getX()) == -1 ) && ( (col - getPiecePosition().getY()) == 1 || (col - getPiecePosition().getY() ) == -1 ) )) {    //newX - oldX = 1 or -1 AND newY - oldY = 1 or -1
                    System.out.println("DIAGONAL  move is possible");
                    System.out.println(Math.abs((row - getPiecePosition().getX())));
                    answer = true;
                }else {
                    System.out.println("piece exists on this field -- move not possible");
                    answer = false;
                }
            }
        }else {
            System.out.println("piece DOES NOT sexists on this field -- move not possible");
            answer = false;
        }
        return answer;
    }

}
