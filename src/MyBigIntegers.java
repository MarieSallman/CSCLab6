import java.net.StandardSocketOptions;
import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class MyBigIntegers {

    String str1;
    String str2;


    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public MyBigIntegers(){
        str1 = "0";
    }

    public MyBigIntegers(String string){
        str1 = string;
    }

    public String value(){
        return str1;
    }

    public void ToString(){
        System.out.println(value());
    }

    public MyBigIntegers times(MyBigIntegers x){

        str2 = x.value();

        String num1 = str1;
        String num2 = str2;
        String tempnum1 = num1;
        String tempnum2 = num2;


        // Check condition if one string is negative
        if(num1.charAt(0) == '-' && num2.charAt(0)!='-')
        {
            num1 = num1.substring(1);
        }
        else if(num1.charAt(0) != '-' && num2.charAt(0) == '-')
        {
            num2 = num2.substring(1);
        }
        else if(num1.charAt(0) == '-' && num2.charAt(0) == '-')
        {
            num1 = num1.substring(1);
            num2 = num2.substring(1);
        }
        String s1 = new StringBuffer(num1).reverse().toString();
        String s2 = new StringBuffer(num2).reverse().toString();

        int[] m = new int[s1.length()+s2.length()];

        // Go from right to left in num1
        for (int i = 0; i < s1.length(); i++)
        {
            // Go from right to left in num2
            for (int j = 0; j < s2.length(); j++)
            {
                m[i+j] = m[i+j]+(s1.charAt(i)-'0')*(s2.charAt(j)-'0');

            }
        }


        String product = new String();
        // Multiply with current digit of first number
        // and add result to previously stored product
        // at current position.


        for (int i = 0; i < m.length; i++)
        {
            int digit = m[i]%10;
            int carry = m[i]/10;
            if(i+1<m.length)
            {
                m[i+1] = m[i+1] + carry;
            }
            product = digit+product;

        }

        // ignore '0's from the right
        while(product.length()>1 && product.charAt(0) == '0')
        {
            product = product.substring(1);
        }

        // Check condition if one string is negative
        if(tempnum1.charAt(0) == '-' && tempnum2.charAt(0)!='-')
        {
            product = new StringBuffer(product).insert(0,'-').toString();
        }
        else if(tempnum1.charAt(0) != '-' && tempnum2.charAt(0) == '-')
        {
            product = new StringBuffer(product).insert(0,'-').toString();
        }
        /*else if(tempnum1.charAt(0) == '-' && tempnum2.charAt(0) == '-')
        {
            product = product;
        }*/
        System.out.println("Product of the two numbers is (simple multiplication) : "+product);


        return x;
    }


    static boolean isSmaller(String str1, String str2)
    {
        // Calculate lengths of both string
        int n1 = str1.length(), n2 = str2.length();

        if (n1 < n2)
            return true;
        if (n2 > n1)
            return false;

        for (int i = 0; i < n1; i++)
        {
            if (str1.charAt(i) < str2.charAt(i))
                return true;
            else if (str1.charAt(i) > str2.charAt(i))
                return false;
        }
        return false;
    }

    // Function for finding difference of larger numbers
    static void findDiff(String str1, String str2)
    {

        if(str1.charAt(0) == '-' && str2.charAt(0)!='-')
        {
            str1 = str1.substring(1);
        }
        else if(str1.charAt(0) != '-' && str2.charAt(0) == '-')
        {
            str2 = str2.substring(1);
        }
        // Before proceeding further, make sure str1
        // is not smaller
        if (isSmaller(str1, str2))
        {
            String t = str1;
            str1 = str2;
            str2 = t;
        }

        // Take an empty string for storing result
        String str = "";

        // Calculate lengths of both string
        int n1 = str1.length(), n2 = str2.length();
        int diff = n1 - n2;

        // Initially take carry zero
        int carry = 0;

        // Traverse from end of both strings
        for (int i = n2 - 1; i >= 0; i--)
        {
            // Do school mathematics, compute difference of
            // current digits and carry
            int sub = (((int)str1.charAt(i + diff) - (int)'0') -
                    ((int)str2.charAt(i) - (int)'0') - carry);
            if (sub < 0)
            {
                sub = sub+10;
                carry = 1;
            }
            else
                carry = 0;

            str += String.valueOf(sub);
        }

        // subtract remaining digits of str1[]
        for (int i = n1 - n2 - 1; i >= 0; i--)
        {
            if (str1.charAt(i) == '0' && carry > 0)
            {
                str += "9";
                continue;
            }
            int sub = (((int)str1.charAt(i) - (int)'0') - carry);
            if (i > 0 || sub > 0) // remove preceding 0's
                str += String.valueOf(sub);
            carry = 0;

        }

        String neg = "-";
        StringBuilder string = new StringBuilder();

        string.append(str);
        string = string.reverse();
        System.out.println("The sum of the two numbers is : " + neg + string);



    }


    private static String digit_mult(String a, String b) {
        return Integer.toString( Integer.parseInt(a) * Integer.parseInt(b) );
    }

    private static String digit_padder(String a, String b) {
        int size = Math.abs(a.length() - b.length());
        for (int i = 1; i <= size; i++)
            b = "0" + b;
        return b;
    }

    private static String digit_subtracter(String a, String b) {
        int size_a = a.length();
        int size_b = b.length();
        int size = Math.max(size_a, size_b);
        /* match the digit-lengths of a_1 and a_2 */
        if (size_a > size_b)
            b = digit_padder(a, b);
        else if (size_a < size_b)
            a = digit_padder(b, a);

        /* subtract b from a digit-by-digit from the back */
        String result = "";
        String[] a_carry_board = a.split(""); // look here for carry managing

        for (int i = size - 1; i >= 0; i--) {
            int a_digit = Integer.parseInt(a_carry_board[i]);
            int b_digit = Integer.parseInt( Character.toString( b.charAt(i) ) );
            int temp_sub;

            if (b_digit > a_digit) {
                // borrow carry from a[i-1]
                a_digit += 10;
                int j = i - 1;
                // keep borrowing till non-"0"
                while (a_carry_board[j].equals("0")) {
                    a_carry_board[j] = "9";
                    j--;
                }
                a_carry_board[j] = Integer.toString(
                        Integer.parseInt(a_carry_board[j]) - 1
                ); // never reaches a[-1]
            }
            temp_sub = a_digit - b_digit;
            result = Integer.toString(temp_sub) + result;
        }
        return result;
    }

    private static String digit_adder(String a_1, String a_2) {
        int size_a = a_1.length();
        int size_a_2 = a_2.length();
        int size = Math.max(size_a, size_a_2);

        /* match the digit-lengths of a_1 and a_2 */
        if (size_a > size_a_2)
            a_2 = digit_padder(a_1, a_2);
        else if (size_a < size_a_2)
            a_1 = digit_padder(a_2, a_1);

        /* add string digit by digit from the back by */
        int carry = 0;
        String result = ""; // prepend to result
        int last_idx = 0;   // temp_sum is 1 or 2 digits
        for (int i = size - 1; i >= 0 ; i--) {
            // add the integer-fied digits + carry
            int temp_sum = Integer.parseInt( Character.toString( a_1.charAt(i) ) ) +
                    Integer.parseInt( Character.toString( a_2.charAt(i) ) ) +
                    carry;
            if (temp_sum >= 10) {
                carry = 1;
                last_idx = 1; // temp_sum is 2 digits
            } else {
                carry = 0;
                last_idx = 0;
            }
            result = Integer.toString(temp_sum).substring(last_idx) + result;
        }
        if (carry != 0) result = "1" + result; // last carry if necessary
        return result;
    }

    private static String ktmult(String a, String b) {
        int size_a = a.length();
        int size_b = b.length();

        /* base case */
        if ((size_a + size_b <= 3)) { // 1and1 or 2and1 digits
            return digit_mult(a,b);
        } else {      /* recursion */
            if (size_a > size_b)
                b = digit_padder(a, b);
            else if (size_a < size_b)
                a = digit_padder(b, a);
            int n = Math.max(a.length(), b.length());

            // split rule : floor(n / 2) | ceiling(n / 2)
            String a_1 = a.substring(0, n / 2); // a
            String a_2 = a.substring(n / 2, n); // b

            String b_1 = b.substring(0, n / 2); // c
            String b_2 = b.substring(n / 2, n); // d

            String p = digit_adder(a_1, a_2);   // p = a + b
            String q = digit_adder(b_1, b_2);   // q = c + d

            String k_1 = ktmult(a_1, b_1);      // ac
            String k_2 = ktmult(a_2, b_2);      // bd
            String k_3 = ktmult(p, q);          // pq

            String temp = digit_subtracter(k_3, k_1);
            String k_4  = digit_subtracter(temp, k_2); // (pq - ac) - bd

            /* Pad "0"s to k_1 and k_4 following the rule :
             * 10^(n or n+1)* k_1 + 10^(ceil(n/2))* k_4 + k_2
             */
            double m = (double) n;
            for (int i = 0; i < 2 * Math.ceil(m / 2); i++) {
                if (i < Math.ceil(m / 2))
                    k_4 += "0";
                k_1 += "0";
            }
            String temp2 = digit_adder(k_1, k_4);

            return digit_adder(temp2, k_2);
        }
    }

    public MyBigIntegers plus(MyBigIntegers x){

        str2 = x.value();
        String neg = "";


        if(str1.charAt(0) == '-' && str2.charAt(0)!='-')
        {
            findDiff(str1, str2);

            return x;
        }
        else if(str1.charAt(0) != '-' && str2.charAt(0) == '-')
        {
            findDiff(str1, str2);
            return x;
        }
        else if(str1.charAt(0) == '-' && str2.charAt(0) == '-')
        {
            str1 = str1.substring(1);
            str2 = str2.substring(1);
            neg = "-";
        }

        if (str1.length() > str2.length()){
            String t = str1;
            str1 = str2;
            str2 = t;
        }

        // Take an empty String for storing result
        String str = "";

        // Calculate length of both String
        int n1 = str1.length(), n2 = str2.length();
        int diff = n2 - n1;

        // Initially take carry zero
        int carry = 0;

        // Traverse from end of both Strings
        for (int i = n1 - 1; i>=0; i--)
        {
            // Do school mathematics, compute sum of
            // current digits and carry
            int sum = ((int)(str1.charAt(i)-'0') +
                    (int)(str2.charAt(i+diff)-'0') + carry);
            str += (char)(sum % 10 + '0');
            carry = sum / 10;
        }

        // Add remaining digits of str2[]
        for (int i = n2 - n1 - 1; i >= 0; i--)
        {
            int sum = ((int)(str2.charAt(i) - '0') + carry);
            str += (char)(sum % 10 + '0');
            carry = sum / 10;
        }

        // Add remaining carry
        if (carry > 0)
            str += (char)(carry + '0');

        StringBuilder string = new StringBuilder();

        string.append(str);
        string = string.reverse();
        System.out.println("The sum of the two numbers is : " + neg + string);

        return x;
    }








    public static void main(String[] args) {

        MyBigIntegers A = new MyBigIntegers("1000");
        System.out.println("String 1 = ");
        A.ToString();

        MyBigIntegers B = new MyBigIntegers("-10000");
        System.out.println("String 2 = ");
        B.ToString();


        A.plus(B);

        A.times(B);


        System.out.println("The faster multiplication = " + ktmult(A.value(), B.value()));


        /* Uncomment these when running addition tests.  Make sure to uncomment in runFullExperiment as well. */

        //runFullExperiment("BigInt-Exp1-ThrowAway.txt");

        //runFullExperiment("BigInt-Exp2.txt");

        //runFullExperiment("BigInt-Exp3.txt");



        /* Uncomment these when running simple multiplication tests.  Make sure to uncomment in runFullExperiment as well. */

        //runFullExperiment("BigInt-Multi-Exp1.txt");

        //runFullExperiment("BigInt-Multi-Exp2.txt");

        //runFullExperiment("BigInt-Multi-Exp3.txt");


        /* Uncomment these when running faster multiplication tests.  Make sure to uncomment in runFullExperiment as well. */

        /*runFullExperiment("BigInt-Faster-Exp1.txt");

        runFullExperiment("BigInt-Faster-Exp2.txt");

        runFullExperiment("BigInt-Faster-Exp3.txt");
        */
    }



    static void runFullExperiment(String resultsFileName){

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file...

        }


        long numberOfTrials = 1000;
        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial



        resultsWriter.println("#DigitSize       AverageTime     Number of Trials"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        for(int digitSize=1;digitSize<=4096; digitSize*=2) {

            // progress message...

            System.out.println("Running test for digit size "+digitSize+" ... ");



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

                String SALTCHARS = "1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < digitSize){
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }

                String string1 = salt.toString();

                MyBigIntegers A = new MyBigIntegers(string1);
                //System.out.println("String 1 = ");
                //A.ToString();

                SALTCHARS = "1234567890";
                StringBuilder salt2 = new StringBuilder();
                Random rnd2 = new Random();
                while (salt2.length() < digitSize){
                    int index2 = (int) (rnd2.nextFloat() * SALTCHARS.length());
                    salt2.append(SALTCHARS.charAt(index2));
                }

                String string2 = salt2.toString();

                MyBigIntegers B = new MyBigIntegers(string2);
                //System.out.println("String 2 = ");
                //B.ToString();


                TrialStopwatch.start(); // *** uncomment this line if timing trials individually


                /* Uncomment this when running addition tests.  Make sure to uncomment in main as well. */
                //A.plus(B);


                /* Uncomment this when running simple multiplication tests.  Make sure to uncomment in main as well. */
                //A.times(B);


                /* Uncomment this when running faster multiplication tests.  Make sure to uncomment in main as well */
                ktmult(A.value(), B.value());



                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

            resultsWriter.printf("%12d  %15.2f  %12d \n",digitSize, averageTimePerTrialInBatch, numberOfTrials); // might as well make the columns look nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }

    }

}
