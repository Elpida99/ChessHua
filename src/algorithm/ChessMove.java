package algorithm;

import model_board.FieldCoordinates;
import model_chess_pieces.ChessPiece;

public class ChessMove {

	private FieldCoordinates current;
	private FieldCoordinates newPos;
	private ChessPiece p; // p is a copy of the piece that is to be moved

	public ChessMove() {
		
	}
	
	public ChessMove(FieldCoordinates newPos, ChessPiece p) {
		super();
		this.current = p.getPiecePosition();
		this.newPos = newPos;
		this.p = p;
	}

	public FieldCoordinates getCurrent() {
		return current;
	}

	public void setCurrent(FieldCoordinates current) {
		this.current = current;
	}

	  public void setCurCoor(int row, int col){
            FieldCoordinates curpos = new FieldCoordinates(row,col);
            this.current=curpos;
        }
	
	public FieldCoordinates getNewPos() {
		return newPos;
	}

	public void setNewPos(FieldCoordinates newPos) {
		this.newPos = newPos;
	}
	
	public void setNewCoor(int row,int col) {
		FieldCoordinates newpos = new FieldCoordinates(row,col);
		this.newPos = newpos;
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

