import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author p-uppal
 * 
 * This is the main application building class. Running this class will run the entire project. 
 * It uses swing to build a JFrame with a multitude of other elements. The size of the application
 * is large in case you have a higher-res monitor. 
 *
 */
public class SwingGUI {
	private HashMap<String, String> users;
	private userDatabaseEntry userdb;
	private String name;
	private JFrame frame = new JFrame("Meal Picker");
	private JPanel cards;
	private JPanel login;
	private JPanel mealUI;
	private JPanel innerTop;
	private JPanel innerTop1;
	private JPanel innerTop2;
	private JPanel innerTop3;
	private JPanel innerBot;
	private GroupLayout gl;
	private CardLayout cl1;
//	private CardLayout cl2;
	//private MealGUI meal_gui;
	
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;
	private JLabel label;
	private JTextField field;
	private JLabel passLabel;
	private JPasswordField password;
	
	private JButton button1;
	private JButton button2;
	private JCheckBox chkbox;
	
	private JLabel messages;
	

	/**
	 * This is constructor for the program. It simply sets up the userdatabase, sets the "Look
	 * and Feel" and starts the GUI via startGUI(), which is the main application building
	 * method.
	 */
	public SwingGUI() {
		userdb = new userDatabaseEntry();
		users = userdb.getUserInfo();
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startGUI();
	}

	/**
	 * This is the integral method for the program. It creates the frame that
	 * will be used throughout the program. It calls passwordgui and mealgui when needed to login 
	 * and to get to the actual meal picking functionality.
	 * 
	 */
	private void startGUI() {
		setDefaultUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.PLAIN,30));
		//Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(new Dimension(1800,1000));
		frame.setLayout(new BorderLayout());
		cards = new JPanel();
		cl1 = new CardLayout();
		cards.setLayout(cl1);
		
		login = new JPanel(new GridLayout(2, 1));
		
		
		innerTop = new JPanel(new GridLayout(3, 1));
		innerTop1 = new JPanel(new FlowLayout());
		innerTop2 = new JPanel(new FlowLayout());
		innerTop3 = new JPanel(new FlowLayout());
		
		innerBot = new JPanel();
		gl = new GroupLayout(innerBot);
		innerBot.setLayout(gl);

		lbl1 = new JLabel("Welcome to MealPicker!");
		lbl1.setFont(new Font("Comic Sans MS",Font.BOLD,65));
		
		lbl3 = new JLabel("Created by Puneet Uppal and Rhea Kakar");
		lbl3.setFont(new Font("Serif",Font.ITALIC,25));
		
		lbl2 = new JLabel("Enter your name to continue!");
		lbl2.setFont(new Font("Serif",Font.PLAIN,35));
		
		label = new JLabel("Name:");
		field = new JTextField(15);
		passLabel = new JLabel("Password:");
		password = new JPasswordField(15);
		
		button1 = new JButton("Continue");
		button2 = new JButton("Exit");
		chkbox = new JCheckBox("New User",true);

		frame.add(cards,BorderLayout.CENTER);
		cards.add(login,"login page");
		
		//LOGIN CARD
		login.add(innerTop);
		innerTop.add(innerTop1);
		innerTop1.add(lbl1);

		innerTop.add(innerTop3);
		innerTop3.add(lbl3);
		
		innerTop.add(innerTop2);
		innerTop2.add(lbl2);

		login.add(innerBot);
		
		messages = new JLabel("");

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(label).addPreferredGap(RELATED)
				.addGroup(gl.createParallelGroup().addComponent(field).addComponent(chkbox).addComponent(messages))
				.addPreferredGap(UNRELATED)
				.addGroup(gl.createParallelGroup().addComponent(button1).addComponent(button2)));

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup().addComponent(label).addComponent(field).addComponent(button1))
				.addGroup(gl.createParallelGroup().addComponent(chkbox).addComponent(button2))
				.addComponent(messages));

		gl.setAutoCreateContainerGaps(true);

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = field.getText();
			}
		});
		
		name = field.getText();
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = field.getText();
				if (field.getText().isEmpty()) {
					lbl2.setText("Please enter your name first!");
				} else {
					if (!chkbox.isSelected()) {
						//passwordFrame(2);
						if (users.containsKey(name)) {
							passwordFrame(1);
						} else {
							lbl2.setText("That is not a known name! Did you meant to uncheck the New User Box?");
						}
						
					} else {
						int check = userdb.login(name, "");
						
						if (check == 1 || check == 0) {
							lbl2.setText("That username is already registered!");
						}
						else {
							passwordFrame(2);
						}
					}
				}
			}
		});
		
		//meal_gui = new MealGUI(frame);
		mealUI = new MealGUI(frame);
		
		//MEAL PICKER GUI CARD
		cards.add(mealUI,"mealUI");
		
		frame.pack();
		//frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * This method is what checks the passwords and end up calling the MealGUI maingui which will
	 * contain the bulk of the actual meal picking
	 * 
	 * @param type: an int, 1 if returning user; 2 if new User
	 */
	private void passwordFrame(int type) {
		chkbox.setVisible(false);
		
		if (type == 1) {
			lbl2.setText("Welcome back, " + name + "! Please enter your password.");
		} else {
			lbl2.setText("Please enter a password so we can remember you! Cannot use/be: '&' , ';' , ',', ' ', '' ");
		}
		
		

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup().addComponent(label).addComponent(passLabel))
				.addPreferredGap(RELATED)
				.addGroup(gl.createParallelGroup().addComponent(field).addComponent(password).addComponent(messages))
				.addPreferredGap(UNRELATED)
				.addGroup(gl.createParallelGroup().addComponent(button1).addComponent(button2)));

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup().addComponent(label).addComponent(field).addComponent(button1))
				.addGroup(gl.createParallelGroup().addComponent(passLabel).addComponent(password).addComponent(button2))
				.addComponent(messages));

		//the exit button for closing the window
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		name = field.getText();
		// The continue button for logging in
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (type == 1) {
					// ADD HELPER FUNCTION TO CHECK IF NAME IS VALID?
					System.out.println("I got here. Old User");
					if (name.isEmpty()) {
						messages.setText("Please re-enter your name first!");
					} else if ((new String(password.getPassword())).isEmpty()) {
						messages.setText("Please enter your password first!");
					} else {
						//Checks in password is correct
						System.out.println("Checking if password is correct");
						int check = userdb.login(name, new String(password.getPassword()));
						
						if (check == 1) {
							cl1.show(cards, "mealUI");
							((MealGUI) mealUI).mainGUI(userdb, name, 1);
						}
						else if (check == 0) {
							System.out.println("Password incorrect");
							printError("ERROR: Your password is not correct! Please try again");
						}
						else if (check == 2) {
							messages.setText("ERROR: That username is not registered! Please try again");
						}
						
						/*
						if (users.get(name).equals(new String(password.getPassword()))) {

							cl1.show(cards, "mealUI");
							((MealGUI) mealUI).mainGUI(userdb, name, 1);

						} else {
							lbl2.setText("ERROR: Your password is not correct! Please try again");
						}
						*/
					}
				} else {
					System.out.println("New User");
					//System.out.println("password: "+ (new String(password.getPassword())));
					if (name.isEmpty()) {
						System.out.println("name is empty");
						lbl2.setText("Please re-enter your name first!");
					} else if ((new String(password.getPassword())).trim()=="") {
						System.out.println("password field is empty!");
						
						lbl2.setText("Please enter your password first!");
					} else {
						int check = userdb.login(name, new String(password.getPassword()));
						
						if (check == 1 || check == 0) {
							lbl2.setText("That username is already registered!");
						}
						else {
						System.out.println("switching cards");
						userdb.registerNewUser(name,(new String(password.getPassword())).trim());
						cl1.show(cards, "mealUI");
						((MealGUI) mealUI).mainGUI(userdb, name, 2);
						}

					}
				}

			}

			private void printError(String string) {
				messages.setText(string);
				
			}
		});
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/**
	 * This help function simply allows us to manage and change the default font of the
	 * Swing application
	 * 
	 * @param f: resource to manage Font
	 */
	public static void setDefaultUIFont (javax.swing.plaf.FontUIResource f){

		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put (key, f);
		}
	}

	/**
	 * This main method runs the application
	 * @param args
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			SwingGUI ex = new SwingGUI();
		});
	}
}
