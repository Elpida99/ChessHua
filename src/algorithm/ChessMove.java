package algorithm;

import model_board.FieldCoordinates;
import model_chess_pieces.ChessPiece;

public class ChessMove {

	private FieldCoordinates oldXY;
	private FieldCoordinates newXY;
	private ChessPiece p; // p is a copy of the piece that is to be moved

	public FieldCoordinates getOldXY() {
		return oldXY;
	}

	public void setOldXY(FieldCoordinates oldXY) {
		this.oldXY = oldXY;
	}

	public FieldCoordinates getNewXY() {
		return newXY;
	}

	public void setNewXY(FieldCoordinates newXY) {
		this.newXY = newXY;
	}
	
	public int getnewX(FieldCoordinates newXY) {
		int x=newXY.x;
		return x;
	}
	
	public int getnewY(FieldCoordinates newXY) {
		int y=newXY.y;
		return y;
	}

	public ChessPiece getP() {
		return p;
	}

	public void setP(ChessPiece p) {
		this.p = p;
	}

	public static boolean isValid(int col, int row) {
		return col >= 0 && col < 8 && row >= 0 && row < 8;
	}

}
