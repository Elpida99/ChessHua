package algorithm;

import model_board.FieldCoordinates;
import model_chess_pieces.ChessPiece;

public class ChessMove {

	private FieldCoordinates current;
	private FieldCoordinates newPos;
	private ChessPiece p; // p is a copy of the piece that is to be moved

	public FieldCoordinates getCurrent() {
		return current;
	}

	public void setCurrent(FieldCoordinates current) {
		this.current = current;
	}

	public FieldCoordinates getNewPos() {
		return newPos;
	}

	public void setNewPos(FieldCoordinates newPos) {
		this.newPos = newPos;
	}

	public ChessPiece getP() {
		return p;
	}

	public void setP(ChessPiece p) {
		this.p = p;
	}

	public static boolean isValid(int row, int col) {
		return col >= 0 && col < 8 && row >= 0 && row < 8;
	}

}
