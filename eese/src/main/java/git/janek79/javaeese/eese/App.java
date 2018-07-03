package git.janek79.javaeese.eese;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import git.janek79.javaeese.eese.configuration.ConfigClass;
import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.panel.LoginPanel;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Hello world!
 *
 */
public class App {
	static AnnotationConfigApplicationContext context = null;
	static UserService userService = null;

	public static void main(String[] args) {
		
		try {
			
			context = new AnnotationConfigApplicationContext(ConfigClass.class);
			System.out.println("Hello World!");
			userService = context.getBean(UserService.class);
			userService.addFriend(4, 2);
			userService.addFriend(4, 3);

			JFrame frame = new JFrame("Fakebook");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			new LoginPanel(frame, userService);

			while (true) {

			}
			
		} finally {
			
			context.close();
			System.out.println("Koniec");
			
		}
		
	}
}
