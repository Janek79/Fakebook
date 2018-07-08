package git.janek79.javaeese.eese.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="message")
	private String message;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="conversation_id")
	private Conversation conversationId;
	
	@Column(name="date")
	private Date date;
	
	public Message() {
	}

	public Message(String message, User user, Conversation conversation) {
		this.message = message;
		this.userId = user;
		this.conversationId = conversation;
		this.date = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Conversation getConversationId() {
		return conversationId;
	}

	public void setConversationId(Conversation conversationId) {
		this.conversationId = conversationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", userId=" + userId + ", conversationId="
				+ conversationId + ", date=" + date + "]";
	}
	
	@PreRemove
	public void preRemove() {
		System.out.println("Usuwanie wiadomości");
		this.conversationId = null;
		this.userId = null;
	}
	
}
