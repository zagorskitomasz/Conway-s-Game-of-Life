package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;

import game.Game;

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Game game;
	public JButton start, stop;

	public MenuPanel(Game g){
		game = g;
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		JPanel helloPanel = new JPanel();
		helloPanel.setBackground(new Color(170,200,160));
		helloPanel.setBorder(BorderFactory.createEtchedBorder());
		helloPanel.setLayout(new GridBagLayout());
		helloPanel.setPreferredSize(new Dimension(272,170));
		
		JLabel info1 = new JLabel("Conway's Game of Life");
		info1.setFont(new Font("Serif", Font.BOLD, 20));
		helloPanel.add(info1, new GBC(0,0,5,1).setWeight(100, 100));
		
		JLabel info2 = new JLabel("Left mouse click - revives/kills a cell");
		helloPanel.add(info2, new GBC(0,1,5,1).setWeight(100, 100));
		JLabel info3 = new JLabel("Left mouse drag - draws alive cells");
		helloPanel.add(info3, new GBC(0,2,5,1).setWeight(100, 100));
		JLabel info4 = new JLabel("Right mouse drag - moves camera");
		helloPanel.add(info4, new GBC(0,3,5,1).setWeight(100, 100));
		JLabel info5 = new JLabel("Mouse scroll - camera zoom in/out");
		helloPanel.add(info5, new GBC(0,4,5,1).setWeight(100, 100));
		
		add(helloPanel, new GBC(0,0,5,1).setWeight(0, 100));
		
		start = new JButton("Start");
		start.setPreferredSize(new Dimension(200,32));
		start.addActionListener(l -> {
			game.start(this);
			start.setEnabled(false);
			stop.setEnabled(true);
			stop.requestFocus();
		});
		add(start, new GBC(0,2,5,1).setWeight(100, 100));
		
		stop = new JButton("Stop");
		stop.setPreferredSize(new Dimension(200,32));
		stop.setEnabled(false);
		stop.addActionListener(l -> {
			game.stop(this);
			stop.setEnabled(false);
			start.setEnabled(true);
			start.requestFocus();
		});
		add(stop, new GBC(0,3,5,1).setWeight(100, 100));
		
		JButton clearBut = new JButton("Clear");
		clearBut.setPreferredSize(new Dimension(200,32));
		clearBut.addActionListener(l -> game.clear());
		add(clearBut, new GBC(0,4,5,1).setWeight(100, 100));

		JPanel revPanel = new JPanel();
		revPanel.setBackground(new Color(170,200,170));
		revPanel.setBorder(BorderFactory.createEtchedBorder());
		revPanel.setLayout(new GridBagLayout());
		revPanel.setPreferredSize(new Dimension(120,150));
		
		JLabel revLabel = new JLabel("Revive rule:");
		revPanel.add(revLabel, new GBC(0,6,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JCheckBox[] rev = new JCheckBox[9];
		
		ActionListener revListener = l -> {
			synchronized(this){
				int numbers=0;
				for(int i=0; i<rev.length; i++)
					if(rev[i].isSelected())
						numbers++;
				int[] arr = new int[numbers];
				for(int i=0; i<rev.length; i++){
					if(rev[i].isSelected()){
						arr[arr.length-numbers]=i;
						numbers--;
					}
				}
				game.setRevive(arr);
			}
		};
		
		for(int i=0; i<rev.length; i++){
			rev[i] = new JCheckBox(String.valueOf(i));
			if(i==3)
				rev[i].setSelected(true);
			rev[i].addActionListener(revListener);
			rev[i].setBackground(new Color(170,200,170));
			revPanel.add(rev[i], new GBC(1+i%3, 7+i/3).setWeight(100, 100));
		}
		
		add(revPanel, new GBC(0,9,2,1).setWeight(100, 100));
		
		JPanel surPanel = new JPanel();
		surPanel.setBackground(new Color(170,200,170));
		surPanel.setBorder(BorderFactory.createEtchedBorder());
		surPanel.setLayout(new GridBagLayout());
		surPanel.setPreferredSize(new Dimension(120,150));
		
		JLabel surLabel = new JLabel("Survive rule:");
		surPanel.add(surLabel, new GBC(0,10,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JCheckBox[] sur = new JCheckBox[9];
		
		ActionListener surListener = l -> {
			synchronized(this){
				int numbers=0;
				for(int i=0; i<sur.length; i++)
					if(sur[i].isSelected())
						numbers++;
				int[] arr = new int[numbers];
				for(int i=0; i<sur.length; i++){
					if(sur[i].isSelected()){
						arr[arr.length-numbers]=i;
						numbers--;
					}
				}
				game.setSurvive(arr);
			}
		};
		
		for(int i=0; i<sur.length; i++){
			sur[i] = new JCheckBox(String.valueOf(i));
			if(i==2 || i==3)
				sur[i].setSelected(true);
			sur[i].addActionListener(surListener);
			sur[i].setBackground(new Color(170,200,170));
			surPanel.add(sur[i], new GBC(1+i%3, 11+i/3).setWeight(100, 100));
		}
		
		add(surPanel, new GBC(3,9,2,1).setWeight(100, 100));
		
		JPanel speedPanel = new JPanel();
		speedPanel.setBackground(new Color(170,200,170));
		speedPanel.setBorder(BorderFactory.createEtchedBorder());
		speedPanel.setLayout(new GridBagLayout());
		speedPanel.setPreferredSize(new Dimension(232,50));
		
		JLabel speeLabel = new JLabel("Game speed:");
		speedPanel.add(speeLabel, new GBC(0,15,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JSlider speedSlid = new JSlider(1, 30, 2);
		speedSlid.addChangeListener(l -> game.setSpeed(speedSlid.getValue(), this));
		speedSlid.setBackground(new Color(170,200,170));
		speedPanel.add(speedSlid, new GBC(0,16,5,1).setWeight(0, 100));
		
		add(speedPanel, new GBC(0,10,5,1).setWeight(0, 100));
		
		String separator;
		if(System.getProperty("os.name").toLowerCase(Locale.ENGLISH).indexOf("win")>=0)
			separator = "\\";
		else
			separator = "/";
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("maps"+separator));
		chooser.setFileFilter(new FileNameExtensionFilter("Game of Life fields (*.gol)", "gol"));
		chooser.setAcceptAllFileFilterUsed(false);
		
		JButton loadBut = new JButton("Load field");
		loadBut.setPreferredSize(new Dimension(120,30));
		loadBut.addActionListener(l -> {
			chooser.showOpenDialog(this);
			File openFile = chooser.getSelectedFile();
			if(openFile!=null){
				try{
					game.openField(new Scanner(openFile));
				}
				catch(FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "File broken.", "Error", 0);
				}
			}
		});
		add(loadBut, new GBC(0,18,2,1).setWeight(100, 100));
		
		JButton saveBut = new JButton("Save field");
		saveBut.setPreferredSize(new Dimension(120,30));
		saveBut.addActionListener(l -> {
			chooser.showSaveDialog(this);
			File saveFile = chooser.getSelectedFile();
			if(saveFile!=null){
				try {
					PrintWriter saver = new PrintWriter(saveFile.getPath()+".gol");
					saver.print(game.saveField());
					saver.close();
				} 
				catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "File broken.", "Error", 0);
				}
			}
		});
		add(saveBut, new GBC(3,18,2,1).setWeight(100, 100));
		
		JButton closeBut = new JButton("Close application");
		closeBut.setPreferredSize(new Dimension(200,30));
		closeBut.addActionListener(l -> System.exit(0));
		add(closeBut, new GBC(0,19,5,1).setWeight(100, 100));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(170,200,170));
		infoPanel.setBorder(BorderFactory.createEtchedBorder());
		infoPanel.setLayout(new GridBagLayout());
		infoPanel.setPreferredSize(new Dimension(232,60));
		
		JLabel nameLabel = new JLabel("Author: Tomasz Zagorski");
		infoPanel.add(nameLabel, new GBC(0,20,5,1).setWeight(100, 100));
		
		JLabel mailLabel = new JLabel("zagorskitomasz@gmail.com");
		infoPanel.add(mailLabel, new GBC(0,21,5,1).setWeight(100, 100));
		
		add(infoPanel, new GBC(0,20,5,1).setWeight(0, 100));
	}
}
