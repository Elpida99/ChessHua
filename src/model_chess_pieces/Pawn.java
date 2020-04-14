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

	public void setFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}

	public List<Field> allPossibleMoves(Board board, Player Wplayer, Player Bplayer) {

		List<Field> moves = new LinkedList<>();
		int currow = this.getPiecePosition().getRow();
		int curcol = this.getPiecePosition().getCol();
		ChessMove move = new ChessMove(null, this);

		moves.add(board.getField()[currow][curcol]);

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
		ChessMove enpassant = this.CheckEnPassant(board, Wplayer, Bplayer);
		if (!enpassant.getNewPos().equals(null)) {
			moves.add(new Field(enpassant.getNewPos().getRow(), enpassant.getNewPos().getCol()));
		}

		return moves;
	}

	public boolean isMovePossible(ChessMove move, Board board) {
		boolean answer = false; // answer: is the move possible or not
		String color = this.getColor().toString(); // colour of pawn

		int curRow = this.getPiecePosition().getRow(); // piece's current row
		int curCol = this.getPiecePosition().getCol(); // piece's current column

		int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
		int col = move.getNewPos().getCol();

		if (!this.isBlocked(board)) {
			if (ChessMove.isValid(row, col)) { // if the new position is valid (row&col <8)
				if (col == curCol) {
					// forward
					if (this.isFirstMove()) { // the pawn can move 2 fields forward if it is its first move
						if (Math.abs((row - curRow)) == 1 || Math.abs((row - curRow)) == 2) {
							if (!board.isFieldOccupied(row, col)) {
								answer = true;
							}
						}
					} else { // not its first move-only one field forward is valid
						if (Math.abs((row - curRow)) == 1) {
							answer = true;
						} else {
							answer = false;
						}
					}
				} else {
					// diagonal
					if (Math.abs((col - curCol)) == 1 && Math.abs((row - curRow)) == 1) {
						if (board.isFieldOccupied(row, col)
								&& !(board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
							answer = true;
						} else { // check enpassant
						//	if(the move is en passant){
						//		answer = true;
						//	} else {
							answer = false;
						// 	}
						}
					}
				}
			} else {

			}
		}
		return answer;
	}

	public ChessMove CheckEnPassant(Board board, Player Wplayer, Player Bplayer) {

		ChessMove move = new ChessMove(null, this);
		int currow = this.getPiecePosition().getRow();
		int curcol = this.getPiecePosition().getCol();
		int Pawnrank = 0;
		int back = 0;
		int forward = 0;
		String enemycolour = "";
		Player enemy = new Player();

		if (Wplayer.isTurn()) {// white player's turn
			Pawnrank = 3; // white pawn must be at field 3 to catch en passant
			back = -2; // black pawn must have moved 2 fields forward (it was 2 fields back)
			forward = -1; // white pawn moves 1 field forward horizontally
			enemycolour = "b"; // enemyColour
			enemy = Bplayer; // enemy Player
		} else {
			Pawnrank = 4;
			back = 2;
			forward = 1;
			enemycolour = "w";
			enemy = Wplayer;
		}
		if (currow == Pawnrank) { // this defines if it is white's or black's turn
			for (int i = -1; i <= 1; i += 2) { // we need to check left of the pawn(-1) and right(+1), 0 is not needed
												// hence the i+=2
				int row = currow;
				int col = curcol + i;

				if (ChessMove.isValid(row, col)) {
					Field field = board.getField()[row][col];

					if (field.isOccupied() && field.getChessPiece().getName().equals(ChessPieceCharacteristics.Name.P)
							&& field.getChessPiece().getColor().toString().equals(enemycolour)) {
						if (enemy.getLastMove().getNewPos().equals(field.getFieldCoordintes()) && enemy.getLastMove()
								.getCurrent().equals(board.getField()[row + back][col].getFieldCoordintes())) {
							move.setNewCoor(currow + forward, curcol + i);
						}
					}
				}
			}
		}

		return move;

	}

	public boolean CheckPawnPromotion(int row) {
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

}
