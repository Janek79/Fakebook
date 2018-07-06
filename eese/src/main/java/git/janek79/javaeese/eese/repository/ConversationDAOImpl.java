package git.janek79.javaeese.eese.repository;

import java.util.ArrayList;
import java.util.List;

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
public class ConversationDAOImpl implements ConversationDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Conversation createConservation() {
		return createConservation("Conversation");
	}

	@Override
	public Conversation createConservation(String title) {
		Session session = sessionFactory.getCurrentSession();
		Conversation conversation = new Conversation(title);

		session.save(conversation);

		return conversation;
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

	@Override
	public Conversation getConversation(List<Integer> usersId) {
		if (usersId == null) {
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		List<User> usersList = new ArrayList<>();

		for (Integer userId : usersId) {
			User user = session.get(User.class, userId);
			if (user != null) {
				usersList.add(user);
			} else {
				System.out.println("Such conversation doesn't exist");
				return null;
			}
		}

		System.out.println(usersList);

		Conversation result = null;

		for (Conversation conversation : usersList.get(0).getConversationsList()) {
			System.out.println(conversation);
			if (conversation.getUsersList().size() == usersList.size()) {
				System.out.println("Ta sama wielkość");
				for (User user : usersList) {
					System.out.println(user);
					if (!conversation.getUsersList().contains(user)) {
						System.out.println("Null?");
						System.out.println(user);
					}
				}
				System.out.println("Zwrot?");
				result = conversation;
			}
		}

		return result;
	}
}
