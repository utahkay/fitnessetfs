package fitnesseTfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TfRunner {
	
	String path;
	
	public TfRunner()
	{
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String execute(String option, String file) {
		return execute(path + "tf.exe " + option + " /noprompt " + file);
	}
	
	private static String execute(String command) {
		String result;
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			try {
				process.waitFor();
				result = getOutput(process);
			} catch (InterruptedException e) {
				result = "Error: " + e.getMessage();
			}
		} catch (IOException e1) {
			result = "Error: " + e1.getMessage();
		}

		return result;
	}

	private static String getOutput(Process process) throws IOException {
		String result = "";
		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		while ((line = input.readLine()) != null) {
			result += line + "\n";
		}
		input.close();
		return result;
	}
}
