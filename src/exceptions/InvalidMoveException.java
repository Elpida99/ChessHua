/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author it21735 , it21754, it21762
 */

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception {
	public InvalidMoveException(){
		super("This move is forbidden");
	}

}
