package unl.soc;

public class Post extends PostFormatter {
	
	private int postId;
	private String postText;
	private String postTime;
	private String author;
	private static int objectCount = 0;
	
	public Post(int postId, String postText, String postTime, String author) {
		super();
		this.postId = postId;
		this.postText = postText;
		this.postTime = postTime;
		this.author = author;
		objectCount++;
	}
	
	public int getPostId() {
		return postId;
	}
	
	public String getPostText() {
		return postText;
	}
	
	public String getPostTime() {
		return postTime;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public static int getObjectCount() {
		return objectCount;
	}
}
