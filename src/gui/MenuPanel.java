package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import game.Game;

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Game game;
	public JButton start, stop;

	public MenuPanel(Game g){
		game = g;
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		JLabel info1 = new JLabel("Conway's Game of Life");
		info1.setFont(new Font("Serif", Font.BOLD, 25));
		add(info1, new GBC(0,0,5,1).setWeight(100, 100));
		
		JLabel info2 = new JLabel("Click cells to turn them dead/alive");
		add(info2, new GBC(0,1,5,1).setWeight(100, 100));
		
		start = new JButton("Start");
		start.setPreferredSize(new Dimension(170,35));
		start.addActionListener(l -> {
			game.start(this);
			start.setEnabled(false);
			stop.setEnabled(true);
			stop.requestFocus();
		});
		add(start, new GBC(0,2,5,1).setWeight(100, 100));
		
		stop = new JButton("Stop");
		stop.setPreferredSize(new Dimension(170,35));
		stop.setEnabled(false);
		stop.addActionListener(l -> {
			game.stop(this);
			stop.setEnabled(false);
			start.setEnabled(true);
			start.requestFocus();
		});
		add(stop, new GBC(0,3,5,1).setWeight(100, 100));
		
		JButton clearBut = new JButton("Clear");
		clearBut.setPreferredSize(new Dimension(100,30));
		clearBut.addActionListener(l -> game.clear());
		add(clearBut, new GBC(0,4,2,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		JButton resBut = new JButton("Resize");
		resBut.setPreferredSize(new Dimension(100,30));
		resBut.addActionListener(l -> game.resize());
		add(resBut, new GBC(3,4,2,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		JLabel revLabel = new JLabel("Set revive rule (numbers and spaces only):");
		add(revLabel, new GBC(0,6,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JTextField revField = new JTextField("3");
		revField.setMinimumSize(new Dimension(100,25));
		revField.setPreferredSize(new Dimension(100,25));
		revField.setHorizontalAlignment(SwingConstants.RIGHT);
		add(revField, new GBC(0,7,2,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		JButton revApp = new JButton("Apply");
		revApp.setPreferredSize(new Dimension(100,30));
		revApp.addActionListener(l -> {
			try{
				String revStr = revField.getText();
				String[] numbersStr = revStr.split(" ");
				int[] arr = new int[numbersStr.length];
				
				for(int i=0; i<arr.length; i++){
					int value = Integer.parseInt(numbersStr[i]);
					if(value>=0 && value<9)
						arr[i]=value;
					else
						throw new Exception();
				}
				game.setRevive(arr);
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid number format", "Error", 0);
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Value out of bounds", "Error", 0);
			}
		});
		add(revApp, new GBC(3,7,2,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		JLabel surLabel = new JLabel("Set survive rule (numbers and spaces only):");
		add(surLabel, new GBC(0,8,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JTextField surField = new JTextField("2 3");
		surField.setMinimumSize(new Dimension(100,25));
		surField.setPreferredSize(new Dimension(100,25));
		surField.setHorizontalAlignment(SwingConstants.RIGHT);
		add(surField, new GBC(0,9,2,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		JButton surApp = new JButton("Apply");
		surApp.setPreferredSize(new Dimension(100,30));
		surApp.addActionListener(l -> {
			try{
				String surStr = surField.getText();
				String[] numbersStr = surStr.split(" ");
				int[] arr = new int[numbersStr.length];
				
				for(int i=0; i<arr.length; i++){
					int value = Integer.parseInt(numbersStr[i]);
					if(value>=0 && value<9)
						arr[i]=value;
					else
						throw new Exception();
				}
				game.setSurvive(arr);
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid number format", "Error", 0);
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Value out of bounds", "Error", 0);
			}
		});
		add(surApp, new GBC(3,9,2,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		JLabel arrLabel = new JLabel("Use arrows to move your field of view:");
		add(arrLabel, new GBC(0,10,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		ArrowButton upBut = new ArrowButton(BasicArrowButton.NORTH, 30, 30);
		upBut.addActionListener(l -> game.moveUp());
		add(upBut, new GBC(2,11,1,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		ArrowButton leftBut = new ArrowButton(BasicArrowButton.WEST, 30, 30);
		leftBut.addActionListener(l -> game.moveLeft());
		add(leftBut, new GBC(1,12,1,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		ArrowButton rightBut = new ArrowButton(BasicArrowButton.EAST, 30, 30);
		rightBut.addActionListener(l -> game.moveRight());
		add(rightBut, new GBC(3,12,1,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		ArrowButton downBut = new ArrowButton(BasicArrowButton.SOUTH, 30, 30);
		downBut.addActionListener(l -> game.moveDown());
		add(downBut, new GBC(2,13,1,1).setWeight(100, 100).setAnchor(GBC.NORTH));
		
		JLabel speeLabel = new JLabel("Set game speed (iterations on second):");
		add(speeLabel, new GBC(0,14,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JTextField speeField = new JTextField("2");
		speeField.setMinimumSize(new Dimension(100,25));
		speeField.setPreferredSize(new Dimension(100,25));
		speeField.setHorizontalAlignment(SwingConstants.RIGHT);
		add(speeField, new GBC(0,15,2,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		JButton speApp = new JButton("Apply");
		speApp.setPreferredSize(new Dimension(100,30));
		speApp.addActionListener(l -> {
			try{
				game.setSpeed(Integer.parseInt(speeField.getText()), this);
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid number format", "Error", 0);
			}
			});
		add(speApp, new GBC(3,15,2,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		JLabel filesLabel = new JLabel("Load/save game field:");
		add(filesLabel, new GBC(0,16,5,1).setWeight(100, 100).setAnchor(GBC.SOUTH));
		
		JButton loadBut = new JButton("Load field");
		loadBut.setPreferredSize(new Dimension(100,30));
		loadBut.setEnabled(false);
		add(loadBut, new GBC(0,17,2,1).setWeight(100, 100).setAnchor(GBC.EAST));
		
		JButton saveBut = new JButton("Save field");
		saveBut.setPreferredSize(new Dimension(100,30));
		saveBut.setEnabled(false);
		add(saveBut, new GBC(3,17,2,1).setWeight(100, 100).setAnchor(GBC.WEST));
		
		JButton closeBut = new JButton("Close application");
		closeBut.setPreferredSize(new Dimension(150,30));
		closeBut.addActionListener(l -> System.exit(0));
		add(closeBut, new GBC(0,18,5,1).setWeight(100, 100));
		
		JLabel nameLabel = new JLabel("Author: Tomasz Zagórski");
		add(nameLabel, new GBC(0,19,5,1).setWeight(100, 100));
		
		JLabel mailLabel = new JLabel("zagorskitomasz@gmail.com");
		add(mailLabel, new GBC(0,20,5,1).setWeight(100, 100));
	}
}
