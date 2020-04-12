package model_board;
/**
 * 
 * @author it21735 , it21754, it21762
 */
public class FieldCoordinates {
	public int row;
	public int col;
	
	public FieldCoordinates(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + col;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldCoordinates other = (FieldCoordinates) obj;
		if (row != other.row)
			return false;
		if (col != other.col)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldCoordinates [row=" + row + ", column=" + col + "]";
	}	
	
	
}