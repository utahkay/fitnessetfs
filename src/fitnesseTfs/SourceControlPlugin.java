package fitnesseTfs;

import java.io.IOException;
import java.util.List;

public class SourceControlPlugin {

	/*
	 * •cmEdit(file, payload) is called just before file is about to be written
	 * •cmUpdate(file, payload) is called just after file has been written
	 * •cmPreDelete(directory, payload) is called just before the directory defining a page will be deleted. 
	 * •cmDelete(directory, payload) is called just after the directory defining a page has been deleted.
	 */

	static String rootPath = "";
	static String path = "";
	static boolean fileExists = false;
	static boolean output = true;
	static TfRunner tfRunner = new TfRunner();
	static ConsoleOutputter outputter = new ConsoleOutputter();
	
	public static void setTfRunner(TfRunner runner)
	{
		tfRunner = runner;
	}
	
	public static void setOutputter(ConsoleOutputter outputter)
	{
		SourceControlPlugin.outputter = outputter;
	}
	
	public static void cmEdit(String file, String payload) {
		parsePayload(payload);
		String fullPath = buildPath(payload, file);
		checkFileExists(fullPath); 
		if (fileExists)
			tf("checkout", fullPath);
	}

    public static void cmUpdate(String file, String payload) throws IOException {
        parsePayload(payload);
        String fullPath = buildPath(payload, file);
        if (!fileExists)
            tf("add", fullPath);
    }

	public static void cmPreDelete(String file, String payload)
			throws IOException {
	}

	public static void cmDelete(String file, String payload) throws IOException {
		parsePayload(payload);
		String fullPath = buildPath(payload, file);
		checkFileExists(fullPath);
		if (fileExists)
			tf("delete", fullPath);
	}

	private static void parsePayload(String payload)
	{
		List<String> parameters = StringSplitter.split(payload);
		
		rootPath = parameters.get(1);
		path = parameters.get(2);
		output = parameters.size() > 3 ? !parameters.get(3).equals("nooutput") : true;
		
		tfRunner.setPath(path);
	}
	
	private static void checkFileExists(String fullPath) {
		String existsOutput = tf("dir", fullPath);
		fileExists = existsOutput != null && existsOutput.contains("1 item");
	}

	private static String tf(String command, String path)
	{
		if (output) outputter.output(command + " " + path);
		String result = tfRunner.execute(command, path);
		if (output) outputter.output(result);
		return result;
	}
	
	private static String buildPath(String payload, String fileName) {
		if (!rootPath.endsWith("\\"))
			rootPath += "\\";
		fileName = fileName.replace("/", "\\");
		fileName = fileName.replace("..\\", "");
		fileName = fileName.replace(".\\", "");
		return rootPath + fileName;
	}
}
