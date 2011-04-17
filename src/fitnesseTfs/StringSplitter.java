package fitnesseTfs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSplitter {

	public static List<String> split(String stringToSplit)
	{
		List<String> matchList = new ArrayList<String>(); 
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'"); 
		Matcher regexMatcher = regex.matcher(stringToSplit); 
		while (regexMatcher.find()) 
		{     
			if (regexMatcher.group(1) != null) 
			{         // Add double-quoted string without the quotes         
				matchList.add(regexMatcher.group(1));     
			} 
			else if (regexMatcher.group(2) != null) 
			{         
				// Add single-quoted string without the quotes         
				matchList.add(regexMatcher.group(2));     
			} 
			else 
			{         
				// Add unquoted word         
				matchList.add(regexMatcher.group());     
			} 
		}
		return matchList;
	}

}
