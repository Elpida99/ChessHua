package algorithm;

public class Player {

	private ChessMove lastMove;
	private boolean turn;
	private String playerColour;
	
	public Player() {
		this.lastMove=null;
	}
	
	public Player(String playerColour,ChessMove lastMove) {
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
	public String getPlayerColour() {
		return playerColour;
	}
	public void setPlayerColour(String playerColour) {
		this.playerColour = playerColour;
	}
	
	
}
