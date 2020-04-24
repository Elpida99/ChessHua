package model_board;

import model_chess_pieces.ChessPiece;

/**
 * @author it21735 , it21754, it21762
 */
public class Field {

	private boolean isOccupied;
	private ChessPiece chessPiece;
	private FieldCoordinates fieldCoordinates = new FieldCoordinates();;
	private final FieldCoordinates permanentCoordinates;

   
	public Field(int row, int col) {
		this.chessPiece = null;
		this.fieldCoordinates = new FieldCoordinates(row, col);
		this.permanentCoordinates = new FieldCoordinates(row, col);
		this.isOccupied=false;
	}

	public Field(int row, int col, ChessPiece chessman) {
		this.chessPiece = chessman;
		this.fieldCoordinates = new FieldCoordinates(row, col);
		this.permanentCoordinates = new FieldCoordinates(row, col);
		this.isOccupied=true;
	}

	public ChessPiece getChessPiece() // returns which one of the figures is in a specific field
	{	
		return this.chessPiece;
	}

	public void setChessPiece(ChessPiece chessman) // set one of the figures is in a specific field
	{
		this.chessPiece = chessman;
                chessman.setPiecePosition(this.getFieldCoordintes());
		this.isOccupied=true;
	}

	public void removeChessPiece() {
		this.chessPiece = null;
                this.setOccupied(false);
	}
        
        public void removeChessPiece(ChessPiece chessPiece) {
		this.chessPiece = null;
                this.setOccupied(false);
	}
        
	public FieldCoordinates getFieldCoordintes() {
		return this.fieldCoordinates;
	}

	public void setFieldCoordinates(int row, int col) {
		this.fieldCoordinates.row = row;
		this.fieldCoordinates.col = col;
	}

	public FieldCoordinates getPermanentCoordinates() {
		return this.permanentCoordinates;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

        public ChessPiece takeChessPiece(){    //makes the current piece null and returns the tiles piece  //????????
            if(isOccupied()){
                ChessPiece holder = chessPiece;
                //chessPiece = null;
                //this.setOccupied(false);

                removeChessPiece(chessPiece);
                //setIsAlive(false);
                //this.Field[chessPiece.getPiecePosition().getRow()][ chessPiece.getPiecePosition().getCol())].setOccupied(false);
                //System.out.println("chessPiece = "+chessPiece );
                //System.out.println("holder= "+holder.getPiecePosition() );
                return holder;
            }
            return null;
        }

}
