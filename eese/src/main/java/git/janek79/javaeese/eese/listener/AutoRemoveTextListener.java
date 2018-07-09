package git.janek79.javaeese.eese.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * This mouse listener provides auto-removing default text in text field
 * 
 * @author Jan Jankowicz
 *
 */
public class AutoRemoveTextListener implements MouseListener {
	private String text;
	private JTextField textField;

	public AutoRemoveTextListener(String text, JTextField textField) {
		this.text = text;
		this.textField = textField;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (textField.getText().equals(text)) {
			textField.setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
