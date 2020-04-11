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
		boolean answer = false; // answer: is the move possible or not
		String color = this.getColor().toString(); //colour of pawn
		System.out.println(color);
		move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove
		
		int curRow = this.getPiecePosition().getRow(); //piece's current row
		int curCol = this.getPiecePosition().getCol(); // piece's current column
		
		System.out.println("old coordinates: "+move.getCurrent().getRow()+","+move.getCurrent().getCol());
				
		int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
		int col = move.getNewPos().getCol();
		
		System.out.println("new position: "+row+","+col); // check that they are right
		if (ChessMove.isValid(row,col)) {
			System.out.print("New position is valid, coordinates exist on board : ");
			if (this.isFirstMove()) { // the pawn can move 2 fields forward if it is its first move
				// forward
				if (board.isFieldOccupied(curRow,curCol)) { // if piece exists on the
																								// field
					System.out.print("piece exists on this field and it can be moved\n");
					if (board.isFieldOccupied(row, col)) { // if new position is occupied, the pawn cannot move to that
															// position
						System.out.println("move not possible-new position occupied");
						answer = false;
					} else {
						System.out.println("new position is available");
						if (col== curCol && Math.abs((row - curRow)) <= 2) { // if
							// fields
							System.out.println("move is possible-->move piece "
									+ Math.abs((row - curRow)) + " position(s) forward");
							answer = true;
						} else {
							System.out.println("move not possible for this kind of piece");
							answer = false;
						}
					}
				} else {
					System.out.println("This field " + curRow + " " + curCol
							+ " does not contain a piece");
				}
			} else { // NOT FIRST MOVE
				// forward
				if (col == curCol) {
					if (Math.abs((row-curRow)) == 1) {
						answer = true;
						System.out.println("move is possible-->move piece 1 position forward");
					} else {
						System.out.println("move not possible for this kind of piece");
						answer = false;
					}
					// diagonal
				} else {
					if (Math.abs((col-curCol)) == 1
							&& Math.abs((row-curRow)) == 1) {
						if (board.isFieldOccupied(row,col) && !(board.getField()[col][row].getChessPiece().getColor().toString().equals(color))) {
							System.out.println("Piece at field "+row+","+col+" is eaten by pawn");
							answer = true;
						} else {
							System.out.println("new field empty : pawn can only move diagonally to eat another piece of different color");
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
