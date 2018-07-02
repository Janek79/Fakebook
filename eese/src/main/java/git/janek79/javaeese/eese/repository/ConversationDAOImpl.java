package git.janek79.javaeese.eese.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;

@Repository
@Transactional
public class ConversationDAOImpl implements ConversationDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void createConservation() {
		createConservation("Conversation");
	}
	@Override
	public void createConservation(String title) {
		Session session = sessionFactory.getCurrentSession();
		Conversation conversation = new Conversation(title);
		
		session.save(conversation);		
	}
	
	@Override
	public Conversation getConversation(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(Conversation.class, id);
	}
	
	@Override
	public List<Message> getMessagesList(int conversationId) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(Conversation.class, conversationId).getMessagesList();
	}
}
