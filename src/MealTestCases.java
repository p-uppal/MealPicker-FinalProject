import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class MealTestCases {

	private userDatabaseEntry userInput;
	private String username = "username";
	private String password = "password";
	private Recipe recipe;
	private ArrayList<String> ingredients;
	private ArrayList<String> recipeLines;
	private ArrayList<String> recipeLinesFull;
	private String strIng;
	private String strRec;
	private Meal meal;
	MealPicker mp = new MealPicker();
	HashMap<Meal, Integer> mealTime;
	HashMap<String, String> userDB;

	/**
	 * This tests Four Classes (Meal, Recipe, userDatabaseEntry, MealGUI). There are
	 * 15 total tests as request. We were warned against doing test cases for the
	 * GUI, and most of our other methods were void returning for which it would
	 * tough to test directly.
	 */
	@Before
	public void setup() {
		userInput = new userDatabaseEntry();
		userDB = new HashMap<String, String>();
		ingredients = new ArrayList<String>(Arrays.asList("banana", "cinnamon"));
		recipeLines = new ArrayList<String>(
				Arrays.asList("Banana Walnut Cinnamon Bread", "Cinnamon Chocolate-Chip Banana Pancakes"));
		recipeLinesFull = new ArrayList<String>(Arrays.asList(
				"Mix all of the dry ingredients in a large bowl and mix to combine. Next add the wet ingredients and mix till fairly smooth. Small lumps may still be remaining, this is okay. Once you're batter is prepared, prep the bananas into slices and have both bananas and chocolate chips on hand. Heat a frying pan over medium heat. Once the pan is heated, add about a 1/2 tablespoon of oil, heat, and then add the batter. Place four or five bananas slices in the batter and sprinkle with chocolate chips. After about 3-4 min. on one side, flip pancake and cook for about the same time till golden brown. Repeat and serve! Enjoy!"));
		recipe = new Recipe("1", ingredients, recipeLines);
		meal = new Meal("Banana Walnut Cinnamon Bread", recipe);
		mealTime = new HashMap<Meal, Integer>();

		userInput.registerNewUser("bobmarley", "music");

		userDB.put("bobmarley", "music");

	}

	/**
	 * Tests correct login
	 */
	@Test
	void testOneLogin() {
		setup();
		userInput.registerNewUser(username, password);
		int val = userInput.login(username, password);
		assertEquals(val, 1);

	}

	/**
	 * Tests wrong username in login
	 */
	@Test
	void testTwoLogin() {
		setup();
		String usernameWrong = "usernameWrong";
		int val = userInput.login(usernameWrong, password);
		assertEquals(val, 2);

	}

	/**
	 * Tests wrong password in login
	 */
	@Test
	void testZeroLogin() {
		setup();
		userInput.registerNewUser(username, password);
		String passwordWrong = "passwordWrong";
		int val = userInput.login(username, passwordWrong);
		assertEquals(val, 0);

	}

	/**
	 * Tests getFullRecipe and convert Arrays within Recipe
	 */
	@Test
	void recipeTestOne() {
		setup();
		String outputRecipe = recipe.getFullRecipe();

		String expected = "1. " + "Banana Walnut Cinnamon Bread" + "\n2. Cinnamon Chocolate-Chip Banana Pancakes\n";

		assertEquals(outputRecipe, expected);
	}

	/**
	 * Tests getFullIngredients
	 */
	@Test
	void recipeTestTwo() {
		setup();
		String outputIngredients = recipe.getFullIngredients();

		String expected = "banana\ncinnamon\n";

		assertEquals(outputIngredients, expected);

	}

	// Check if arraylists of ingredients are the same
	@Test
	void recipeTestThree() {
		setup();
		ArrayList<String> fullIngredients = recipe.getIngredients();
		assertEquals(fullIngredients, ingredients);

	}

	// check if arraylists of recipe lines are the same
	@Test
	void recipeTestFour() {
		setup();
		ArrayList<String> fullRecipe = recipe.getRecipeList();
		assertEquals(fullRecipe, recipeLines);

	}

	/**
	 * Tests getRecipeID
	 */
	@Test
	void recipeTestFive() {
		setup();
		String recipeIdentification = recipe.getRecipeID();
		assertEquals(recipeIdentification, "1");

	}

	/**
	 * tests Overriding toString function of Recipe
	 */
	@Test
	void recipeTestSix() {
		setup();

		String codeOutput = recipe.toString();
		String expected = "1" + "&" + "banana\ncinnamon\n" + "&" + "1. " + "Banana Walnut Cinnamon Bread"
				+ "\n2. Cinnamon Chocolate-Chip Banana Pancakes\n";

		assertEquals(codeOutput, expected);
	}

	/**
	 * Tests false case of registernewUser
	 */
	@Test
	void registerNewUserTest() {
		setup();

		boolean check = userInput.registerNewUser("bobmarley", "livelovelaugh");
		assertEquals(check, false);

	}

	/**
	 * Tests that new user was added with registerNewUser
	 */
	@Test
	void registerNewUserTestTwo() {
		setup();

		userInput.registerNewUser("arvind", "professor");
		boolean check2 = userInput.getUserInfo().containsKey("arvind");
		assertEquals(check2, true);

	}

	/**
	 * Tests Meal's getName function
	 */
	@Test
	void MealTest1() {
		setup();

		assertEquals(meal.getName(), "Banana Walnut Cinnamon Bread");
	}

	/**
	 * Tests meal's getRecipe function
	 */
	@Test
	void MealTest2() {
		setup();

		assertEquals(meal.getRecipe().toString(), recipe.toString());
	}

	/**
	 * tests database's getUserInfo function
	 */
	@Test
	void getUserInfoTestPassword() {
		setup();

		String password = userInput.getUserInfo().get("bobmarley");
		assertEquals(password, userDB.get("bobmarley"));
	}

	/**
	 * test's MealGUI's helper function showRecipe. the one that has an output
	 */
	@Test
	void mealGUIshowRecipeTest() {
		setup();
		String expected = "Banana Walnut Cinnamon Bread" + ": \n\nIngredients: \n" + "banana\ncinnamon\n"
				+ "\n\nInstructions: \n"
				+ "1. Banana Walnut Cinnamon Bread\n2. Cinnamon Chocolate-Chip Banana Pancakes\n";

		assertEquals(MealGUI.showRecipe(meal), expected);

	}

}
