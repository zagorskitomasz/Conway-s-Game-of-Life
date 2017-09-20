package gui;

import java.awt.Dimension;

import javax.swing.plaf.basic.BasicArrowButton;

public class ArrowButton extends BasicArrowButton{

	private static final long serialVersionUID = 1L;
	int prefWidth, prefHeight;
	
	public ArrowButton(int direction, int prefW, int prefH) {
		super(direction);
		prefWidth=prefW;
		prefHeight=prefH;
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(prefWidth, prefHeight);
    }
}
