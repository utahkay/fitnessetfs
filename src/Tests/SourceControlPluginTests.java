package Tests;

import java.io.IOException;
import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import fitnesseTfs.*;

public class SourceControlPluginTests {

	private static final String EXPECTED_FILE = "./FitnesseRoot/MyProject/content.txt";
	private static final String EXPECTED_FILE_RELATIVE = "../FitnesseRoot/MyProject/content.txt";
	private static final String EXPECTED_ROOT = "c:\\users\\me\\documents\\fitnesse\\";
	private static final String EXPECTED_OUTPATH = "c:\\users\\me\\documents\\fitnesse\\FitnesseRoot\\MyProject\\content.txt";
    private static final String TFS_PATH = "C:\\Program Files\\Microsoft Visual Studio 9.0\\Common7\\IDE\\";
	private static final String PAYLOAD = "fitnesseTfs.SourceControlPlugin " + EXPECTED_ROOT + " \"" + TFS_PATH + "\"";
	private static final String PAYLOAD_NOOUTPUT = PAYLOAD + " nooutput";
	
	ConsoleOutputter fakeOutputter = Mockito.mock(ConsoleOutputter.class);
    TfRunner fakeTfRunner = Mockito.mock(TfRunner.class);
	
	@Before
	public void setup()
	{
        SourceControlPlugin.setTfRunner(fakeTfRunner);
		SourceControlPlugin.setOutputter(fakeOutputter);
	}

	@Test
	public void cmEditCallsSetPathOnRunner() throws IOException
	{
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).setPath(TFS_PATH);
	}
	
	@Test
	public void cmEditCallsDirWhenFileIsNew() throws IOException
	{
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).execute("dir", EXPECTED_OUTPATH);
    }
	
	@Test
	public void cmEditCallsDirAndCheckoutWhenFileExists() throws IOException
	{
        when(fakeTfRunner.execute("dir", EXPECTED_OUTPATH)).thenReturn("1 item");
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).execute("dir", EXPECTED_OUTPATH);
        verify(fakeTfRunner).execute("checkout", EXPECTED_OUTPATH);
    }

	@Test
	public void cmUpdateCallsAddWhenFileIsNew() throws IOException
	{
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        SourceControlPlugin.cmUpdate(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).execute("add", EXPECTED_OUTPATH);
    }
	
	@Test
	public void cmUpdateDoesNothingWhenFileExists() throws IOException
	{
        when(fakeTfRunner.execute("dir", EXPECTED_OUTPATH)).thenReturn("1 item");
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        SourceControlPlugin.cmUpdate(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner, never()).execute(eq("add"), anyString());
        verify(fakeTfRunner, never()).execute(eq("checkin"), anyString());
	}
	
	@Test
	public void cmDeleteCallsDeleteWhenFileExists() throws IOException
	{
        when(fakeTfRunner.execute("dir", EXPECTED_OUTPATH)).thenReturn("1 item");
        SourceControlPlugin.cmDelete(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).execute("delete", EXPECTED_OUTPATH);
    }

	@Test
	public void cmDeleteDoesNotCallDeleteWhenFileDoesNotExist() throws IOException
	{
        SourceControlPlugin.cmDelete(EXPECTED_FILE, PAYLOAD);
        verify(fakeTfRunner).execute("dir", EXPECTED_OUTPATH);
        verify(fakeTfRunner, never()).execute(eq("delete"), anyString());
	}
	
	@Test
	public void cmEditOutputsWithoutNooutputInPayload()
	{
        SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD);
        verify(fakeOutputter).output("dir " + EXPECTED_OUTPATH);
	}
	
	@Test
	public void cmEditDoesntOutputWithNooutputInPayload()
	{
		SourceControlPlugin.cmEdit(EXPECTED_FILE, PAYLOAD_NOOUTPUT);
        verify(fakeOutputter, never()).output(anyString());
	}

    @Test
    public void cmEditStripsRelativePath()
    {
        SourceControlPlugin.cmEdit(EXPECTED_FILE_RELATIVE, PAYLOAD);
        verify(fakeTfRunner).execute("dir", EXPECTED_OUTPATH);
    }

}
