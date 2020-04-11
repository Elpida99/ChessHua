/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import java.util.List;

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

}
