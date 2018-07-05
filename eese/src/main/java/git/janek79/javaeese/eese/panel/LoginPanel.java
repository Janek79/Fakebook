package git.janek79.javaeese.eese.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

public class LoginPanel {
	private UserService userService;
	private User user = null;
	private Font myFont = new Font("Arial", Font.BOLD, 20);
	
	public LoginPanel(JFrame frame, UserService userService) {
		this.userService = userService;
		
		frame.setSize(320, 240);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
		frame.setVisible(true);
		frame.setResizable(false);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.setVisible(true);
		frame.add(panel1);

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

		JLabel lbl3 = new JLabel("wrong login or password");
		lbl3.setFont(myFont);
		lbl3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		lbl3.setVisible(false);
		lbl3.setForeground(Color.red);
		panel1.add(lbl3);
		
		btn1.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn1.getText())) {
				user = userService.getUserbyLogin(txt1.getText(), txt2.getText());
				if (user != null) {
					System.out.println("You've been logged in successfully!");
					frame.remove(panel1);
					new MainPanel(frame, user, userService);
				} else {
					System.out.println("Wrong login or password");
					lbl3.setVisible(true);
					frame.setSize(320, 280);
				}
			}
		});
		
		btn2.addActionListener((e)->{
			frame.remove(panel1);
			new RegisterPanel(frame, userService);
		});
		
		txt1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (txt1.getText().equals("login")) {
					txt1.setText("");
				}
			}
		});
		
		txt2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (txt2.getText().equals("password")) {
					txt2.setText("");
				}
			}
		});

		panel1.revalidate();
	}
}
