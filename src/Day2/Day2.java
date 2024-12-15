package Day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

class Day2 {
//    private static final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day2\\test.txt";
    private static final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day2\\input.txt";

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        long nSafe = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (isSafe(parts))
                    nSafe++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(nSafe);
    }

    private static void part2() {
        long nSafe = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (isDampenerSafe(parts))
                    nSafe++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(nSafe);
    }

    private static boolean isSafe(String[] parts) {
        if (parts.length <= 1) return false;

        int num1 = Integer.parseInt(parts[0].trim());
        int num2 = Integer.parseInt(parts[1].trim());

        if (num1 == num2) return false;

        boolean increment = num2 > num1;
        for (int i = 1; i < parts.length; i++) {
            num2 = Integer.parseInt(parts[i].trim());
            num1 = Integer.parseInt(parts[i - 1].trim());

            int diff = abs(num2 - num1);
            if (!(diff >= 1 && diff <= 3)) {
                return false;
            }

            if (increment) {
                if (num2 < num1) {
                    return false;
                }
            } else {
                if (num2 > num1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isDampenerSafe(String[] parts) {
        if (isSafe(parts)) return true;

        List<String> list;

        for (int i = 0; i < parts.length; i++) {
            list = new ArrayList<>(Arrays.asList(parts));
            list.remove(i);
            String[] arr = list.toArray(new String[0]);
            if (isSafe(arr)) return true;
        }
        return false;
    }
}