package game;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.MenuPanel;

public class Game {
	private Cell[][] board;
	private int width, height, margin;
	private int[] revive, survive;
	private Lock gameLock;
	private int speed;
	private Timer timer;
	private JPanel panel;
	
	public Game(int size, int[] r, int[] s, float sp){
		width=size;
		height=size;
		margin=310;
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
	
	public void zoomIn(){
		gameLock.lock();
		
		try{
			setMargin((int)Math.round(Math.min(350-(350-getMargin())/1.4, 345)));
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void zoomOut(){
		gameLock.lock();
		
		try{
			setMargin((int)Math.round(Math.max(350-(350-getMargin())*1.4, 100)));
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
	}
	
	public void openField(Scanner field){
		gameLock.lock();
		
		try{
			try{
				int w = field.nextInt();
				int h = field.nextInt();
				Cell[][] temp = new Cell[w][h];
				for(int i=0; i<w; i++)
					for(int j=0; j<h; j++)
						temp[i][j] = new Cell();
				
				while(field.hasNextInt())
					temp[field.nextInt()][field.nextInt()].setAlive(true);
				
				width = w;
				height = h;
				board = temp;
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "File broken.", "Error", 0);
				e.printStackTrace();
			}
		}
		finally{
			gameLock.unlock();
		}
		panel.repaint();
	}
	
	public String saveField(){
		gameLock.lock();
		StringBuilder result = new StringBuilder("");
		
		try{
			result.append(width+" "+height+" ");
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j].isAlive())
						result.append(i+" "+j+" ");
				}
			}
		}
		finally{
			gameLock.unlock();
		}
		return result.toString();
	}
	
	public void setPanel(JPanel p){
		panel = p;
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getMargin(){
		return margin;
	}
	
	public void setMargin(int m){
		margin=m;;
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
	
	public boolean moveUp(int dist){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=0; i<width; i++){
				for(int j=0; j<dist; j++){
					newBoard[i][j] = new Cell();
				}
			}
				
			
			for(int i=0; i<width; i++){
				for(int j=0; j<height-dist; j++){
					newBoard[i][j+dist] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
		if(dist>0) return true;
		else return false;
	}
	
	public boolean moveDown(int dist){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=0; i<width; i++){
				for(int j=dist; j<height; j++){
					newBoard[i][j] = new Cell();
				}
			}
				
			
			for(int i=0; i<width; i++){
				for(int j=0; j<height-dist; j++){
					newBoard[i][j] = board[i][j+dist];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
		if(dist>0) return true;
		else return false;
	}
	
	public boolean moveLeft(int dist){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=0; i<dist; i++){
				for(int j=0; j<height; j++){
					newBoard[i][j] = new Cell();
				}
			}
			
			for(int i=0; i<width-dist; i++){
				for(int j=0; j<height; j++){
					newBoard[i+dist][j] = board[i][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
		if(dist>0) return true;
		else return false;
	}
	
	public boolean moveRight(int dist){
		gameLock.lock();
		
		try{
			Cell[][] newBoard = new Cell[width][height];
			
			for(int i=dist; i<width; i++){
				for(int j=0; j<height; j++){
					newBoard[i][j] = new Cell();
				}
			}
			
			for(int i=0; i<width-dist; i++){
				for(int j=0; j<height; j++){
					newBoard[i][j] = board[i+dist][j];
				}
			}
			
			board = newBoard;
			panel.repaint();
		}
		finally{
			gameLock.unlock();
		}
		if(dist>0) return true;
		else return false;
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
