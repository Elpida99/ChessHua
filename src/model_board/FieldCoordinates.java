package model_board;
/**
 * 
 * @author it21735 , it21754, it21762
 */
public class FieldCoordinates {
	public int x;
	public int y;
	
	public FieldCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldCoordinates [x=" + x + ", y=" + y + "]";
	}	
	
	
}
