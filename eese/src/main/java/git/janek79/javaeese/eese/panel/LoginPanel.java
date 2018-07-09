package git.janek79.javaeese.eese.panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.listener.AutoRemoveTextListener;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Frame that provides login function
 * 
 * @author Jan Jankowicz
 *
 */

public class LoginPanel extends JFrame {
	private Font myFont = new Font("Arial", Font.BOLD, 20);

	public LoginPanel(UserService userService) {

		setSize(320, 240);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fakebook - Login");

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.setVisible(true);
		this.add(panel1);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel lbl1 = new JLabel("Welcome in Fakebook!");
		lbl1.setFont(myFont);
		lbl1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel1.add(lbl1);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		JTextField txt1 = new JTextField(1);
		txt1.setText("login");
		txt1.setMaximumSize(new Dimension(200, 20));

		panel1.add(txt1);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		JTextField txt2 = new JPasswordField(1);
		txt2.setText("password");
		txt2.setMaximumSize(new Dimension(200, 20));
		panel1.add(txt2);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		// "Log in" button
		JButton btn1 = new JButton("Log in");
		btn1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel1.add(btn1);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel lbl2 = new JLabel("or");
		lbl2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel1.add(lbl2);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		JButton btn2 = new JButton("Create new account");
		btn2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btn2.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn2.getText())) {

			}
		});
		panel1.add(btn2);

		panel1.add(Box.createRigidArea(new Dimension(0, 15)));

		btn1.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn1.getText())) {
				User user = userService.getUserbyLogin(txt1.getText(), txt2.getText());
				if (user != null) {
					System.out.println("You've been logged in successfully!");
					new MainPanel(user, userService);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Wrong login or password");
				}
			}
		});

		btn2.addActionListener((e) -> {
			new RegisterPanel(userService);
			dispose();
		});

		txt1.addMouseListener(new AutoRemoveTextListener("login", txt1));

		txt2.addMouseListener(new AutoRemoveTextListener("password", txt2));

		panel1.revalidate();
	}
}
