import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class BubbleSort {

    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );

    static int MAXINPUTSIZE  = 2560;

    static int MININPUTSIZE  =  10;
    static long MAXVALUE = 2000;
    static long MINVALUE = -2000;
    static long numberOfTrials = 10;

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        checkSortCorrectness();

        runFullExperiment("BubbleSort-Exp1-ThrowAway.txt");

        runFullExperiment("BubbleSort-Exp2.txt");

        runFullExperiment("BubbleSort-Exp3.txt");

    }

    static void runFullExperiment(String resultsFileName){

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file...

        }



        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial



        resultsWriter.println("#InputSize    AverageTime"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        for(int inputSize=MININPUTSIZE;inputSize<=MAXINPUTSIZE; inputSize*=2) {

            // progress message...

            System.out.println("Running test for input size "+inputSize+" ... ");



            /* repeat for desired number of trials (for a specific size of input)... */

            long batchElapsedTime = 0;

            // generate a list of randomly spaced integers in ascending sorted order to use as test input

            // In this case we're generating one list to use for the entire set of trials (of a given input size)

            // but we will randomly generate the search key for each trial






            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)

            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the

            // stopwatch methods themselves

            //BatchStopwatch.start(); // comment this line if timing trials individually



            // run the tirals

            for (long trial = 0; trial < numberOfTrials; trial++) {

                long[] testingList = createRandomIntegerList(inputSize);

                TrialStopwatch.start(); // *** uncomment this line if timing trials individually

                bubbleSortNumberList(testingList);

                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

            resultsWriter.printf("%12d  %15.2f \n",inputSize, averageTimePerTrialInBatch); // might as well make the columns look nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }

    }

    /* return the index of the searched number if found or -1 if not found */
    public static void bubbleSortNumberList(long[] list) {
        /* make N passes through the list (N is the length of the list) */
        for (int i = 0; i < list.length; i++) {
            /* for index from 0 to N-1 compare item[index] to next item, swap if needed */
            for (int j = 0; j < list.length - 1; j++) {
                if (list[j] > list[j + 1]) {
                    long tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                }
            }
        }
    }

    public static long[] createRandomIntegerList(int size) {

        long[] newList = new long[size];
        for (int j = 0; j < size; j++) {
            newList[j] = (long) (MINVALUE + Math.random() * (MAXVALUE - MINVALUE));
        }
        return newList;
    }

    public static void checkSortCorrectness(){
        System.out.println("||   Bubble sort test   ||");
        int inputSize = 10;
        long[] testList = createRandomIntegerList(inputSize);

        System.out.println("Unsorted: ");
        System.out.println(Arrays.toString(testList));

        bubbleSortNumberList(testList);

        System.out.println("Sorted: ");
        System.out.println(Arrays.toString(testList));

        System.out.println("The list has been correctly sorted: ");

        if (verifySorted(testList) == true){
            System.out.println("True");
        }else{System.out.println("False");}
    }

    public static boolean verifySorted(long[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }
}