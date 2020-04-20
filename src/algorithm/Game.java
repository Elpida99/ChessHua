package algorithm;

import model_board.Board;
import model_chess_pieces.ChessPieceCharacteristics;

public class Game {

	private static Player whiteP = new Player(ChessPieceCharacteristics.Color.w,null);
	private static Player blackP = new Player(ChessPieceCharacteristics.Color.b,null);
	
	private static Board board = new Board();
	
	public void setBoard() {
		board.createBoard();
		board.printBoard();
		System.out.println("Players: w=white and b=black");
		System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn\n");

	}
	

}
