/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import algorithm.ChessMove;
import algorithm.Player;
import exceptions.InvalidMoveException;
import java.util.LinkedList;
import java.util.List;
import model_board.Board;
import model_board.Field;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Bishop extends ChessPiece {

    private final int value = 3;

    private int[][] directions = { //in which direction is it possible for them move 
        {-1, -1},
        {1, 1},
        {1, -1},
        {-1, 1}
    };

    public Bishop(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

    public int[][] getDirections() {
        return directions;
    }

    public void setDirections(int[][] directions) {
        this.directions = directions;
    }

    public void movePiece(ChessMove move, Board board) throws InvalidMoveException {
        if (!isMovePossible(move, board)) {
            throw new InvalidMoveException();
        } else {
            Field curField = board.getField(move.getCurrent().getRow(), move.getCurrent().getCol());
            Field newField = board.getField(move.getNewPos().getRow(), move.getNewPos().getCol());

            curField.removeChessPiece();
            newField.setChessPiece(this);
        }
    }

    public boolean isMovePossible(ChessMove move, Board board) {

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

    public List<Field> allPossibleMoves(Board board, Player curplayer) {

        List<Field> moves = new LinkedList<>();
        int curRow = this.getPiecePosition().getRow();
        int curCol = this.getPiecePosition().getCol();
        ChessMove move = new ChessMove(null, this);

        moves.add(board.getField()[curRow][curCol]);

        //checks if there are any pieces in the path for the rook based on directions
        for (int[] direction : directions) {
            for (int j = 1; j < 8; j++) {
                int col = curCol + direction[0] * j;
                int row = curRow + direction[1] * j;
                
                move.setNewCoor(row, col);
                //if the possible position is valid check the next positions
                if (this.isMovePossible(move, board)) {
                    
                    moves.add(new Field(row,col));
                    
                }
            }
        }
        
            return moves;

    }
}
