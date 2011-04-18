package Tests;

import fitnesseTfs.ConsoleOutputter;
import fitnesseTfs.FileSystem;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import org.mockito.Mockito;
import static org.mockito.Matchers.contains;

public class FileSystemTests {
    FileSystem fileSystem = new FileSystem();
    ConsoleOutputter fakeOutputter = Mockito.mock(ConsoleOutputter.class);

    @Before
    public void setup()
    {
        fileSystem.setOutputter(fakeOutputter);
    }

    @Test
    public void fileExistsNo()
    {
        assertEquals(false, fileSystem.fileExists("NoSuchFile"));
    }

    @Test
    public void isWritableFileDoesNotExist()
    {
        assertEquals(false, fileSystem.isWritable("noSuchFile"));
    }

    @Test
    public void fileExistsCallsOutputter()
    {
        fileSystem.fileExists("ExpectedFile");
        verify(fakeOutputter).output(contains("ExpectedFile"));
    }

    @Test
    public void isWritableCallsOutputter()
    {
        fileSystem.isWritable("ExpectedFile");
        verify(fakeOutputter).output(contains("ExpectedFile"));
    }

}
