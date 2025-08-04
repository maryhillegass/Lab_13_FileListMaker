import java.util.Scanner;

public class SafeInput {
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print(prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);

        return retString;

    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return an int response that is an integer
     */
    public static int getInt(Scanner pipe, String prompt){
        int result = 0;
        boolean done = false;
        String trash = "";
        do
        {
            // Code and control logic to loop until validated
            System.out.print(prompt + ": ");
            if(pipe.hasNextInt())
            {
                result = pipe.nextInt();
                pipe.nextLine(); // clear input buffer
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an int : " + trash);
            }

        }while(!done);

        return result;


    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a double response that is a double
     */
    public static double getDouble(Scanner pipe, String prompt){
        double result = 0;
        boolean done = false;
        String trash = "";
        do
        {
            // Code and control logic to loop until validated
            System.out.print(prompt + ": ");
            if(pipe.hasNextDouble())
            {
                result = pipe.nextDouble();
                pipe.nextLine(); // clear input buffer
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double : " + trash);
            }

        }while(!done);

        return result;

    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param lo lower bound of range
     * @param hi upper bound of range
     * @return an int within the range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int lo, int hi)
    {
        int result = 0;
        boolean done = false;
        String trash = "";
        do
        {
            // Code and control logic to loop until validated
            System.out.print(prompt + "[" + lo + " - " + hi + "]: ");
            if(pipe.hasNextInt())
            {
                result = pipe.nextInt();
                pipe.nextLine(); // clear input buffer
                if(result >= lo && result <= hi)
                {
                    done = true;
                }
                else
                {
                    System.out.println("You must enter a value in range [" + lo + " - " + hi + "]: " + result);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an int [" + lo + " - " + hi + "]: " + trash);
            }

        }while(!done);

        return result;
    }


    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param lo lower bound of range
     * @param hi upper bound of range
     * @return a double within the range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double lo, double hi)
    {
        double result = 0;
        boolean done = false;
        String trash = "";
        do
        {
            // Code and control logic to loop until validated
            System.out.print(prompt + "[" + lo + " - " + hi + "]: ");
            if(pipe.hasNextDouble())
            {
                result = pipe.nextDouble();
                pipe.nextLine(); // clear input buffer
                if(result >= lo && result <= hi)
                {
                    done = true;
                }
                else
                {
                    System.out.println("You must enter a value in range [" + lo + " - " + hi + "]: " + result);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double [" + lo + " - " + hi + "]: " + trash);
            }

        }while(!done);

        return result;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt){
        boolean done = false;
        boolean result = false;
        do{
            String input = SafeInput.getNonZeroLenString(pipe,prompt);
            if(input.equalsIgnoreCase("Y")) {
                result = true;
                done = true;
            }
            else if (input.equalsIgnoreCase("N")) {
                result = false;
                done = true;
            }
            else{
                System.out.print("Please enter [Y/N]: ");
            }
        }while(!done);
        return result;
    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param regEx Regular expression pattern to test against input
     * @return input that matches the regular expression
     */

    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String value = "";
        boolean gotAValue = false;

        do
        {
            // show the prompt
            System.out.print(prompt + ": ");
            // input the data
            value = pipe.nextLine();
            // test to see if it is correct
            if(value.matches(regEx))
            {
                // We have a match this String passes!
                gotAValue = true;
            }
            else
            {
                System.out.println("\nInvalid input: " + value);
            }

        }while(!gotAValue);

        return value;
    }

    /**
     *
     * @param msg message to be centered across 60 characters
     */
    public static void prettyHeader(String msg){
        int length = msg.length();
        int blank = (54-length)/2;
        //first row of stars
        for (int x = 1; x <= 60; x++)
            System.out.print("*");
       // first 3 stars
        System.out.println();
        for (int i = 1; i <= 3; i++)
            System.out.print("*");
        //blanks before message
        for (int i = 1; i <= blank; i++)
            System.out.print(" ");
        //message
        System.out.print(msg);
        //blanks after message
        for (int i = 1; i <= (54-length-blank); i++)
            System.out.print(" ");
        //last 3 stars
        for (int i = 1; i <= 3; i++)
            System.out.print("*");
        System.out.println();
        //last row of stars
        for (int x = 1; x <= 60; x++)
            System.out.print("*");
    }

}
