import java.util.ArrayList;

/**
 * @author p-uppal
 * 
 *         This is a simple class that holds a meal that was taken from the web.
 *         It will contain at least a Recipe object and the name of the Meal.
 *         The only purpose of this is to keep the name and recipe together and
 *         add a little DRYness. Most functionality has been taken away from
 *         here.
 *
 */
public class Meal {

	String name;
	Recipe recipe;

	/**
	 * Constructor using the recipe name and a Recipe Object
	 * 
	 * @param name
	 * @param recipe
	 */
	public Meal(String name, Recipe recipe) {
		this.name = name;
		this.recipe = recipe;

	}

	/**
	 * This is a getter method to get a copy of the name of the meal because it is
	 * private
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * This is a getter method to get a copy of the recipe of the meal because it is
	 * private
	 * 
	 * @return
	 */
	public Recipe getRecipe() {
		return recipe;
	}

}
