package fitnesseTfs;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 *  Michael C. Daconta, JavaWorld.com, 12/29/00
 */
class StreamGobbler extends Thread
{
    InputStream is;
    String result = "";

    StreamGobbler(InputStream is)
    {
        this.is = is;
    }

    public void run()
    {
        try {
            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            while ( (line = input.readLine()) != null) {
                result = line + "\n";
            }
            input.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getOutput()
    {
        return result;
    }

}