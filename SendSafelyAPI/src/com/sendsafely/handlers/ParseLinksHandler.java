package com.sendsafely.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseLinksHandler 
{	
	private final String REGEX = "(https:\\/\\/[a-zA-Z\\.-]+\\/receive\\/\\?[A-Za-z0-9&=\\-]+packageCode=[A-Za-z0-9\\-_]+#keyCode=[A-Za-z0-9\\-_]+)";
	
	public List<String> parse(String text) {
		List<String> links = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(text);
		while(matcher.find()) {
			String link = matcher.group(0);
			if(!links.contains(link)) {
				links.add(link);
			}
		}
		return links;
	}
}
