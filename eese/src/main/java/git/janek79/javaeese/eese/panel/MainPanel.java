package git.janek79.javaeese.eese.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Frame with all utilities prepared for user.
 * For example:
 * - searching and adding friends
 * - sending messages
 * - creating group conversations 
 * 
 * @author Jan Jankowicz
 *
 */

public class MainPanel extends JFrame {
	private UserService userService;
	private User user;
	private Conversation currentConversation = null;

	private Font myFont = new Font("Arial", Font.BOLD, 20);

	private JList<User> lst1;
	private JList<Conversation> conversationsList;
	private JLabel lbl6;
	private JTextArea txtArea1;

	public MainPanel(User user, UserService userService) {
		this.userService = userService;
		this.user = user;

		setSize(800, 480);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fakebook " + user);

		JPanel panel = new JPanel(null);
		panel.setVisible(true);
		add(panel);
		setResizable(false);

		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new BoxLayout(pnl1, BoxLayout.PAGE_AXIS));
		pnl1.setBounds(0, 0, 300, 480);
		pnl1.setBackground(new Color(203, 42, 20));
		panel.add(pnl1);

		JPanel pnl2 = new JPanel();
		pnl2.setLayout(null);
		pnl2.setBounds(300, 0, 500, 480);
		pnl2.setBackground(new Color(23, 42, 200));
		panel.add(pnl2);

		JLabel lbl4 = new JLabel();
		if (user != null) {
			lbl4.setText("Hello " + user.getFirstName());
		}
		lbl4.setFont(myFont);
		lbl4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		lbl4.setForeground(Color.red);
		pnl2.add(lbl4, BorderLayout.CENTER);

		txtArea1 = new JTextArea("Messages here");
		txtArea1.setBounds(20, 20, 460, 340);
		txtArea1.setEditable(false);
		pnl2.add(txtArea1);

		JTextArea txtArea2 = new JTextArea();
		txtArea2.setBounds(20, 380, 380, 20);
		pnl2.add(txtArea2);

		JButton btn3 = new JButton("Send");
		btn3.setBounds(420, 380, 60, 60);
		pnl2.add(btn3);

		JButton btn31 = new JButton("Conversation options");
		btn31.setBounds(20, 410, 185, 30);
		pnl2.add(btn31);

		JButton btn32 = new JButton("Load messages");
		btn32.setBounds(215, 410, 185, 30);
		pnl2.add(btn32);

		pnl2.revalidate();

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		// left panel
		JLabel lbl5 = new JLabel("FRIENDS");
		lbl5.setFont(myFont);
		lbl5.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lbl5);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		lst1 = new JList<>();
		List<User> friendsList = user.getFriendsList();
		lst1.setListData(friendsList.toArray(new User[friendsList.size()]));
		lst1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lst1);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		lbl6 = new JLabel("GROUP CONVERSATIONS");
		lbl6.setFont(myFont);
		lbl6.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lbl6);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		conversationsList = new JList<>();
		updateConversationsList();

		pnl1.add(conversationsList);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		JButton btnOption = new JButton("Options");
		btnOption.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(btnOption);

		panel.revalidate();
		pnl2.revalidate();
		pnl1.revalidate();
		pnl1.repaint();
		pnl2.repaint();

		// listeners
		btn3.addActionListener((e) -> {
			if (currentConversation == null) {
				txtArea1.setText("Choose conversation!");
			} else if (txtArea2.getText().trim().equals("")) {
				txtArea1.setText("Message cannot be empty!");
			} else {
				try {
					userService.sendMessage(txtArea2.getText(), user, currentConversation);
					System.out.println("Message '" + txtArea2.getText() + "' has been send!");
					txtArea2.setText("");
					updateConversation();
				} catch (Exception exc) {
					txtArea2.setText("Your message contains invalid chars");
				}
			}

		});

		lst1.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					if (lst1.getSelectedValue() != null) {
						conversationsList.clearSelection();
						conversationsList.repaint();
						currentConversation = userService.getConversationWithUser(user.getId(),
								((User) lst1.getSelectedValue()).getId());
						updateConversation();
					} else {
						txtArea1.setText("Choose conversation");
					}
				}
			}

		});

		conversationsList.addListSelectionListener((e) -> {
			if (!e.getValueIsAdjusting()) {
				if (conversationsList.getSelectedValue() != null) {
					lst1.clearSelection();
					lst1.repaint();
					currentConversation = userService.getConversation(conversationsList.getSelectedValue().getId());
					updateConversation();
				} else {
					txtArea1.setText("Click again");
				}
			}
		});

		btnOption.addActionListener((e) -> {
			updateUser();
			updateFriendsList();

			JPopupMenu menu = new JPopupMenu();

			JMenuItem searchFriendsItem = new JMenuItem("Search friends...");

			searchFriendsItem.addActionListener((event) -> {
				new SearchPanel(this, user, userService);
			});
			menu.add(searchFriendsItem);

			if (!this.user.getFriendsList().isEmpty()) {
				JMenuItem deleteFriendItem = new JMenuItem("Remove friend...");

				deleteFriendItem.addActionListener((event) -> {
					User choosedUser = (User) JOptionPane.showInputDialog(this, "Choose friend to remove from list",
							"Goodbye", JOptionPane.INFORMATION_MESSAGE, null,
							this.user.getFriendsList().toArray(new User[this.user.getFriendsList().size()]), null);

					if (choosedUser != null) {
						this.userService.deleteFriendship(this.user.getId(), choosedUser.getId());

						JOptionPane.showMessageDialog(this, "Friendship ended");

						currentConversation = null;
						
						updateUser();
						updateConversationsList();
						updateFriendsList();
						updateConversation();
						
					}
				});

				menu.add(deleteFriendItem);
			}

			JMenuItem logoutItem = new JMenuItem("Log out");

			logoutItem.addActionListener((event) -> {
				new LoginPanel(userService);
				dispose();
			});

			menu.add(logoutItem);

			JMenuItem deleteAccountItem = new JMenuItem("Delete account");

			deleteAccountItem.addActionListener((event) -> {
				String result = JOptionPane.showInputDialog("Are you sure that you want to delete your account? \n "
						+ "If yes please type in your password:");
				if (result.equals(this.user.getPassword())) {
					if (userService.deleteAccount(this.user.getId())) {
						JOptionPane.showMessageDialog(this, "Your account has been deleted");
						this.user = null;
						currentConversation = null;
						new LoginPanel(userService);
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Something goes wrong :O");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Wrong password");
				}
			});

			menu.add(deleteAccountItem);

			menu.show(this, btnOption.getX(), btnOption.getY());

		});

		btn31.addActionListener((e) -> {

			JPopupMenu menu = new JPopupMenu();
			JMenuItem item1 = new JMenuItem("Add friend to conversation...");

			if (currentConversation != null) {
				item1.addActionListener((event) -> {
					currentConversation = userService.getConversation(currentConversation.getId());
					List<User> friendsToChoose = new ArrayList<>();
					for (User u : userService.getFriendsList(user.getId())) {
						if (!this.currentConversation.getUsersList().contains(u)) {
							friendsToChoose.add(u);
						}
					}
					if (!friendsToChoose.isEmpty()) {

						User choosedUser = (User) JOptionPane.showInputDialog(this, "Choose friend from list",
								"Adding friend", JOptionPane.QUESTION_MESSAGE, null,
								friendsToChoose.toArray(new User[friendsToChoose.size()]), null);

						if (choosedUser != null) {

							List<Integer> usersId = new ArrayList<>();
							for (User u : currentConversation.getUsersList()) {
								usersId.add(u.getId());
							}
							usersId.add(choosedUser.getId());
							Conversation existingConversation = this.userService.getConversation(usersId);

							if (existingConversation != null) {

								currentConversation = existingConversation;
								updateConversation();

								lst1.clearSelection();
								conversationsList.setSelectedValue(currentConversation, true);
								lst1.repaint();
								conversationsList.repaint();

							} else if (currentConversation.getUsersList().size() > 2) {

								this.userService.joinConversation(choosedUser.getId(), currentConversation.getId());

							} else if (currentConversation.getUsersList().size() == 2) {

								Conversation conversation = this.userService.createConservation();
								this.userService.joinConversation(user.getId(), conversation.getId());
								this.userService.joinConversation(choosedUser.getId(), conversation.getId());
								this.userService.joinConversation(lst1.getSelectedValue().getId(),
										conversation.getId());
								this.currentConversation = conversation;
								updateConversationsList();
								updateConversation();
								lbl6.setVisible(true);

								lst1.clearSelection();
								conversationsList.setSelectedValue(conversation, true);
								lst1.repaint();
								conversationsList.repaint();

							}
						}
					} else {
						JOptionPane.showMessageDialog(this, "You haven't any friends abled to add", ": - (",
								JOptionPane.ERROR_MESSAGE);
					}
				});

				menu.add(item1);

				if (currentConversation.getUsersList().size() > 2) {

					JMenuItem item2 = new JMenuItem("Left conversation");
					item2.addActionListener((event) -> {

						this.userService.leftConversation(user.getId(), currentConversation.getId());
						this.currentConversation = null;

						updateConversationsList();
						lst1.clearSelection();
						conversationsList.clearSelection();
						lst1.repaint();
						conversationsList.repaint();
						txtArea1.setText("Choose conversation!");

					});

					menu.add(item2);
				}
			}

			JMenuItem item11 = new JMenuItem("Cancel");
			menu.add(item11);

			menu.show(this, btn31.getX(), btn31.getY());
		});

		btn32.addActionListener((e) -> {
			updateConversation();
		});
	}

	public void updateConversation() {
		List<Message> msgs = userService.getMessagesList(currentConversation.getId());
		txtArea1.setText("");
		for (Message m : msgs) {
			txtArea1.append(m.getUserId().getLogin() + " [" + m.getDate() + "]: " + m.getMessage() + "\n");
		}
	}

	public void updateConversationsList() {
		List<Conversation> conList = userService.getConversationsList(user.getId());

		for (int i = 0; i < conList.size(); i++) {
			if (conList.get(i).getUsersList().size() == 2) {
				conList.remove(i);
				i--;
			}
		}
		if (conList.isEmpty()) {
			lbl6.setVisible(false);
		}
		conversationsList.setListData(conList.toArray(new Conversation[conList.size()]));
	}

	public void updateFriendsList() {
		List<User> friendsList = userService.getFriendsList(user.getId());

		lst1.setListData(friendsList.toArray(new User[friendsList.size()]));
	}
	
	public void updateUser() {
		user = userService.getUser(user.getId());
	}

}
