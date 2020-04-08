import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author p-uppal
 * 
 *         This is the main MealPicker class that will do the job of matching
 *         ingredients to meals and providing a list of several meals that the
 *         user can choose from. It does this by using the Spoonacular API, for
 *         which we have the most basic Free plan. As such, it is limited to 150
 *         calls per day.
 *
 */
public class MealPicker {

	private static final String API_END_POINT = "https://api.spoonacular.com/recipes/";
	//private static final String API_KEY = "3efeec0e9b4e4e009fd8f3b6090d9c56";
	//private static final String API_KEY = "4bd762f969e9404cb3565f0f6eeae74d";
	private static final String API_KEY = "04c89ffb5e3c426496e14efecdaa2904";
	
	/**
	 * This function manages and uses all the other functions in the class. It
	 * serves to make all the calls, get the data, and package them into data
	 * structure that will inevitably be packaged into an ArrayList of Meal to be
	 * sent back to MealRunner
	 * 
	 * @param ingredients: arraylist input from the user from Gui
	 * @return arraylist of Meal for the suggested options based on the ingredients
	 * @throws ParseException: from readers called in the helper functions
	 * @throws IOException:    from api calls in helper functions
	 */
	public ArrayList<Meal> getPickedMeals(ArrayList<String> ingredients) throws ParseException, IOException {
		ArrayList<Meal> pickedMeals = new ArrayList<Meal>();
		HashMap<String, String> results = packageMeals(getApiData(ingredients));

		for (String key : results.keySet()) {
			String id = results.get(key);
			pickedMeals.add(new Meal(key,
					new Recipe(id, packageIngredients(getApiData(id, 2)), packageRecipe(getApiData(id, 1)))));
		}

		//System.out.println(pickedMeals.toString());
		return pickedMeals;
	}

	/**
	 * This overloaded function is for the recipe and ingredient API calls. It uses
	 * helper functions to make the call and get the json data in. It is different
	 * from the next overloaded method, as the inputs are different
	 * 
	 * @param id:   string id of the recipe in question
	 * @param type: 1 for recipe call; 2 for ingredient call
	 * @return String of the jsondata which will be packaged into better data
	 *         structures later
	 * @throws IOException:    carried from the httpresponse
	 * @throws ParseException: thrown if the reader cannot read the inputstream
	 *                         correctly
	 */
	private static String getApiData(ArrayList<String> ingredients) throws IOException, ParseException {
		HttpResponse response = makeHttpRequest(ingredients);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder myOutput = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			myOutput.append(line + "\n");
		}

		return myOutput.toString();
	}

	/**
	 * This overloaded function is for the recipe and ingredient API calls. It is
	 * different from the previous method, as the inputs are different
	 * 
	 * @param id:   string id of the recipe in question
	 * @param type: 1 for recipe call; 2 for ingredient call
	 * @return String of the jsondata which will be packaged into better data
	 *         structures later
	 * @throws IOException:    carried from the httpresponse
	 * @throws ParseException: thrown if the reader cannot read the inputstream
	 *                         correctly
	 */
	private static String getApiData(String id, int type) throws IOException, ParseException {
		HttpResponse response = makeHttpRequest(id, type);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuilder myOutput = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			myOutput.append(line + "\n");
		}
		// System.out.println(myOutput.toString());
		return myOutput.toString();
	}

	/**
	 * This is one of the private HttpRequest functions that will handle api call to
	 * get the meal names.
	 * 
	 * @param id: string that contains the ID for the recipe in question
	 * @return HttpResponse that will be converted to a more workable json format
	 * @throws IOException: thrown if the link does not call the API correctly
	 */
	private static HttpResponse makeHttpRequest(ArrayList<String> ingredients) throws IOException {
		StringBuffer ingredientBuffer = new StringBuffer();

		for (String ingredient : ingredients) {
			if (ingredientBuffer.length() != 0) {
				ingredientBuffer.append(",+");
			}
			ingredientBuffer.append(ingredient);
		}
		HttpClient client = HttpClientBuilder.create().build();
		String query = "apiKey=" + API_KEY + "&ingredients=" + ingredientBuffer.toString() + "&number=10";
		String link = API_END_POINT + "findByIngredients?" + query;
		
		System.out.println(link);
		
		HttpGet request = new HttpGet(link);
		return client.execute(request);
	}

	/**
	 * This is one of the private HttpRequest functions that will handle the recipe
	 * call and the ingredient call. It is what puts together the link and calls the
	 * API. Could not package all three calls in one function because the links were
	 * too different, plus I wanted to try some polymorphism
	 * 
	 * @param id:   string that contains the ID for the recipe in question
	 * @param type: int which is 1 for recipe call and 2 for ingredient call
	 * @return HttpResponse that will be converted to a more workable json format
	 * @throws IOException: thrown if the link does not call the API correctly
	 */
	private static HttpResponse makeHttpRequest(String id, int type) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();

		String link = API_END_POINT + id + (type == 1 ? "/analyzedInstructions?" : "/information?") + "apiKey="
				+ API_KEY;

		// System.out.println(link);
		HttpGet request = new HttpGet(link);
		return client.execute(request);
	}

	/**
	 * This private helper method packages the jsondata from the API call into a
	 * manageable ArrayList of the meal recipes
	 * 
	 * @param jsondata: string with the jsondata for the recipe
	 * @return Arraylist of lines of the recipe
	 * @throws ParseException: if the jsondata is incorrect or not what is expected
	 */
	private static ArrayList<String> packageRecipe(String jsondata) throws ParseException {
		ArrayList<String> recipeData = new ArrayList<String>();

		JSONParser parsed = new JSONParser();
		JSONArray parsedSteps = (JSONArray) parsed.parse(jsondata.toString());

		for (Object obj : parsedSteps) {
			if (obj instanceof JSONObject) {
				JSONObject myJsonArr = (JSONObject) obj;
				JSONArray myJson = (JSONArray) myJsonArr.get("steps");
				for (Object obj2 : myJson) {
					if (obj2 instanceof JSONObject) {
						JSONObject myJson2 = (JSONObject) obj2;
						recipeData.add(myJson2.get("step").toString());
					}
				}
			}
		}
		return recipeData;
	}

	/**
	 * This private helper method packages the jsondata from the API call into a
	 * manageable HashMap of String,String : Name,ID for the meals. Will later be
	 * converted to Meal
	 * 
	 * @param jsondata: string with the jsondata for the meal names
	 * @return HashMap of name, id
	 * @throws ParseException: if the jsondata is incorrect or not what is expected
	 */
	private static HashMap<String, String> packageMeals(String jsondata) throws ParseException {
		HashMap<String, String> mealData = new HashMap<String, String>();
		if ((new JSONParser().parse(jsondata)) instanceof JSONObject) {
			if (((JSONObject) new JSONParser().parse(jsondata)).get("status").toString().equals("failure")) {
				System.out.println("We have used up the free API calls for the day");
				System.err.println("We have used up the free API calls for the day");
				throw new RuntimeException("We have used up the free API calls for the day");
			}
		}
		JSONArray parse = (JSONArray) new JSONParser().parse(jsondata);

		for (Object recipe : parse) {
			if (recipe instanceof JSONObject) {
				JSONObject myJsonRecipe = (JSONObject) recipe;

				Object title = myJsonRecipe.get("title");
				Object id = myJsonRecipe.get("id");
				mealData.put(title.toString(), id.toString());
			}
		}

		return mealData;
	}

	/**
	 * This private helper method packages the jsondata from the API call into a
	 * manageable ArrayList of the the ingredients
	 * 
	 * @param jsondata: string with the jsondata for the ingredients
	 * @return Arraylist of Ingredients
	 * @throws ParseException: if the jsondata is incorrect or not what is expected
	 */
	private static ArrayList<String> packageIngredients(String jsondata) throws ParseException {
		ArrayList<String> ingData = new ArrayList<String>();
		JSONObject parse = (JSONObject) new JSONParser().parse(jsondata);
		JSONArray ingredientjson = (JSONArray) parse.get("extendedIngredients");

		for (Object ingredient : ingredientjson) {
			if (ingredient instanceof JSONObject) {
				JSONObject myJsonIng = (JSONObject) ingredient;

				JSONObject amount = (JSONObject) ((JSONObject) myJsonIng.get("measures")).get("us");
				String unitAmt = (amount.get("amount").toString()) + " " + ((String) amount.get("unitShort"));
				String name = (String) myJsonIng.get("originalName");
				ingData.add(unitAmt + " " + name);
			}
		}
		return ingData;
	}

}
