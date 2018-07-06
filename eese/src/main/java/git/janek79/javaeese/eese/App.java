package git.janek79.javaeese.eese;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import git.janek79.javaeese.eese.configuration.ConfigClass;
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

			JFrame frame = new JFrame("Fakebook");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			new LoginPanel(frame, userService);
			
			while(true) {
				
			}
			
		} finally {
			
			context.close();
			
		}
		
	}
}
