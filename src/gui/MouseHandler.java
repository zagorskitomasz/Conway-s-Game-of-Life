package gui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class MouseHandler extends MouseAdapter implements MouseWheelListener, MouseMotionListener{
	
	int x, y, xDrag=0, yDrag=0;
	GamePanel panel;
	
	public MouseHandler(GamePanel p){
		super();
		panel = p;
	}
	
	public void mousePressed(MouseEvent press){
		if(SwingUtilities.isRightMouseButton(press))
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		else if(SwingUtilities.isLeftMouseButton(press))
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		x = press.getX();
		y = press.getY();
	}
	
	public void mouseReleased(MouseEvent release){
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	public void mouseClicked(MouseEvent click){
		panel.game.switchAlive(panel.game.getMargin()+click.getX()*(panel.game.getWidth()-panel.game.getMargin()*2)/panel.getWidth(),panel.game.getMargin()+click.getY()*(panel.game.getHeight()-panel.game.getMargin()*2)/panel.getHeight());
		panel.repaint();
	}
	
	public void mouseWheelMoved(MouseWheelEvent scroll){
		if(scroll.getWheelRotation()<0)
			panel.game.zoomIn();
		else
			panel.game.zoomOut();
	}
	
	public void mouseDragged(MouseEvent drag){
		
		if(SwingUtilities.isRightMouseButton(drag)){
			
			if(drag.getX()<x){
				xDrag+=x-drag.getX();
				x-=x-drag.getX();
				if(xDrag>10){
					if(panel.game.moveRight(Math.round(xDrag*(panel.game.getWidth()-2*panel.game.getMargin())/panel.getWidth())))
						xDrag=0;
				}
			}
			
			else if(drag.getX()>x){
				xDrag+=x-drag.getX();
				x-=x-drag.getX();
				if(xDrag<-10){
					if(panel.game.moveLeft(Math.round(Math.abs(xDrag)*(panel.game.getWidth()-2*panel.game.getMargin())/panel.getWidth())))
						xDrag=0;
				}
			}
			
			if(drag.getY()<y){
				yDrag+=y-drag.getY();
				y-=y-drag.getY();
				if(yDrag>10){
					if(panel.game.moveDown(Math.round(yDrag*(panel.game.getHeight()-2*panel.game.getMargin())/panel.getHeight())))
						yDrag=0;
				}
			}
			
			else if(drag.getY()>y){
				yDrag+=y-drag.getY();
				y-=y-drag.getY();
				if(yDrag<-10){
					if(panel.game.moveUp(Math.round(Math.abs(yDrag)*(panel.game.getHeight()-2*panel.game.getMargin())/panel.getHeight())))
						yDrag=0;
				}
			}
		}
		else if(SwingUtilities.isLeftMouseButton(drag)){
			panel.game.getCell(panel.game.getMargin()+Math.round(drag.getX()*(panel.game.getWidth()-2*panel.game.getMargin())/panel.getWidth()), panel.game.getMargin()+Math.round(drag.getY()*(panel.game.getHeight()-2*panel.game.getMargin())/panel.getHeight())).setAlive(true);
			panel.repaint();
		}
	}
}
