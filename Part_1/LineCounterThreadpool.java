import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * A class that counts the number of lines in a file using a separate thread.
 * The `call` method can be called to start the thread and get the number of lines.
 */
public class LineCounterThreadpool implements Callable {

    private String filename;
    private int totalines;

    /**
     * Creates a new `LineCounterThreadpool` object for the given file.
     *
     * @param filename the name of the file
     */
    LineCounterThreadpool(String filename)
    {
        this.filename=filename;
    }

    /**
     * Counts the number of lines in the file.
     *
     * @return the number of lines in the file
     */
    @Override
    public Integer call()  {

        try {
            BufferedReader reader=new BufferedReader(new FileReader(filename));
            while (reader.readLine()!=null)
            {
                totalines ++;

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return totalines;
    }
}
