import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static javax.swing.LayoutStyle.ComponentPlacement.UNRELATED;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author p-uppal
 * 
 *         This class is the Meal gui portion of the swing gui. Basically it is
 *         where all the meal suggestions/picking and recipe viewing occurs. It
 *         is after you have logged into the program.
 *
 */
public class MealGUI extends JPanel {
	private CardLayout cl2;
	// private JPanel mealUI;
	private static JPanel menu;
	private static JPanel viewer;

	private static JButton find;
	private static JButton list;
	private static JButton current;
	private static JButton history;
	private static JButton exit;

	private static JPanel ingredients;
	private static JLabel ingLabel1;
	private static JLabel ingLabel2;
	private GroupLayout gl3;
	private static JPanel listOfMeals;
	private static JPanel recipe;
	private static JPanel historyView;

	private static JTextArea hometext;
	private static JTextArea mealListintro;
	private static JTextArea recipe_area;
	private static JScrollPane recipeScroll;
	private static JButton save;
	private static JTextArea mealList_area;
	private static JComboBox<String> mealList;
	private static JButton getRecipe;
	private static JTextArea history_area;
	private static JScrollPane historyScroll;

	private static JButton search;

	private static JTextField ing1;
	private static JTextField ing2;

	private static int recipeChoice;

	private static ArrayList<Meal> meals = new ArrayList<Meal>();
	private static userDatabaseEntry userdb;
	private static String currentUser;

	/**
	 * The constructor that sets up the given frame with new elements to show. Has a
	 * large viewer for the recipes and everything, and buttons on the left to
	 * navigate. Data is only saved if you click on the exit button after saving.
	 * 
	 * @param frame: Jframe from the SwingGUI class
	 */
	public MealGUI(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		// this.setVisible(false);

		viewer = new JPanel();
		setLayout(new FlowLayout());
		menu = new JPanel(new GridLayout(10, 1));
		viewer = new JPanel();
		find = new JButton("Find a meal");
		list = new JButton("List of Meals");
		current = new JButton("Current Meal");
		history = new JButton("Meal History");
		exit = new JButton("Exit");

		ingredients = new JPanel();
		listOfMeals = new JPanel();
		recipe = new JPanel();
		historyView = new JPanel();
		search = new JButton("Search for Meals...");

		ingLabel1 = new JLabel("Ingredient 1: ");
		ingLabel2 = new JLabel("Ingredient 2: ");

		ing1 = new JTextField(15);
		ing2 = new JTextField(15);

		add(menu);

		menu.add(find);
		menu.add(list);
		menu.add(current);
		//menu.add(history);
		menu.add(new JLabel());
		menu.add(new JLabel());
		menu.add(new JLabel());
		menu.add(new JLabel());
		menu.add(new JLabel());
		menu.add(exit);

		cl2 = new CardLayout();
		viewer.setLayout(cl2);
		add(viewer);

		setIngredientsPage();
		setMealListPage();
		setRecipePage();
		//setHistoryPage();

		viewer.add(ingredients, "ingredients");
		viewer.add(listOfMeals, "listmeals");
		viewer.add(recipe, "recipe");
		//viewer.add(historyView, "historyView");

	}

	/**
	 * This helper just sets up one of the cards of viewer Jpanel. It is where you
	 * will see the list of meals, a drop down menu to choose one, and the search
	 * button
	 * 
	 */
	private void setMealListPage() {
		listOfMeals.setLayout(new FlowLayout());

		String[] mealNums = { "Select Meal", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		mealList = new JComboBox<String>(mealNums);
		mealList.setSelectedIndex(0);
		mealList.setEditable(false);

		mealListintro = new JTextArea(3, 60);
		mealListintro.setText("Here are 10 possible meals with those ingredients. \n"
				+ "Please choose one of them in the drop down menu.\n");

		mealListintro.setEditable(false);
		mealListintro.setLineWrap(true);
		mealListintro.setWrapStyleWord(true);

		getRecipe = new JButton("Get Recipe!");

		mealList_area = new JTextArea(11, 50);
		mealList_area.setEditable(false);
		mealList_area.setLineWrap(true);
		mealList_area.setWrapStyleWord(true);

		listOfMeals.add(mealList_area);
		listOfMeals.add(mealList);
		listOfMeals.add(getRecipe);
	}

	/**
	 * This is a deprecated function!
	 * It sets the historyView card in viewer's cardlayout. It shows the past
	 * meals that were saved by the user (which get saved into a local file)
	 */
	private void setHistoryPage() {
		history_area = new JTextArea("Past Recipes: \n\n");
		historyView.setLayout(new FlowLayout());
		historyView.add(history_area);

		historyScroll = new JScrollPane(history_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		historyScroll.setPreferredSize(new Dimension(1200, 1000));
		JScrollBar vertBar2 = historyScroll.getVerticalScrollBar();
		vertBar2.setPreferredSize(new Dimension(40, 0));



		history_area.setEditable(false);
		history_area.setLineWrap(true);
		history_area.setWrapStyleWord(true);

		historyView.add(historyScroll);

	}
	
	/**
	 * This is a deprecated function!
	 * It updates the history page if the user has a meal history
	 * 
	 * @param type
	 */
	private void updateHistPage(int type) {
		String past = "";
		
		//HashMap<String, MealHistory> hist = userdb.getUserHistoryDB();
		//ArrayList<Meal> pastm;
		
		if (type == 1) {
			if (!userdb.getUserHistoryDB().get(currentUser).getPastMeals().isEmpty()) {
				for (Meal m : userdb.getUserHistoryDB().get(currentUser).getPastMeals()) {
					past += showRecipe(m) + "\n\n";
				}
			}
		}

		history_area.setText(past);
	}

	/**
	 * This sets up the recipe card of the viewer's cardlayout It shows the chosen
	 * recipe
	 */
	private void setRecipePage() {
		recipe_area = new JTextArea(3, 60);
		recipeScroll = new JScrollPane(recipe_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		recipeScroll.setPreferredSize(new Dimension(1200, 900));
		JScrollBar vertBar = recipeScroll.getVerticalScrollBar();
		vertBar.setPreferredSize(new Dimension(40, 0));

		save = new JButton("Save this Recipe!");

		recipe_area.setEditable(false);
		recipe_area.setLineWrap(true);
		recipe_area.setWrapStyleWord(true);

		recipe.setLayout(new FlowLayout());
		recipe.add(recipeScroll);
		//recipe.add(save);
	}

	/**
	 * This sets up the ingredient card of the viewer cardlayout It is the home page
	 * of the program where you can enter in the ingredients you would like to
	 * search for. It uses a grouplayout setup.
	 * 
	 */
	private void setIngredientsPage() {
		gl3 = new GroupLayout(ingredients);
		ingredients.setLayout(gl3);
		ingLabel1 = new JLabel("Ingredient 1: ");
		ingLabel2 = new JLabel("Ingredient 2: ");

		hometext = new JTextArea(
				"Welcome to the Meal Picker App! \nEnter in some ingredients, and we'll find some good potential recipes for you! \n"
						+ "You can enter One or Two ingredients!",
				4, 50);

		hometext.setEditable(false);
		hometext.setLineWrap(true);
		hometext.setWrapStyleWord(true);

		gl3.setHorizontalGroup(gl3.createSequentialGroup()
				.addGroup(gl3.createParallelGroup().addComponent(ingLabel1).addComponent(ingLabel2))
				.addGroup(gl3.createParallelGroup().addComponent(hometext).addComponent(ing1).addComponent(ing2))
				// .addPreferredGap(RELATED)
				.addComponent(search));

		gl3.setVerticalGroup(gl3.createSequentialGroup().addComponent(hometext)
				.addGroup(gl3.createParallelGroup().addComponent(ingLabel1).addComponent(ing1).addComponent(search))
				.addGroup(gl3.createParallelGroup().addComponent(ingLabel2).addComponent(ing2)));

	}

	/**
	 * This is the main GUI that gets called from swing gui. It is where all the
	 * actionlisteners for the various buttons and menus reside. This is where
	 * MealRunner will be called as well. It holds the functionality for the left
	 * pannel buttons to move around the program. Most importantly, the program will
	 * only save data if the user hits the Exit button
	 * 
	 * @param userdb: userDatabaseEntry that holds all the different users data
	 * @param user:   the current user after login
	 */
	public void mainGUI(userDatabaseEntry userdb, String user, int type) {
		this.userdb = userdb;
		this.currentUser = user;
		// frame.setVisible(false);
		cl2.show(viewer, "ingredients");
		
		//updateHistPage(type);

		// actionlistener for go button
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (ing1.getText().trim().isEmpty() && ing2.getText().trim().isEmpty()) {
					hometext.append("\nPlease enter at least one ingredient!");
				} else {

					System.out.println(
							"Calling the API for meal suggestions. Can take a few moments ");
					hometext.append("\nHold on, it can take a few moments to pull the recipes from the API!");

					// !!!!!!!!!! NEED TO CHECK IF THE INGREDIENTS ARE REAL !!!!!!!!! How?
					MealRunner mr = new MealRunner();
					if (ing1.getText().trim().isEmpty()) {
						meals = mr.pullData(ing2.getText().trim());
					} else if (ing2.getText().trim().isEmpty()) {
						meals = mr.pullData(ing1.getText().trim());
					} else {
						meals = mr.pullData(ing1.getText().trim(), ing2.getText().trim());
					}
					cl2.show(viewer, "listmeals");
					showNames();

				}
			}
		});

		// Action listener for Search button
		getRecipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = (Integer) mealList.getSelectedIndex();
				if (choice == 0) {
					mealListintro.append("Again, please select a choice from the dropdown menu first...");
				} else {
					cl2.show(viewer, "recipe");
					recipeChoice = choice;
					showRecipe();
				}
			}
		});

		// Action Listener for save recipe button
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userdb.getUserHistoryDB().get(currentUser).addMeal(meals.get((recipeChoice - 1)));
			}
		});

		// Action listener for left button to return to ingredient page
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(viewer, "ingredients");
			}
		});

		// Action listener for left button to return to searched list page
		list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(viewer, "listmeals");
			}
		});

		// Action listener for left button to return to recipe page
		current.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(viewer, "recipe");
			}
		});

		// Action listener for left button to return to history page
		history.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl2.show(viewer, "historyView");
			}
		});

		// Action listener for left button to write data to file and exit
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//userdb.writeToFile();
				System.exit(0);
			}
		});

	}

	/**
	 * This is a helper program to write up the meal names in an aesthetic fashion
	 * to be printed to the GUI
	 */
	private void showNames() {
		String text = "Here are 10 possible meals with those ingredients: \n";
		for (int i = 0; i < meals.size(); i++) {
			text += "\n" + (i + 1) + ". " + meals.get(i).getName() + "\n";
		}
		mealList_area.setText(text);
	}

	/**
	 * This is the helper function called by mainGUI to print the chosen recipe to
	 * the GUI in an aesthetic way
	 */
	private void showRecipe() {
		String recipe = "Recipe for " + meals.get(recipeChoice - 1).getName() + ": \n\nIngredients: \n";

		recipe += meals.get(recipeChoice - 1).getRecipe().getFullIngredients() + "\n\nInstructions: \n"
				+ meals.get(recipeChoice - 1).getRecipe().getFullRecipe();

		recipe_area.setText(recipe + "\n\nEnjoy!!");
	}

	/**
	 * This is the overloaded helper function that is called by Historyview to print
	 * all past recipes to a scrollable text area on that page. It cannot use the
	 * previous function because that is just for the current chosen function.
	 * 
	 * THE ONLY REASON THIS IS PUBLIC IS FOR THE JUNIT TESTS
	 * 
	 * @param m: a Meal object from pastMeals in mealhistory
	 * @return string of the recipe preloaded with line spaces and numbering
	 */
	public static String showRecipe(Meal m) {
		String recipe = m.getName() + ": \n\nIngredients: \n";

		recipe += m.getRecipe().getFullIngredients() + "\n\nInstructions: \n" + m.getRecipe().getFullRecipe();

		return recipe;
	}
}
