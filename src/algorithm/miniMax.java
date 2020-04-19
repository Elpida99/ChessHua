package algorithm;

import exceptions.InvalidMoveException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model_board.*;
import model_chess_pieces.*;

public class miniMax {

    private ChessPieceCharacteristics.Color AIColor;
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

                    } else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.K)) {
                        piece = new King(tempPiece.getColor(), ChessPieceCharacteristics.Name.K);

                    } else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.N)) {
                        piece = new Knight(tempPiece.getColor(), ChessPieceCharacteristics.Name.N);

                    } else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.Q)) {
                        piece = new Queen(tempPiece.getColor(), ChessPieceCharacteristics.Name.Q);

                    } else if (tempPiece.getName().equals(ChessPieceCharacteristics.Name.R)) {
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
					// if
					// (board.getField()[i][j].getChessPiece().getName().equals(ChessPieceCharacteristics.Name.B))
					// {
					System.out.println("Move of: " + board.getField()[i][j].getChessPiece().getColor() + ""
							+ board.getField()[i][j].getChessPiece().getName() + " is ");
					Field tempField = board.getField()[i][j];
					totalPieces++;
					// if the piece is the colour of current player--> increase value
					if (tempField.getChessPiece().getColor() == AIColor) {
						value += tempField.getChessPiece().getValue();
						mobility += getAllPieceMoves(board, tempField).toArray().length;

						List<ChessMove> moves = new LinkedList<>();
						moves = getAllPieceMoves(board, tempField);
						for (int y = 0; y < moves.size(); y++) {
							System.out.print(" " + moves.get(y).getCurrent().getRow() + ","
									+ moves.get(y).getCurrent().getCol() + "-->" + moves.get(y).getNewPos().getRow()
									+ "," + moves.get(y).getNewPos().getCol() + "\n");
						}
					} else { // if enemy piece-->decrease value
						value -= tempField.getChessPiece().getValue();
						mobility -= getAllPieceMoves(board, tempField).toArray().length;
						List<ChessMove> moves = new LinkedList<>();
						moves = getAllPieceMoves(board, tempField);
						for (int y = 0; y < moves.size(); y++) {
							System.out.print(" " + moves.get(y).getCurrent().getRow() + ","
									+ moves.get(y).getCurrent().getCol() + "-->" + moves.get(y).getNewPos().getRow()
									+ "," + moves.get(y).getNewPos().getCol() + "\n");
						}
					}
				}

			}
		}
		/*
		 * The evaluation of the board is the value plus the mobility times the sum of
		 * the remaining pieces divided by the original number of pieces, making the
		 * mobility more important as the game progresses
		 */
		System.out.println("mobility is: " + mobility);
		return value + (int) Math.round(mobility * ((totalPieces / 32) + 0.1));

	}

	public List<ChessMove> getAllPieceMoves(Board board, Field field) {
		List<ChessMove> allMoves = new LinkedList<>();
		int row = field.getFieldCoordintes().getRow();
		int col = field.getFieldCoordintes().getCol();

		try {
			possibleMoves = board.getField()[row][col].getChessPiece().allPossibleMoves(board, player);
			for (Field Field : possibleMoves) {
				allMoves.add(new ChessMove(Field.getFieldCoordintes(), board.getField()[row][col].getChessPiece()));
			}
		} catch (Exception e) {

		}
		return allMoves;
	}
    
    //gets all the moves of the color passed to it
    private List<ChessMove> getAllMoves(Board board, ChessPieceCharacteristics.Color colorType){
        //empty list that holds all the moves
        List<ChessMove> playerMoves = new LinkedList<>();
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if that particular position has a piece that is the same color as the AI
                if (board.isFieldOccupied(j, j)&& board.getField()[i][j].getChessPiece().getColor() == colorType){
                    
                    possibleMoves = board.getField(i, j).getChessPiece().allPossibleMoves(board, player);
                    
                    for (Field field : possibleMoves) {
                            playerMoves.add(new ChessMove(field.getFieldCoordintes(), board.getField()[i][j].getChessPiece()));
                        }
                }
            }
        }
        return playerMoves;
    }

    /*
    private int Min(Board board, int alpha, int beta, int boardDepth) {

        ChessPiece piece;
        boolean madeFirstMove;

        int maxVal;
        //if its the bottom of the depth tree get the board value
        if (boardDepth == 0) {
            return evaluateBoard(board);
        }
        List<ChessMove> moves;
        //get the list of moves for the opposite player
        if (AIColor == ChessPieceCharacteristics.Color.w) {
            moves = getAllMoves(board, ChessPieceCharacteristics.Color.b);
        } else {
            moves = getAllMoves(board, ChessPieceCharacteristics.Color.w);
        }
        //if there are still moves that can be made
        if (moves != null) {
            while (moves.size() > 0) {
                //get the first move
                ChessMove firstMove = moves.remove(0);
                if (firstMove.getCurrent().equals(firstMove.getNewPos())) {
                    continue;
                }
                //check to see if its the first move
                madeFirstMove = chessBoard.checkFirstMove(firstMove.getStart());
                //make the first move in the move list
                piece = chessBoard.moveSpecialPiece(firstMove);
                //get the max value by going down one more level in the depth
                maxVal = Max(chessBoard, alpha, beta, boardDepth - 1);
                if (maxVal <= alpha) {
                    chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove); // undo
                    return alpha;
                }
                if (maxVal < beta) {
                    if (boardDepth == depth) {
                        nextMove = firstMove;
                    }
                    beta = maxVal;
                }
                chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove); // undo
            }
        }
        return beta;
    }
*/
	

    private int Max(Board board, int alpha, int beta, int boardDepth) {

        ChessPiece piece;
        boolean madeFirstMove;
        int minVal;

        //if it's at the depth get the board evulation
        if (boardDepth == 0) {
            return evaluateBoard(board);
        }

        //get the list of moves for the color of the current player
        List<ChessMove> moves;

        if (AIColor == ChessPieceCharacteristics.Color.w) {
            moves = getAllMoves(board, ChessPieceCharacteristics.Color.w);
        } else {
            moves = getAllMoves(board, ChessPieceCharacteristics.Color.b);
        }

        //if there are moves that can be made for the player
        if (moves != null) {

            //while there are still moves that the player can make
            while (moves.size() > 0) {

                // get the first move from the move list  => iterate the whole list with the moves
                ChessMove firstMove = moves.remove(0);

                if (firstMove.getCurrent().equals(firstMove.getNewPos())) {
                    continue;
                }
                //check if the first move is being made
                madeFirstMove =  board.checkFirstMove(firstMove.getCurrent());

                // make move
                piece = board.moveSpecialPiece(firstMove);    //?????

                //get the min value by calling the min function at a depth one lower than the current depth
                minVal = Min(board, alpha, beta, boardDepth - 1);

                // if the min is greater than the beta undo the move and return the beta value
                if (minVal >= beta) {
                    board.undoSpecialMove(firstMove, piece, madeFirstMove);    //here , needsd to be completed 
                    return beta;
                }

                //the alpha value set if the min is greater than the alpha
                if (minVal > alpha) {
                    if (boardDepth == depth) {
                        nextMove = firstMove;
                    }
                    alpha = minVal;
                }
                //undo the move made
                board.undoSpecialMove(firstMove, piece, madeFirstMove);
            }
        }
        return alpha;
    }

}
