package git.janek79.javaeese.eese.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="conversations")
public class Conversation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@ManyToMany(mappedBy="conversationsList", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<User> usersList = new ArrayList<>();
	
	@OneToMany(mappedBy="conversationId", cascade=CascadeType.ALL)
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

	@Override
	public String toString() {
		return "Conversation [id=" + id + ", title=" + title + ", usersList=" + usersList + "]";
	}

	public List<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}
	
	
	
	
}
