package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameOfLife {
	public static void main(String[] args){
		
		EventQueue.invokeLater(() ->{
			MainFrame frame = new MainFrame(setSize());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
	
	public static int setSize(){
		String sizeS;
		int size=0;
		boolean done = false;
		do{
			try{
				sizeS = JOptionPane.showInputDialog(null, "Enter game field size (10-500):", "Enter size", 3);
				if(sizeS==null)
					System.exit(0);
				size = Integer.parseInt(sizeS);
				if(size>=10 && size<=500)
					done=true;
				else
					JOptionPane.showMessageDialog(null, "Value out of bounds", "Error", 0);
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid number format", "Error", 0);
			}
		}
		while(!done);
		return size;
	}
}
