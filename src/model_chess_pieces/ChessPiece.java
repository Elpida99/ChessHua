/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import java.util.List;
import model_board.*;

/**
 *
 * @author it21735 , it21754, it21762
 */
public abstract class ChessPiece {

    private boolean isAlive;                                          //if they still exist on the board => isAlive = true;
    private final ChessPieceCharacteristics.Color color;              //is the piece black or white
    private final ChessPieceCharacteristics.Name name;                //the type of piece
    private FieldCoordinates piecePosition;
    
    public ChessPiece(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        this.color = color;
        this.name = name;
    }

    
    //public abstract boolean isMovePossible();

    //public abstract List<Field> getPossibleMoves();

    public abstract int getValue();

    public ChessPieceCharacteristics.Name getName() {
        return this.name;
    }

    public ChessPieceCharacteristics.Color getColor() {
        return color;
    }

    public FieldCoordinates getPiecePosition() {
		return piecePosition;
	}


	public void setPiecePosition(FieldCoordinates piecePosition) {
		this.piecePosition = piecePosition;
	}
    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
	
	public void makeMove(ChessMove move, Board board) {

		int curRow = this.getPiecePosition().getRow(); // piece's current row
		int curCol = this.getPiecePosition().getCol(); // piece's current column

		int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
		int col = move.getNewPos().getCol();

		System.out.println("Current position: " + curRow + "," + curCol);
		System.out.println("New position: " + row + "," + col);

		board.getField()[curRow][curCol].removeChessPiece(); // remove piece from current position
		board.getField()[row][col].setChessPiece(this); // set it to the new field

	}
	
	public boolean isBlocked(Board board) {
		boolean blocked = false;
		boolean white = false;
		
		int curRow = this.getPiecePosition().getRow();
		int curCol = this.getPiecePosition().getCol();
		
		if (this.getColor().equals(ChessPieceCharacteristics.Color.w)) { // white
			white = true;
		}
		if(white) { //white
			if(board.getField()[curRow-1][curCol].isOccupied()) {
				blocked=true;
			}
		}else { //black 
			if(board.getField()[curRow+1][curCol].isOccupied()) {
				blocked=true;
			}
		}
		
		return blocked;
	}

}
