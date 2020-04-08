import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * 
 * 
 *         This is a Class that holds the data of a user for the program. It
 *         will store some information about the user and keep a MealHistory of
 *         them. This class also creates a user database containing the user's
 *         username and password on an output file.
 */

public class userDatabaseEntry {

	private HashMap<String, String> userInfoDB = new HashMap<String, String>();
	private HashMap<String, MealHistory> userHistoryDB = new HashMap<String, MealHistory>();

	File db = new File("UserDatabase.csv");

	/**
	 * This is a method for class that will construct an object instance from a
	 * username. It will prompt the user to provide a password for the account. It
	 * will then create empty MealHistory and ArrayList<String> objects.
	 * 
	 * @param userN
	 */

	public userDatabaseEntry() {
		try (Scanner s = new Scanner(db)) {
			while (s.hasNextLine()) {
				String temp = s.nextLine();
				if (temp.isEmpty()) {
					break;
				}
				else {
					String[] tempstore = temp.split(",");
					if (tempstore.length == 2) {
						userInfoDB.put(tempstore[0], tempstore[1]);
					}
					else { //if length == 3
						userInfoDB.put(tempstore[0], tempstore[1]);
						//userHistoryDB.put(tempstore[0], new MealHistory(tempstore[2]));
					}

				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("User database file is missing or has been tampered with!");
			e.printStackTrace();
		}

	}

	/**
	 * A getter function for the userdatabase with usernames and passwords
	 * 
	 * @return HashMap<String, String> of username, password
	 */
	public HashMap<String, String> getUserInfo() {
		return userInfoDB;
	}

	/**
	 * Deprecated getter function that returned the database for username
	 * and mealhistory.
	 * 
	 * @return  HashMap<String, MealHistory> of username, MealHistory of user
	 */
	public HashMap<String, MealHistory> getUserHistoryDB() {
		return userHistoryDB;
	}

	/**
	 * This method adds a new user to the database via the SwingGUI and calls a function
	 * to write the information to our file. It will only do this if the username does not
	 * already exist.
	 * 
	 * @param userN: string username to add
	 * @param passW: string password to add
	 * @return boolean: true if successful writing; false if the username already in database
	 */
	public boolean registerNewUser(String userN, String passW) {
		if (userInfoDB.containsKey(userN)) {
			return false;
		}
		else {
			userInfoDB.put(userN, passW);
			writeToFile();
			return true;
		}
		
		//userHistoryDB.put(userN, new MealHistory());
		
	}

	/**
	 * Writes new registered users to the existing database file. Uses filewriter and
	 * Printwriter to write to the local file: UserDatabase.csv 
	 */
	public void writeToFile() {
		
		try (FileWriter fw = new FileWriter(db); PrintWriter out = new PrintWriter(fw)) {
			if (checkHashMaps()) {
				for (String user : userInfoDB.keySet()) {

//					String entry = user + "," + userInfoDB.get(user) + "," + "\"" 
//							+ userHistoryDB.get(user).toString() + "\"";
					
					String entry = user + "," + userInfoDB.get(user) + "\n";

					out.print(entry);

				}
			} else {
				System.out.println("the history array size does not match the userdb");
			}

		} catch (IOException e) {
			System.out.println("User database file is missing or has been tampered with!");
			e.printStackTrace();
		}

	}

	/**
	 * This is a deprecated function from when we used MealHistory
	 * It made sure that the two arraylists are the same size
	 * 
	 * @return
	 */
	private boolean checkHashMaps() {
		//return userInfoDB.size() == userHistoryDB.size();
		return true;
	}

	/**
	 * Method to determine existing users.
	 * 
	 * @param userName - the user's inputted username
	 * @param password - the user's inputted password
	 * @return - either a 1, 0, or 2. If the username and password match the
	 *         database stored in the hashmap, method returns one. if the username
	 *         is not found in the hashmap, then return 0. If username and password
	 *         are correct and match the hashmap, return 2 and log in successfully.
	 */
	public int login(String userName, String password) {
		if (userInfoDB.containsKey(userName)) {
			if (userInfoDB.get(userName).equals(password)) {
				System.out.println("Login successful!");
				return 1;

			}
			System.out.println("Incorrect username or password. Please try again.");
			return 0;

		} else {
			System.out.println("User not registered. Please register and log in again.");
			return 2;
		}
	}
}
