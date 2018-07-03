package git.janek79.javaeese.eese;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import git.janek79.javaeese.eese.service.UserService;

public class RegisterPanel extends JPanel {
	public RegisterPanel(JFrame frame, UserService userService) {
		frame.setSize(new Dimension(200, 300));
	}
}
