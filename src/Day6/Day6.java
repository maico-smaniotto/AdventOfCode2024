package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Day6 {
    private enum Direction {UP, RIGHT, DOWN, LEFT};
    private static List<String> lines;
    private static int startX;
    private static int startY;
    private static Direction startDirection;

    public static void main(String[] args) {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day6\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day6\\input.txt";

        lines = new ArrayList<>();
        startX = -1;
        startY = -1;
        startDirection = Direction.UP;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = -1;
            while ((line = reader.readLine()) != null) {
                index++;
                if (startY == -1) {
                    if (line.contains("^")) {
                        startY = index;
                        startX = line.indexOf("^");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        part1();
        part2();
    }

    private static void part1() {
        int x = startX;
        int y = startY;
        Direction direction = startDirection;
        long result = 0;
        boolean complete = false;
        do {
            int newX = x;
            int newY = y;

            switch (direction) {
                case UP -> newY--;
                case RIGHT -> newX++;
                case DOWN -> newY++;
                case LEFT -> newX--;
            }
            // If new position is out of bounds mark as complete
            if (newY < 0 || newY >= lines.size() || newX < 0 || newX >= lines.getFirst().length())
                complete = true;

            if (!complete) {
                // Check if facing an obstacle then turn right
                if (lines.get(newY).charAt(newX) == '#') {
                    direction = turn90(direction);
                    continue;
                }
            }
            // Mark current position (if not marked already) and increment counter
            if (lines.get(y).charAt(x) != 'X') {
                lines.set(y, replaceCharAt(lines.get(y), x, 'X'));
                result++;
            }
            // update current position with new position
            y = newY;
            x = newX;
        } while (!complete);

        System.out.println(result);
    }

    private static void part2() {
        int result = 0;
        for (int l = 0; l < lines.size(); l++) {
            for (int c = 0; c < lines.get(l).length(); c++) {
                if (lines.get(l).charAt(c) != '#') {
                    putObstruction(lines, l, c, '#');
                    if (!completeMap(lines)) {
                        result++;
                    }
                    putObstruction(lines, l, c, '.');
                }
            }
        }
        System.out.println(result);
    }

    private static Direction turn90(Direction direction) {
        int nextIndex = (direction.ordinal() + 1) % Direction.values().length;
        return Direction.values()[nextIndex];
    }

    private static String replaceCharAt(String str, int position, char newChar) {
        if (position < 0 || position >= str.length()) {
            throw new IllegalArgumentException("Invalid character position");
        }
        return str.substring(0, position) + newChar + str.substring(position + 1);
    }

    private static void putObstruction(List<String> lines, int y, int x, char c) {
        lines.set(y, replaceCharAt(lines.get(y), x, c));
    }

    private static boolean completeMap(List<String> lines) {
        int x = startX;
        int y = startY;
        Direction direction = startDirection;
        boolean complete = false;
        int moves = 0;
        // define the limit of moves to find the way out of the map
        // this is an ugly bruteforce and very inefficient
        long limit = (long) lines.size() * lines.getFirst().length() * 10;
        do {
            moves++;
            int newX = x;
            int newY = y;

            switch (direction) {
                case UP -> newY--;
                case RIGHT -> newX++;
                case DOWN -> newY++;
                case LEFT -> newX--;
            }
            // If new position is out of bounds mark as complete
            if (newY < 0 || newY >= lines.size() || newX < 0 || newX >= lines.getFirst().length())
                complete = true;

            if (!complete) {
                // Check if obstacle then turn right
                if (lines.get(newY).charAt(newX) == '#') {
                    direction = turn90(direction);
                    continue;
                }
            }
            // update current position with new position
            y = newY;
            x = newX;
        } while (!complete && moves < limit);

        return complete;
    }
}