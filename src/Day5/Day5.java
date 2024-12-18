package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Day5 {
    public static List<String> rules;
    public static List<String> pageList;

    public static void main(String[] args) {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day5\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day5\\input.txt";

        rules = new ArrayList<>();
        pageList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("|"))
                    rules.add(line);
                else if (line.contains(","))
                    pageList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        part1();
        part2();
    }

    private static void part1() {
        long result = 0;
        for (String pages: pageList) {
            String[] pagesArray = pages.split(",");
            if (pagesAreValid(pagesArray, rules)) {
                int pageNum = getMiddlePageNumber(pagesArray);
                result += pageNum;
            }
        }
        System.out.println(result);
    }

    private static void part2() {
        long result = 0;
        for (String pages: pageList) {
            String[] pagesArray = pages.split(",");
            if (!pagesAreValid(pagesArray, rules)) {
                arrangePages(pagesArray, rules);
                int pageNum = getMiddlePageNumber(pagesArray);
                result += pageNum;
            }
        }
        System.out.println(result);
    }

    private static boolean pagesAreValid(String[] pagesArray, List<String> rules) {
        for (String rule: rules) {
            String[] rulePair = rule.split("\\|");
            int index0 = findPageIndex(pagesArray, rulePair[0]);
            if (index0 == -1) continue;
            int index1 = findPageIndex(pagesArray, rulePair[1]);
            if (index1 == -1) continue;
            if (index0 > index1) return false;
        }
        return true;
    }

    public static int findPageIndex(String[] pagesArray, String page) {
        for (int i = 0; i < pagesArray.length; i++) {
            if (pagesArray[i].equals(page)) {
                return i;
            }
        }
        return -1;
    }

    private static int getMiddlePageNumber(String[] pagesArray) {
        return Integer.parseInt(pagesArray[pagesArray.length / 2]);
    }

    private static void arrangePages(String[] pagesArray, List<String> rules) {
        do {
            for (String rule: rules) {
                String[] rulePair = rule.split("\\|");
                int index0 = findPageIndex(pagesArray, rulePair[0]);
                if (index0 == -1) continue;
                int index1 = findPageIndex(pagesArray, rulePair[1]);
                if (index1 == -1) continue;
                if (index0 > index1) {
                    String tmp = pagesArray[index0];
                    pagesArray[index0] = pagesArray[index1];
                    pagesArray[index1] = tmp;
                }
            }
        } while (!pagesAreValid(pagesArray, rules));
    }
}