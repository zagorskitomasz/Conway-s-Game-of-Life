package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import game.Game;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	Game game;
	int size;
	JPanel gamePanel;
	
	public MainFrame(int s){
		size=s+200;
		game = new Game(size, new int[] {3}, new int[] {2, 3}, 2);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		this.setLocation(screen.width/2-460, screen.height/2-360);
		
		setTitle("Conway's Game of Life");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		//setLocationByPlatform(true);
		//setExtendedState(Frame.NORMAL);
		
		JPanel menuPanel = new MenuPanel(game);
		gamePanel = new GamePanel(game);
		gamePanel.addMouseListener(new MouseHandler((GamePanel)gamePanel));
		
		Border menuBorder = BorderFactory.createEtchedBorder();
		menuPanel.setBorder(menuBorder);
		gamePanel.setBorder(menuBorder);
		
		menuPanel.setBackground(new Color(170,200,170));
		gamePanel.setBackground(new Color(255,255,255));
		
		menuPanel.setPreferredSize(new Dimension(270,650));
		gamePanel.setPreferredSize(new Dimension(650,650));
		menuPanel.setMinimumSize(new Dimension(270,650));
		
		add(menuPanel, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0, 100));
		add(gamePanel, new GBC(1,0,1,1).setFill(GBC.BOTH).setWeight(100, 100));
		pack();
	}

}
