/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author it21735 , it21754, it21762
 */

public enum MoveResult {

    OK,
    REN,         //announcement that the queen is being threatened
    CHECK,      //ALLIWS: ROYA, SAX  : announcement that the king is being threatened
    MATE,       //if the king is being threatened and there is no way to remove the threat 
    CHECK_MATE ,   
    //ALLIWS: ROYA-MAT
    //In checkmate, the King is in check and it cannot move in any square on chess board.
    //Either when a King has been captured, or it is understood that the King has no alternative but to be captured. 
    //This can occur if he cannot escape his “checked” status, as mentioned above. 
    //When the King has been surrounded by its opponents and has nowhere to turn without being captured, a checkmate has occurred.

    STALEMATE  //????
    /*A stalemate occurs when there’s no “legal” move for the King. 
    The King has been surrounded by squares that are each in the line of attack, but the King is not actually under attack. The King can not legally be moved where it would put him under attack.
    Therefore, the game ends in a draw, or a tie, called stalemate. **/

}