package model_chess_pieces;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import algorithm.ChessMove;
import algorithm.Player;
import model_board.Board;
import model_board.Field;
import model_board.FieldCoordinates;

/**
 * it21735 , it21754, it21762
 */
public class Pawn extends ChessPiece {

    private final int value = 1;
    private boolean isFirstMove = true;

    public Pawn(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    @Override
    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    @Override
    public List<Field> allPossibleMoves(Board board, Player curplayer) {

        List<Field> moves = new LinkedList<>();
        int currow = this.getPiecePosition().getRow();
        int curcol = this.getPiecePosition().getCol();
        ChessMove move = new ChessMove(null, this);

        // moves.add(board.getField()[currow][curcol]);
        int forward = this.getColor().equals(ChessPieceCharacteristics.Color.b) ? 1 : -1;
        // forward
        for (int i = 0; i <= 2; i++) {
            int col = curcol;
            int row = currow + forward * i;
            move.setNewCoor(row, col);
            if (this.isMovePossible(move, board)) {
                moves.add(new Field(row, col));
            }
        }

        // diagonal
        int col = curcol + 1;
        int row = currow + forward;
        move.setNewCoor(row, col);
        if (this.isMovePossible(move, board)) {
            moves.add(new Field(row, col));
        }

        col = curcol - 1;
        move.setNewCoor(row, col);
        if (this.isMovePossible(move, board)) {
            moves.add(new Field(row, col));
        }

        // enPassant
        if (board.getLastMove() != null) {
            ChessMove enpassant = this.CheckEnPassant(board, curplayer);

            if (enpassant.getNewPos() != null) {
                moves.add(new Field(enpassant.getNewPos().getRow(), enpassant.getNewPos().getCol()));
            }
        }

        return moves;
    }

    @Override
    public boolean isMovePossible(ChessMove move, Board board) {
        if (move.getNewPos() == null) {
            return false;
        }
        boolean answer = false; // answer: is the move possible or not
        ChessPieceCharacteristics.Color color = this.getColor(); // colour of pawn
        int forward;
        if (color.equals(ChessPieceCharacteristics.Color.w)) {
            forward = -1; //white pieces start at row 6 
        } else {
            forward = 1; //black pieces start at row 1
        }
        int curRow = this.getPiecePosition().getRow(); // piece's current row
        int curCol = this.getPiecePosition().getCol(); // piece's current column

        int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        int col = move.getNewPos().getCol();

        if (ChessMove.isValid(row, col)) { // if the new position is valid (row&col <8)
            if (col == curCol) {
                if (!this.isBlocked(board)) {
                    // forward
                    if (this.isFirstMove()) { // the pawn can move 2 fields forward if it is its first move
                        if ((row - curRow) == forward * 1 || (row - curRow) == forward * 2) {
                            if (!board.isFieldOccupied(row, col)) {
                                answer = true;
                            }
                        }
                    } else { // not its first move -- > only one field forward is valid
                        if ((row - curRow) == forward * 1) {
                            if (!board.isFieldOccupied(row, col)) {
                                answer = true;
                            }
                        }
                    }
                }
            } else {
                // diagonal
                if (Math.abs((col - curCol)) == 1 && (row - curRow) == forward * 1) {
                    if (board.isFieldOccupied(row, col)) { //is the new field occupied?
                        if ((board.getField()[row][col].getChessPiece().getColor() != color)) { //is it occupied by an enemy piece?
                            move.setAttack(true); //is yes, the other piece gets eaten
                            answer = true;
                        }
                    } else { //check if enpassant is happening
                        if (board.getLastMove() != null) { //if the other player has played yet
                            Player player = new Player();
                            player.setPlayerColour(this.getColor());

                            ChessMove enpassant = this.CheckEnPassant(board, player);
                            if (enpassant.getNewPos() != null) {
                                move.setAttack(true);
                                answer = true;
                            }
                        }
                    }
                }
            }
        } else {
            move.setAttack(false);
        }

        return answer;
    }

    public ChessMove CheckEnPassant(Board board, Player curplayer) { //check enpassant and catch it

        ChessMove move = new ChessMove(null, this); //ChessMove by this pawn

        FieldCoordinates oldEnemy = board.getLastMove().getCurrent(); //old position of enemy pawn
        FieldCoordinates curEnemy = board.getLastMove().getNewPos(); //new position of enemy pawn

        int currow = this.getPiecePosition().getRow();
        int curcol = this.getPiecePosition().getCol();
        int Pawnrank; //row of current player's pawn
        int back; //for enemy pawn
        int forward; //for current pawn
        ChessPieceCharacteristics.Color enemycolour;
        Player enemy = new Player();

        if (curplayer.getPlayerColour().equals(ChessPieceCharacteristics.Color.w)) {// white player's turn
            Pawnrank = 3; // white pawn must be at row 3 to catch en passant
            back = -2; // black pawn must have moved 2 fields forward (it was 2 fields back)
            forward = -1; // white pawn moves 1(-1) field forward vertically (eg 3 --> 2)
            enemycolour = ChessPieceCharacteristics.Color.b; // enemyColour
            enemy.setPlayerColour(enemycolour);

        } else {
            Pawnrank = 4; //black pawn must be at row 4 to catch en passant
            back = 2; //white piece must have moved -2 fields forward (it was 2 fields back)
            forward = 1; //black pawn moves 1 field forward vertically (eg 4 --> 5)
            enemycolour = ChessPieceCharacteristics.Color.w; // enemyColour
            enemy.setPlayerColour(enemycolour);

        }
        if (currow == Pawnrank) { // if it's 3, current pawn is white, is it's 4, current pawn is black
            for (int i = -1; i <= 1; i += 2) { // we need to check left of the pawn(-1) and right(+1), 0 is the pawn's position
                // hence the i+=2
                int row = currow;
                int col = curcol + i; //first time --> curcol-1(left), second time --> curcol+1(right)

                if (ChessMove.isValid(row, col)) {
                    Field field = board.getField()[row][col];
                    if (field.isOccupied() && field.getChessPiece().getName().equals(ChessPieceCharacteristics.Name.P)
                            && field.getChessPiece().getColor().equals(enemycolour)) {
                        //if current pawn has next to it an enemy pawn then 
                        FieldCoordinates currenttmp = board.getField()[row][col].getFieldCoordintes();
                        FieldCoordinates previoustmp = board.getField()[row + back][col].getFieldCoordintes();
                        //we check if the enemy player's last move was to move this enemy pawn 2 fields forward
                        if (currenttmp.equals(curEnemy) && previoustmp.equals(oldEnemy)) {
                            move.setNewCoor(currow + forward, curcol + i);
                            //if yes then current pawn can move diagonally and catch en passant(eat the other pawn)
                        }
                    }
                }
            }
        }

        return move;

    }

    public boolean CheckPawnPromotion(int row) { //check if a pawn can be promoted
        boolean promotion = false;
        if (this.getColor().equals(ChessPieceCharacteristics.Color.w)) {
            if (row == 0) {
                // white pawn can be promoted
                promotion = true;
            }
        } else {
            if (row == 7) {
                // black pawn can be promoted
                promotion = true;
            }
        }
        return promotion;
    }

    public void PawnPromotion(int row, Board board) {
        if (this.CheckPawnPromotion(row)) {
            boolean temp = true;
            while (temp) {
                System.out.println("This pawn can be promoted to:");
                System.out.println("\t1.Queen | 2.Bishop | 3.Knight | 4.Rook");
                Scanner input = new Scanner(System.in);
                int answer = input.nextInt();

                switch (answer) {
                    case 1:
                        System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
                                + " is promoted to Queen");
                        Queen queen = new Queen(this.getColor(), ChessPieceCharacteristics.Name.Q);
                        this.promotePawn(queen, board);
                        temp = false;
                        break;
                    case 2:
                        System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
                                + " is promoted to Bishop");
                        Bishop bishop = new Bishop(this.getColor(), ChessPieceCharacteristics.Name.B);
                        this.promotePawn(bishop, board);
                        temp = false;
                        break;
                    case 3:
                        System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
                                + " is promoted to Knight");
                        Knight knight = new Knight(this.getColor(), ChessPieceCharacteristics.Name.N);
                        this.promotePawn(knight, board);
                        temp = false;
                        break;
                    case 4:
                        System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
                                + " is promoted to Rook");
                        Rook rook = new Rook(this.getColor(), ChessPieceCharacteristics.Name.R);
                        this.promotePawn(rook, board);
                        temp = false;
                        break;
                    default:
                        System.out.println("Not a valid answer");
                        break;

                }
                if (temp = false) {
                    input.close();
                }
            }
        }

    }

    public void promotePawn(ChessPiece piece, Board board) {
        int row = this.getPiecePosition().getRow();
        int col = this.getPiecePosition().getCol();

        board.getField()[row][col].removeChessPiece();
        this.setIsAlive(false);
        board.getField()[row][col].setChessPiece(piece);
    }

    @Override
    public void makeMove(ChessMove move, Board board) {
        if (this.isFirstMove) {
            this.setFirstMove(false);
        }
        int curRow = this.getPiecePosition().getRow(); // piece's current row
        int curCol = this.getPiecePosition().getCol(); // piece's current column

        int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        int col = move.getNewPos().getCol();
        if (move.getAttack()) { //if it eats another piece
            if (board.isFieldOccupied(row, col)) { //simple diagonal attack
                board.getField()[row][col].getChessPiece().setIsAlive(false);
                board.getField()[row][col].removeChessPiece();
            } else { //en passant
                int enpRow = board.getLastMove().getNewPos().getRow();
                int enpCol = board.getLastMove().getNewPos().getCol();
                board.getField()[enpRow][enpCol].getChessPiece().setIsAlive(false);
                board.getField()[enpRow][enpCol].removeChessPiece();

            }
        }
        board.getField()[curRow][curCol].removeChessPiece(); // remove piece from current position
        board.getField()[row][col].setChessPiece(this); // set it to the new field

        this.PawnPromotion(row, board);
    }

}
