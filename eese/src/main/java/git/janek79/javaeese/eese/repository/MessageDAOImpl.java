package git.janek79.javaeese.eese.repository;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;

@Repository
@Transactional
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void sendMessage(String message, User user, Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();

		Message msg = new Message(message, user, conversation);

		session.save(msg);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
//	@Override
	public boolean isNewMessageAppear(Date lastMessageTime) {
		Session session = sessionFactory.getCurrentSession();
		return false;
	}

}
