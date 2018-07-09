package git.janek79.javaeese.eese;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

			SwingUtilities.invokeLater(()->{
				new LoginPanel(userService);
			});

			
			while(true) {
				
			}
			
		} finally {
			
			context.close();
			
		}
		
	}
}
