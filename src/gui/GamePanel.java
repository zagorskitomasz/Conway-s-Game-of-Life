package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.Game;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public Game game;
	
	public GamePanel(Game g){
		game = g;
		game.setPanel(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(int i=100; i<game.getWidth()-100; i++){
			for(int j=100; j<game.getHeight()-100; j++){
				if(game.getCell(i, j).isAlive()){
					g.setColor(new Color(0,80,0));
					g.fillRect((i-100)*getWidth()/(game.getWidth()-200), (j-100)*getHeight()/(game.getHeight()-200), getWidth()/(game.getWidth()-200), getHeight()/(game.getHeight()-200));
				}
			}
		}
	}
}
