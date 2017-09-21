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
					g.fillRect((i-game.getMargin())*getWidth()/(game.getWidth()-game.getMargin()*2), (j-game.getMargin())*getHeight()/(game.getHeight()-game.getMargin()*2), getWidth()/(game.getWidth()-game.getMargin()*2), getHeight()/(game.getHeight()-game.getMargin()*2));
				}
			}
		}
	}
}
