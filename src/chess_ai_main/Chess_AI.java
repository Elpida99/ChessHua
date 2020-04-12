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
        board.printBoard();
        System.out.println("Players: w=white and b=black");
        System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn");

        //tests for pawn
        ChessMove move = new ChessMove();
        Pawn pawn = new Pawn(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.P);

        pawn.setPiecePosition(board.getField()[1][4].getFieldCoordintes());
        System.out.println(board.getField()[1][4].getFieldCoordintes());

        move.setP(pawn);
        FieldCoordinates newpos = new FieldCoordinates(3, 4);
        move.setNewPos(newpos);
        System.out.println(pawn.isMovePossible(move, board));
        
        //test for bishop
       /* ChessMove bMove = new ChessMove();
        Bishop bishop = new Bishop(ChessPieceCharacteristics.Color.b,ChessPieceCharacteristics.Name.B);
        
        
        bishop.setPiecePosition(board.getField()[6][4].getFieldCoordintes());
        System.out.println(board.getField()[6][4].getFieldCoordintes());
        
        bMove.setP(bishop);
        FieldCoordinates newBPos = new FieldCoordinates(3,5);
        bMove.setNewPos(newBPos);
        System.out.println(bishop.isMovePossible(bMove, board)); */
    }
}
