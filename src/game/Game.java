package game;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import javax.swing.Timer;

import gui.GameOfLife;
import gui.MenuPanel;

public class Game {
	private Cell[][] board;
	private int width, height;
	private int[] revive, survive;
	private Lock gameLock;
	private int speed;
	private Timer timer;
	private JPanel panel;
	
	public Game(int size, int[] r, int[] s, float sp){
		width=size;
		height=size;
		revive=r;
		survive=s;
		speed=(int)(1000/sp);
		
		gameLock = new ReentrantLock();
		
		board = new Cell[width][height];
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				board[i][j] = new Cell();
			}
		}
	}
	
	public void start(MenuPanel panel) {
		gameLock.lock();
		
		try{
			panel.start.setEnabled(false);
			panel.stop.setEnabled(true);
			timer = new Timer(speed, e -> iterate());
			timer.start();
		}
		finally{
			gameLock.unlock();
		}
	}

	public void stop(MenuPanel panel) {
		gameLock.lock();
		
		try{
			panel.start.setEnabled(true);
			panel.stop.setEnabled(false);
			if(timer!=null && timer.isRunning())
				timer.stop();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void setPanel(JPanel p){
		panel = p;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setRevive(int... numbers){
		gameLock.lock();
		
		try{
			revive = numbers;
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void setSpeed(int s, MenuPanel panel){
		gameLock.lock();
		
		try{
			if(s<=0)
				throw new NumberFormatException();
			if(panel.start.isEnabled())
				speed=(int)(1000/s);
			else{
				stop(panel);
				speed=(int)(1000/s);
				start(panel);
			}
		}
		finally{
			gameLock.unlock();
		}
	}

	public void setSurvive(int... numbers){
		gameLock.lock();
		
		try{
			survive = numbers;
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public Cell getCell(int w, int h){
		return board[w][h];
	}
	
	public void switchAlive(int x, int y){
		gameLock.lock();
		try{
			board[x][y].setAlive(!board[x][y].isAlive());
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void clear(){
		gameLock.lock();
		try{
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[0].length; j++)
					board[i][j].setAlive(false);
			}
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void resize(){
		gameLock.lock();
		try{
			clear();
			int size = GameOfLife.setSize()+200;
			width = size;
			height = size;
			board = new Cell[size][size];
			
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[0].length; j++)
					board[i][j] = new Cell();
			}
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void iterate(){
		gameLock.lock();
		
		try{
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
			if(panel!=null){
				panel.repaint();
			}
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void moveUp(){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=0; i<width; i++)
				newBoard[i][0] = new Cell();
			
			for(int i=0; i<width; i++){
				for(int j=0; j<height-1; j++){
					newBoard[i][j+1] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void moveDown(){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=0; i<width; i++)
				newBoard[i][height-1] = new Cell();
			
			for(int i=0; i<width; i++){
				for(int j=1; j<height; j++){
					newBoard[i][j-1] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void moveLeft(){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int j=0; j<height; j++)
				newBoard[0][j] = new Cell();
			
			for(int i=0; i<width-1; i++){
				for(int j=0; j<height; j++){
					newBoard[i+1][j] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void moveRight(){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int j=0; j<height; j++)
				newBoard[width-1][j] = new Cell();
			
			for(int i=1; i<width; i++){
				for(int j=0; j<height; j++){
					newBoard[i-1][j] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
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
}
