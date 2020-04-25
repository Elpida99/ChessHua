package model_chess_pieces;

import algorithm.ChessMove;
import algorithm.Player;
import algorithm.miniMax;
import java.util.LinkedList;
import java.util.List;
import model_board.Board;
import model_board.Field;
import model_board.FieldCoordinates;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class King extends ChessPiece {

    private boolean firstMove;
    private final int value = 900;
    private boolean answer = false; //answer: is the move possiple or not

    
    private final int[][] directions = {
        {-1, -1},
        {1, 1},
        {1, -1},
        {-1, 1},
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    public King(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }
         
     //logic used for castling
    @Override
    public boolean firstMove() {
        return firstMove;
    }

    @Override
    public void madeFirstMove() {
        firstMove = false;
    }

    @Override
    public void setFirstMove(boolean move) {
        firstMove = move;
    }
    
    @Override
    public List<Field> allPossibleMoves(Board board,Player player ) {  
		List<Field> moves = new LinkedList<>();
		int currow = this.getPiecePosition().getRow();
		int curcol = this.getPiecePosition().getCol();
		ChessMove move = new ChessMove(null, this);

		moves.add(board.getField()[currow][curcol]);

		for (int i = 0; i < this.directions.length; i++) {
			int[] direction = directions[i];
			int col = curcol + direction[0];
			int row = currow + direction[1];
		
			if (this.isMovePossible(move,  board)) {
				moves.add(new Field(row, col));
			}
		}
		return moves;
    }


    @Override
    public boolean isMovePossible(ChessMove move, Board board ){
        
        if (move == null || move.getNewPos() == null || move.getCurrent()==null) {
            return  answer = false;
        }
        String color = null;

        color = this.getColor().toString(); // colour of pawn
        //System.out.println("colour=" + color);
        move.setCurrent(this.getPiecePosition()); // piece's current position-->set it to class ChessMove

        int curRow = this.getPiecePosition().getRow(); // piece's current row , x !!
        int curCol = this.getPiecePosition().getCol(); // piece's current column, y !!
        //System.out.println("old coordinates: " + move.getCurrent().getRow() + "," + move.getCurrent().getCol());

        int row = move.getNewPos().getRow(); // coordinates of desired move are the directions of the user
        int col = move.getNewPos().getCol();
        //System.out.println("new position: " + row + "," + col); // check that they are right
        
        if (board.isFieldOccupied(curRow, curCol)&& ChessMove.isValid(row, col)) { //if piece exists on the field and the new position is valid (row&col <8)
            //System.out.println("piece exists on this field and it can be moved AND New position is valid, coordinates exist on board, (row&col <8)\n");

                if (board.isFieldOccupied(row, col) && !(board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is DIFFERENT from the one that the king has 
                    //System.out.println("field occupied-the colour of the pawn (pioni) needs to be checked ");
                    check_col_row(col, curCol, row, curRow);

                } else if (board.isFieldOccupied(row, col) && (board.getField()[row][col].getChessPiece().getColor().toString().equals(color))) {
                    //if NEW POSITION IS OCCUPIED/ NOT EMPTY , we need to check the colour of the pioni + and the colour is the SAME from the one that the king has 
                   // System.out.println("field occupied-the colour of the pawn (pioni) is the SAME with the colour of the king--king cannot be moved to this field ");
                    answer = false;
                } else if (!(board.isFieldOccupied(row, col))) { //FIELD EMPTY
                   // System.out.println("position available-FIELD NOT OCCUPIED/EMPTY  --- Let's check if the new move meets the \"criteria\"");
                    check_col_row(col, curCol, row, curRow);
                }   
        } else {
          // System.out.println("KING'S piece DOES NOT exists on this field -- move not possible");
          //  System.out.println("KING: This field " + curRow + " " + curCol + " does not contain a piece");
            answer = false;
        }
        return answer;
    }

    private void check_col_row(int col, int curCol, int row, int curRow) {
        //row = new x, col = new y 
        //curRow = old x, curCol = old y
        if //VERTICAL 
                (col == curCol && ((row - curRow) == 1 || (row - curRow == -1))) { //if column(Yposition) is the same-->the move is forward/ backword : newX - oldX = 1 or -1
            System.out.println("KING'S VERTICAL move is possible: row - curRow: " + Math.abs(row - curRow) + " col == curCol: " + col + "\n");
            setFirstMove(true);
            answer = true;
        }//HORIZONTAL 
        else if (row == curRow && ((col - curCol) == 1 || (col - curCol) == -1)) {   //if row(Xposition)is the same and newY - oldY = 1 or -1
            System.out.println("KING'S HORIZONTAL move is possible: row == curRow: " + row + "col - curCol: " + (col - curCol) + "col == curCol : " + col + "\n");
            setFirstMove(true);
            answer = true;
        }//DIAGONAL 
        else if ((((row - curRow) == 1 || (row - curRow) == -1) && ((col - curCol) == 1 || (col - curCol) == -1))) {    //newX - oldX = 1 or -1 AND newY - oldY = 1 or -1
            System.out.println("KING'S DIAGONAL  move is possible: row - curRow: " + (row - curRow) + "col - curCol: " + (col - curCol) + "\n");
            setFirstMove(true);

            answer = true;
        } else {
            System.out.println("KING'S piece exists on this field -- move not possible");
            setFirstMove(false);
            answer = false;
        }
    }
    public void undoCastling(Board board, Field startTile, Field endTile){
        //undo castling, change positions between rook and king !

        //position before the castling 
        int king_curRow = 0;
        int king_curCol = 0; 
        int rook_curRow = 0;
        int rook_curCol = 0;
        
        //positions after the castling has happened 
        int new_king_Row = startTile.getFieldCoordintes().getRow();
        int new_king_Col = startTile.getFieldCoordintes().getCol();           
        int new_rook_Row =  endTile.getFieldCoordintes().getRow();
        int new_rook_Col = endTile.getFieldCoordintes().getCol();

        //BLACK  colour
        if( new_king_Row == 0 && new_king_Col == 6 && new_rook_Row == 0 && new_rook_Col == 5 ) {
            king_curRow = 0;
            king_curCol = 4; 
            rook_curRow = 0; 
            rook_curCol = 7;
            //undo castling moves, move king, rook back to their initial positions
            board.movePiecesForCastling(new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col), new FieldCoordinates(rook_curRow, rook_curCol) );      
        } else if (new_king_Row == 0 && new_king_Col == 3 && new_rook_Row == 0 && new_rook_Col == 2 ) {
            king_curRow = 0;
            king_curCol = 4;
            rook_curRow = 0; 
            rook_curCol = 0;
            //undo castling moves, move king, rook back to their initial positions
            board.movePiecesForCastling(new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col), new FieldCoordinates(rook_curRow, rook_curCol) );      
        }  
        //WHITE colour
        else if (new_king_Row == 7 && new_king_Col == 6 && new_rook_Row == 7 && new_rook_Col == 5 ){
            king_curRow = 7;
            king_curCol = 4; 
            rook_curRow = 7; 
            rook_curCol = 7;
            //undo castling moves, move king, rook back to their initial positions
            board.movePiecesForCastling(new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col), new FieldCoordinates(rook_curRow, rook_curCol) );      
        } else if (new_king_Row == 7 && new_king_Col == 3 && new_rook_Row == 7 && new_rook_Col == 2 ) {  
            king_curRow = 7;
            king_curCol = 4; 
            rook_curRow = 7; 
            rook_curCol = 0;
            //undo castling moves, move king, rook back to their initial positions
            board.movePiecesForCastling(new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col), new FieldCoordinates(rook_curRow, rook_curCol) );      
        } 
    }
    
    public boolean hasCastlingHappened(Board board, Field startTile, Field endTile){
        //check if castling has happened 
        boolean castlingHappened = false;
        
        int new_king_Row = startTile.getFieldCoordintes().getRow();
        int new_king_Col = startTile.getFieldCoordintes().getCol();           
        int new_rook_Row =  endTile.getFieldCoordintes().getRow();
        int new_rook_Col = endTile.getFieldCoordintes().getCol();

        if( startTile.getChessPiece().firstMove() == false  && endTile.getChessPiece().firstMove() == false ){  

            //BLACK  colour
            if(new_king_Row == 0 && new_king_Col == 6 && new_rook_Row == 0 && new_rook_Col == 5  && (!board.isFieldOccupied(0, 4)) && (!board.isFieldOccupied(0, 7))) {
                castlingHappened = true; 
            } else if (new_king_Row == 0 && new_king_Col == 3 && new_rook_Row == 0 && new_rook_Col == 2 && (!board.isFieldOccupied(0, 4)) && (!board.isFieldOccupied(0, 0))) {
                castlingHappened = true; 
            }  
            //WHITE colour
            else if (new_king_Row == 7 && new_king_Col == 6 && new_rook_Row == 7 && new_rook_Col == 5 && (!board.isFieldOccupied(7, 4)) && (!board.isFieldOccupied(7, 7))) {  
                castlingHappened = true; 
            } else if (new_king_Row == 7 && new_king_Col == 3 && new_rook_Row == 7 && new_rook_Col == 2 && (!board.isFieldOccupied(7, 4)) && (!board.isFieldOccupied(7, 0))) {  
                castlingHappened = true; 
            }
        }
        return castlingHappened;
    }
    
    public void doCastling(Board board, Field startTile, Field endTile){
        //castling, change positions between rook and king !
        int king_curRow = startTile.getFieldCoordintes().getRow();
        int king_curCol = startTile.getFieldCoordintes().getCol();
                
        int rook_curRow = endTile.getFieldCoordintes().getRow();
        int rook_curCol =  endTile.getFieldCoordintes().getCol();
        
        int new_king_Row = 0;
        int new_king_Col = 0;           
        int new_rook_Row = 0;
        int new_rook_Col = 0;

        //BLACK  colour
        if (king_curRow == 0 && king_curCol == 4 && rook_curRow == 0 && rook_curCol == 7 && (!board.isFieldOccupied(0, 5)) && (!board.isFieldOccupied(0, 6))) {
            new_king_Row = 0;
            new_king_Col = 6;           
            new_rook_Row = 0;
            new_rook_Col = 5;
            board.movePiecesForCastling(new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(rook_curRow, rook_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col) );      
        } else if (king_curRow == 0 && king_curCol == 4 && (rook_curRow == 0 && rook_curCol == 0) && (!board.isFieldOccupied(0, 1)) && (!board.isFieldOccupied(0, 2)) && (!board.isFieldOccupied(0, 3))) {
            new_king_Row = 0;
            new_king_Col = 3;           
            new_rook_Row = 0;
            new_rook_Col = 2;
            board.movePiecesForCastling(new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(rook_curRow, rook_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col) );      
        }  
        //WHITE colour
        else if (king_curRow == 7 && king_curCol == 4 && rook_curRow == 7 && rook_curCol == 7 && (!board.isFieldOccupied(7, 5)) && (!board.isFieldOccupied(7, 6))) {
            new_king_Row = 7;
            new_king_Col = 6;           
            new_rook_Row = 7;
            new_rook_Col = 5;
            board.movePiecesForCastling(new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(rook_curRow, rook_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col) );      
        } else if (king_curRow == 7 && king_curCol == 4 && rook_curRow == 7 && rook_curCol == 0 && (!board.isFieldOccupied(7, 1)) && (!board.isFieldOccupied(7, 2)) && (!board.isFieldOccupied(7, 3))) {
            new_king_Row = 7;
            new_king_Col = 3;           
            new_rook_Row = 7;
            new_rook_Col = 2;
            board.movePiecesForCastling(new FieldCoordinates(king_curRow,king_curCol), new FieldCoordinates(new_king_Row, new_king_Col), new FieldCoordinates(rook_curRow, rook_curCol), new FieldCoordinates( new_rook_Row,new_rook_Col) );      
        } else {
            System.out.println("CASTLING ERROR!!");
        }
    }
    
    public boolean isCastlingPossible(Board board,Field startTile, Field endTile ){

        int king_curRow = startTile.getFieldCoordintes().getRow();
        int king_curCol = startTile.getFieldCoordintes().getCol();
        String king_color = startTile.getChessPiece().getColor().toString();
                
        int rook_curRow = endTile.getFieldCoordintes().getRow();
        int rook_curCol =  endTile.getFieldCoordintes().getCol();
        String rook_color = endTile.getChessPiece().getColor().toString();
        boolean possible = false;
        
        //*both rook, king must have not been moved once, this must be their first move   
        //*the in-between fields empty  
        //*in their initial position based on their colour  
        if( startTile.getChessPiece().firstMove() && endTile.getChessPiece().firstMove()){  
            if ("b".equals(rook_color) && "b".equals(king_color)) { //black  colour
                //*if they are in their initial position ++ the in-between fields empty
                if (king_curRow == 0 && king_curCol == 4 && rook_curRow == 0 && rook_curCol == 7 && (!board.isFieldOccupied(0, 5)) && (!board.isFieldOccupied(0, 6))) {                    //*if they are in their initial position  ++ the in-between fields empty : [0][5]  [0][6]
                    System.out.println("same color=b, king_curRow == 0 && king_curCol == 4 && rook_curRow == 0 && rook_curCol == 7 ");
                    possible = true;
                } else if (king_curRow == 0 && king_curCol == 4 && (rook_curRow == 0 && rook_curCol == 0) && (!board.isFieldOccupied(0, 1)) && (!board.isFieldOccupied(0, 2)) && (!board.isFieldOccupied(0, 3))) { // [0][1] [0][2]  [0][3]: must be empty 
                    System.out.println("same color=b, king( Row == 0 , Col == 4 ) && rook ( Row == 0 && Col == 0)");
                    possible = true;
                } else {
                    System.out.println("same color=b, criteria do not match");
                    possible = false;
                }
            } else if ("w".equals(rook_color) && "w".equals(king_color)) { //white colour
                //*if they are in their initial position ++ the in-between fields empty
                if(king_curRow == 7 && king_curCol == 4 &&  rook_curRow == 7 && rook_curCol == 7 && ( !board.isFieldOccupied(7,5) ) &&  ( !board.isFieldOccupied(7,6) ) ){   //[7][5]  [7][6]: must be empty 
                    System.out.println("same color=w, king_curRow == 7 && king_curCol == 4 &&  rook_curRow == 7 && rook_curCol == 7 && empty: [7][5]  [7][6]");
                    possible = true;
                }else if (king_curRow == 7 && king_curCol == 4 && rook_curRow == 7 && rook_curCol == 0 && ( !board.isFieldOccupied(7,1) ) && (! board.isFieldOccupied(7,2) ) && ( !board.isFieldOccupied(7,3) ) ){  //[7][1],  [7][2], [7][3]: must be empty 
                    System.out.println("same color=w, king_curRow == 7 && king_curCol == 4 && rook_curRow == 7 && rook_curCol == 0 + empty: [7][1],  [7][2], [7][3]");
                    possible = true;
                }else{
                    System.out.println("same color=w, criteria do not match");
                    possible = false;
                }                
            }else{
                System.out.println("the king and rook do not have the same color");
                possible = false;
            }
        }else{
            System.out.println("one or both of the pawns have been moved before");
            possible = false;
        } 
        return possible;
    }
       
    
    //See if a certain position is attackable by enemy piece. Used to find if a king's position of potential move is in check
    public boolean isKingInCheck(FieldCoordinates fieldcoordinates, boolean is_color_white, Board board, List<ChessMove> blackPieceList,List <ChessMove> whitePieceList ) {
        //column and row : the location of the king 
    
        if(fieldcoordinates == null){
            return false;
        }
        int row = fieldcoordinates.getRow();
        int column = fieldcoordinates.getCol();
        
        miniMax minimax = new miniMax();

        blackPieceList  = new LinkedList<>();
        whitePieceList =  new LinkedList<>();
        
        blackPieceList = miniMax.getAllMoves(board,ChessPieceCharacteristics.Color.b ); //Hold a list of black pieces. Could be handy.
        whitePieceList = miniMax.getAllMoves(board, ChessPieceCharacteristics.Color.w); //Hold a list of white pieces. Could be handy again.
        
        boolean isCheck = false;
        
        if(is_color_white) {   //if king has white color 
            for(ChessMove chessmove:blackPieceList){
                if(chessmove.getNewPos().getRow() == row && chessmove.getCurrent().getCol() == column ){
                    //if a chesspiece can attack and "eat" the king, then is checkmate
                        isCheck = true;
                         return isCheck;
                }
            }
            isCheck = false;
            return isCheck;
        }else{       //if king has black color 
            for(ChessMove chessmove:whitePieceList){
                if(chessmove.getNewPos().getRow() == row && chessmove.getCurrent().getCol() == column ){
                    //if a chesspiece can attack and "eat" the king, then is checkmate
                        isCheck = true;
                         return isCheck;
                }
            }
            isCheck = false;
            return isCheck;
        }
    }

    //Method to find checkmate condition
    //returns false if the king is not in check or if he is not being threatened by another chesspiece, otherwise it returns true 
    public boolean isCheckmate(ChessPiece king, boolean isWhite, Board board, Player player, List<ChessMove> blackPieceList,List <ChessMove> whitePieceList) {

        if (!isKingInCheck(king.getPiecePosition(), isWhite, board, blackPieceList, whitePieceList)) {  
       // isKingInCheck(FieldCoordinates fieldcoordinates, boolean is_color_white, Board board, List<ChessMove> blackPieceList,List <ChessMove> whitePieceList ) {
            return false;
        }

        for (Field f: king.allPossibleMoves(board, player)) {
            if (!this.isKingInCheck(f.getFieldCoordintes(), isWhite, board, blackPieceList, whitePieceList)) {
                return false;
            }
        }
        return true;
    }
    
    
    public ChessPiece get_Kings_position(Board result_board, miniMax m, boolean is_color_white  ) {//returns the position of the king, based on the board 
        ChessPiece piece = null;
        m = new miniMax();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (result_board.isFieldOccupied(i, j) && result_board.getField()[i][j].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && is_color_white == true && result_board.getField()[i][j].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w) {
                        piece = result_board.getField()[i][j].getChessPiece();
                        return piece;
                }else if (result_board.isFieldOccupied(i, j) && result_board.getField()[i][j].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && is_color_white == false && result_board.getField()[i][j].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b) {
                        piece = result_board.getField()[i][j].getChessPiece();
                        return piece;
                }
            }
        }
        return piece;
    }
}
