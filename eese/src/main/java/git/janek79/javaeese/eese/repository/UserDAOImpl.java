package git.janek79.javaeese.eese.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addUser(String firstName, String lastName) {
		System.out.println(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		
		User u = getUser(firstName, lastName);

		if (u == null) {
			session.save(new User(firstName, lastName));
			System.out.println("User added successfully");
		} else {
			System.out.println(u + " already exists");
		}
	}

	@Override
	public User getUser(int id) {
		Session session = sessionFactory.getCurrentSession();

		return session.get(User.class, id);
	}

	@Override
	public User getUser(String firstName, String lastName) {
		Session session = sessionFactory.getCurrentSession();

		List<User> list = session
				.createQuery("FROM User u WHERE u.firstName='" + firstName + "' AND u.lastName='" + lastName + "'",
						User.class)
				.getResultList();
		
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			System.out.println("Jest kilka wyników");
			return null;
		} else {
			return null;
		}
	}

	@Override
	public void joinConversation(User user, Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();

		User u = session.get(User.class, user.getId());
		Conversation c = session.get(Conversation.class, conversation.getId());

		if (u != null && c != null) {
			List<Conversation> list = session.get(User.class, user.getId()).getConversationsList();
			
			if(!list.contains(c)) {
				list.add(conversation);
			} else {
				System.out.println("User already belong to this conversation!");
			}
		} else {
			System.out.println("User or conversation doesn't exists!");
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Conversation> getConversationsList(User user) {
		return getUser(user.getId()).getConversationsList();
	}
	
	@Override
	public List<Conversation> getConversationsList(int userId) {
		return getUser(userId).getConversationsList();
	}

	@Override
	public boolean belongToConversation(User user, Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();
		
		User u = getUser(user.getId());
		Conversation c = session.get(Conversation.class, conversation.getId());
		
		return u.getConversationsList().contains(c);
	}
}
