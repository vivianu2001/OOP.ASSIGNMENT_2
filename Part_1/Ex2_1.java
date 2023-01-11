import java.io.*;
import  java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Ex2_1 {

    /**
     * The main method of the program. Creates a number of text files, counts the number of lines in the files using
     * different methods, and measures the run time of each method.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args)  {
        Ex2_1 ex2_1 = new Ex2_1();
        String[] filenames = ex2_1.createTextFiles(100 ,2, 6);
        long starT,endT;
        int linesNum;


        starT=System.currentTimeMillis();
        linesNum=getNumOfLines(filenames);
        endT=System.currentTimeMillis();
        System.out.println("The total run time of getNumOfLine to count "+ linesNum + " lines  is:" + (endT-starT) + "MS");



        starT=System.currentTimeMillis();
        linesNum=ex2_1.getNumOfLinesThreads(filenames);
        endT=System.currentTimeMillis();
        System.out.println("The total run time of getNumOfLinesThreads using Threads to count "+ linesNum + " lines  is:" + (endT-starT) + "MS");



        starT=System.currentTimeMillis();
        linesNum=ex2_1.getNumOfLinesThreadPool(filenames);
        endT=System.currentTimeMillis();
        System.out.println("The total run time of getNumOfLinesThreadPool using ThreadPool to count "+ linesNum + " lines  is:" + (endT-starT) + "MS");



    }

    /**
     * Creates n text files with random number of lines.
     * The file names are in the form "File_i.txt" where i is the index of the file.
     * The number of lines in each file is a random integer between 0 and bound-1.
     *
     * @param n the number of files to create
     * @param seed the seed for the random number generator
     * @param bound the upper bound for the number of lines in each file
     * @return an array of the file names
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String fileNamePrefix = "File_";
        String fileNameSuffix = ".txt";
        String[] filenames = new String[n];
        Random random = new Random(seed);

        for (int i = 0; i < n; i++) {
            String fileName = fileNamePrefix + i + fileNameSuffix;
            try {
                File file = new File(fileName);
                boolean fileCreate = file.createNewFile();
                if (fileCreate) {
                    filenames[i] = fileName;
                    System.out.println("File created: " + fileName);

                    PrintWriter writer = new PrintWriter(file);
                    int numberOfLines = random.nextInt(bound);
                    for (int j = 0; j < numberOfLines; j++) {
                        writer.println("Hello line: " + j);
                    }
                    writer.close();

                } else {
                    System.out.println("File already exists");
                    filenames[i] = fileName;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return filenames;
    }
    /**
     * Counts the number of lines in the given files.
     *
     * @param fileNames the names of the files
     * @return the total number of lines in the files
     */
    public static int getNumOfLines(String[] fileNames) {
        int lines = 0;
        for (int i = 0; i < fileNames.length; i++) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileNames[i]));
                while (reader.readLine() != null) {
                    lines++;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return lines;

    }
    /**
     * Counts the number of lines in the given files using multiple threads.
     *
     * @param fileNames the names of the files
     * @return the total number of lines in the files
     */
    public int getNumOfLinesThreads(String[] fileNames) {
        LinesCounterThread[] threads = new LinesCounterThread[fileNames.length];
        int totalnumberoflines = 0;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new LinesCounterThread(fileNames[i]);
            threads[i].start();

        }
        for (LinesCounterThread t : threads) {
            try {
                t.join();
                totalnumberoflines = (int)(totalnumberoflines + t.getTotalines());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        return totalnumberoflines;


    }
    /**
     * Counts the number of lines in the given files using a thread pool.
     *
     * @param fileNames the names of the files
     * @return the total number of lines in the files
     */
    public int getNumOfLinesThreadPool(String[] fileNames)  {
        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(fileNames.length) ;
        LineCounterThreadpool[] tasks=new LineCounterThreadpool[fileNames.length];
        Future<Integer>[] results= new Future[fileNames.length];
        int totalnumberoflines = 0;

        for (int i = 0; i < fileNames.length; i++) {
            tasks[i] = new LineCounterThreadpool(fileNames[i]);

        }

        for (int i = 0; i < fileNames.length; i++) {
            results[i] = executor.submit(tasks[i]);

        }

        executor.shutdown();
        for(Future<Integer> result :results)
        {
            try {
                totalnumberoflines =totalnumberoflines +result.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
       
return totalnumberoflines;
        
    }








}


















