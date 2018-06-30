package git.janek79.javaeese.eese.repository;

import java.util.List;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;

public interface ConversationDAO {
	public void createConservation(String title);
	public void createConservation();
	
	public Conversation getConversation(int ConversationId);
	
	public List<Message> getMessagesList(int ConversationId);
}
