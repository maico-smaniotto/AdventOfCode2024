package Day3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

class Day3 {
    // Regular expression pattern to match "mul(1,2)" where 1 and 2 are integers with 1 to 3 digits
    private static final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    // Compile the pattern
    private static Pattern pattern;

    public static void main(String[] args) {
        // Compile the pattern
        pattern = Pattern.compile(regex);
        part1();
        part2();
    }

    private static void part1() {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day3\\test1.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day3\\input.txt";
        long result = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Create a matcher for the input string
                result += compute(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(result);
    }

    private static void part2() {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day3\\test2.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day3\\input.txt";

        StringBuilder allTokens = new StringBuilder();

        List<String> validInstructions = new ArrayList<>();

        final String dontToken = "don't()";
        final String doToken = "do()";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allTokens.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String strTokens = allTokens.toString();
        do {
            // Find the position of the first dontToken occurrence
            int position = strTokens.indexOf(dontToken);
            if (position != -1) {
                // consider as valid the string before the dontToken
                validInstructions.add(strTokens.substring(0, position));
                // update the string cutting off the processed part
                strTokens = strTokens.substring(position + dontToken.length());

                // Find the position of the doToken occurrence
                position = strTokens.indexOf(doToken);
                if (position != -1) {
                    // update the string cutting off the processed part
                    strTokens = strTokens.substring(position + doToken.length());
                } else {
                    // doToken not found, the rest of the string is invalid
                    break;
                }
            } else {
                // dontToken not found, the entire string is valid
                validInstructions.add(strTokens);
                break;
            }
        } while (true);

        long result = 0;
        for (String instr: validInstructions) {
            result += compute(instr);
        }

        System.out.println(result);
    }

    private static long compute(String line) {
        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(line);
        long result = 0;
        // Find all matches
        while (matcher.find()) {
            // Get the first number
            String str1 = matcher.group(1);
            int num1 = Integer.parseInt(str1);

            // Get the second number
            String str2 = matcher.group(2);
            int num2 = Integer.parseInt(str2);

            result += (long) num1 * num2;
        }
        return result;
    }
}