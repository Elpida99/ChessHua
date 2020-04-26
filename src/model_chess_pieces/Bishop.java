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

    @Override
    public boolean isMovePossible(ChessMove move, Board board) {
        if (move == null || move.getNewPos() == null || move.getCurrent() == null) {
            return false;
        }
        ChessPieceCharacteristics.Color colour = this.getColor(); // colour of queen

        int curRow = this.getPiecePosition().getRow(); //get Queen's current coordinates 
        int curCol = this.getPiecePosition().getCol();

        int newRow = move.getNewPos().getRow(); //get bishop's new coordinates 
        int newCol = move.getNewPos().getCol();

        if (move.getCurrent() == null || move.getNewPos() == null) { //check
            return false;
        }

        int deltaX = newRow - curRow;
        int deltaY = newCol - curCol;

        if (deltaX == 0 || deltaY == 0) {

            return false;

        } else {

            int dx = deltaX / Math.abs(deltaX);
            int dy = deltaY / Math.abs(deltaY);

            //check that field is actually occupied
            if (ChessMove.isValid(newRow, newCol)) {
                if (board.isFieldOccupied(move.getCurrent().getRow(), move.getCurrent().getCol())) {

                    Field nextField;

                    for (int i = 1; i <= Math.abs(deltaX); i++) {

                        nextField = board.getField(curRow + i * dx, curCol + i * dy);
                        if (nextField.isOccupied() && (i == Math.abs(deltaX) && !nextField.getChessPiece().getColor().equals(this.getColor()))) {
                            move.setAttack(true);
                            return true;
                        }
                        if (nextField.getChessPiece() != null && (i != Math.abs(deltaX) || nextField.getChessPiece().getColor() == this.getColor())) {
                            return false;
                        }

                    }

                } else {
                    return false;
                }

            } else {
                return false;
            }

        }

        return true;
    }

    @Override
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

                    moves.add(new Field(row, col));

                }
            }
        }

        return moves;

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
