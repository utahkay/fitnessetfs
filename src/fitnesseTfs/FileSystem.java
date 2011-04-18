package fitnesseTfs;

import java.io.File;

public class FileSystem {
    ConsoleOutputter outputter = new ConsoleOutputter();

    public boolean fileExists(String path) {
        boolean result = new File(path).exists();
        if (outputter != null)
            outputter.output("File exists? " + path + (result ? " - Yes" : " - No"));
        return result;
    }

    public boolean isWritable(String path) {
        boolean result =  new File(path).canWrite();
        if (outputter != null)
            outputter.output("Is writable? " + path + (result ? " - Yes" : " - No"));
        return result;
    }

    public void setOutputter(ConsoleOutputter outputter) {
        this.outputter = outputter;
    }

    public void setOutput(boolean output) {
        if (outputter != null)
            outputter.setOutput(output);
    }
}
