/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_chess_pieces;

import java.util.List;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class King extends ChessPiece {
    
    private final int value = 10;
     
    private int[][] directions = {

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
    
 
    
    

    
    
}
