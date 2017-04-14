package br.com.alois.domain.entity.notification;

public abstract class Notification 
{
	public static String toJson(String title, String message, String deviceToken)
	{
		return "{ \"notification\": "
				+ "{"
					+ "\"title\": \""+title+"\","
					+ "\"text\": \""+message+"\"},"
					+ "\"to\" : \""+deviceToken+"\""
				+ "}";
	}
}
