package pieces;


import java.util.List;

public abstract class Piece {

   // private PieceType pieceType; //the type of piece
    //private ColorType pieceColorType; //is the piece black or white
    private int pieceCost; //the value or cost of the piece (used for the AI)
    //private ChessBoard board;


    public int getPieceCost() {
        return pieceCost;
    }

    public void setPieceCost(int pieceCost) {
        this.pieceCost = pieceCost;
    }


//    public PieceType getPieceType() {
//        return pieceType;
//    }
//
//    public void setPieceType(PieceType pieceType) {
//        this.pieceType = pieceType;
//    }
//
//    public ColorType getPieceColorType() {
//        return pieceColorType;
//    }
//
//    public void setPieceColorType(ColorType pieceColorType) {
//        this.pieceColorType = pieceColorType;
//    }

    public void madeFirstMove(){
    }
    //used for the king and rook for castling and the pawn for first double jump move
    public boolean firstMove(){
        return false;
    }

//    public ChessBoard getBoard() {
//        return board;
//    }
//
//    public void setBoard(ChessBoard board){
//        this.board = board;
//    }
//
//    public abstract List<Position> getMoves(Position current);

    public void setFirstMove(boolean firstMove){
    }
}
