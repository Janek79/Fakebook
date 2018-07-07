package git.janek79.javaeese.eese.panel;

import javax.swing.JFrame;

public class SearchPanel {
	public SearchPanel(JFrame parentFrame) {
		parentFrame.setEnabled(false);
		
		JFrame frame = new JFrame("Find friends");
		frame.setSize(300, 400);
		frame.setVisible(true);
		
	}
}
