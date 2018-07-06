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
	public void addUser(String login, String password, String firstName, String lastName) {
		System.out.println(sessionFactory);
		Session session = sessionFactory.getCurrentSession();

		User u = getUser(firstName, lastName);

		if (u == null) {
			session.save(new User(login, password, firstName, lastName));
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
	public void joinConversation(int userId, int conversationId) {
		Session session = sessionFactory.getCurrentSession();

		User u = session.get(User.class, userId);
		Conversation c = session.get(Conversation.class, conversationId);

		if (u != null && c != null) {
			List<Conversation> list = session.get(User.class, userId).getConversationsList();

			if (!list.contains(c)) {
				list.add(c);
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
		Session session = sessionFactory.getCurrentSession();
		
		List<Conversation> conversationsList = session.get(User.class, userId).getConversationsList();
		
		return conversationsList;
	}

	@Override
	public boolean belongToConversation(User user, Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();

		User u = getUser(user.getId());
		Conversation c = session.get(Conversation.class, conversation.getId());

		return u.getConversationsList().contains(c);
	}

	@Override
	public void addFriend(int user1Id, int user2Id) {
		Session session = sessionFactory.getCurrentSession();
				
		User u1 = getUser(user1Id);
		User u2 = getUser(user2Id);
		
		if(u1==null || u2==null) {
			System.out.println("Not able to find user(s)!");
		} else if(u1.getFriendsList().contains(u2) || u2.getFriendsList().contains(u1)) {
			System.out.println("Those users are friends already!");
		} else {
			u1.getFriendsList().add(u2);
			u2.getFriendsList().add(u1);
			System.out.println("Friend added successfully!");
		}
	}
	
	@Override
	public User getUserbyLogin(String login, String password) {
		Session session = sessionFactory.getCurrentSession();

		List<User> list = session
				.createQuery("FROM User u WHERE u.login='" + login + "' AND u.password='" + password + "'",
						User.class)
				.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
		
	}
	
	@Override
	public User getUserbyLogin(String login) {
		Session session = sessionFactory.getCurrentSession();

		List<User> list = session
				.createQuery("FROM User u WHERE u.login='" + login + "'",
						User.class)
				.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public List<User> getFriendsList(int userId) {
		Session session = sessionFactory.getCurrentSession();
			
		return session.get(User.class, userId).getFriendsList();
	}
	
	@Override
	public Conversation getConversationWithUser(int user1Id, int user2Id) {
		Session session = sessionFactory.getCurrentSession();
		
		User user1 = session.get(User.class, user1Id); 
		User user2 = session.get(User.class, user2Id);
		
		System.out.println(user1.getFirstName());
		System.out.println(user2.getFirstName());
		
		if(user1 == null || user2 == null) {
			System.out.println("Such users don't exist");
		} else {
			List<Conversation> cList = user1.getConversationsList();
			for(int i = 0; i < cList.size(); i++) {
				if(belongToConversation(user2, cList.get(i)) && cList.get(i).getUsersList().size()==2) {
					System.out.println("ZWRACAM!");
					return cList.get(i);
				}
				System.out.println("Pętla");
			}
			System.out.println("Nie zwróciło");
			Conversation con = new Conversation();
			session.save(con);
			joinConversation(user1.getId(), con.getId());
			joinConversation(user2.getId(), con.getId());
			return con;
		}
		
		return null;
	}
	
	@Override
	public boolean deleteAccount(int userId) {
		Session session = sessionFactory.getCurrentSession();
		
		User user = session.get(User.class, userId);
		
		if(user == null) {
			System.out.println("Such user doesn't exist");
			return false;
		} else {
			session.remove(user);
			System.out.println("User has been removed");
			return true;
		}
		
	}
	
	@Override
	public void leftConversation(int userId, int conversationId) {
		Session session = sessionFactory.getCurrentSession();
		
		User user = session.get(User.class, userId);
		Conversation conversation = session.get(Conversation.class, conversationId);
		
		if(user == null || conversation == null) {
			System.out.println("Such user or conversation doesn't exists");
		} else {
			user.getConversationsList().remove(conversation);
		}
	}
}
