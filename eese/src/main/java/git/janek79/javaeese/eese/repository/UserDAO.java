package git.janek79.javaeese.eese.repository;

import java.util.List;
import java.util.Set;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.User;

public interface UserDAO {
	public void addUser(String login, String password, String firstName, String lastName);
	
	public User getUserbyLogin(String login, String password);
	public User getUserbyLogin(String login);
	public User getUser(String firstName, String lastName);
	public User getUser(int id);
	
	public Set<User> getPossibleUsers(String string);
	
	public void joinConversation(int userId, int conversationId);
	
	public void leftConversation(int userId, int conversationId);
	
	public List<Conversation> getConversationsList(User user);
	public List<Conversation> getConversationsList(int userId);
	
	public Conversation getConversationWithUser(int user1Id, int user2Id);
	
	public boolean belongToConversation(User user, Conversation conversation);
	
	public void addFriend(int user1Id, int user2Id);
	
	public List<User> getFriendsList(int userId);
	
	public boolean deleteAccount(int userId);

}
