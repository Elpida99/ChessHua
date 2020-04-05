package model_chess_pieces;

import java.util.ArrayList;
import java.util.List;


/**
 * it21735 , it21754, it21762
 */
public class Knight extends ChessPiece {
     
   private final int value = 3;
	

    private int[][] directions = {
            {1, 2},
            {-1, 2},
            {2, -1},
            {-2, -1},
            {-1, -2},
            {1, -2},
            {-2, 1},
            {2, 1}
    };

    public Knight(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }
}
