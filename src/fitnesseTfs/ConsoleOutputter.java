package fitnesseTfs;

public class ConsoleOutputter {
    private boolean output = true;

    public void output(String message) {
		if (output)
            System.out.println(message);
	}

    public void setOutput(boolean output) {
        this.output = output;
    }
}
