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
import javax.swing.JTextField;

import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

/**
 * This frame lets user to create new account 
 * 
 * @author Jan Jankowicz
 *
 */

public class RegisterPanel extends JFrame {
	private Font myFont = new Font("Arial", Font.BOLD, 20);
	private Font myFontMinor = new Font("Arial", Font.BOLD, 12);

	public RegisterPanel(UserService userService) {
		setSize(new Dimension(320, 310));
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setVisible(true);
		add(panel);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel label = new JLabel("Create new account");
		label.setFont(myFont);
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(label);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel label1 = new JLabel("First name");
		label1.setFont(myFontMinor);
		panel.add(label1);

		JTextField txt1 = new JTextField(1);
		txt1.setMaximumSize(new Dimension(200, 20));
		panel.add(txt1);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel label2 = new JLabel("Last name");
		label2.setFont(myFontMinor);
		panel.add(label2);

		JTextField txt2 = new JTextField(1);
		txt2.setMaximumSize(new Dimension(200, 20));
		panel.add(txt2);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel label3 = new JLabel("Login");
		label3.setFont(myFontMinor);
		panel.add(label3);

		JTextField txt3 = new JTextField(1);
		txt3.setMaximumSize(new Dimension(200, 20));
		panel.add(txt3);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel label4 = new JLabel("Password");
		label4.setFont(myFontMinor);
		panel.add(label4);

		JTextField txt4 = new JTextField(1);
		txt4.setMaximumSize(new Dimension(200, 20));
		panel.add(txt4);

		panel.add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		panel.add(buttonsPanel);

		JButton btn1 = new JButton("Back");
		btn1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonsPanel.add(btn1);

		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		JButton btn2 = new JButton("Create");
		btn2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonsPanel.add(btn2);

		btn1.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn1.getText())) {
				new LoginPanel(userService);
				dispose();
			}
		});

		btn2.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn2.getText())) {
				if (!txt1.getText().trim().equals("") && !txt2.getText().trim().equals("")
						&& !txt3.getText().trim().equals("") && !txt4.getText().trim().equals("")) {
					System.out.println("Wszystko jest");
					User u = userService.getUserbyLogin(txt3.getText().trim());
					if (u == null) {
						try {
							userService.addUser(txt3.getText(), txt4.getText(), txt1.getText(), txt2.getText());
							JOptionPane.showMessageDialog(this, "User added correctly", ":-)",
									JOptionPane.INFORMATION_MESSAGE);
							
							new LoginPanel(userService);
							dispose();
						} catch (Exception exc) {
							JOptionPane.showMessageDialog(this, "Don't use weird chars :-)");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Such login already exists");
					}
				}
			}
		});

		panel.revalidate();
	}
}
