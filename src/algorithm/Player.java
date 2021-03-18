package algorithm;

import model_chess_pieces.ChessPieceCharacteristics;
import model_chess_pieces.ChessPieceCharacteristics.Color;

public class Player {

	private ChessMove lastMove;
	private boolean turn;
	private ChessPieceCharacteristics.Color playerColour;
	
	public Player() {
		this.lastMove=null;
	}
	
	public Player(Color playerColour,ChessMove lastMove) {
		this.playerColour=playerColour;
		this.lastMove = lastMove;
	}
	
	public ChessMove getLastMove() {
		return lastMove;
	}
	public void setLastMove(ChessMove lastMove) {
		this.lastMove = lastMove;
	}
	public boolean isTurn() {
		return turn;
	}
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	public Color getPlayerColour() {
		return playerColour;
	}
	public void setPlayerColour(Color playerColour) {
		this.playerColour = playerColour;
	}
	
	
}
