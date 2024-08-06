import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

class Main {

    //HashMap variable to hold the Diceware Word List, method call to fill the HashMap with the Word List.
    public static HashMap<Integer, String> dicewareWordList = getDicewareHashMap();
    //Scanner object for use in reading user input.
    public static Scanner input = new Scanner(System.in);

    //Custom Exception used to allow the user to exit the program whenever they would like.
    public static class ExitProgramException extends Exception {
    }

    //Utility method to check if a given String is an integer or not using a try-catch block to see if attempting to parse an integer from a string throws a NumberFormatException or not.
    public static boolean isInteger(String input) {
        try {
            parseInt(input);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    //Accessor method to generate a random Diceware number (a five-digit number that can be rolled with dice).
    public static int getDicewareNumber() {
        int dicewareNumber = 0;
        for (int i = 1; i < 100000; i *= 10){
            dicewareNumber += ((int)(Math.random() * 6) + 1) * i;
        }
        return dicewareNumber;
    }

    //Accessor method to generate the Diceware Word List, reading it from a .txt file and storing it in a HashMap. The file name must be changed if a different file is used. This method is only called once, and the return is put into a static HashMap variable, so that the HashMap isn't having to be generated over and over throughout the use of the program.
    public static HashMap<Integer, String> getDicewareHashMap() {
        String fileName = "EFF Diceware Word List.txt";
        HashMap<Integer, String> tempWordList = new HashMap<>();
        //Try-catch block to make sure the .txt file is found that contains the Diceware Word, otherwise a FileNotFoundException is thrown.
        try {
            //Code block to read the .txt file, split the lines into two (into key-value pairs), and then store the key-value pairs in a temporary HashMap that is returned when this method is called.
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                String[] parts = tempLine.split(" ");
                int keyHolder = Integer.parseInt(parts[0]);
                String valueHolder = parts[1];
                tempWordList.put(keyHolder, valueHolder);
            }
            reader.close();
        } catch (Exception ex) {
            //noinspection CallToPrintStackTrace
            ex.printStackTrace();
        }
        return tempWordList;
    }

    //Accessor method to look up a value (word) in the Diceware Word List HashMap given a specific key (Diceware number).
    public static String getDicewareWord(int dicewareNumber) {
        return dicewareWordList.get(dicewareNumber);
    }

    //Accessor method to generate a Diceware passphrase based on the user's desired passphrase length.
    public static String getDicewarePassphrase(int passphraseLength) {
        StringBuilder dicewarePassphrase = new StringBuilder();
        for (int i = 1; i <= passphraseLength; i++) {
            //Conditionals that test if the loop is generating the first or subsequent word of the passphrase, so that it can properly display the passphrase without a space at the very beginning.
            if (i == 1) {
                dicewarePassphrase = new StringBuilder(getDicewareWord(getDicewareNumber()));
            }
            else {
                dicewarePassphrase.append(" ").append(getDicewareWord(getDicewareNumber()));
            }
        }
        return dicewarePassphrase.toString();
    }

    //Method for the first major component of the program, Diceware Passphrase Generator. This method works with the getDicewarePassphrase method to query the user for their desired passphrase length, generate the passphrase, and the print it out to the user. It also can throw the ExitProgramException exception to allow the user to exit the program.
    public static void dicewarePassphraseGenerator() throws ExitProgramException {
        while (true) {
            System.out.print("\n\n\n\nHow many words would you like in your passphrase? (Input a numeric value): ");
            String component1Input = input.nextLine();
            //Nested conditionals that test if the user's input is an integer, and if it is an integer, checks if it's greater than 0, and then generates a passphrase based on that input. The user can input "home" or "exit" to go home or exit the program, and all other inputs are invalid.
            if (isInteger(component1Input)) {
                if (parseInt(component1Input) > 0) {
                    System.out.print("\n\nHere is your passphrase: " + "\u001B[32m\u001B[1m" +
                            getDicewarePassphrase(parseInt(component1Input)) + "\u001B[0m");
                }
                else { System.out.print("\n\n\u001B[31mInvalid input. Try again.\u001B[0m"); }
            }
            else if (component1Input.equals("home")){
                break;
            }
            else if (component1Input.equals("exit")){
                throw new ExitProgramException();
            }
            else {
                System.out.print("\n\n\u001B[31mInvalid input. Try again.\u001B[0m");
            }
        }
    }

    //Method for the second major component of the program, Diceware Random Number Generator. This method works with the getDicewareNumber method to randomly generate a Diceware number and print out the number to the user.
    public static void dicewareRandomNumberGenerator() throws ExitProgramException {
        while (true) {
            System.out.print("\n\n\n\nPress the Enter key to generate a random number: ");
            String component2Input = input.nextLine();
            //Conditional that tests if the user pressed the Enter key without inputting anything. If they did, then another random Diceware number will be generated. The user can also input "home" or "exit" to go home or exit the program, and all other inputs are invalid.
            if (component2Input.isEmpty()) {
                System.out.print("\n\nHere is your number: " + "\u001B[32m\u001B[1m" +
                        getDicewareNumber() + "\u001B[0m" + " ");
            }
            else if (component2Input.equals("home")) { break; }
            else if (component2Input.equals("exit")) { throw new ExitProgramException(); }
            else { System.out.print("\n\n\u001B[31mInvalid input, try again.\u001B[0m"); }
        }
    }

    //Method for the third major component of the program, Diceware Word Lookup Tool. This method works with the getDicewareWord method to allow the user to supply a diceware number (key) to look up the associated word in the Diceware word list HashMap, and print out the word to the user.
    public static void dicewareWordLookupTool() throws ExitProgramException {
        while (true) {
            System.out.print("""




                    What five-digit Diceware number would you like to look up the associated word for? (Input a\s
                    numeric value):\s""");
            String component3Input = input.nextLine();
            //Nested conditionals that test if the user's input is an integer, and then if passing the integer as an argument to the getDicewareWord method returns "null" or not. If it doesn't return "null" the associated value in the word list HashMap is printed to the user. If it does return null, the user is told to use a valid input. The user can also input "home" or "exit" to go home or exit the program, and all other inputs are invalid.
            if (isInteger(component3Input)) {
                int parsedInteger = parseInt(component3Input);
                if (getDicewareWord(parsedInteger) != null)
                    System.out.print("\n\nHere is the associated value from the Diceware Word List: " + "\u001B[32m\u001B[1m" +                        getDicewareWord(parsedInteger) + "\u001B[0m");
                else { System.out.print("\n\n\u001B[31mInvalid input, try again. Please enter a five-digit number using digits 1 to 6 only.\u001B[0m"); }
            }
            else if (component3Input.equals("home")) { break; }
            else if (component3Input.equals("exit")) { throw new ExitProgramException(); }
            else { System.out.print("\n\n\u001B[31mInvalid input, try again.\u001B[0m"); }
        }
    }

    //Method to store the greeting for the program that prints out to the user whenever the program starts.
    public static void offlineDicewareToolGreeting() {
        System.out.print("\n\n\u001B[1m\u001B[4mWelcome to Offline Diceware Tool!\u001B[0m\n\n\n");
        System.out.print("""
                Diceware is a method for creating passphrases by using physical dice as a random number
                generator. Diceware passphrases are cryptographically strong, while also remaining easy to memorize,
                unlike randomly generated character strings. Generating a Diceware passphrase with dice involves
                rolling five dice (or one die five times) to get a five digit number, and then looking up the number
                in a Diceware word list. You then repeat the process until you have as many words in your passphrase
                as you would like. The longer the passphrase, the stronger the passphrase. This program's random number
                generator does not use physical dice, but instead programmatically simulates dice rolls to achieve
                the same effect. Note, that the pseudorandom number generation of this program does not have the
                same randomness as rolling physical dice, but still offers a high level of randomness and a more
                convenient experience compared to rolling physical dice.
                
                """);
        System.out.print("-----------------------------------------------------------------------------------------------------\n\n");
        System.out.print("\u001B[1m\u001B[4mFeatures:\u001B[0m\n\n\n");
        System.out.print("This program has three components.\n\n\n");
        System.out.print("1) Diceware Passphrase Generator\n\n");
        System.out.print("""
                This component will generate Diceware passphrases for you after prompting you to choose the passphrase
                word length. The Passphrase Generator combines the other two components of this program in order to
                generate random Diceware passphrases.


                """);
        System.out.print("2) Diceware Random Number Generator\n\n");
        System.out.print("""
                This component simulates five die rolls and produces a random, five-digit number for Diceware use.
                If you would like to use your own Diceware word list, you can use this feature to generate Diceware
                numbers.


                """);
        System.out.print("3) Diceware Word Lookup Tool\n\n");
        System.out.print("""
                This component allows you to use your own five-digit numbers to lookup the associated word on the
                Diceware Word List, if you would like to use actual physical dice to roll your numbers or your own
                number generation method. Note, this program uses EFF's Diceware Word List, specifically.


                """);
        System.out.print("------------------------------------------------------------------------------------------------------\n\n\n");
        System.out.print("At any time, you can input 'home' to return to the component selection, or 'exit' to exit the program.");
    }

    //The Main Method
    public static void main(String[] args) {

        //try-catch block to catch the ExitProgramException, which allows the user to exit the program whenever they want.
        try {
            //Method call to the greeting method, to print out the program greeting before the home loop.
            offlineDicewareToolGreeting();
            //The home loop is where the meat of the program resides, and lets the user come back home and use another component of the program if they want to.
            //noinspection InfiniteLoopStatement
            while (true) {

                //This part asks the user which component listed in the program greeting they would like to use, and the stores the user's input in a String variable.
                System.out.print("\n\nWhat component of the program would you like to use? 1, 2, or 3: ");
                String homeInput = input.nextLine();

                //This switch block handles the different possible options that the user can input on the home screen.
                switch (homeInput) {
                    //This case is for the first major component of the program, Diceware Passphrase Generator.
                    case "1" -> {
                        System.out.print("\n\n------------------------------------------------------------------------------------------------------");
                        System.out.print("\n\n\n\u001B[1m\u001B[4mDiceware Passphrase Generator\u001B[0m");
                        dicewarePassphraseGenerator();
                    }
                    //This case is for the second major component of the program, Diceware Random Number Generator.
                    case "2" -> {
                        System.out.print("\n\n------------------------------------------------------------------------------------------------------");
                        System.out.print("\n\n\n\u001B[1m\u001B[4mDiceware Random Number Generator\u001B[0m");
                        dicewareRandomNumberGenerator();
                    }
                    //This case is for the third major component of the program, Diceware Word Lookup Tool.
                    case "3" -> {
                        System.out.print("\n\n------------------------------------------------------------------------------------------------------");
                        System.out.print("\n\n\n\u001B[1m\u001B[4mDiceware Word Lookup Tool\u001B[0m");
                        dicewareWordLookupTool();
                    }
                    //This case checks if the user typed in "home", and tells them that they are already home
                    case "home" -> System.out.print("\nYou are already home, try a different input.");

                    //This case checks if the user typed in "exit", and throws the ExitProgramException.
                    case "exit" -> throw new ExitProgramException();

                    //The default case tells the user that their input is invalid, if none of the valid inputs occurred.
                    default -> System.out.print("\n\u001B[31mInvalid input, try again.\u001B[0m");
                }
            }
        }
        //The ExitProgramException catch block that lets the user exit the program. Ends the program entirely by closing the scanner and printing out a statement to the user that tells them that the program was exited.
        catch (ExitProgramException ex) {
            input.close();
            System.out.print("\n\n\n\n\nProgram exited.");
        }
    }
}