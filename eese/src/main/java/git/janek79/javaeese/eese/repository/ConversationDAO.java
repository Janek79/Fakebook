package git.janek79.javaeese.eese.repository;

import java.util.List;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;

public interface ConversationDAO {
	public Conversation createConservation(String title);
	public Conversation createConservation();
	
	public Conversation getConversation(int conversationId);
	
	public List<Message> getMessagesList(int conversationId);
}
