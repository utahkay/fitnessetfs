package fitnesseTfs;

public class TfRunner {
	
	private String path;
    private ConsoleOutputter outputter = new ConsoleOutputter();

	public void setPathToTfCommand(String path) {
		this.path = path;
	}

    public void setOutputter(ConsoleOutputter outputter) {
        this.outputter = outputter;
    }

	public String execute(String option, String file) {
        String command = path + "tf.exe " + option + " /noprompt " + file;
        outputter.output(command);
		String result = execute(command);
        outputter.output(result);
        return result;
	}
	
    private static String execute(String command) {
        String result = "";

        try {
            Process process = Runtime.getRuntime().exec(command);

            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream());
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream());

            errorGobbler.start();
            outputGobbler.start();
            
            process.waitFor();
            result = outputGobbler.getOutput();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public void setOutput(boolean output) {
        if (outputter != null)
            outputter.setOutput(output);
    }
}
