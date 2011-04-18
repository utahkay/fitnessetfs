package fitnesseTfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TfRunner {
	
	private String path;
    private ConsoleOutputter outputter;

	public void setPathToTfCommand(String path) {
		this.path = path;
	}

    public void setOutputter(ConsoleOutputter outputter) {
        this.outputter = outputter;
    }

	public String execute(String option, String file) {
        outputter.output("tf.exe " + option + " " + file);
		String result = execute(path + "tf.exe " + option + " /noprompt " + file);
        outputter.output(result);
        return result;
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

    public void setOutput(boolean output) {
        if (outputter != null)
            outputter.setOutput(output);
    }
}
