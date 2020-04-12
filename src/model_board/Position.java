/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_board;

/**
 *
 * @author Nicole Description: This is a basic Position class called helper.
 * Position, makes it easier to keep track of the position as opposed to passing
 * seperate rows and columns
 */
public class Position {

    private int row;
    private int col;
    private boolean color;

    //set the row and col for the Coordinate
    public Position(int col, int row) {
        this.col = col;
        this.row = row;
        //set the color for the position(used for the JTile in the GUI), if it's a white its false, if its a black square its true
        if (row % 2 == 0) {
            if (col % 2 == 0) {
                color = false;
            } else {
                color = true;
            }
        }
        if (row % 2 != 0) {
            if (col % 2 == 0) {
                color = true;
            } else {
                color = false;
            }
        }
    }

    //getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    //gets the color
    public boolean getColor() {
        return color;
    }

    //checks if the passed paramaters are valid or not
    public static boolean isValid(int col, int row) {
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }

    //checks to see if two positions are equal
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Position)) {
            return false;
        }

        Position other = (Position) obj;

        return getRow() == other.getRow() && getCol() == other.getCol();
    }
}
