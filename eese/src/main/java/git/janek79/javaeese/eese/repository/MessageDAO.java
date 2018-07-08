package git.janek79.javaeese.eese.repository;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.User;

public interface MessageDAO {
	public void sendMessage(String message, User user, Conversation conversation);
	
//	public boolean isNewMessageAppear(Date lastMessageTime);
}
