/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Bishop extends ChessPiece{
    
    private final int value = 3;
    
     private int[][] directions = {   //in which direction is it possible for them move 
            {-1, -1},
            {1, 1},
            {1, -1},
            {-1, 1}
    };

    public Bishop(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }
    
}
