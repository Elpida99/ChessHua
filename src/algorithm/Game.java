package algorithm;

import java.util.Scanner;

import model_board.Board;
import model_chess_pieces.ChessPieceCharacteristics;

public class Game {

	private static Player whiteP = new Player(ChessPieceCharacteristics.Color.w, null);
	private static Player blackP = new Player(ChessPieceCharacteristics.Color.b, null);

	private static Board board = new Board();

	public void setBoard() {
		board.createBoard();
		board.printBoard();
		System.out.println("Players: w=white and b=black");
		System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn");
		System.out.println("Choose which piece you want to move like this: g7 h5 to move from g7 to h5");

	}

	public int returnCol(String letter) {
		int column = 0;
		if (letter.equalsIgnoreCase("a")) {
			column = 0;
		} else if (letter.equalsIgnoreCase("b")) {
			column = 1;
		} else if (letter.equalsIgnoreCase("c")) {
			column = 2;
		} else if (letter.equalsIgnoreCase("d")) {
			column = 3;
		} else if (letter.equalsIgnoreCase("e")) {
			column = 4;
		} else if (letter.equalsIgnoreCase("f")) {
			column = 5;
		} else if (letter.equalsIgnoreCase("g")) {
			column = 6;
		} else if (letter.equalsIgnoreCase("h")) {
			column = 7;
		} else {
			column = -1; // in case of wrong input
		}
		return column;
	}

	public int returnRow(int inputRow) {
		int row = 0;
		if (inputRow >= 0 && inputRow <= 8) {
			row = inputRow - 1;
		} else {
			row = -1; // in case the user inputs a row that does not exist
		}

		return row;
	}

	public void readFromUser() {
		Scanner input = new Scanner(System.in);

		String userInput = input.nextLine();
		userInput = userInput.trim();
		userInput = userInput + " ";
		String[] inputTokens = userInput.split(" ");

		if (inputTokens.length == 2) {
			// inputTokens[0] is the position of the piece
			// inputTokens[1] is the new position

			String curPos = inputTokens[0];
			curPos = curPos.trim();

			String newPos = inputTokens[1];
			newPos = newPos.trim();

			String[] curTokens = curPos.split("");
			String[] newTokens = newPos.split("");

			if (curTokens.length == 2 && newTokens.length == 2) {

				int curRow = returnRow(Integer.parseInt(curTokens[1]));
				int curCol = returnCol(curTokens[0]);

				System.out.println(
						"currow is: (" + curTokens[1] + ")" + curRow + " curcol is: (" + curTokens[0] + ")" + curCol);

				int newRow = returnRow(Integer.parseInt(newTokens[1]));
				int newCol = returnCol(newTokens[0]);

				System.out.println(
						"newrow is: (" + newTokens[1] + ")" + newRow + " newcol is: (" + newTokens[0] + ")" + newCol);

			} else {
				System.out.println("Not valid input");
			}
		}else {
			System.out.println("Not valid input");
		}
	}
	
	public void Game() {
		setBoard();
		whiteP.setTurn(true);
		blackP.setTurn(false);
		
		readFromUser();
	}

}
