/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_board;

import model_chess_pieces.*;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Board {

	private Field field[][];
	private ChessMove lastMove;

	public ChessMove getLastMove() {
		return lastMove;
	}

	public void setLastMove(ChessMove lastMove) {
		this.lastMove = lastMove;
	}

     	public Field[][] getField() {
        return field;
   	 }

  	public void setField(Field[][] field) {
  	    this.field = field;
  	  }

    public Field getField(int x, int y)   //NEW METHOD
	{
		if(x<0||x>7||y<0||y>7)
			return null;
		Field possible = field[x][y];
		FieldCoordinates possibleCoords = possible.getFieldCoordintes();
		if(possibleCoords.row == x && possibleCoords.col == y){
			return possible;
		}
		int invertedX = Math.abs(x-7);
		int invertedY = Math.abs(y-7);
		possible = field[invertedY][invertedX];
		possibleCoords = possible.getFieldCoordintes();
		if(possibleCoords.row == x && possibleCoords.col == y){
			return possible;
		}
		return null;
	}
    
	public Board() {
		field = new Field[8][8];
	}
	
	public void createEmptyBoard() {
		field = new Field[8][8]; // how many there on the board

		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				field[i][j] = new Field(i, j);

			}
		}
	}

	public void createBoard() {

		createEmptyBoard();
		// emptyFields();
		placeBlackPieces();
		placeBlackPawns();
		placeWhitePieces();
		placeWhitePawns();
	}

	//NEW METHOD AND NEW FIELD IN CLASS FIELD !!
	public boolean isFieldOccupied(int x, int y) {
		return this.getField()[x][y].isOccupied();
	}
	
	private void placeBlackPieces() {
		this.field[0][0].setChessPiece(new Rook(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.R));
		this.field[0][1].setChessPiece(new Knight(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.N));
		this.field[0][2].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.B));
		this.field[0][3].setChessPiece(new Queen(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.Q));
		this.field[0][4].setChessPiece(new King(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.K));
		this.field[0][5].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.B));
		this.field[0][6].setChessPiece(new Knight(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.N));
		this.field[0][7].setChessPiece(new Rook(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.R));

	}

	private void placeBlackPawns() {
		for (int i = 0; i < 8; ++i) {
			this.field[1][i]
					.setChessPiece(new Pawn(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.P));
		}
	}

	private void placeWhitePieces() {
		this.field[7][0].setChessPiece(new Rook(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.R));
		this.field[7][1].setChessPiece(new Knight(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.N));
		this.field[7][2].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.B));
		this.field[7][3].setChessPiece(new Queen(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.Q));
		this.field[7][4].setChessPiece(new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K));
		this.field[7][5].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.B));
		this.field[7][6].setChessPiece(new Knight(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.N));
		this.field[7][7].setChessPiece(new Rook(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.R));
	}

	private void placeWhitePawns() {
		for (int i = 0; i < 8; ++i) {
			this.field[6][i]
					.setChessPiece(new Pawn(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.P));
		}
	}


	private void emptyFields() {
		int i = 0;
		for (i = 0; i < 8; i++) {
			for (int j = 3; j < 7; j++) {
				this.field[j][i].removeChessPiece();
			}
		}
	}

	// Standard even check
	public boolean isEven(int i) {
		if ((i % 2) == 0) {
			return true;
		}
		return false;
	}

	public void printBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.isFieldOccupied(i, j)) {
					System.out.print(this.field[i][j].getChessPiece().getColor() + ""
							+ this.field[i][j].getChessPiece().getName() + " ");
					if (j == 7) {
						int k = i + 1;
						System.out.print(" | " + k + "(" + (k - 1) + ")\n");
					}
					continue;
				} else {
					if ((isEven(i) && isEven(j)) || (!isEven(i) && !isEven(j))) {
						System.out.print("## ");
						if (j == 7) {
							int k = i + 1;
							System.out.print(" | " + k + "(" + (k - 1) + ")\n");
						}
						continue;
					} else {
						System.out.print("   ");
						if (j == 7) {
							int k = i + 1;
							System.out.print(" | " + k + "(" + (k - 1) + ")\n");
						}
						continue;
					}
				}
			}

		}
		System.out.println("------------------------");
		System.out.println(" a  b  c  d  e  f  g  h ");
		System.out.println("(0  1  2  3  4  5  6  7) \n");
	}
	
	public boolean containsPiece(int row, int col){
		return isFieldOccupied(row, col);
	}


	public boolean checkFirstMove(FieldCoordinates fieldcoordinates){
		if(containsPiece(fieldcoordinates)){
		    return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece().firstMove();
		}
		return false;
	}

	public boolean containsPiece(FieldCoordinates fieldcoordinates){
		return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].isOccupied();
	}

}
