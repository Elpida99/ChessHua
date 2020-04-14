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

	public List<Field> allPossibleMoves(Board board) { //missing en passant

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
		
		col=curcol - 1;
		move.setNewCoor(row, col);
		if (this.isMovePossible(move, board)) {
			moves.add(new Field(row, col));
		}

		return moves;
	}

	public boolean isMovePossible(ChessMove move, Board board) {
		boolean answer = false; // answer: is the move possible or not
		String color = this.getColor().toString(); // colour of pawn
		System.out.println("colour=" + color);

		int curRow = this.getPiecePosition().getRow(); // piece's current row
		int curCol = this.getPiecePosition().getCol(); // piece's current column

		System.out.println("old coordinates: " + curRow + "," + curCol);

		int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
		int col = move.getNewPos().getCol();

		System.out.println("new position: " + row + "," + col); // check that they are right
		if (!this.isBlocked(board)) {
			if (ChessMove.isValid(row, col)) { // if the new position is valid (row&col <8)
				System.out.print("New position is valid, coordinates exist on board : ");
				if (col == curCol) {
					// forward
					if (this.isFirstMove()) { // the pawn can move 2 fields forward if it is its first move
						if (Math.abs((row - curRow)) == 1 || Math.abs((row - curRow)) == 2) {
							System.out.println("move is possible-->move piece " + Math.abs((row - curRow))
									+ " position(s) forward");
							answer = true;
						}
					} else { // not its first move-only one field forward is valid
						if (Math.abs((row - curRow)) == 1) {
							answer = true;
							System.out.println("move is possible-->move piece 1 position forward");
						} else {
							System.out.println("move not possible for this kind of piece");
							answer = false;
						}
					}
				} else {
					// diagonal
					if (Math.abs((col - curCol)) == 1 && Math.abs((row - curRow)) == 1) {
						if (board.isFieldOccupied(row, col)
								&& !(board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
							System.out.println("Piece at field " + row + "," + col + " is eaten by pawn");
							answer = true;
						} else {
							System.out.println(
									"new field empty : pawn can only move diagonally to eat another piece of different color");
							answer = false;
						}
					}
				}
			} else {
				// not valid
				System.out.println("new field must exist on board!");
			}
		}
		return answer;
	}

	public int CheckEnPassantWhitePlayer(Board board, Player player) {

		boolean occupied = false;
		int counter = 0;
		int curRow = this.getPiecePosition().getRow();
		int curCol = this.getPiecePosition().getRow();

		int enemyRow = curRow;
		int enemyCol = 0;
		int enemyCol1 = curCol + 1;
		int enemyCol2 = curCol - 1;

		String enemyColour = ChessPieceCharacteristics.Color.b.toString();

		if (curRow == 4) {
			if (board.isFieldOccupied(enemyRow, enemyCol1)) { // right
				enemyCol = enemyCol1;
				occupied = true;
				counter++;
			}
			if (board.isFieldOccupied(enemyRow, enemyCol2)) { // left
				enemyCol = enemyCol2;
				occupied = true;
				counter++;
			}
			if (occupied && counter == 1) {
				ChessPiece piece = board.getField()[enemyRow][enemyCol].getChessPiece();
				if (piece.getColor().toString().equals(enemyColour)
						&& piece.getName().equals(ChessPieceCharacteristics.Name.P)) {
					if (player.getLastMove().getP().equals(piece)) {

					}

				}
			} else if (occupied && counter == 2) {
				ChessPiece piece1 = board.getField()[enemyRow][enemyCol1].getChessPiece();
				if (piece1.getColor().toString().equals(enemyColour)
						&& piece1.getName().equals(ChessPieceCharacteristics.Name.P)) {

				}
				ChessPiece piece2 = board.getField()[enemyRow][enemyCol2].getChessPiece();
				if (piece2.getColor().toString().equals(enemyColour)
						&& piece2.getName().equals(ChessPieceCharacteristics.Name.P)) {

				}
			}

		}
		return counter;
	}

	public int CheckEnPassantBlackPlayer(Board board) {

		int counter = 0;
//			enemyColour = ChessPieceCharacteristics.Color.b.toString();
//			if (curRow == 3) {
//
//			}
		return counter;

	}

	public void enPassant() {
		// if the last move of the enemy is
		// a pawn 2 fields forward and it would be eaten if it moved 1 field,then
		// pawn can catch the enPassant move and eat it as if it would have moved 1
		// field

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

	public void PawnPromotion(int row,Board board) {
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
					this.promotePawn(queen,board);
					temp = false;
					break;
				case 2:
					System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
							+ " is promoted to Bishop");
					Bishop bishop = new Bishop(this.getColor(), ChessPieceCharacteristics.Name.B);
					this.promotePawn(bishop,board);
					temp = false;
					break;
				case 3:
					System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
							+ " is promoted to Knight");
					Knight knight = new Knight(this.getColor(), ChessPieceCharacteristics.Name.N);
					this.promotePawn(knight,board);
					temp = false;
					break;
				case 4:
					System.out.println("Pawn at position " + row + "," + this.getPiecePosition().getCol()
							+ " is promoted to Rook");
					Rook rook = new Rook(this.getColor(), ChessPieceCharacteristics.Name.R);
					this.promotePawn(rook,board);
					temp = false;
					break;
				default:
					System.out.println("Not a valid answer");
					break;

				}
			}
		}

	}
	
	public void promotePawn(ChessPiece piece,Board board) {
		int row = this.getPiecePosition().getRow();
		int col = this.getPiecePosition().getCol();
		
		board.getField()[row][col].removeChessPiece(); 
		this.setIsAlive(false);
		board.getField()[row][col].setChessPiece(piece);
	}

}
