package algorithm;

import java.util.ArrayList;

import model_board.Board;
import model_chess_pieces.ChessPiece;

public class miniMax {

	private List<Field> possibleMoves;
	private ChessMove nextMove;
	private Player player;
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
public int evaluateBoard(Board board) {
		int value = 0;
		double mobility = 0.0; // total number of legal moves of current player
		int totalPieces = 0;

		// goes thorough the entire board to evaluate the summed value of the pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.isFieldOccupied(i, j)) {
					Field tempField = board.getField()[i][j];
					totalPieces++;
					// if the piece is the colour of current player--> increase value
					if (tempField.getChessPiece().getColor().toString().equals(player.getPlayerColour())) {
						value += tempField.getChessPiece().getValue();
						mobility += getAllPieceMoves(board, tempField).toArray().length;
						
						List<ChessMove> moves = new LinkedList<>();
						moves = getAllPieceMoves(board, tempField);
//						
					} else { // if enemy piece-->decrease value
						value -= tempField.getChessPiece().getValue();
						mobility -= getAllPieceMoves(board, tempField).toArray().length;
					}
				}
			}
		}
		/*
		 * The evaluation of the board is the value plus the mobility times the sum of
		 * the remaining pieces divided by the original number of pieces, making the
		 * mobility more important as the game progresses
		 */
		System.out.println(mobility);
		return value + (int) Math.round(mobility * ((totalPieces / 32) + 0.1));

	}

	public List<ChessMove> getAllPieceMoves(Board board, Field field) {
		List<ChessMove> allMoves = new LinkedList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.isFieldOccupied(i, j)) { //currently finds only for pawns and knights
					if (board.getField()[i][j].getChessPiece().getName().equals(ChessPieceCharacteristics.Name.P) || board.getField()[i][j].getChessPiece().getName().equals(ChessPieceCharacteristics.Name.N)) {
						possibleMoves = board.getField()[i][j].getChessPiece().allPossibleMoves(board, player);
						for (Field Field : possibleMoves) {
							allMoves.add(
									new ChessMove(Field.getFieldCoordintes(), board.getField()[i][j].getChessPiece()));
						}
					}
				}
			}
		}

		return allMoves;
	}

}

