package game;

public class Game {
	private Cell[][] board;
	private int width, height;
	private int[] revive, survive;
	
	public Game(int w, int h, int[] r, int[] s){
		width=w;
		height=h;
		revive=r;
		survive=s;
		
		board = new Cell[width][height];
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				board[i][j] = new Cell();
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Cell getCell(int w, int h){
		return board[w][h];
	}
	
	public void switchAlive(int x, int y){
		board[x][y].setAlive(!board[x][y].isAlive());
	}
	
	public void move(){
		Cell[][] newBoard = new Cell[width][height];
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				newBoard[i][j] = new Cell();
			}
		}
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				if(board[i][j].isAlive()==true)
					newBoard[i][j].setAlive(true);
				
				if(i>0 && j>0 && board[i-1][j-1].isAlive())
					newBoard[i][j].addNeighbor();
				if(j>0 && board[i][j-1].isAlive())
					newBoard[i][j].addNeighbor();
				if(j>0 && i<width-1 && board[i+1][j-1].isAlive())
					newBoard[i][j].addNeighbor();
				if(i>0 && board[i-1][j].isAlive())
					newBoard[i][j].addNeighbor();
				if(i<width-1 && board[i+1][j].isAlive())
					newBoard[i][j].addNeighbor();
				if(i>0 && j<height-1 && board[i-1][j+1].isAlive())
					newBoard[i][j].addNeighbor();
				if(j<height-1 && board[i][j+1].isAlive())
					newBoard[i][j].addNeighbor();
				if(i<width-1 && j<height-1 && board[i+1][j+1].isAlive())
					newBoard[i][j].addNeighbor();
			}
		}
		
		board = newBoard;
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				if(board[i][j].isAlive()){
					board[i][j].setAlive(false);
					for(int k=0; k<survive.length; k++){
						if(survive[k]==board[i][j].getNeighbors()){
							board[i][j].setAlive(true);
						}
					}
				}
				else{
					for(int k=0; k<revive.length; k++){
						if(revive[k]==board[i][j].getNeighbors()){
							board[i][j].setAlive(true);
						}
					}
				}
			}
		}
	}
	
	public String toString(){
		String result = "";
		
		for(int j=0; j<height; j++){
			for(int i=0; i<width; i++){
				result = result+" "+board[i][j].toString();
			}
			result = result+"\n";
		}
		
		return result;
	}
	
	public static void main(String[] args){
		int[] revive = {3};
		int[] survive = {2,3};
		
		Game game = new Game(10, 10, revive, survive);
		
		System.out.println(game);
		
		game.switchAlive(6, 6);
		game.switchAlive(7, 6);
		game.switchAlive(8, 6);
		game.switchAlive(6, 7);
		game.switchAlive(7, 8);
		
		for(int i=0; i<20; i++){
			System.out.println(game);
			game.move();
		}
	}
}
