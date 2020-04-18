/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai_main;

import algorithm.*;
import model_board.*;
import model_chess_pieces.*;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Chess_AI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Board board = new Board();
		board.createBoard();
		board.printBoard();
		System.out.println("Players: w=white and b=black");
		System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn\n");
        
        //tests for pawn
        
        ChessPiece piece = board.getField()[6][7].getChessPiece();
		Pawn pawn = new Pawn(null, null);
		pawn = (Pawn) piece;
        FieldCoordinates newpos = new FieldCoordinates(3, 4);
        ChessMove move = new ChessMove(newpos,pawn);
        
        //test for bishop
       /* ChessMove bMove = new ChessMove();
        Bishop bishop = new Bishop(ChessPieceCharacteristics.Color.b,ChessPieceCharacteristics.Name.B);
        
        
        bishop.setPiecePosition(board.getField()[6][4].getFieldCoordintes());
        System.out.println(board.getField()[6][4].getFieldCoordintes());
        
        bMove.setP(bishop);
        FieldCoordinates newBPos = new FieldCoordinates(3,5);
        bMove.setNewPos(newBPos);
        System.out.println(bishop.isMovePossible(bMove, board)); */
        
        //test for rook 
        ChessPiece piece1 = board.getField()[7][0].getChessPiece();
        Rook rook = (Rook) piece1;
        rook.setPiecePosition(piece1.getPiecePosition());
        List<Field> moves_rook = new LinkedList<>();
        moves_rook = rook.allPossibleMoves(board);
        for (int i = 0; i < moves_rook.size(); i++) {
            System.out.println("possible fields: " + moves_rook.get(i).getFieldCoordintes() + "\n");
        }
        
        //test for king 
        //        ChessPiece piece = board.getField()[7][4].getChessPiece();
//        King king = (King) piece;
//        king.setPiecePosition(piece.getPiecePosition());
//        List<Field> moves = new LinkedList<>();
//        moves = king.allPossibleMoves(board);
//        for (int i = 0; i < moves.size(); i++) {
//            System.out.println("possible fields: " + moves.get(i).getFieldCoordintes() + "\n");
//        }
        
        //test for miniMax
        miniMax alg = new miniMax();
		alg.AIColor=ChessPieceCharacteristics.Color.w;

		System.out.println("Evaluation of board: "+alg.evaluateBoard(board));
    }
}
