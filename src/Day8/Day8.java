package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Day8 {
    private static List<String> lines;
    private static List<String> antinodeMap;
    private static List<String> antinodeMap2;

    public static void main(String[] args) {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day8\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day8\\input.txt";

        lines = new ArrayList<>();
        antinodeMap = new ArrayList<>();
        antinodeMap2 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                antinodeMap.add(String.valueOf('.').repeat(line.length()));
                antinodeMap2.add(String.valueOf('.').repeat(line.length()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        part1();
        part2();
    }

    private static void part1() {
        long result = 0;
        for (int l = 0; l < lines.size(); l++) {
            for (int c = 0; c < lines.get(l).length(); c++) {
                char val = lines.get(l).charAt(c);
                if (val == '.') continue;
                for (int l2 = l; l2 < lines.size(); l2++) {
                    for (int c2 = 0; c2 < lines.get(l2).length(); c2++) {
                        if (l2 == l && c2 <= c) continue; // Same line, only reads past the column index it's comparing
                        char val2 = lines.get(l2).charAt(c2);
                        if (val2 != val) continue;
                        if (markNewAntinode(antinodeMap, l - (l2 - l), c - (c2 - c))) {
                            result++;
                        }
                        if (markNewAntinode(antinodeMap, l2 + (l2 - l), c2 + (c2 - c))) {
                            result++;
                        }
                    }
                }
            }
        }
        for (String line: antinodeMap) {
            System.out.println(line);
        }
        System.out.println(result);
    }

    private static void part2() {
        long result = 0;
        for (int l = 0; l < lines.size(); l++) {
            for (int c = 0; c < lines.get(l).length(); c++) {
                char val = lines.get(l).charAt(c);
                if (val == '.') continue;
                for (int l2 = l; l2 < lines.size(); l2++) {
                    for (int c2 = 0; c2 < lines.get(l2).length(); c2++) {
                        if (l2 == l && c2 <= c) continue; // Same line, only reads past the column index it's comparing
                        char val2 = lines.get(l2).charAt(c2);
                        if (val2 != val) continue;
                        int offsetY = (l2 - l);
                        int offsetX = (c2 - c);
                        result += markNewAntinodesRecursivelyBackward(antinodeMap2, l, offsetY, c, offsetX);
                        result += markNewAntinodesRecursivelyForward(antinodeMap2, l2, offsetY, c2, offsetX);
                    }
                }
            }
        }
        for (String line: antinodeMap2) {
            System.out.println(line);
        }
        System.out.println(result);
    }

    private static boolean inBounds(List<String> map, int l, int c) {
        return l >= 0 && l < map.size() && c >= 0 && c < map.getFirst().length();
    }

    private static boolean markNewAntinode(List<String> map, int l, int c) {
        // Marks new antinode in the map if it's not here yet
        if (!inBounds(map, l, c)) return false;
        if (map.get(l).charAt(c) == '#') return false;

        map.set(l, replaceCharAt(map.get(l), c, '#'));
        return true;
    }

    private static String replaceCharAt(String str, int position, char newChar) {
        return str.substring(0, position) + newChar + str.substring(position + 1);
    }

    private static int markNewAntinodesRecursivelyBackward(List<String> map, int l, int offsetY, int c, int offsetX) {
        if (!inBounds(map, l, c)) return 0;

        int result = 0;
        if (markNewAntinode(map, l, c)) {
            result++;
        }
        result += markNewAntinodesRecursivelyBackward(map, l - offsetY, offsetY, c - offsetX, offsetX);
        return result;
    }

    private static int markNewAntinodesRecursivelyForward(List<String> map, int l, int offsetY, int c, int offsetX) {
        if (!inBounds(map, l, c)) return 0;

        int result = 0;
        if (markNewAntinode(map, l, c)) {
            result++;
        }
        result += markNewAntinodesRecursivelyForward(map, l + offsetY, offsetY, c + offsetX, offsetX);
        return result;
    }
}