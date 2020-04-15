package algorithm;

import java.util.ArrayList;

import model_board.Board;
import model_chess_pieces.ChessPiece;

public class miniMax {

	// 3 methods needed
	// min, max, decision

	private static final int DEPTH = 1;
	
	public Board copyBoard(Board board) {
		Board copy = new Board();
		copy.createEmptyBoard();
		ChessPiece piece = null;
		// go through each tile and based on what was in the passed game board sets the
		// new game board to that piece
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.isFieldOccupied(i, j)) {
					ChessPiece tempPiece = board.getField()[i][j].getChessPiece();
					if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.P)) {
						piece = new Pawn(tempPiece.getColor(), ChessPieceCharacteristics.Name.P);
					} else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.B)) {
						piece = new Bishop(tempPiece.getColor(), ChessPieceCharacteristics.Name.B);

					}else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.K)) {
						piece = new King(tempPiece.getColor(), ChessPieceCharacteristics.Name.K);

					}else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.N)) {
						piece = new Knight(tempPiece.getColor(), ChessPieceCharacteristics.Name.N);

					}else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.Q)) {
						piece = new Queen(tempPiece.getColor(), ChessPieceCharacteristics.Name.Q);

					}else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.R)) {
						piece = new Rook(tempPiece.getColor(), ChessPieceCharacteristics.Name.R);

					}

					copy.getField()[i][j].setChessPiece(piece);
				}
			}
		}
		return copy;
	}

}

 
