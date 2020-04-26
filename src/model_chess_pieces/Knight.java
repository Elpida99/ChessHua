package model_chess_pieces;

import java.util.LinkedList;
import java.util.List;

import algorithm.ChessMove;
import algorithm.Player;
import model_board.Board;
import model_board.Field;

/**
 * it21735 , it21754, it21762
 */
public class Knight extends ChessPiece {

    private final int value = 3;

    private final int[][] directions = {{1, 2}, {-1, 2}, {2, -1}, {-2, -1}, {-1, -2}, {1, -2}, {-2, 1},
    {2, 1}}; // {vertical,horizontal}

    public Knight(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public List<Field> allPossibleMoves(Board board, Player player) {
        List<Field> moves = new LinkedList<>();
        int currow = this.getPiecePosition().getRow();
        int curcol = this.getPiecePosition().getCol();
        ChessMove move = new ChessMove(null, this);

        moves.add(board.getField()[currow][curcol]);
        for (int[] direction : this.directions) {
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
    public boolean isMovePossible(ChessMove move, Board board) {
        if (move == null || move.getNewPos() == null || move.getCurrent() == null) {
            return false;
        }
        boolean answer = false; // final answer--is the requested move possible?
        boolean validPosition = false; // temp boolean to check if the move is valid without checking if field is
        // occupied

        ChessPieceCharacteristics.Color colour = this.getColor(); // colour of piece

        int currow = this.getPiecePosition().getRow(); // current row
        int curcol = this.getPiecePosition().getCol(); // current column

        int row = move.getNewPos().getRow(); // requested new coordinates
        int col = move.getNewPos().getCol();

        if (ChessMove.isValid(row, col)) { // do the new coordinates exist on board?

            // 2 vertical + 1 horizontal
            if ((Math.abs(col - curcol) == 1) && Math.abs(row - currow) == 2) { // if column changes by 1 and row by
                // 2 fields
                validPosition = true; // possible move---> 2 fields vertically and 1 horizontally

                // 2 horizontal + 1 vertical
            } else if ((Math.abs(col - curcol) == 2) && Math.abs(row - currow) == 1) { // if column changes by 2 and
                // row by one field
                validPosition = true; // possible move---> 2 fields horizontally and 1 vertically

            } else { // no other move is possible for the knight
            }
            if (validPosition) { // if valid position is true, we must check if the field is occupied or not and
                // by what colour
                if ((board.isFieldOccupied(row, col))
                        && !(board.getField()[row][col].getChessPiece().getColor().equals(colour))) {
                    // if occupied by different colour-it gets eaten
                    move.setAttack(true);
                    answer = true;
                } else if (!board.isFieldOccupied(row, col)) { // if not occupied, the position is free for the
                    // knight
                    answer = true;
                }
            }
        }

        return answer;
    }

    @Override
    public void makeMove(ChessMove move, Board board) {

        int curRow = this.getPiecePosition().getRow(); // piece's current row
        int curCol = this.getPiecePosition().getCol(); // piece's current column

        int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        int col = move.getNewPos().getCol();

        if (move.getAttack()) {
            board.getField()[row][col].getChessPiece().setIsAlive(false);
            board.getField()[row][col].removeChessPiece();
        }
        board.getField()[curRow][curCol].removeChessPiece(); // remove piece from current position
        board.getField()[row][col].setChessPiece(this); // set it to the new field

    }
}
