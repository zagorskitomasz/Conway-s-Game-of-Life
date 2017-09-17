package game;

public class Cell {
	private int neighbors;
	private boolean alive;
	
	public Cell(){
		neighbors = 0;
		alive = false;
	}

	public int getNeighbors(){
		return neighbors;
	}

	public void addNeighbor(){
		neighbors++;
	}
	
	public void setAlive(boolean a){
		alive=a;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public String toString(){
		return isAlive() ? "1" : "0";
	}
}
