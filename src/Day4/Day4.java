package Day4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

class Day4 {
    public static void main(String[] args) {
        // Compile the pattern
        part1();
        part2();
    }

    private static void part1() {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day4\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day4\\input.txt";

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String word = "XMAS";
        long result = 0;

        for (int l = 0; l < lines.size(); l++) {
            for (int c = 0; c < lines.get(l).length(); c++) {
                if (rightLine(lines, l, c, word)) result++;
                if (rightDiagonalBottom(lines, l, c, word)) result++;
                if (bottomLine(lines, l, c, word)) result++;
                if (leftDiagonalBottom(lines, l, c, word)) result++;
                if (leftLine(lines, l, c, word)) result++;
                if (leftDiagonalTop(lines, l, c, word)) result++;
                if (topLine(lines, l, c, word)) result++;
                if (rightDiagonalTop(lines, l, c, word)) result++;
            }
        }
        System.out.println(result);
    }

    private static void part2() {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day4\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day4\\input.txt";

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long result = 0;

        for (int l = 0; l < lines.size(); l++) {
            for (int c = 0; c < lines.get(l).length(); c++) {
                if (xMas(lines, l, c)) result++;
            }
        }
        System.out.println(result);
    }

    private static boolean rightLine(List<String> lines, int l, int c, String word) {
        if (!(c <= lines.get(l).length() - word.length())) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l).charAt(c + offset)) return false;
        }
        return true;
    }
    private static boolean rightDiagonalBottom(List<String> lines, int l, int c, String word) {
        if (!(c <= lines.get(l).length() - word.length())) return false;
        if (!(l <= lines.size() - word.length())) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l + offset).charAt(c + offset)) return false;
        }
        return true;
    }

    private static boolean bottomLine(List<String> lines, int l, int c, String word) {
        if (!(l <= lines.size() - word.length())) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l + offset).charAt(c)) return false;
        }
        return true;
    }

    private static boolean leftDiagonalBottom(List<String> lines, int l, int c, String word) {
        if (!(c >= word.length() - 1)) return false;
        if (!(l <= lines.size() - word.length())) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l + offset).charAt(c - offset)) return false;
        }
        return true;
    }

    private static boolean leftLine(List<String> lines, int l, int c, String word) {
        if (!(c >= word.length() - 1)) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l).charAt(c - offset)) return false;
        }
        return true;
    }

    private static boolean leftDiagonalTop(List<String> lines, int l, int c, String word) {
        if (!(c >= word.length() - 1)) return false;
        if (!(l >= word.length() - 1)) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l - offset).charAt(c - offset)) return false;
        }
        return true;
    }

    private static boolean topLine(List<String> lines, int l, int c, String word) {
        if (!(l >= word.length() - 1)) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l - offset).charAt(c)) return false;
        }
        return true;
    }

    private static boolean rightDiagonalTop(List<String> lines, int l, int c, String word) {
        if (!(c <= lines.get(l).length() - word.length())) return false;
        if (!(l >= word.length() - 1)) return false;
        for (int offset = 0; offset < word.length(); offset++) {
            if (word.charAt(offset) != lines.get(l - offset).charAt(c + offset)) return false;
        }
        return true;
    }

    private static boolean xMas(List<String> lines, int l, int c) {
        if (!(c >= 1)) return false;
        if (!(c < lines.get(l).length() - 1)) return false;
        if (!(l >= 1)) return false;
        if (!(l < lines.size() - 1)) return false;

        boolean valid = lines.get(l).charAt(c) == 'A';
        valid = valid && (
                (lines.get(l - 1).charAt(c - 1) == 'M' && lines.get(l + 1).charAt(c + 1) == 'S')
                || (lines.get(l - 1).charAt(c - 1) == 'S' && lines.get(l + 1).charAt(c + 1) == 'M')
        ) && (
                (lines.get(l - 1).charAt(c + 1) == 'M' && lines.get(l + 1).charAt(c - 1) == 'S')
                || (lines.get(l - 1).charAt(c + 1) == 'S' && lines.get(l + 1).charAt(c - 1) == 'M')
        );
        return valid;
    }
}