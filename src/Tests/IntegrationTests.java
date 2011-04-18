package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import fitnesseTfs.TfRunner;


public class IntegrationTests {
    @Ignore
	@Test
	public void canGetWorkspaceOutput()
	{
		TfRunner runner = new TfRunner();
		runner.setPathToTfCommand("C:\\program files\\Microsoft Visual Studio 9.0\\Common7\\IDE\\");
		String output = runner.execute("WORKSPACES", "");
		Assert.assertTrue(output.contains("LAPTOP-LEA-07"));
	}

}