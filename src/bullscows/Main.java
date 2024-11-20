package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main{
    static int cows = 0;
    static int bulls = 0;
    static Scanner sc = new Scanner(System.in);
    final static Random rand = new Random();
    static String code;
    static int length;

    public static void main(String[] args) {

        System.out.println("Input the length of the secret code:");
        try {
            String input = sc.nextLine();
            length = Integer.parseInt(input);
            if (length > 36 || length <= 0) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", length);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a positive integer.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int symbols = sc.nextInt();
        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z");
            return;
        } else if (symbols < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, symbols);
            return;
        }

        System.out.print("The secret is prepared: ");
        countingSymbols(symbols);

        code = randomSequence(length, symbols);
        System.out.println("\nOkay, let's start a game!");
        firstPath();
    }

    public static String randomSequence(int length, int symbols) {
        StringBuilder codeBuilder = new StringBuilder();
        char[] allSymbols = generateAllSymbols(symbols);

        while (codeBuilder.length() < length) {
            char symbol = allSymbols[rand.nextInt(symbols)];

            if (codeBuilder.toString().contains(String.valueOf(symbol))) {
                continue;
            }
            codeBuilder.append(symbol);
        }

        return codeBuilder.toString();
    }

    public static char[] generateAllSymbols(int symbols) {
        char[] allSymbols = new char[symbols];
        int index = 0;

        for (char i = '0'; i <= '9' && index < symbols; i++) {
            allSymbols[index++] = i;
        }

        for (char i = 'a'; i <= 'z' && index < symbols; i++) {
            allSymbols[index++] = i;
        }

        return allSymbols;
    }

    public static void firstPath() {
        int turn = 1;
        String attemptCode;

        while (true) {
            System.out.printf("Turn %d:%n> ", turn);
            attemptCode = sc.next();

            bulls = 0;
            cows = 0;

            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == attemptCode.charAt(i)) {
                    bulls++;
                } else if (code.contains(String.valueOf(attemptCode.charAt(i)))) {
                    cows++;
                }
            }

            System.out.printf("Grade: %d bull%s and %d cow%s%n", bulls, (bulls == 1 ? "" : "s"), cows, (cows == 1 ? "" : "s"));

            if (bulls == code.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            turn++;
        }
    }

    public static void countingSymbols(int symbols) {
        char[] allSymbols = generateAllSymbols(symbols);

        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }

        if (symbols == 1) {
            System.out.println(" (0)");
        } else if (symbols <= 10) {
            System.out.printf(" (0-%c)%n", allSymbols[symbols - 1]);
        } else {
            System.out.printf(" (0-9, a-%c)%n", allSymbols[symbols - 1]);
        }
    }
}