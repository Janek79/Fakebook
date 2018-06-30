package git.janek79.javaeese.eese.repository;

import java.util.List;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.User;

public interface UserDAO {
	public void addUser(String firstName, String lastName);
	
	public User getUser(String firstName, String lastName);
	public User getUser(int id);
	
	public void joinConversation(User user, Conversation conversation);
	
	public List<Conversation> getConversationsList(User user);
	public List<Conversation> getConversationsList(int userId);
	
	public boolean belongToConversation(User user, Conversation conversation);
	
	public void addFriend(int user1Id, int user2Id);
}
