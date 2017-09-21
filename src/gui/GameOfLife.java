package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GameOfLife {
	public static void main(String[] args){
		
		EventQueue.invokeLater(() ->{
			MainFrame frame = new MainFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
