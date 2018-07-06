package git.janek79.javaeese.eese.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.google.protobuf.Extension.MessageType;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

public class MainPanel {
	private UserService userService;
	private User user;
	private Conversation currentConversation = null;

	private Font myFont = new Font("Arial", Font.BOLD, 20);

	private JLabel lbl6;

	public MainPanel(JFrame frame, User user, UserService userService) {
		this.userService = userService;
		this.user = user;

		System.out.println(userService.getFriendsList(user.getId()));

		frame.setSize(800, 480);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);

		JPanel panel = new JPanel(null);
		panel.setVisible(true);
		frame.add(panel);
		frame.setResizable(false);

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

		JTextArea txtArea1 = new JTextArea("Messages here");
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
		btn31.setBounds(20, 410, 380, 30);
		pnl2.add(btn31);

		pnl2.revalidate();

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		// left panel
		JLabel lbl5 = new JLabel("FRIENDS");
		lbl5.setFont(myFont);
		lbl5.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lbl5);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		JList<User> lst1 = new JList<>();
		List<User> friendsList = user.getFriendsList();
		lst1.setListData(friendsList.toArray(new User[friendsList.size()]));
		lst1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		System.out.println(lst1.getListSelectionListeners());
		pnl1.add(lst1);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		lbl6 = new JLabel("GROUP CONVERSATIONS");
		lbl6.setFont(myFont);
		lbl6.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lbl6);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		JList<Conversation> conversationsList = new JList<>();
		updateConversationsList(conversationsList);

		pnl1.add(conversationsList);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel panelBtn = new JPanel();
		panelBtn.setBackground(new Color(203, 42, 20));
		panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.LINE_AXIS));
		pnl1.add(panelBtn);

		JButton btn4 = new JButton("Log out");
		panelBtn.add(btn4);

		panelBtn.add(Box.createRigidArea(new Dimension(5, 0)));

		JButton btn5 = new JButton("Delete account");
		panelBtn.add(btn5);

		panel.revalidate();
		pnl2.revalidate();
		pnl1.revalidate();
		panelBtn.revalidate();
		pnl1.repaint();
		pnl2.repaint();
		panelBtn.repaint();

		// listeners
		btn3.addActionListener((e) -> {
			if (currentConversation == null) {
				txtArea1.setText("Choose conversation!");
			} else if (txtArea2.getText().trim().equals("")) {
				System.out.println("Message cannot be empty!");
			} else {
				try {
					userService.sendMessage(txtArea2.getText(), user, currentConversation);
					System.out.println("Message '" + txtArea2.getText() + "' has been send!");
					txtArea2.setText("");
					updateConversation(txtArea1);
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
						System.out.println("ZMIANA");
						conversationsList.clearSelection();
						conversationsList.repaint();
						System.out.println(lst1.getSelectedValue());
						currentConversation = userService.getConversationWithUser(user.getId(),
								((User) lst1.getSelectedValue()).getId());
						System.out.println(currentConversation.getId());
						updateConversation(txtArea1);
					} else {
						txtArea1.setText("Click again");
					}
				}
			}

		});

		conversationsList.addListSelectionListener((e) -> {
			if (!e.getValueIsAdjusting()) {
				if (conversationsList.getSelectedValue() != null) {
					System.out.println("Not adjusting");
					lst1.clearSelection();
					lst1.repaint();
					System.out.println(conversationsList.getSelectedValue());
					currentConversation = userService.getConversation(conversationsList.getSelectedValue().getId());
					updateConversation(txtArea1);
				} else {
					txtArea1.setText("Click again");
				}
			} else {
				System.out.println("Adjusting");
			}
		});

		btn4.addActionListener((e) -> {
			this.user = null;
			currentConversation = null;
			frame.remove(panel);
			new LoginPanel(frame, userService);
		});

		btn5.addActionListener((e) -> {
			String result = JOptionPane.showInputDialog(
					"Are you sure that you want to delete your account? \n " + "If yes please type in your password:");
			if (result.equals(this.user.getPassword())) {
				if (userService.deleteAccount(user.getId())) {
					JOptionPane.showMessageDialog(frame, "Your account has been deleted");
					this.user = null;
					currentConversation = null;
					frame.remove(panel);
					new LoginPanel(frame, userService);
				} else {
					JOptionPane.showMessageDialog(frame, "Something goes wrong :O");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Wrong password");
			}
		});

		btn31.addActionListener((e) -> {

			JPopupMenu menu = new JPopupMenu();
			JMenuItem item1 = new JMenuItem("Add friend to conversation...");
			if (currentConversation != null) {
				item1.addActionListener((event) -> {
					List<User> friendsToChoose = new ArrayList<>();
					for (User u : friendsList) {
						if (!this.currentConversation.getUsersList().contains(u)) {
							friendsToChoose.add(u);
						}
					}
					if (!friendsToChoose.isEmpty()) {

						User choosedUser = (User) JOptionPane.showInputDialog(frame, "Choose friend from list",
								"Adding friend", JOptionPane.QUESTION_MESSAGE, null,
								friendsToChoose.toArray(new User[friendsToChoose.size()]), null);

						if (choosedUser != null) {

							List<Integer> usersId = new ArrayList<>();
							for (User u : currentConversation.getUsersList()) {
								usersId.add(u.getId());
							}
							usersId.add(choosedUser.getId());
							Conversation existingConversation = this.userService.getConversation(usersId);
							// && existingConversation.getUsersList().size() == 2
							if (existingConversation != null) {

								currentConversation = existingConversation;
								updateConversation(txtArea1);

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
								updateConversationsList(conversationsList);
								updateConversation(txtArea1);

								lst1.clearSelection();
								conversationsList.setSelectedValue(conversation, true);
								lst1.repaint();
								conversationsList.repaint();

								System.out.println("Nowa konwersacja");
							}
						}
					} else {
						JOptionPane.showMessageDialog(frame, "You haven't any friends abled to add", ": - (",
								JOptionPane.ERROR_MESSAGE);
					}
				});

				menu.add(item1);
			}

			if (currentConversation.getUsersList().size() > 2) {
				
				JMenuItem item2 = new JMenuItem("Left conversation");
				item2.addActionListener((event) -> {
					
					this.userService.leftConversation(user.getId(), currentConversation.getId());
					this.currentConversation = null;

					updateConversationsList(conversationsList);
					lst1.clearSelection();
					conversationsList.clearSelection();
					lst1.repaint();
					conversationsList.repaint();
					txtArea1.setText("Choose conversation!");
					
				});

				menu.add(item2);
			}

			JMenuItem item11 = new JMenuItem("Cancel");
			menu.add(item11);

			menu.show(frame, btn31.getX(), btn31.getY());
		});
	}

	public void updateConversation(JTextArea textArea) {
		List<Message> msgs = userService.getMessagesList(currentConversation.getId());
		textArea.setText("");
		for (Message m : msgs) {
			textArea.append(m.getUserId().getLogin() + " [" + m.getDate() + "]: " + m.getMessage() + "\n");
		}
	}

	public void updateConversationsList(JList<Conversation> conversationsList) {
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

}
