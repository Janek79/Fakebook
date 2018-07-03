package git.janek79.javaeese.eese.panel;

import java.awt.Point;

import javax.swing.JComponent;

public class CenterClass {
	public static int getCenterX(JComponent container, JComponent component) {
		return (container.getWidth()-component.getWidth())/2;
	}
	
	public static int getCenterY(JComponent container, JComponent component) {
		return (container.getHeight()-component.getHeight())/2;
	}
	
	public static Point getCenterPoint(JComponent container, JComponent component) {
		return new Point(getCenterX(container, component), getCenterY(container, component));
	}
}
