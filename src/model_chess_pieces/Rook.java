package model_chess_pieces;

import algorithm.ChessMove;
import algorithm.Player;
import java.util.ArrayList;
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

            if (this.isMovePossible(move, board)) {
                moves.add(new Field(row, col));
            }

        }
        return moves;
    }

    @Override
    public boolean isMovePossible( ChessMove move, Board board ) { // int curRow, int curCol, int row, int col, String color) {

        if (move == null || move.getNewPos() == null || move.getCurrent()==null ) {
            return  answer = false;
        }
        String color = this.getColor().toString(); // colour of pawn
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        int curRow = this.getPiecePosition().getRow(); // piece's current row , x !!
        int curCol = this.getPiecePosition().getCol(); // piece's current column, y !!

        int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        int col = move.getNewPos().getCol();

        if (board.isFieldOccupied(curRow, curCol) && ChessMove.isValid(row, col)) { //if piece exists on the field and the new position is valid (row&col <8)

                if (board.isFieldOccupied(row, col) && !(board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is DIFFERENT from the one that the king has
                    return check_col_row(col, curCol, row, curRow, board);
                    
                } else if (board.isFieldOccupied(row, col) && (board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is the SAME from the one that the king has
                    answer = false;
                    return answer;
                } else if (!(board.isFieldOccupied(row, col))) { //FIELD EMPTY
                    return check_col_row(col, curCol, row, curRow, board);
                }
        } else {
            answer = false;
            return answer;
        }
       return answer;
    }

    private boolean check_col_row(int col, int curCol, int row, int curRow, Board board) {
        //row = new x, col = new y
        //curRow = old x, curCol = old y
        List<String> field_empty_or_not ;
        if //VERTICAL
                (col == curCol) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX
            field_empty_or_not = new ArrayList<>();
            for (int i = curRow; i < row; i++) {
                if (!(board.isFieldOccupied(i, col))) {
                    field_empty_or_not.add("true");   //empty field 
                } else {
                    field_empty_or_not.add("false");  //not empty field 
                }     
            }
            if( !field_empty_or_not.contains("false") ){  //if all intermediate fields are empty, then the rook can be moved 
                setFirstMove(true);
                System.out.println("ROOK'S VERTICAL move  old col: " + curCol + " new col:(must be the same ) " + col + " and old row is: " + curCol + " new row is : " + row);
                answer = true;
                return answer;
            }else {           //if there is a field that is not empty, then the rook can not be moved 
                answer = false;
                return answer;
            }
        }//HORIZONTAL
        else if (row == curRow) {   //if row(Xposition)is the same and newY - oldY
            field_empty_or_not = new ArrayList<>();
            for (int i = curCol; i < col; i++) {
                if (!(board.isFieldOccupied(row, i))) {
                    field_empty_or_not.add("true");   //empty field 
                } else {
                    field_empty_or_not.add("false");  //not empty field 
                }   
            }
            if( !field_empty_or_not.contains("false") ){   //if all intermediate fields are empty, then the rook can be moved 
                setFirstMove(true);
                System.out.println("ROOK'S HORIZONTAL move is possible new row : " + row + "old row(must be same)" + curRow + "and new col: " + (col - curCol));
                answer = true;
                return answer;
            }else {                                //if there is a field that is not empty, then the rook can not be moved 
                answer = false;
                return answer;
            }
        } else {
            System.out.println("ROOK'S piece exists on this field -- move not possible");
            answer = false;
            return answer;
        }
    }

}
