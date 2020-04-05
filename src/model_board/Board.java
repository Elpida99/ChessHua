/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_board;

import model_chess_pieces.*;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Board {

    private Field field[][];

    public Board() {

        field = new Field[8][8];    //how many there on the board 

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                field[i][j] = new Field(j, i);
            }
        }

        placeBlackPieces();
        placeBlackPawns();
        placeWhitePieces();
        placeWhitePawns();
    }

    private void placeBlackPieces() {
        this.field[0][0].setChessPiece(new Rook(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.ROOK));
        this.field[0][1].setChessPiece(new Knight(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.KNIGHT));
        this.field[0][2].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.BISHOP));
        this.field[0][3].setChessPiece(new Queen(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.QUEEN));
        this.field[0][4].setChessPiece(new King(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.KING));
        this.field[0][5].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.BISHOP));
        this.field[0][6].setChessPiece(new Knight(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.KNIGHT));
        this.field[0][7].setChessPiece(new Rook(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.ROOK));

    }

    private void placeBlackPawns() {
        for (int i = 0; i < 8; ++i) {
            this.field[1][i].setChessPiece(new Pawn(ChessPieceCharacteristics.Color.white, ChessPieceCharacteristics.Name.PAWN));
        }
    }

    private void placeWhitePieces() {
        this.field[7][0].setChessPiece(new Rook(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.ROOK));
        this.field[7][1].setChessPiece(new Knight(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.KNIGHT));
        this.field[7][2].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.BISHOP));
        this.field[7][3].setChessPiece(new Queen(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.QUEEN));
        this.field[7][4].setChessPiece(new King(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.KING));
        this.field[7][5].setChessPiece(new Bishop(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.BISHOP));
        this.field[7][6].setChessPiece(new Knight(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.KNIGHT));
        this.field[7][7].setChessPiece(new Rook(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.ROOK));
    }

    private void placeWhitePawns() {
        for (int i = 0; i < 8; ++i) {
            this.field[6][i].setChessPiece(new Pawn(ChessPieceCharacteristics.Color.black, ChessPieceCharacteristics.Name.PAWN));
        }
    }

}
