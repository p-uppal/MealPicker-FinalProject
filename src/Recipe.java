import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

/**
 * 
 * 
 *         This Class serves to hold an individual recipe that is pulled from
 *         the internet. It contains an arraylist that holds all the lines of
 *         the recipe in question. It also contains an arraylist of the
 *         ingredients. FInally it has more manageable strings of these as well
 *
 */
public class Recipe {
	String fullIngredients;
	String fullRecipe;
	ArrayList<String> ingredients;
	ArrayList<String> recipeLines;
	String recipeID;
	MealPicker mp4;

	/**
	 * This first constructor is for when the API is called and the recipe lines are
	 * packaged into an arraylist. It will simply build the strings out of them with
	 * helper functions
	 * 
	 * @param id:          string id used by the API
	 * @param ingredients: arraylist of ingredients in the recipe
	 * @param recipeLines: arraylist of lines of the recipe
	 */
	public Recipe(String id, ArrayList<String> ingredients, ArrayList<String> recipeLines) {
		recipeID = id;
		this.recipeLines = recipeLines;
		this.ingredients = ingredients;
		convertArrays();
	}

	/**
	 * This second constructor is for when reading from the database file. It takes
	 * in strings instead of the api arraylists and then remakes arraylists out of
	 * them.
	 * 
	 * @param id:            string of the recipe id
	 * @param ingredientsIN: string ingredients that are separated by linespaces
	 * @param recipeIN:      string recipe lines that are separated by linespaces
	 */
	public Recipe(String id, String ingredientsIN, String recipeIN) {
		recipeID = id;
		fullIngredients = ingredientsIN;
		fullRecipe = recipeIN;
		packageArrays();
	}

	/**
	 * This is a private helper function that will convert the arraylists in the
	 * class into strings that can be more easily printed on the database file and
	 * in the GUi
	 * 
	 */
	private void convertArrays() {
		fullIngredients = "";
		fullRecipe = "";

		for (String ing : ingredients) {
			fullIngredients += ing + "\n";
		}

		int counter = 1;
		for (String recLine : recipeLines) {
			fullRecipe += counter + ". " + recLine + "\n";
			counter++;
		}
	}

	/**
	 * This private helper function takes in string from the database file and
	 * packages them into Arraylists mostly just so every Recipe instance has both
	 * arraylists and strings
	 */
	private void packageArrays() {
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> recipeLines = new ArrayList<String>();

		for (String ing : fullIngredients.split("\n")) {
			ingredients.add(ing);
		}

		for (String recLine : fullRecipe.split("\n")) {
			recipeLines.add(recLine);
		}
	}

	/**
	 * Getter that returns the arraylist recipe
	 * 
	 * @return recipe in arraylist form
	 */
	public ArrayList<String> getRecipeList() {
		return recipeLines;
	}

	/**
	 * Getter that return the arraylist ingredients
	 * 
	 * @return
	 */
	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	/**
	 * Getter that returns the \n-separated ingredients
	 * 
	 * @return string with line separated ingredients
	 */
	public String getFullIngredients() {
		return fullIngredients;
	}

	/**
	 * Getter that returns the line separated recipe
	 * 
	 * @return string with the line separated recipe
	 */
	public String getFullRecipe() {
		return fullRecipe;
	}

	/**
	 * Getter that returns the api's recipe ID
	 * 
	 * @return string of the ID
	 */
	public String getRecipeID() {
		return recipeID;
	}

	/**
	 * This is just an override of the Object method toString so that we can
	 * actually print it to the file and gui well
	 */
	@Override
	public String toString() {
		return recipeID + "&" + fullIngredients + "&" + fullRecipe;
	}
}
