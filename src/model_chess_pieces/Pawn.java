package model_chess_pieces;

import java.util.ArrayList;
import java.util.List;


/**
 * it21735 , it21754, it21762
 */
public class Pawn extends ChessPiece {
	
	private final int value = 1;

    public Pawn(ChessPieceCharacteristics.Color color, ChessPieceCharacteristics.Name name) {
        super(color, name);
    }

    @Override
    public int getValue() {
        return value;
    }

}
