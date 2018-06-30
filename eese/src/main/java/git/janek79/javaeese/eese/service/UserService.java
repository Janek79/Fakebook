package git.janek79.javaeese.eese.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.repository.ConversationDAO;
import git.janek79.javaeese.eese.repository.MessageDAO;
import git.janek79.javaeese.eese.repository.UserDAO;

@Service
public class UserService implements UserDAO, ConversationDAO {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ConversationDAO conversationDAO;
	
	@Autowired
	private MessageDAO messageDAO;

	public UserService() {
	}

	public void addUser(String firstName, String lastName) {
		this.userDAO.addUser(firstName, lastName);
	}

	public User getUser(int id) {
		return this.userDAO.getUser(id);
	}
	
	public void joinConversation(User user, Conversation conversation) {
		userDAO.joinConversation(user, conversation);
	}
	
	public void sendMessage(String message, User user, Conversation conversation) {
		User u = this.userDAO.getUser(user.getId());
		Conversation c = this.conversationDAO.getConversation(conversation.getId());
		List<Conversation> list = this.userDAO.getConversationsList(user.getId());
		
		if(u == null || c == null) {
			System.out.println("Such user or conversation doesn't exists");
		} else if(!this.userDAO.belongToConversation(user, conversation)) {
			System.out.println("User doesn't belong to this conversation!");
		} else {
			this.messageDAO.sendMessage(message, user, conversation);
		}
	}

	@Override
	public void createConservation() {
		this.conversationDAO.createConservation();
	}
	
	@Override
	public void createConservation(String title) {
		this.conversationDAO.createConservation(title);
	}

	@Override
	public User getUser(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Conversation getConversation(int id) {
		return this.conversationDAO.getConversation(id);
	}

	
	
	// getters and setters
	public ConversationDAO getConversationDAO() {
		return conversationDAO;
	}
	
	public void setConversationDAO(ConversationDAO conversationDAO) {
		this.conversationDAO = conversationDAO;
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public List<Conversation> getConversationsList(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Conversation> getConversationsList(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean belongToConversation(User user, Conversation conversation) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Message> getMessagesList(int id) {
		List<Message> list = this.conversationDAO.getMessagesList(id);
		
		Collections.sort(list, new Comparator<Message>() {
			@Override
			public int compare(Message o1, Message o2) {
				if(o1.getDate().before(o2.getDate())) {
					return -1;
				} else {
					return 1;
				}

			}
		});
		
		return list;
	}
	
	@Override
	public void addFriend(int user1Id, int user2Id) {
		this.userDAO.addFriend(user1Id, user2Id);
	}

}
