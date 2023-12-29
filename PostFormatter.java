package unl.soc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostFormatter {
	protected String postTime;
	protected String postAccount;
	protected String postText;

	public static String tag(String postTime, String postAccount) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(postTime, formatter);
		String s = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return String.format("|%40s|" + System.lineSeparator(), s + " by " + postAccount);
	}
	
	public static String getFormattedContent(String postText) {
		return String.format("|%-40s|" + System.lineSeparator(), postText);
	}


}