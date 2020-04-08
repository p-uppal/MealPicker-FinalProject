This is the Final Project of Puneet for CIT591. Our program is a MealPicker which gives users suggestions for meals based on a 1-2 inputted ingredients.

***********
HOW TO RUN:
***********

If you simply clone the program and run it in Eclipse, everything should be preloaded including the external libraries. If it is not, you will have to add the reference libraries in the "lib" folder of the repository first.

If you simply hit the Run button in the GUI files, a window will pop up prompted a username. If you have not made one previously, you can keep New User selected and enter your name. This will bring you to the next page where you will have to set a password. This information gets written to a file. If you are a returning user, it will instead check to make sure your information matches what is contained in the local file.

If all of this is successful, it will bring you to the MealGUI, where you can enter in one or two ingredients. Only one ingredient per textfield. After hitting search, it can take up to 20 seconds (average around 10-15) to call the API and load up 10 recipes on the page that use those ingredients. Then, you can pick one via the drop down menu and hit get Recipe. This will populate the page with the name, ingredients, and instructions for the recipe. You can use the buttons on the left to navigate to previous pages.

**************
TO THE USERS:
**************
1. We deprecated the use of MealHistory in our program. The class and much of the interwining code is still in the program. We kept it to show the work we had done on it. It was very hard to make sure that the recipes did not have characters that would throw off the writing in our file. It was not reliably writing to it correctly, so we removed the history functionality. This is the reason you will see commented out code referring to history.

2. There is a possibility that the API key free usage runs out during the testing! This will lead to a custom RuntimeException that you can see in the console. We just inserted a new API key before submitting, so it should be fine for the majority of it. 


3. We were warned against doing JUnit test cases for the GUI, and most of our other methods were void returning for which it would tough to test directly. So, our JUnit tests were basically most of the functions that returned something. There are 4 classes tested and 15 tests. All were successful as of the submission time. One test tests a private helper function in MealGUI, which was only made public for usage in the JUnit test file.

4. You cannot set an empty password. It also cannot contain "&", ",", ";" or leading/trailing spaces. 
Technically, the leading/trailing spaces is fine, but it will be trimmed off.
So, you would be able to enter the same account with the trimmed password, which is not good for security.

5. The API call takes about 10-15 seconds. It may look like nothing is happening, but something is. 

6. We could not find a good way to check if the ingredients entered are correctly spelled. 
We would have had to use a dictionary database or another api. So as it stands, it will not handle ingredients that it cannot find int the API. 
Just fixing the spelling to something within the API will make it work.

7. The way we handled exceptions is just to print things to the console. We had special messages 
based on what happened, but generally we did not push this up to the GUI level, 
as I imagine it is not something the User should see. 

8. The size of the application may seem large, but this is to slightly accomodate high resolution laptop screens. Its dimensions are 1800x1000.

