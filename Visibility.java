package unl.soc;

public class Visibility {
	private int visibilityID;
	private int userID;
	private int visibleToUserID;
	public static int objectCount = 0;
	
	public Visibility(int visibilityID, int userID, int visibleToUserID) {
		super();
		this.visibilityID = visibilityID;
		this.userID = userID;
		this.visibleToUserID = visibleToUserID;
		objectCount++;
	}

	public int getVisibilityID() {
		return visibilityID;
	}

	public void setVisibilityID(int visibilityID) {
		this.visibilityID = visibilityID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getVisibleToUserID() {
		return visibleToUserID;
	}

	public void setVisibleToUserID(int visibleToUserID) {
		this.visibleToUserID = visibleToUserID;
	}
	public static int getObjectCount() {
		return objectCount;
	}
	
}
