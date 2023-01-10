import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that counts the number of lines in a file using a separate thread.
 * The `run` method is called when the thread is started. The number of lines can be obtained
 * by calling the `getTotalines` method.
 */
public class LinesCounterThread extends Thread {
private  String filename;
private int totalines;

    /**
     * Creates a new `LinesCounterThread` object for the given file.
     *
     * @param filename the name of the file
     */
LinesCounterThread(String filename)
    {
     this.filename=filename;
    }
    /**
     * Counts the number of lines in the file.
     */
@Override
   public void run()
    {
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

    }

    public int getTotalines()
    {
        return totalines;
    }


}
