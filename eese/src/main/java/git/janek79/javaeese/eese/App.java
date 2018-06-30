package git.janek79.javaeese.eese;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import git.janek79.javaeese.eese.configuration.ConfigClass;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Hello world!
 *
 */
public class App {
	static AnnotationConfigApplicationContext context = null;
	static JLabel lbl1, lbl2, lbl3 = null;
	
	public static void main(String[] args) {
		try {
			context = new AnnotationConfigApplicationContext(ConfigClass.class);
			System.out.println("Hello World!");
			UserService userService = context.getBean(UserService.class);

			Font myFont = new Font("Arial", Font.BOLD, 20);
			

			JFrame frame = new JFrame("Fakebook");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 320);
			frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
					(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
			frame.setVisible(true);
			frame.setResizable(true);

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.setVisible(true);
			frame.add(panel);

			panel.add(Box.createRigidArea(new Dimension(0, 10)));

			
			//Welcome in fakebook label
			lbl1 = new JLabel("Welcome in Fakebook!");
			lbl1.setFont(myFont);
			lbl1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			panel.add(lbl1);

			
			panel.add(Box.createRigidArea(new Dimension(0, 10)));

			
			//text field for login
			JTextField txt1 = new JTextField(1);
			txt1.setText("login");
			txt1.setMaximumSize(new Dimension(200, 20));
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
			panel.add(txt1);

			
			panel.add(Box.createRigidArea(new Dimension(0, 10)));

			
			//text field for password
			JTextField txt2 = new JTextField(1);
			txt2.setText("password");
			txt2.setMaximumSize(new Dimension(200, 20));
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
			panel.add(txt2);

			
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			
			
			// "Log in" button
			JButton btn1 = new JButton("Log in");
			btn1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			btn1.addActionListener((e) -> {
				if (e.getActionCommand().equals(btn1.getText())) {
					if (userService.getUserbyLogin(txt1.getText(), txt2.getText()) != null) {
						System.out.println("You've been logged in successfully!");
						panel.setVisible(false);
						lbl3.setVisible(false);
					} else {
						System.out.println("Wrong login or password");
						lbl3.setVisible(true);
					}
				}
			});
			panel.add(btn1);
			
			
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			
			
			lbl2 = new JLabel("or");
			lbl2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			panel.add(lbl2);
			
			
			panel.add(Box.createRigidArea(new Dimension(0, 10)));
			
			
			JButton btn2 = new JButton("Create new account");
			btn2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			btn2.addActionListener((e) -> {
				if (e.getActionCommand().equals(btn2.getText())) {
					
				}
			});
			panel.add(btn2);
			
			
			panel.add(Box.createRigidArea(new Dimension(0, 15)));
			
			
			lbl3 = new JLabel("wrong login or password!");
			lbl3.setFont(myFont);
			lbl3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			lbl3.setVisible(false);
			lbl3.setForeground(Color.red);
			panel.add(lbl3);
			
			
			
			panel.revalidate();
			
			while(true) {
				
			}
			
			

		} finally {
			context.close();
		}

	}

	public static void createLoginFrame() {

	}
}
