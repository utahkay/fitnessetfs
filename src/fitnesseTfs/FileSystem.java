package fitnesseTfs;

import java.io.File;

public class FileSystem {
    ConsoleOutputter outputter = new ConsoleOutputter();

    public boolean fileExists(String path) {
        if (outputter != null)
            outputter.output("File exists? " + path);
        return new File(path).exists();
    }

    public boolean isWritable(String path) {
        if (outputter != null)
            outputter.output("Is writable? " + path);
        return new File(path).canWrite();
    }

    public void setOutputter(ConsoleOutputter outputter) {
        this.outputter = outputter;
    }

    public void setOutput(boolean output) {
        if (outputter != null)
            outputter.setOutput(output);
    }
}
