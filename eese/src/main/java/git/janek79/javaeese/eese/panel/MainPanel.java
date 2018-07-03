package git.janek79.javaeese.eese.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

public class MainPanel extends JPanel {
	private UserService userService;
	private User user;
	private Conversation currentConversation = null;
	
	private Font myFont = new Font("Arial", Font.BOLD, 20);
	
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
		txtArea2.setBounds(20, 380, 380, 60);
		pnl2.add(txtArea2);

		JButton btn3 = new JButton("Send");
		btn3.setBounds(420, 380, 60, 60);
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
				} catch(Exception exc) {
					txtArea2.setText("Your message contains invalid chars");
				}
			}

		});
		pnl2.add(btn3);

		pnl2.revalidate();

		// pnl1
		JLabel lbl5 = new JLabel("FRIENDS");
		lbl5.setFont(myFont);
		lbl5.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pnl1.add(lbl5);

		pnl1.add(Box.createRigidArea(new Dimension(0, 10)));

		JList<String> lst1 = new JList<>();
		lst1.setListData(userService.createFriendsArray(user));
		lst1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		lst1.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					System.out.println("ZMIANA");
					// System.out.println(catchLogin(lst1.getSelectedValue()));
					currentConversation = userService.getConversationWithUser(user.getId(),
					userService.getUserbyLogin(catchLogin(lst1.getSelectedValue())).getId());
					System.out.println(currentConversation.getId());
					updateConversation(txtArea1);
				}
			}

		});
		System.out.println(lst1.getListSelectionListeners());
		pnl1.add(lst1);

		JButton btn4 = new JButton("Log out");
		btn4.addActionListener((e) -> {
			this.user = null;
			currentConversation = null;
			frame.remove(panel);
			new LoginPanel(frame, userService);
		});
		pnl1.add(btn4);

		panel.revalidate();
		pnl2.revalidate();
		pnl1.revalidate();
		pnl1.repaint();
		pnl2.repaint();

	}
	
	public void updateConversation(JTextArea textArea) {
		List<Message> msgs = userService.getMessagesList(currentConversation.getId());
		textArea.setText("");
		for (Message m : msgs) {
			textArea.append(m.getUserId().getLogin() + " [" + m.getDate() + "]: " + m.getMessage() + "\n");
		}
	}
	
	public String catchLogin(String label) {
		return label.split(" ")[3];
	}
}
