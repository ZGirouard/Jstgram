//Jstgram
//Zach Girouard and Dane Troia

package unl.soc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends PostFormatter {

	// Initialize Scanner
	public static final Scanner myScanner = new Scanner(System.in);
	public static String username = "";

	public static void main(String[] args) {
		// Initialize data from MySQL
		Database.performDatabaseOperations();

		// Send user to main window and have them log in
		mainWindow();
		boolean login = checkLogin();
		boolean mainWin = true;

		// main window
		while (mainWin) {

			// user successful logs in
			if (login == true) {

				// bring user to account window
				System.out.println("Successful Login!");
				boolean accountWin = true;

				// account window
				while (accountWin) {
					// get user account input
					String accountString = accountInput();

					// user chooses to go to post window
					if (accountString.equalsIgnoreCase("P")) {
						// Reload all data to add new posts
						Database.performDatabaseOperations();

						boolean postWin = true;

						// post window
						while (postWin) {
							// get user post input
							String postString = postInput();

							// user chooses to make a new post
							if (postString.equalsIgnoreCase("+")) {
								newPost();
								break;
							}

							// user goes back from post window
							else if (postString.equalsIgnoreCase("B")) {
								postWin = false;
								break;
							}
						}
					}

					// user chooses to go to postVis window
					else if (accountString.equalsIgnoreCase("V")) {
						Database.performDatabaseOperations();
						boolean postVisWin = true;

						// postVis window
						while (postVisWin) {
							// get user postVis Input
							String postVisString = postVisInput();

							// user chooses to add a new user to vis list
							if (postVisString.equalsIgnoreCase("+")) {
								Database.performDatabaseOperations();
								addVisibility();
								break;
							}

							// user chooses to delete a user from vis list
							else if (postVisString.equalsIgnoreCase("-")) {
								Database.performDatabaseOperations();
								removeVisibility();
								break;
							}

							// user goes back from post vis list
							else if (postVisString.equalsIgnoreCase("B")) {
								Database.performDatabaseOperations();
								postVisWin = false;
								break;
							}
						}
					}

					// user quits from account screen
					else if (accountString.equalsIgnoreCase("Q")) {
						System.out.println("Bye!");
						System.exit(0);
					}

					// user has invalid input on account screen
					else {
						System.out.println("Try Again...");
						accountString = accountInput();

					}
				}
			}

			// Make user try again until successful login
			else {
				System.out.println("Try again...");
				login = checkLogin();
			}
		}

	}

//check user login info against database
	public static boolean checkLogin() {
		System.out.println("Enter your username: ");
		username = myScanner.next();
		System.out.println("Enter your password: ");
		String password = myScanner.next();
		ArrayList<Account> accounts = Database.getAccounts();

		for (Account a : accounts) {
			if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

//get user account input from window
	public static String accountInput() {
		accountWindow();
		System.out.println("Enter Input: ");
		String account = myScanner.next();
		return account;
	}

	// get new visibility input from window
	public static void addVisibility() {
		ArrayList<Account> accounts = Database.getAccounts();
		ArrayList<Visibility> visibility = Database.getVisibilities();
		ArrayList<Account> account = Database.getAccounts();
		ArrayList<Integer> visibleUsers = new ArrayList<Integer>();
		ArrayList<String> visibleUsernames = new ArrayList<String>();
		boolean alreadyContains = false;
		int currentUserID = 1000;

		// get userID of current user
		for (Account a : account) {
			if (a.getUsername().equals(username)) {
				currentUserID = a.getUserID();
			}
		}

		// get visible userIDs of current user
		for (Visibility v : visibility) {
			if (currentUserID == v.getVisibleToUserID()) {
				visibleUsers.add(v.getUserID());
			}
		}

		// get visibleUsernames of current user
		for (Account a : account) {
			for (int i = 0; i < visibleUsers.size(); i++) {
				if (visibleUsers.get(i) == a.getUserID()) {
					visibleUsernames.add(a.getUsername());
				}
			}
		}
		
		
		System.out.println("Enter Name of User to Add:");
		String userToAdd = myScanner.next();
		
		for (int i=0; i < visibleUsernames.size(); i++) {
			String currentUser = visibleUsernames.get(i);
			if (currentUser.equals(userToAdd)){
				if (currentUser.equals(username)) {
					break;
				}
				else {
					alreadyContains = true;
					break;
				}
		}}
		
		for (Account a : accounts) {
			if (a.getUsername().equals(userToAdd)) {
				if (userToAdd.equals(username)) {
					System.out.println("User can't be you!");
					break;
				// } else if () { IMPLEMENT CHECK IF USER IS ALREADY IN VISIBILITY LIST
				}
				
				else if (alreadyContains == true) {
					System.out.println("User already in list!");
					break;
				}
				
				else {
					try {
						Database.newVisibilityUser(userToAdd, username);
						break;
					} catch (SQLException e) {
						// Handle the exception or log the error
						e.printStackTrace();
					}}
			}}}

	// get remove visibility input from window
	public static void removeVisibility() {
		ArrayList<Account> accounts = Database.getAccounts();

		System.out.println("Enter Name of User to Remove:");
		String userToRemove = myScanner.next();

		for (Account a : accounts) {
			if (a.getUsername().equals(userToRemove)) {
				if (userToRemove.equals(username)) {
					System.out.println("User can't be you!");
					break;
					// } else if () { IMPLEMENT CHECK IF USER IS ALREADY IN VISIBILITY LIST

				} else {
					try {
						Database.removeVisibilityUser(userToRemove, username);
						break;
					} catch (SQLException e) {
						// Handle the exception or log the error
						e.printStackTrace();
					}
				}
			}
		}

	}

	// get new post input from window
	public static void newPost() {
		try {
			System.out.println("Enter New Post Text: ");

			// Make new scanner to capture entire line
			Scanner s = new Scanner(System.in);
			String newPostText = s.nextLine();

			Database.newPostToDatabase(newPostText, username);

		} catch (SQLException e) {
			// Handle the exception or log the error
			e.printStackTrace();
		}
	}

//get user post input from window
	public static String postInput() {
		postWindow();
		System.out.println("Enter Input: ");
		String post = myScanner.next();
		return post;
	}

//get user postVis input from window
	public static String postVisInput() {
		postVisWindow();
		System.out.println("Enter Input: ");
		String postVis = myScanner.next();
		return postVis;
	}

//format user and text output information for visible users
	public static void postWindow() {
		ArrayList<Post> post = Database.getPosts();
		ArrayList<Visibility> visibility = Database.getVisibilities();
		ArrayList<Account> account = Database.getAccounts();
		ArrayList<Integer> visibleUsers = new ArrayList<Integer>();
		ArrayList<String> visibleUsernames = new ArrayList<String>();
		ArrayList<String> postTexts = new ArrayList<String>();
		ArrayList<String> postTimes = new ArrayList<String>();
		ArrayList<String> postAuthors = new ArrayList<String>();

		int currentUserID = 1000;

		// get userID of current user
		for (Account a : account) {
			if (a.getUsername().equals(username)) {
				currentUserID = a.getUserID();
			}
		}

		// get visible userIDs of current user
		for (Visibility v : visibility) {
			if (currentUserID == v.getVisibleToUserID()) {
				visibleUsers.add(v.getUserID());
			}
		}

		// get visibleUsernames of current user
		for (Account a : account) {
			for (int i = 0; i < visibleUsers.size(); i++) {
				if (visibleUsers.get(i) == a.getUserID()) {
					visibleUsernames.add(a.getUsername());
				}
			}
		}

		// get postTexts, times, and authors for visible users
		for (Post p : post) {
			for (int i = 0; i < visibleUsernames.size(); i++) {
				if (visibleUsernames.get(i).equals(p.getAuthor())) {
					postTexts.add(p.getPostText());
					postTimes.add(p.getPostTime());
					postAuthors.add(p.getAuthor());
				}
			}
		}
		

		// print out information for textPosts
		System.out.println(" ========================================");
		for (int i = 0; i < postTexts.size(); i++) {
			System.out.print(getFormattedContent(postTexts.get(i)));
			System.out.print(tag(postTimes.get(i), postAuthors.get(i)));
		}
		System.out.println("| (+) Publish a new post              	 |");
		System.out.println("| (B) Back         			 |");
		System.out.println(" ========================================");
	}

//main window graphic
	public static void mainWindow() {
		System.out.println(" ========================================");
		System.out.println("|         Welcome to Jstgram 2.0!        |");
		System.out.println("|                                        |");
		System.out.println("|              *************             |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|                    *                   |");
		System.out.println("|              *     *                   |");
		System.out.println("|              *******                   |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("| Current number of users in database: " + Account.getObjectCount() + " |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println(" ========================================");
	}

//account window graphic
	public static void accountWindow() {
		System.out.println(" ========================================");
		System.out.println("|                                        |");
		System.out.println("|  (P) Posts         			 |");
		System.out.println("|  (V) Post Visibility     		 |");
		System.out.println("|  (Q) Quit         			 |");
		System.out.println("|                                        |");
		System.out.println("|                                        |");
		System.out.println("|   Current user : " + username + "            	 |");
		System.out.println(" ========================================");
	}

//postVis Window graphic
	public static void postVisWindow() {
		ArrayList<Visibility> visibility = Database.getVisibilities();
		ArrayList<Account> account = Database.getAccounts();
		ArrayList<Integer> visibleUsers = new ArrayList<Integer>();
		ArrayList<String> visibleUsernames = new ArrayList<String>();
		int currentUserID = 1000;

		// get userID of current user
		for (Account a : account) {
			if (a.getUsername().equals(username)) {
				currentUserID = a.getUserID();
			}
		}

		// get visible userIDs of current user
		for (Visibility v : visibility) {
			if (v.getUserID() == currentUserID) {
				visibleUsers.add(v.getVisibleToUserID());
			}
		}

		// get visibleUsernames of current user
		for (Account a : account) {
			for (int i = 0; i < visibleUsers.size(); i++) {
				if (visibleUsers.get(i) == a.getUserID()) {
					visibleUsernames.add(a.getUsername());
				}
			}
		}

		System.out.println(" =============================================");
		System.out.println("| My posts are visible to the following users |");
		System.out.println("|                                             |");
		for (int i = 0; i < visibleUsernames.size(); i++) {
			String currentUsername = visibleUsernames.get(i);
			if (currentUsername.equals(username)) {
				continue;
			} else {
				System.out.printf("| %-44s|" + System.lineSeparator(), currentUsername);
			}
		}
		System.out.println("|                                             |");
		System.out.println("| (+) Add a user         		      |");
		System.out.println("| (-) Delete a user              	      |");
		System.out.println("| (B) Back         			      |");
		System.out.println("|                                             |");
		System.out.println("|   Current user : " + username + "            	      |");
		System.out.println(" =============================================");
	}
}
