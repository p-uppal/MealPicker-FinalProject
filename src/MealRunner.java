import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

/**
 * @author p-uppal
 * 
 *         This Class is the runner class for the Meal Picking Program. It is
 *         called in the GUI to run all the API stuff in choosing meal
 *         suggestions
 *
 */
public class MealRunner {
	private static MealPicker mp = new MealPicker();

	/**
	 * Overloaded function for if the user enters 2 ingredients. It simply calls
	 * MealPicker and handles the exceptions it can throw
	 * 
	 * @param ingredient1: string from user
	 * @param ingredient2: string from user
	 * @return arraylist of Meal to be shown to user
	 */
	public ArrayList<Meal> pullData(String ingredient1, String ingredient2) {
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		ArrayList<Meal> tenMeals = new ArrayList<Meal>();

		try {
			tenMeals = mp.getPickedMeals(ingredients);
		} catch (ParseException e) {
			System.out.println("Apologies, it seems the JSON readings have gone awry!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Apologies, it seems the access to the API is not working!");
			e.printStackTrace();
		}

		return tenMeals;
	}

	/**
	 * This overloaded function is if the user only enters 1 ingredient. Also calls
	 * MealPicker and handles the exceptions. All exceptions generally lead to
	 * nonfunction of the program though the GUI will remain open.
	 * 
	 * @param ingredient
	 * @return
	 */
	public /* static */ ArrayList<Meal> pullData(String ingredient) {
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add(ingredient);
		ArrayList<Meal> tenMeals = new ArrayList<Meal>();

		try {
			tenMeals = mp.getPickedMeals(ingredients);
		} catch (ParseException e) {
			System.out.println("Apologies, it seems the JSON readings have gone awry!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Apologies, it seems the access to the API is not working!");
			e.printStackTrace();
		}

		return tenMeals;
	}
}
