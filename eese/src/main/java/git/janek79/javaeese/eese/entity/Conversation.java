package git.janek79.javaeese.eese.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@ManyToMany(mappedBy = "conversationsList", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<User> usersList = new ArrayList<>();

	@OneToMany(mappedBy = "conversationId", cascade = CascadeType.ALL)
	private List<Message> messagesList = new ArrayList<>();

	public Conversation() {
	}

	public Conversation(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public List<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(User user: this.usersList) {
			result.append(user.getFirstName() + " " + user.getLastName() + ", ");
		}
		return result.delete(result.length()-2, result.length()).toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj instanceof Conversation) {
			List<User> objList = ((Conversation) obj).getUsersList();
			if(objList.size() == getUsersList().size()) {
				Set<Integer> objIdList = new HashSet<>();
				Set<Integer> thisIdList = new HashSet<>();
				
				for(User u: objList) {
					objIdList.add(u.getId());
				}
				
				for(User u: getUsersList()) {
					thisIdList.add(u.getId());
				}
				
				return objIdList.equals(thisIdList);
			
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		for(User u: getUsersList()) {
			sum =+ u.getId();
		}
		return sum;
	}
	
	@PreRemove
	public void preRemove() {
		System.out.println("Usuwanie konwersacji");
		
		for(User u: this.usersList) {
			u.getConversationsList().remove(this);
		}
		
		for(Message m: this.messagesList) {
			m.setConversationId(null);
		}
	}

}
