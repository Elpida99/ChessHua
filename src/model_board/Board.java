package model_board;

import algorithm.ChessMove;
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

    public Field getField(int x, int y) //NEW METHOD
    {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        Field possible = field[x][y];
        FieldCoordinates possibleCoords = possible.getFieldCoordintes();
        if (possibleCoords.row == x && possibleCoords.col == y) {
            return possible;
        }
        int invertedX = Math.abs(x - 7);
        int invertedY = Math.abs(y - 7);
        possible = field[invertedY][invertedX];
        possibleCoords = possible.getFieldCoordintes();
        if (possibleCoords.row == x && possibleCoords.col == y) {
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

    public boolean checkFirstMove(FieldCoordinates fieldcoordinates){    ///??????
        if(containsPiece(fieldcoordinates)){
            return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece().firstMove();   // method firstMove() :   returns false
        }
        return false;
    }
    
    public boolean containsPiece(FieldCoordinates fieldcoordinates){
        return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].isOccupied();
    }
    
        
     //gets the type of piece at the given FieldCoordinates = (row, col)
    public ChessPieceCharacteristics.Name getPieceType(FieldCoordinates fieldcoordinates ){ //FieldCoordinates fieldcoordinates == Position position

        if(containsPiece(fieldcoordinates)){
            return field[fieldcoordinates.getCol()][fieldcoordinates.getRow()].getChessPiece().getName();  //returns the type of the piece 
        }
        return null;
    }
    
     //move a piece from the start to the end, it takes the piece at the start position and moves it to ene end position
    public void movePiece(FieldCoordinates start, FieldCoordinates end){
      //  field[end.getRow()][end.getCol()].setChessPiece(field[start.getCol()][start.getRow()].takeChessPiece());  
        
        // setPiecePosition(FieldCoordinates piecePosition)
        Field myfield = field[end.getRow()][end.getCol()];
        myfield.setFieldCoordinates(myfield.getFieldCoordintes().getRow(), myfield.getFieldCoordintes().getCol());
    }   
    
    //if the tile is not occupied it can place down the piece, if it's occupied it doesn't place down the piece
    boolean setPiece(FieldCoordinates position, ChessPiece piece){

        if(!containsPiece(position)){
            field[position.getRow()][position.getCol()].setChessPiece(piece);
            return true;
        }

        return false;

    }
       
    //takes the piece off the tile
    public ChessPiece removePiece(FieldCoordinates fieldcoordinates){

        if(containsPiece(fieldcoordinates)){
            return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].takeChessPiece();
        }
        return null;
    }
    
    //this handles castling and pawn moving for the first time
    public ChessPiece moveSpecialPiece (ChessMove move) {

        //position variables to make it a bit easier when looking up moves
        FieldCoordinates moveStart = move.getCurrent();   //start
        FieldCoordinates moveEnd = move.getNewPos();      //end

        //tiles from the start to the end
        Field startTile = field[moveStart.getRow()][moveStart.getCol()];
        Field endTile = field[moveEnd.getRow()][moveEnd.getCol()];

        //pieces from the start to the end
        ChessPiece startPiece = startTile.getChessPiece();
        ChessPiece endPiece = null;

        //if the start piece is null, there's no way for a move to be made
        if (startPiece == null) {
            return null;
        }

        //if the pawn is moving for the first time and its moving twice set the first move to true so it can't move twice again
        if (startPiece.getName() == ChessPieceCharacteristics.Name.P) {  //method getName returns the type of the piece
            startPiece.setFirstMove(false);
            //if the rook or king is moving for the first time set that piece to already moved, so it can't be used for castling
        } else if (startPiece.getName() == ChessPieceCharacteristics.Name.R || startPiece.getName() == ChessPieceCharacteristics.Name.K) { // rook || king 
            if (startPiece.firstMove()) 
                startPiece.setFirstMove(false);
        }

        //if the end piece is occupied get the piece
        if (endTile.isOccupied()) {
            endPiece = endTile.takeChessPiece();
        }

        endTile.setChessPiece(startTile.takeChessPiece());
       
        //castle the pieces if if's a castling move
        if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.K && getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.R) {

            System.out.println("Castling");

//            if (move.getEnd().getCol() == 6) movePiece(new Position(7, move.getEnd().getRow()),

//                    new Position(5, move.getEnd().getRow()));

//            else if (move.getEnd().getCol() == 2) {

//                movePiece(new Position(0, move.getEnd().getRow()),

//                        new Position(3, move.getEnd().getRow()));

//            }
        }
        return endPiece;
    }
        
     // undoes the passed move and any special moves
    public void undoSpecialMove (ChessMove move, ChessPiece piece, boolean firstMove) {

        //reverse the move
        movePiece(move.getNewPos(), move.getCurrent()); //end, start 

        //if there is a piece place the piece down at the destination of the move
        if (piece != null) 
            setPiece(move.getNewPos(), piece);

        //set the first move if there is a piece at the start position
        if (firstMove && containsPiece(move.getCurrent()))
            field[move.getCurrent().getRow()][move.getCurrent().getCol()].getChessPiece().madeFirstMove();

        // undo castling if castling has happened
        if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.K   //king
                &&
                field[move.getCurrent().getRow()][move.getCurrent().getCol()].getChessPiece().firstMove() &&  move.getCurrent().getCol() == 4) {

            if (move.getCurrent().getCol() - move.getNewPos().getCol() == -2 &&

                    containsPiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol()-1)) &&

                    getPieceType(new FieldCoordinates(move.getNewPos().getRow() , move.getNewPos().getCol()-1)) == ChessPieceCharacteristics.Name.R  &&

                    field[move.getCurrent().getRow()][move.getNewPos().getCol() - 1].getChessPiece().firstMove()) {

                        setPiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() + 1),

                        removePiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() - 1)));

            } else if (move.getCurrent().getCol() - move.getNewPos().getCol() == 2 &&

                    containsPiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() + 1)) &&

                    getPieceType(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() + 1)) == ChessPieceCharacteristics.Name.R &&

                    field[move.getCurrent().getRow()][move.getNewPos().getCol() + 1].getChessPiece().firstMove()) {

                setPiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() - 2),

                        removePiece(new FieldCoordinates(move.getNewPos().getRow(), move.getNewPos().getCol() + 1)));

            }

        }
    }
  
}
