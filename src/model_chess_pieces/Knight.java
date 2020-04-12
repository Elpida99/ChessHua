package model_chess_pieces;

import algorithm.ChessMove;
import model_board.Board;
import model_board.FieldCoordinates;

/**
 * it21735 , it21754, it21762
 */
public class Knight extends ChessPiece {

	private final int value = 3;

	private int[][] directions = { { 1, 2 }, { -1, 2 }, { 2, -1 }, { -2, -1 }, { -1, -2 }, { 1, -2 }, { -2, 1 },
			{ 2, 1 } }; // {vertical,horizontal}

	public Knight(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
		super(color, name);
	}

	@Override
	public int getValue() {
		return value;
	}

	public List<Field> allPossibleMoves(Board board) {
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

	public boolean isMovePossible(Board board, ChessMove move) {
		boolean answer = false; //final answer--is the requested move possible?
		boolean validPosition = false; //temp boolean to check if the move is valid without checking if field is occupied
		FieldCoordinates curpos = this.getPiecePosition(); //current position of piece

		String colour = this.getColor().toString(); //colour of piece
		System.out.println(colour + this.getName());

		int currow = curpos.getRow(); //current row
		int curcol = curpos.getCol(); //current column

		int row = move.getNewPos().getRow(); //requested new coordinates
		int col = move.getNewPos().getCol(); 

		if (ChessMove.isValid(row, col)) { //do the new coordinates exist on board?
			System.out.println("new coordinates exist on board---let's check if the move is valid");
			
			// 2 vertical + 1 horizontal
			if ((Math.abs(col - curcol) == 1) && Math.abs(row - currow) == 2) { //if column changes by 1 and row by 2 fields
				validPosition = true; //possible move---> 2 fields vertically and 1 horizontally

			// 2 horizontal + 1 vertical
			} else if ((Math.abs(col - curcol) == 2) && Math.abs(row - currow) == 1) { //if column changes by 2 and row by one field
				validPosition = true; //possible move---> 2 fields horizontally and 1 vertically

			} else { //no other move is possible for the knight
				System.out.println("Move not possible for this kind of piece");
			}
			if (validPosition) { //if valid position is true, we must check if the field is occupied or not and by what colour
				if ((board.isFieldOccupied(row, col))
						&& !(board.getField()[row][col].getChessPiece().getColor().toString().equals(colour))) { //if occupied by different colour-it gets eaten
					System.out.println("Piece at " + row + "," + col + " is eaten by the knight!");
					answer = true;
				} else if (!board.isFieldOccupied(row, col)) { //if not occupied, the position is free for the knight
					System.out.println("Knight is moved from " + currow + "," + curcol + " to " + row + "," + col);
					answer = true;
				}else {
					System.out.println("Position not available -- taken by another piece");
				}
			}
		} else {
			System.out.println("New position does not exist on the board!");
		}

		
		return answer;
	}
}
