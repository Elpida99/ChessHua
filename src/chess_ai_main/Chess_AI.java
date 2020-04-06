/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai_main;

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
    	System.out.println("Pieces: K= king, Q= queen, N= knight, B= bishop, R= rook, P= pawn");
    }
    
}

    
}
