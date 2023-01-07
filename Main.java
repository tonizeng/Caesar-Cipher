import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        boolean validEntry = false;
        boolean validInt = false;
        boolean run = true;
        String encodedStr = "";
        String decodedStr = "";
        String shiftR = "";
        String shiftL = "";

        while (run == true) {
            System.out.println("Encode (e) Decode (d) BruteForce (b) Quit (q): ");
            String select = keyboard.nextLine();
            validEntry = validMenuOption(select);

            if (select.length() == 0) {
                System.out.println("Please enter a non empty string.");
            } else if (validEntry == false) {
                System.out.println("Please enter a valid menu option.");
            } else {
                switch (select) {
                    case "e": // encode

                        encodedStr = "";
                        shiftR = "";

                        while (encodedStr.length() == 0) {

                            System.out.print("Phrase to encode: ");
                            encodedStr = keyboard.nextLine();

                            if (encodedStr.length() != 0) {

                                while (shiftR.length() == 0) {

                                    System.out.print("shift right by?: ");
                                    shiftR = keyboard.nextLine();

                                    if (shiftR.length() != 0) {
                                        validInt = validInt(shiftR);

                                        if (validInt == false) {
                                            System.out.println("Enter valid shift.");
                                            shiftR = "";
                                        } else {
                                            String shiftedEnc = code(encodedStr, Integer.parseInt(shiftR));
                                            System.out.println(shiftedEnc);
                                        }
                                    } else {
                                        System.out.println("Enter valid shift.");
                                    }
                                }
                            } else {
                                System.out.println("Please enter a valid phrase.");
                            }
                        }
                        break;

                    case "d": { // decode

                        decodedStr = "";
                        shiftL = "";

                        while (decodedStr.length() == 0) {

                            System.out.print("Phrase to decode: ");
                            decodedStr = keyboard.nextLine();

                            if (decodedStr.length() != 0) {

                                while (shiftL.length() == 0) {

                                    System.out.print("shift left by?: ");
                                    shiftL = keyboard.nextLine();

                                    if (shiftL.length() != 0) {
                                        validInt = validInt(shiftL);

                                        if (validInt == false) {
                                            System.out.println("Enter valid shift.");
                                            shiftL = "";
                                        } else {
                                            String shiftedDec = code(decodedStr, Integer.parseInt(shiftL) * -1);
                                            System.out.println(shiftedDec);
                                        }
                                    } else {
                                        System.out.println("Enter valid shift.");
                                    }
                                }
                            } else {
                                System.out.println("Please enter a valid phrase.");
                            }
                        }
                    }
                    break;

                    case "b": // brute force
                        System.out.print("Phrase to Brute Force: ");
                        String bruteStr = keyboard.nextLine();
                        String[] bruteForce = new String[26];
                        bruteForce = BruteForce(bruteStr);
                        int bestLocation = mostValidStr(bruteForce);

                        for (int i = 0; i < bruteForce.length; i++) {
                            System.out.println("For a shift of " + (i) + ", decoded is:  " + bruteForce[i]);
                        }

                        System.out.println("The best decode was with key " + (bestLocation));
                        System.out.println("Decoded message is: " + bruteForce[bestLocation]);

                        break;

                    case "q": // quit
                        run = false;
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static String code(String code, int shift) { // encodes or decodes a message
        String codedStr = "";
        for (int i = 0; i < code.length(); i++) {
            int numVal = code.charAt(i);

            if (numVal >= 65 && numVal <= 90) {
                numVal = numVal + shift;
                if (numVal > 90) {
                    numVal = numVal - 26;
                }
                if (numVal < 65) {
                    numVal = numVal + 26;
                }
            }

            if (numVal >= 97 && numVal <= 122) {
                numVal = numVal + shift;
                if (numVal > 122) {
                    numVal = numVal - 26;
                }
                if (numVal < 97) {
                    numVal = numVal + 26;
                }
            }

            char asciiToChar = (char) numVal;

            codedStr = codedStr + asciiToChar;
        }
        return codedStr;
    }
    public static String[] BruteForce(String bruteStr) { // applies all 26 shifts
        String codeStr[] = new String[26];

        for (int shift = 0; shift < 26; shift++) {
            codeStr[shift] = code(bruteStr, shift * -1);
        }
        return codeStr;
    }
    public static int mostValidStr(String[] bruteStr) { // determines the string with the decoded message that is most likely
        int bestCount = 0;
        String[] bigrams = {"th", "he", "in", "en", "nt", "re", "er", "an", "ti", "es", "on", "at",
                "se", "nd", "or", "ar", "al", "te", "co", "de", "to", "ra", "et", "ed", "it", "sa", "em", "ro"};
        int[] bigramCount = new int[26];
        int location = 0;

        for (int i = 0; i < 26; i++) {
            for (int b = 0; b < bigrams.length; b++) {
                int findBigram = bruteStr[i].toLowerCase().indexOf(bigrams[b]);
                while (findBigram >= 0) {
                    findBigram = bruteStr[i].toLowerCase().indexOf(bigrams[b], findBigram + 1);
                    bigramCount[i]++;
                }
            }
            if (bigramCount[i] > bestCount) {
                bestCount = bigramCount[i];
                location = i;
            }
        }
        return location;

    }

    public static boolean validMenuOption(String input) { // verifies that the value entered is a menu option
        boolean entryValid = false;

        if (input.equals("e") || input.equals("d") || input.equals("b") || input.equals("q")) {
            entryValid = true;
        }

        return entryValid;
    }

    public static boolean validInt(String shift) { // checks if the shift is a valid int, and between 0 and 25
        boolean isInt = false;
        int validInt;
        try {
            validInt = Integer.parseInt(shift);
            if (validInt >= 0 && validInt <= 25) {
                isInt = true;
            } else {
                isInt = false;
            }
        } catch (NumberFormatException e) {
            isInt = false;
        }
        return isInt;
    }
}
