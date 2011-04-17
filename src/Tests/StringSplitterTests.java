package Tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import fitnesseTfs.StringSplitter;


public class StringSplitterTests {
	
	@Test
	public void canSplitPayload()
	{
		String pluginName = "fitnesseTfs.SourceControlPlugin";
		String root = "C:\\Users\\bi-lea\\Documents\\Fitnesse\\FitNesseRoot\\";
		String payload = pluginName + " \"" + root + "\"";
		List<String> result = StringSplitter.split(payload);
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(pluginName, result.get(0));
		Assert.assertEquals(root, result.get(1));
	}

}
