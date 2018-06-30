package git.janek79.javaeese.eese;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import git.janek79.javaeese.eese.configuration.ConfigClass;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(ConfigClass.class);
			System.out.println("Hello World!");
			UserService userService = context.getBean(UserService.class);
//			userService.addUser("Bronek", "Lis");
//			System.out.println(userService.getUser(2));
//			userService.joinConversation(userService.getUser(2), userService.getConversation(1));
//			System.out.println(userService.getConversation(1).getUsersList());
//			userService.addUser("Andrzej", "Kolanko");
			//userService.sendMessage("Testowo", userService.getUser(2), userService.getConversation(1));
			
//			userService.joinConversation(userService.getUser(3), userService.getConversation(1));
//			userService.sendMessage("Stary czas", userService.getUser(3), userService.getConversation(1));
			
			userService.addFriend(1, 2);
			
		List<Message> list = userService.getMessagesList(1);
		for(Message msg: list) {
			System.out.println(msg.getMessage() + "(" + msg.getUserId().getFirstName() + ") on " + msg.getDate());
		}
			
		} finally {
			context.close();
		}
		// JDBCConnectionTest test = new JDBCConnectionTest();
		// test.testConnection();

	}
}
