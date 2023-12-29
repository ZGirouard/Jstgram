package unl.soc;

public class Account extends PostFormatter{
	private static int objectCount = 0;
	private int userId;
	private String username;
	private String password;

	public Account(int userID, String username, String password) {
		this.userId = userID;
		this.username = username;
		this.password = password;
		objectCount++;
	}

	public int getUserID() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public static int getObjectCount() {
		return objectCount;
	}
}
