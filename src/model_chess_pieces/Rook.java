/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import algorithm.ChessMove;
import algorithm.Player;
import java.util.LinkedList;
import java.util.List;
import model_board.Board;
import model_board.Field;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Rook extends ChessPiece {

    private boolean firstMove;
    private final int value = 5;
    private boolean answer = false; //answer: is the move possiple or not

    private final int[][] directions = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };
    
    //logic used for castling
    @Override
    public boolean firstMove() {
        return firstMove;
    }

    @Override
    public void madeFirstMove() {
        firstMove = false;
    }

    @Override
    public void setFirstMove(boolean move) {
        firstMove = move;
    }
    
    @Override
    public int getValue() {
        return value;
    }

    public Rook(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public List<Field> allPossibleMoves(Board board, Player player) {
        List<Field> moves = new LinkedList<>();
        int currow = this.getPiecePosition().getRow();
        int curcol = this.getPiecePosition().getCol();
        ChessMove move = new ChessMove(null, this);

        moves.add(board.getField()[currow][curcol]);

        for (int i = 0; i < this.directions.length; i++) {
            int[] direction = directions[i];
            int col = curcol + direction[0];
            int row = currow + direction[1];
            move.setNewCoor(row, col);
            if (this.isMovePossible(move, board)) {
                moves.add(new Field(row, col));
            }

        }
        return moves;
    }

    @Override
    public boolean isMovePossible( ChessMove move, Board board ) { // int curRow, int curCol, int row, int col, String color) {
        int curRow = 0;
        int curCol = 0;
        int row = 0;
        int col = 0;

        answer = false; //answer: is the move possiple or not
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

                if (board.isFieldOccupied(row, col) && !(board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is DIFFERENT from the one that the king has 
                    System.out.println("field occupied-the colour of the pawn (pioni) needs to be checked ");

                    check_col_row(col, curCol, row, curRow);

                } else if (board.isFieldOccupied(row, col) && (board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
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
    }


}
