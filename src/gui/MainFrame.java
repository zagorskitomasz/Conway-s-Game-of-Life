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
	
	public MainFrame(){
		size=700;
		game = new Game(size, new int[] {3}, new int[] {2, 3}, 2);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		this.setLocation(screen.width/2-460, screen.height/2-360);
		
		setTitle("Conway's Game of Life");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		
		JPanel menuPanel = new MenuPanel(game);
		gamePanel = new GamePanel(game);
		
		MouseHandler mouse = new MouseHandler((GamePanel)gamePanel);
		gamePanel.addMouseListener(mouse);
		gamePanel.addMouseMotionListener(mouse);
		gamePanel.addMouseWheelListener(mouse);
		
		Border menuBorder = BorderFactory.createEtchedBorder();
		menuPanel.setBorder(menuBorder);
		gamePanel.setBorder(menuBorder);
		
		menuPanel.setBackground(new Color(170,200,170));
		gamePanel.setBackground(new Color(255,255,255));
		
		menuPanel.setPreferredSize(new Dimension(340,650));
		gamePanel.setPreferredSize(new Dimension(650,650));
		menuPanel.setMinimumSize(new Dimension(340,650));
		
		add(menuPanel, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(0, 100));
		add(gamePanel, new GBC(1,0,1,1).setFill(GBC.BOTH).setWeight(100, 100));
		pack();
	}

}
