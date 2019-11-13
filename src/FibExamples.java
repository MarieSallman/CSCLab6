import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class FibExamples {

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public static void main(String[] args) {
        /* Uncomment for fibFormula tests, remember to uncomment in runFullExperiment as well. */

        /*
        runFullExperiment("LoopBig-Exp1.txt");

        runFullExperiment("LoopBig-Exp2.txt");

        runFullExperiment("LoopBig-Exp3.txt");
        */

        /* Uncomment for fibFormula tests, remember to uncomment in runFullExperiment as well. */


        runFullExperiment("MatrixBig-Exp1.txt");

        runFullExperiment("MatrixBig-Exp2.txt");

        runFullExperiment("MatrixBig-Exp3.txt");


        /* Uncomment for fibFormula tests, remember to uncomment in runFullExperiment as well. */

        /*
        runFullExperiment("FibForm-Exp1.txt");

        runFullExperiment("FibForm-Exp2.txt");

        runFullExperiment("FibForm-Exp3.txt");
        */

        /* Uncomment for fibFormula tests, remember to uncomment in runFullExperiment as well. */

        /*
        runFullExperiment("FibBig-Exp1.txt");

        runFullExperiment("FibBig-Exp2.txt");

        runFullExperiment("FibBig-Exp3.txt");
        */

    }


    private static BigInteger fibMatrixBig(int n) {
        BigInteger[] matrix = {BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO};
        return matrixPow(matrix, n)[1];
    }

    // Computes the power of a matrix. The matrix is packed in row-major order.
    private static BigInteger[] matrixPow(BigInteger[] matrix, int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        BigInteger[] result = {BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE};
        while (n != 0) {  // Exponentiation by squaring
            if (n % 2 != 0)
                result = matrixMultiply(result, matrix);
            n /= 2;
            matrix = matrixMultiply(matrix, matrix);
        }
        return result;
    }

    // Multiplies two matrices.
    private static BigInteger[] matrixMultiply(BigInteger[] x, BigInteger[] y) {
        return new BigInteger[] {
                multiply(x[0], y[0]).add(multiply(x[1], y[2])),
                multiply(x[0], y[1]).add(multiply(x[1], y[3])),
                multiply(x[2], y[0]).add(multiply(x[3], y[2])),
                multiply(x[2], y[1]).add(multiply(x[3], y[3]))
        };
    }


    private static BigInteger fibLoopBig(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            BigInteger c = a.add(b);
            a = b;
            b = c;
        }
        return a;
    }

    // Multiplies two BigIntegers. This function makes it easy to swap in a faster algorithm like Karatsuba multiplication.
    private static BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }


    private static double fibFormula(int n){

        double phi = (1 + Math.sqrt(5))/2;
        double b = n;
        double c = -n;
        double a = (Math.pow(phi, b) - Math.pow(phi, c))/Math.sqrt(5);

        return a;
    }

    private static BigDecimal fibFormulaBig(int n){



        BigDecimal x, squareRoot;

        x = new BigDecimal(5);
        MathContext mc = new MathContext(800);
        squareRoot = x.sqrt(mc);


        BigDecimal b = (BigDecimal.ONE.add(squareRoot).divide(BigDecimal.valueOf(2)));
        BigDecimal c = b.negate().add(BigDecimal.ONE);

        BigDecimal e = b.pow(n).subtract(c.pow(n)).divide(squareRoot);

        return e;

    }

    static void runFullExperiment(String resultsFileName){

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file...

        }


        long numberOfTrials = 10000;
        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial



        resultsWriter.println("#InputNumber       AverageTime     Number of Trials"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        for(int inputNumber=1;inputNumber<=32768; inputNumber*=2) {

            // progress message...

            System.out.println("Running test for digit size "+inputNumber+" ... ");



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

                int count = inputNumber;


                TrialStopwatch.start(); // *** uncomment this line if timing trials individually

                /* Uncomment this when running simple multiplication tests.  Make sure to uncomment in main as well. */
                //fibLoopBig(count);


                /* Uncomment this when running addition tests.  Make sure to uncomment in main as well. */
                fibMatrixBig(count);


                /* Uncomment this when running faster multiplication tests.  Make sure to uncomment in main as well */
                //System.out.println(fibFormula(count));


                /* Uncomment this when running faster multiplication tests.  Make sure to uncomment in main as well */
                //System.out.println(fibFormulaBig(count));



                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

            resultsWriter.printf("%12d  %15.2f  %12d \n",inputNumber, averageTimePerTrialInBatch, numberOfTrials); // might as well make the columns look nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }

    }
}
