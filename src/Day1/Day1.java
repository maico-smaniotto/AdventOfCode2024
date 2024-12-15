package Day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

class Day1 {
    private static List<Integer> listL;
    private static List<Integer> listR;

    public static void main(String[] args) {
        listL = new ArrayList<>();
        listR = new ArrayList<>();

        part1();
        part2();
    }

    public static void insertSorted(List<Integer> list, int value) {
        int index = Collections.binarySearch(list, value);
        if (index < 0) { // If the value is not found, binarySearch returns (-(insertion point) - 1)
            index = -index - 1;
        }
        list.add(index, value); // Add the element at the correct position
    }

    private static void part1() {
        listL = new ArrayList<>();
        listR = new ArrayList<>();

//        String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day1\\test.txt";
        String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day1\\input.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line using exactly three spaces as a delimiter
                String[] parts = line.split(" {3}");
                int num1 = Integer.parseInt(parts[0].trim());
                int num2 = Integer.parseInt(parts[1].trim());

                insertSorted(listL, num1);
                insertSorted(listR, num2);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long value = 0;

        for (int i = 0; i < listL.size(); i++) {
            value += abs(listL.get(i) - listR.get(i));
        }

        System.out.println(value);
    }

    private static void part2() {
        Map<Integer, Long> occurrences = listR.stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        long total = 0;

        for (int key: listL) {
            if (occurrences.containsKey(key)) {
                total += key * occurrences.get(key);
            }
        }

        System.out.println(total);
    }
}