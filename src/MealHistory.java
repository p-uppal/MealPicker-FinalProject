import java.util.ArrayList;

/**
 * @author P-uppal
 * 
 *         THIS CLASS IS DEPRECATED. WE DO NOT USE IT ANYMORE IN OUR PROGRAM.
 *         WE HAVE ONLY KEPT IT TO SHOW THE WORK THAT HAS BEEN DONE WITH IT.
 *         
 *         This class will serve to keep a record of the past ingredients that
 *         the user provided and the meals that they decided to go with.
 *
 */
public class MealHistory {

	ArrayList<Meal> pastMeals = new ArrayList<Meal>();

	/**
	 * General constructor for the class. Used when a new user is added
	 */
	public MealHistory() {
		//pastMeals = new ArrayList<Meal>();
	}

	/**
	 * This is another constructor for MealHistory. There should only be one object
	 * for every user. This is used when reading from the database file
	 */
	public MealHistory(String csvMeals) {
		buildMealHistory(csvMeals);
	}

	/**
	 * @param csvMeals
	 */
	private void buildMealHistory(String csvMeals) {
		pastMeals = new ArrayList<Meal>();
		String[] commaSep = csvMeals.split(",");
		for (int i = 1; i < commaSep.length; i++) {
			String cell = commaSep[i];
			if (cell.contains(";")) {
				String[] semiColonSep = cell.split(";");
				String[] ampersandSep = semiColonSep[1].split("&");
				System.out.println(ampersandSep[0] + "\n");
				System.out.println(ampersandSep[1] + "\n");
				System.out.println(ampersandSep[2] + "\n");
				pastMeals.add(new Meal(semiColonSep[0], new Recipe(ampersandSep[0], ampersandSep[1], ampersandSep[2])));
			} else {
				System.out.println("Something went wrong in buildMealHistory");
			}
		}
	}

	/**
	 * This is a simple "setter" class that will add meals to the history after the
	 * user chooses something.
	 * 
	 * @param m: a Meal object with the meal the user chose
	 */
	public void addMeal(Meal m) {
		pastMeals.add(m);
	}

	/**
	 * @return
	 */
	public ArrayList<Meal> getPastMeals() {
		return pastMeals;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		String output = "";
		for (Meal m : pastMeals) {
			output += "," + m.getName() + ";" + m.getRecipe().toString();
		}
		return output;
	}
}
