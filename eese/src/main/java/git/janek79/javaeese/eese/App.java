package git.janek79.javaeese.eese;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.config.TxNamespaceHandler;

import git.janek79.javaeese.eese.configuration.ConfigClass;
import git.janek79.javaeese.eese.entity.Conversation;
import git.janek79.javaeese.eese.entity.Message;
import git.janek79.javaeese.eese.entity.User;
import git.janek79.javaeese.eese.service.UserService;

/**
 * Hello world!
 *
 */
public class App {
	static AnnotationConfigApplicationContext context = null;
	static UserService userService = null;
	
	static Font myFont = new Font("Arial", Font.BOLD, 20);
	
	static JPanel panel1 = null;
	static JLabel lbl1, lbl2, lbl3 = null;
	
	static JPanel panel2 = null;
	static JLabel lbl4, lbl5 = null;
	
	static User user = null;
	static Conversation currentConversation = null;
	
	public static void main(String[] args) {
		try {
			context = new AnnotationConfigApplicationContext(ConfigClass.class);
			System.out.println("Hello World!");
			userService = context.getBean(UserService.class);
			userService.addFriend(4, 2);
			userService.addFriend(4, 3);

			JFrame frame = new JFrame("Fakebook");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			
			createLoginPanel(frame, userService);
//			user = userService.getUserbyLogin("javaFan", "psw");
//			createMainPanel(frame, userService);
			
			while(true) {
				
			}
			

		} finally {
			context.close();
			System.out.println("Koniec");
		}

	}

	public static void createFrame() {

	}
	
	public static void createLoginPanel(JFrame frame, UserService userService) {
		frame.setSize(320, 240);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
		frame.setVisible(true);
		frame.setResizable(false);

		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.setVisible(true);
		frame.add(panel1);

		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		
		//Welcome in Fakebook label
		lbl1 = new JLabel("Welcome in Fakebook!");
		lbl1.setFont(myFont);
		lbl1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel1.add(lbl1);

		
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		
		//text field for login
		JTextField txt1 = new JTextField(1);
		txt1.setText("login");
		txt1.setMaximumSize(new Dimension(200, 20));
		txt1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (txt1.getText().equals("login")) {
					txt1.setText("");
				}
			}
		});
		panel1.add(txt1);

		
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));

		
		//text field for password
		JTextField txt2 = new JPasswordField(1);
		txt2.setText("password");
		txt2.setMaximumSize(new Dimension(200, 20));
		txt2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (txt2.getText().equals("password")) {
					txt2.setText("");
				}
			}
		});
		panel1.add(txt2);

		
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		
		// "Log in" button
		JButton btn1 = new JButton("Log in");
		btn1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btn1.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn1.getText())) {
				user = userService.getUserbyLogin(txt1.getText(), txt2.getText());
				if (user != null) {
					System.out.println("You've been logged in successfully!");
					frame.remove(panel1);
					createMainPanel(frame, userService);
					lbl3.setVisible(false);
				} else {
					System.out.println("Wrong login or password");
					lbl3.setVisible(true);
					frame.setSize(320, 280);
				}
			}
		});
		panel1.add(btn1);
		
		
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		
		lbl2 = new JLabel("or");
		lbl2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel1.add(lbl2);
		
		
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		
		JButton btn2 = new JButton("Create new account");
		btn2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btn2.addActionListener((e) -> {
			if (e.getActionCommand().equals(btn2.getText())) {
				
			}
		});
		panel1.add(btn2);
		
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		
		lbl3 = new JLabel("wrong login or password");
		lbl3.setFont(myFont);
		lbl3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		lbl3.setVisible(false);
		lbl3.setForeground(Color.red);
		panel1.add(lbl3);
		
		
		panel1.revalidate();
	}

	public static void createRegistraionPanel(JFrame frame, UserService userService) {
		System.out.println("TO DO");
//		TODO
	}
	
	public static void createMainPanel(JFrame frame, UserService userService) {
		System.out.println(userService.getFriendsList(user.getId()));
		
		frame.setSize(800, 480);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height) / 2);
		
		panel2 = new JPanel(null);
		panel2.setVisible(true);
		frame.add(panel2);
		frame.setResizable(false);
		
		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new BoxLayout(pnl1, BoxLayout.PAGE_AXIS));
		pnl1.setBounds(0, 0, 300, 480);
		pnl1.setBackground(new Color(203, 42, 20));
		panel2.add(pnl1);
		
		JPanel pnl2 = new JPanel();
		pnl2.setLayout(null);
		pnl2.setBounds(300, 0, 500, 480);
		pnl2.setBackground(new Color(23, 42, 200));
		panel2.add(pnl2);
		
		lbl4 = new JLabel();
		if(user!=null) {
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
		btn3.addActionListener((e)->{
			if(currentConversation==null) {
				txtArea1.setText("Choose conversation!");
			} else if(txtArea2.getText().trim().equals("")) {
				System.out.println("Message cannot be empty!");
			} else {
				userService.sendMessage(txtArea2.getText(), user, currentConversation);
				System.out.println("Message '" + txtArea2.getText() + "' has been send!");
				txtArea2.setText("");
				updateConversation(txtArea1);
			}
			
		});
		pnl2.add(btn3);
		
		pnl2.revalidate();
		
		//pnl1
		lbl5 = new JLabel("FRIENDS");
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
				
				if(!e.getValueIsAdjusting()) {
				System.out.println("ZMIANA");
				//System.out.println(catchLogin(lst1.getSelectedValue()));
				currentConversation = userService.getConversationWithUser(user.getId(), userService.getUserbyLogin(catchLogin(lst1.getSelectedValue())).getId());
				System.out.println(currentConversation.getId());
				updateConversation(txtArea1);
				}
			}
			
		});
		System.out.println(lst1.getListSelectionListeners());
		pnl1.add(lst1);
		
		JButton btn4 = new JButton("Log out");
		btn4.addActionListener((e)->{
			user = null;
			currentConversation = null;
			frame.remove(panel2);
			createLoginPanel(frame, userService);
		});
		pnl1.add(btn4);
		
		panel2.revalidate();
		pnl2.revalidate();
		pnl1.revalidate();
		pnl1.repaint();
		pnl2.repaint();

	}
	
	public static String catchLogin(String label) {
		return label.split(" ")[3];
	}
	
	public static void updateConversation(JTextArea textArea) {
		List<Message> msgs = userService.getMessagesList(currentConversation.getId());
		textArea.setText("");
		for(Message m: msgs) {
			textArea.append(m.getUserId().getLogin() + " [" + m.getDate() + "]: " + m.getMessage() + "\n");
		}
	}
}
