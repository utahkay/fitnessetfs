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
	static TfRunner tfRunner = new TfRunner();
    static FileSystem fileSystem = new FileSystem();

    public static void setTfRunner(TfRunner runner) {
		tfRunner = runner;
	}
	
    public static void setFileSystem(FileSystem fileSystem) {
        SourceControlPlugin.fileSystem = fileSystem;        
    }

	public static void cmEdit(String file, String payload) {
		parsePayload(payload);
		String fullPath = buildPath(payload, file);
		if (fileSystem.fileExists(fullPath) && !fileSystem.isWritable(fullPath))
			tf("checkout", fullPath);
	}

    public static void cmUpdate(String file, String payload) throws IOException {
        parsePayload(payload);
        String fullPath = buildPath(payload, file);
        if (!fileSystem.fileExists(fullPath))
            tf("add", fullPath);
    }

	public static void cmPreDelete(String file, String payload) throws IOException {
	}

	public static void cmDelete(String file, String payload) throws IOException {
		parsePayload(payload);
		String fullPath = buildPath(payload, file);
        if (tfFileExists(fullPath))
			tf("delete", fullPath);
	}

	private static void parsePayload(String payload) {
		List<String> parameters = StringSplitter.split(payload);
		
		rootPath = parameters.get(1);
		String pathToTfCommand = parameters.get(2);
		boolean output = parameters.size() > 3 ? !parameters.get(3).equals("nooutput") : true;
		
		tfRunner.setPathToTfCommand(pathToTfCommand);
        tfRunner.setOutput(output);
        fileSystem.setOutput(output);
	}
	
	public static boolean tfFileExists(String fullPath) {
		String existsOutput = tf("dir", fullPath);
		return existsOutput != null && existsOutput.contains("1 item");
	}

	private static String tf(String command, String path) {
        return tfRunner.execute(command, path);
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
