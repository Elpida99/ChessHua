package model_chess_pieces;

import algorithm.ChessMove;
import model_board.Board;

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

	public boolean isMovePossible(ChessMove move, Board board) {
		boolean answer = false; // answer: is the move possiple or not
		move.setOldXY(this.getPiecePosition()); // piece's current position-->set it to class ChessMove
		int row = move.getNewXY().getX(); // coordinates of desired move are the directions of the user
		int col = move.getNewXY().getY();
		System.out.println(row + " " + col); // check that they are right
		if (ChessMove.isValid(col, row)) {
			System.out.print("New position is valid, coordinates exist on board : ");
			if (this.isFirstMove()) { // the pawn can move 2 fields forward if it is its first move
				// forward
				if (board.isFieldOccupied(move.getOldXY().getX(), move.getOldXY().getY())) { // if piece exists on the
																								// field
					System.out.print("piece exists on this field and it can be moved\n");
					if (board.isFieldOccupied(row, col)) { // if new position is occupied, the pawn cannot move to that
															// position
						System.out.println("move not possible-new position occupied");
						answer = false;
					} else {
						System.out.println("new position is available");
						if (col == getPiecePosition().getY() && Math.abs((row - getPiecePosition().getX())) <= 2) { // if
							// fields
							System.out.println("move is possible-->move piece "
									+ Math.abs((row - getPiecePosition().getX())) + " position(s) forward");
							answer = true;
						} else {
							System.out.println("move not possible for this kind of piece");
							answer = false;
						}
					}
				} else {
					System.out.println("This field " + move.getOldXY().getX() + " " + move.getOldXY().getY()
							+ " does not contain a piece");
				}
			} else { // NOT FIRST MOVE
				// forward
				if (col == getPiecePosition().getY()) {
					if (Math.abs((getPiecePosition().getX() - row)) == 1) {
						answer = true;
						System.out.println("move is possible-->move piece 1 position forward");
					} else {
						System.out.println("move not possible for this kind of piece");
						answer = false;
					}
					// diagonal
				} else {
					if (Math.abs((getPiecePosition().getX() - row)) == 1
							&& Math.abs((getPiecePosition().getY() - col)) == 1) {
						if (board.isFieldOccupied(row, col)) {
							System.out.println("Piece at field "+move.getNewXY().getX()+","+move.getNewXY().getY()+" is eaten by pawn");
							answer = true;
						} else {
							System.out.println("new field empty : pawn can only move diagonally to eat another piece");
							answer = false;
						}
					}
				}

			}
		} else {
			System.out.println("new field must exist on board!");
		}
		return answer;
	}


}
