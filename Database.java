package unl.soc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	// JDBC driver parameters
	// user must insert their own hostname, username, and password for their sql server
	public final static String hostname = "hostName";
	public final static String username = "username";
	public final static String password = "password";
	public final static String url = "jdbc:mysql://" + hostname + "/" + username;

	// Establish a connection to the database
	public static Connection connect() throws SQLException {
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish the connection
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			throw new SQLException("MySQL JDBC driver not found", e);
		}
	}

	// Create ArrayLists for each Object and their respective getters
	static ArrayList<Account> accounts = new ArrayList<>();

	public static ArrayList<Account> getAccounts() {
		return accounts;
	}

	static ArrayList<Post> posts = new ArrayList<>();

	public static ArrayList<Post> getPosts() {
		return posts;
	}

	static ArrayList<Visibility> visibilities = new ArrayList<>();

	public static ArrayList<Visibility> getVisibilities() {
		return visibilities;
	}

	// Method for adding a new post to the Post database table
	public static void newPostToDatabase(String postText, String author) throws SQLException {
		// Define the SQL query
		String sql = "INSERT INTO Post (postId, postText, postTime, author) VALUES (?, ?, NOW(), ?)";
		try (Connection connection = connect()) {
			int postId = Post.getObjectCount() + 1;

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				// Set the values for the parameters
				statement.setInt(1, postId);
				statement.setString(2, postText);
				statement.setString(3, author);

				// Execute the query
				statement.executeUpdate();
				System.out.println("New Post Added Successfully!");
			}
		}
	}

	// Method to add a user to the visibility list
	public static void newVisibilityUser(String userToAdd, String username) throws SQLException {
		// Define the SQL query
		String sql = "INSERT INTO Visibility (visibilityId, userId, visibleToUserId) VALUES (?, ?, ?)";
		try (Connection connection = connect()) {
			int visibilityId = Visibility.getObjectCount() + 1;
			int userId = 0;
			int userAddId = 0;

			// Loop to retrieve proper IDs for user and userToAdd
			for (Account a : accounts) {
				if (a.getUsername().equals(username)) {
					userId = a.getUserID();
				} else if (a.getUsername().equals(userToAdd)) {
					userAddId = a.getUserID();
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				// set the values for the parameters
				statement.setInt(1, visibilityId);
				statement.setInt(2, userId);
				statement.setInt(3, userAddId);

				// Execute the query
				statement.executeUpdate();
				System.out.println("New User Added Successfully!");
			}
		}
	}

	// Method to remove a user from the visibility list
	public static void removeVisibilityUser(String userToRemove, String username) throws SQLException {
		// Define the SQL query
		String sql = "DELETE FROM Visibility WHERE userId = ? AND visibleToUserId = ?";
		try (Connection connection = connect()) {
			int userId = 0;
			int userRemoveId = 0;

			// Loop to retrieve proper IDs for user and userRemove
			for (Account a : accounts) {
				if (a.getUsername().equals(username)) {
					userId = a.getUserID();
				} else if (a.getUsername().equals(userToRemove)) {
					userRemoveId = a.getUserID();
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				// set the values for the parameters
				statement.setInt(1, userId);
				statement.setInt(2, userRemoveId);

				// Execute the query
				statement.executeUpdate();
				System.out.println("User Removed Successfully");
			}
		}

	}

	// Method to perform database operations
	public static void performDatabaseOperations() {
		// clear all old data to refresh
		accounts.clear();
		posts.clear();
		visibilities.clear();

		try (Connection connection = connect()) {
			// Create a statement
			try (Statement statement = connection.createStatement()) {
				// Execute the query
				ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

				// Process the results from User and store them in lists
				while (resultSet.next()) {
					int userId = resultSet.getInt("userId");
					String username = resultSet.getString("username");
					String password = resultSet.getString("password");

					Account a = new Account(userId, username, password);
					accounts.add(a);

				}
			}

			// Create a statement
			try (Statement statement = connection.createStatement()) {
				// Execute the query
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Post");

				// Process the results from Post and store them in lists
				while (resultSet.next()) {
					int postId = resultSet.getInt("postId");
					String postText = resultSet.getString("postText");
					String postTime = resultSet.getString("postTime");
					String author = resultSet.getString("author");

					Post p = new Post(postId, postText, postTime, author);
					posts.add(p);

				}
			}

			// Create a statement
			try (Statement statement = connection.createStatement()) {
				// Execute the query
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Visibility");

				// Process the results from Visibility and add to Visibility Object
				while (resultSet.next()) {
					int visibilityID = resultSet.getInt("visibilityID");
					int userId = resultSet.getInt("userId");
					int visibleToUserID = resultSet.getInt("visibleToUserID");

					Visibility v = new Visibility(visibilityID, userId, visibleToUserID);
					visibilities.add(v);

				}
			}

		} catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
		}
	}
}
