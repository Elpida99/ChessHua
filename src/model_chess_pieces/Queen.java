package model_chess_pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * it21735 , it21754, it21762
 * */

public class Queen extends ChessPiece{
    
    private final int value = 9;

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

    public Queen(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

}
