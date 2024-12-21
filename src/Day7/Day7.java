package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Day7 {
    private static List<String[]> lines;

    public static void main(String[] args) {
//        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day7\\test.txt";
        final String filePath = "D:\\Desenv\\Git\\_maico-smaniotto\\AdventOfCode2024\\src\\Day7\\input.txt";

        lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Posição 0 é o resultado e a partir da posição 1 os operandos
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(": | ");
                lines.add(arr);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        part1();
        part2();
    }

    private static void part1() {
        long result = 0;
        for (String[] arr: lines) {
            // Quantas operações a serem efetuadas entre os operandos
            int numOperations = arr.length - 2;
            // Gera a lista de possibilidades de operações
            ArrayList<char[]> listOperations = generateOperationsAddMultiply(numOperations);
            // Para cada possibilidade de sequência de operações
            for (char[] operations: listOperations) {
                long partialResult = Integer.parseInt(arr[1]);
                // Aplica as operações entre os valores
                for (int valIndex = 2; valIndex < arr.length; valIndex++) {
                    switch (operations[valIndex - 2]) {
                        case '+' -> partialResult += Integer.parseInt(arr[valIndex]);
                        case '*' -> partialResult *= Integer.parseInt(arr[valIndex]);
                    }
                }
                if (partialResult == Long.parseLong(arr[0])) {
                    result += partialResult;
                    break;
                }
            }
        }
        System.out.println(result);
    }

    private static void part2() {
        long result = 0;
        for (String[] arr: lines) {
            // Quantas operações a serem efetuadas entre os operandos
            int numOperations = arr.length - 2;
            // Operadores possíveis
            char[] possibleOperators = {'+', '*', '|'};
            // Através de permutação gera a lista de todas as possibilidades de operações
            ArrayList<char[]> listOperations = generateOperations(numOperations, possibleOperators);
            // Para cada possibilidade de sequência de operações
            for (char[] operations: listOperations) {
                long partialResult = Integer.parseInt(arr[1]);
                // Aplica as operações entre os valores
                for (int valIndex = 2; valIndex < arr.length; valIndex++) {
                    switch (operations[valIndex - 2]) {
                        case '+' -> partialResult += Integer.parseInt(arr[valIndex]);
                        case '*' -> partialResult *= Integer.parseInt(arr[valIndex]);
                        case '|' -> {
                            partialResult = Long.parseLong(Long.toString(partialResult) + arr[valIndex]);
                        }
                    }
                }
                if (partialResult == Long.parseLong(arr[0])) {
                    result += partialResult;
                    break;
                }
            }
        }
        System.out.println(result);
    }

    private static ArrayList<char[]> generateOperationsAddMultiply(int numOperations) {
        ArrayList<char[]> result = new ArrayList<>();
        // Total de permutações (2^n)
        int total = (int) Math.pow(2, numOperations);
        // Gera todas as possibilidades
        for (int i = 0; i < total; i++) {
            char[] operations = new char[numOperations];
            for (int bit = 0; bit < numOperations; bit++) {
                // Se o bit está definido considera multiplicação senão soma
                if ((i & (1 << bit)) != 0) {
                    operations[bit] = '*';
                } else {
                    operations[bit] = '+';
                }
            }
            result.add(operations);
        }
        return result;
    }

    private static ArrayList<char[]> generateOperations(int numOperations, char[] possibleOperators) {
        ArrayList<char[]> result = new ArrayList<>();
        // Total de permutações ((número de operadores)^número de operações)
        int total = (int) Math.pow(possibleOperators.length, numOperations);
        // Gera todas as possibilidades
        for (int i = 0; i < total; i++) {
            char[] operations = new char[numOperations];
            int temp = i;
            for (int j = 0; j < numOperations; j++) {
                operations[j] = possibleOperators[temp % possibleOperators.length];
                temp /= possibleOperators.length;
            }
            result.add(operations);
        }
        return result;
    }
}