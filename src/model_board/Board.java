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

    public Field getField(int x, int y) 
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
                         System.out.print(" | " + k +  "\n");                 
                    }
                    continue;
                } else {
                    if ((isEven(i) && isEven(j)) || (!isEven(i) && !isEven(j))) {
                        System.out.print("## ");
                        if (j == 7) {
                            int k = i + 1;
                             System.out.print(" | " + k +  "\n");
                        }
                        continue;
                    } else {
                        System.out.print("   ");
                        if (j == 7) {
                            int k = i + 1;
                             System.out.print(" | " + k +  "\n");
                        }
                        continue;
                    }
                }
            }

        }
        System.out.println("------------------------");
        System.out.println(" a  b  c  d  e  f  g  h \n");
        //System.out.println("(0  1  2  3  4  5  6  7) \n");
    }
    
    
    public boolean containsPiece(int row, int col){
        return isFieldOccupied(row, col);
    }

    public boolean checkFirstMove(FieldCoordinates fieldcoordinates){    ///??????
        if(containsPiece(fieldcoordinates) && field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece() != null  && ChessMove.isValid(fieldcoordinates.getRow(), fieldcoordinates.getCol())){
            return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece().firstMove();   // method firstMove() :   returns false
        }
        return false;
    }
    
    public boolean containsPiece(FieldCoordinates fieldcoordinates){
        return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].isOccupied();
    }
    
        
     //gets the type of piece at the given FieldCoordinates = (row, col)
    public ChessPieceCharacteristics.Name getPieceType(FieldCoordinates fieldcoordinates ){ //FieldCoordinates fieldcoordinates == Position position

        if(containsPiece(fieldcoordinates) && ChessMove.isValid(fieldcoordinates.getRow(), fieldcoordinates.getCol())){
            if ( field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece() != null) 
                return field[fieldcoordinates.getRow()][fieldcoordinates.getCol()].getChessPiece().getName();  //returns the type of the piece 
        }
        return null;
    }
    
    //move a piece from the start to the end, it takes the piece at the start position and moves it to the end position
    //for castling
    public void movePiecesForCastling(FieldCoordinates king_start,FieldCoordinates king_end, FieldCoordinates rook_start,FieldCoordinates rook_end ){       
        field[king_end.getRow()][king_end.getCol()].setChessPiece(field[king_start.getCol()][king_start.getRow()].takeChessPiece());  
        field[rook_end.getRow()][rook_end.getCol()].setChessPiece(field[rook_start.getCol()][rook_start.getRow()].takeChessPiece());  
    }   
    
    //move a piece from the start to the end, it takes the piece at the start position and moves it to the end position
    public void movePiece(FieldCoordinates start,FieldCoordinates end ){       
        
        Field endfield = field[end.getRow()][end.getCol()];
        Field startfield = field[start.getRow()][start.getCol()];
        endfield.setChessPiece(startfield.takeChessPiece());  

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
         Field startTile = null;
        //position variables to make it a bit easier when looking up moves
        FieldCoordinates moveStart = move.getCurrent();   //start
        FieldCoordinates moveEnd = move.getNewPos();      //end

        //tiles from the start to the end
        startTile = field[moveStart.getRow()][moveStart.getCol()];
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

        King king = null;
        
        //castle the pieces if it's a castling move
        if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.K && getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.R) {
            king = new King(startPiece.getColor(), ChessPieceCharacteristics.Name.K);
            if( king.isCastlingPossible(this, startTile , endTile)){  //board, king, rook 
    
                System.out.println("AI: Castling");
                king.doCastling(this, startTile, endTile);
            }
        }else if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.R && getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.K) {
            king = new King(endTile.getChessPiece().getColor(), ChessPieceCharacteristics.Name.K);
            if( king.isCastlingPossible(this,endTile, startTile)){  //board, king ,  rook 
            
                System.out.println("AI: Castling");
                king.doCastling(this,endTile, startTile);
            }         
        }      

        return endPiece;
    }
        
     // undoes the passed move and any special moves
    public void undoSpecialMove (ChessMove move, ChessPiece piece, boolean firstMove) {

        //reverse the move
        movePiece( move.getNewPos(), move.getCurrent()); //end, start 

        //if there is a piece place the piece down at the destination of the move
        if (piece != null) 
            setPiece(move.getNewPos(), piece);

        //set the first move if there is a piece at the start position
        if (firstMove && containsPiece(move.getCurrent()))
            field[move.getCurrent().getRow()][move.getCurrent().getCol()].getChessPiece().madeFirstMove();

        King king = null;

        // undo castling if castling has happened
        if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.K && getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.R) {
            king = new King(field[move.getCurrent().getRow()][move.getCurrent().getCol()].getChessPiece().getColor(), ChessPieceCharacteristics.Name.K);
            if(king.hasCastlingHappened(this,field[ move.getCurrent().getRow()][ move.getCurrent().getCol()], field[move.getNewPos().getRow()][move.getNewPos().getCol()])){   //board, king, rook 
                //if true, undo castling
                System.out.println("AI: Undo Castling");
                king.undoCastling(this,field[ move.getCurrent().getRow()][ move.getCurrent().getCol()], field[move.getNewPos().getRow()][move.getNewPos().getCol()]);
            }
        }else if (getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.R && getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.K) {
            king = new King(field[move.getNewPos().getRow()][move.getNewPos().getCol()].getChessPiece().getColor(), ChessPieceCharacteristics.Name.K);
            if(king.hasCastlingHappened(this,  field[move.getNewPos().getRow()][move.getNewPos().getCol()], field[ move.getCurrent().getRow()][ move.getCurrent().getCol()])){ 
                //if true, undo castling
                System.out.println("AI: Undo Castling");
                king.undoCastling(this,  field[move.getNewPos().getRow()][move.getNewPos().getCol()], field[ move.getCurrent().getRow()][ move.getCurrent().getCol()]);
            }
        }
    }
  
}