package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter{
	
	GamePanel panel;
	
	public MouseHandler(GamePanel p){
		super();
		panel = p;
	}
	
	public void mouseClicked(MouseEvent click){
		panel.game.switchAlive(100+click.getX()*(panel.game.getWidth()-200)/panel.getWidth(),100+click.getY()*(panel.game.getHeight()-200)/panel.getHeight());
		panel.repaint();
	}
}
