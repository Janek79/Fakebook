package git.janek79.javaeese.eese;

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
			
//			System.out.println(userService.getPossibleUsers(""));
			
			while(true) {
				
			}
			
		} finally {
			
			context.close();
			
		}
		
	}
}
