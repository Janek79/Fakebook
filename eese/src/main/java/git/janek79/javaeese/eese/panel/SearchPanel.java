package git.janek79.javaeese.eese.panel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.listener.AutoRemoveTextListener;
import git.janek79.javaeese.eese.service.UserService;

public class SearchPanel extends JFrame {
	private boolean end = false;
	private User searchedUser = null;
	private JButton btnAdd;

	public SearchPanel(JFrame parentFrame, User currentUser, UserService userService) {
		parentFrame.setEnabled(false);

		setResizable(false);
		setTitle("Search friends");
		setSize(300, 400);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		add(panel);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		panel.add(searchPanel);

		JTextField friendSearchField = new JTextField();
		friendSearchField.setMaximumSize(new Dimension(160, 25));
		friendSearchField.setText("type in searched user");
		friendSearchField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		searchPanel.add(friendSearchField);

		searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		JButton findButton = new JButton("Find users");
		searchPanel.add(findButton);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JList<User> usersList = new JList<>();
		panel.add(usersList);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		this.btnAdd = new JButton("Add user to friends list");
		this.btnAdd.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.btnAdd.setVisible(false);
		panel.add(btnAdd);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		friendSearchField.addMouseListener(new AutoRemoveTextListener("type in searched user", friendSearchField));

		btnAdd.addActionListener((e) -> {
			if (usersList.getSelectedValue() != null) {
				userService.addFriend(currentUser.getId(), usersList.getSelectedValue().getId());
				
				dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			} else {
				JOptionPane.showMessageDialog(this, "You haven't choosed anyone", ":-(", JOptionPane.ERROR_MESSAGE);
				btnAdd.setVisible(false);
			}


		});

		findButton.addActionListener((e) -> {
			btnAdd.setVisible(false);
			if (friendSearchField.getText().trim().length() > 2) {
				Set<User> resultSet = userService.getPossibleUsers(friendSearchField.getText());
				resultSet.removeAll(new HashSet<User>(currentUser.getFriendsList()));
				resultSet.remove(currentUser);
				usersList.setListData(resultSet.toArray(new User[resultSet.size()]));

				usersList.repaint();
				if (resultSet.isEmpty()) {
					JOptionPane.showMessageDialog(this, "I didn't find anybody", ":-(", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Use at least 3 chars", ":-(", JOptionPane.ERROR_MESSAGE);
			}
		});

		usersList.addListSelectionListener((e) -> {
			if (!e.getValueIsAdjusting()) {
				btnAdd.setVisible(true);
			}
		});

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				MainPanel.updateConversationsList();
				MainPanel.updateFriendsList();
				
				parentFrame.repaint();
				parentFrame.revalidate();
				
				parentFrame.setEnabled(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		repaint();

	}
}
