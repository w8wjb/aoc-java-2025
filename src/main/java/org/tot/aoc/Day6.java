package org.tot.aoc;


import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {


    public long solvePuzzle1(List<String> input) {

        List<List<Long>> numbers = new ArrayList<>();

        String[] operators = new String[0];

        for (String line : input) {
            String[] cols = line.trim().split("\\s+");
            if (Character.isDigit(cols[0].charAt(0))) {
                List<Long> row = Arrays
                        .stream(cols)
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                numbers.add(row);
            } else {
                operators = cols;
            }
        }

        long[] resultCols = Arrays
                .stream(operators)
                .mapToLong((c) -> "*".equals(c) ? 1L : 0L)
                .toArray();


        for (List<Long> row : numbers) {

            for (int col = 0; col < row.size(); col++) {
                String op = operators[col];

                switch (op) {
                    case "*":
                        resultCols[col] *= row.get(col);
                        break;
                    case "+":
                        resultCols[col] += row.get(col);
                        break;
                }

            }
        }

        return Arrays.stream(resultCols).sum();
    }


    public long solvePuzzle2(List<String> input) {

        // Calculate the longest line. Not all lines have the same length
        int maxCols = input.stream().mapToInt(String::length).max().orElse(0);

        // Remove the last line, containing operators
        String operatorLine = input.remove(input.size() - 1);

        // A buffer to build up digits
        CharBuffer buffer = CharBuffer.allocate(input.size());

        // Initialize the current operator
        char op = '*';

        // grand total
        long total = 0;

        // accumulates the current running sum or product, depending on current operator
        long accum = 0;

        // Loop through each column
        for (int i = 0; i < maxCols; i++) {

            if (i < operatorLine.length()) { // bounds check
                char opIn = operatorLine.charAt(i);
                if (opIn == '*') {
                    op = opIn; // multiplication mode
                    accum = 1; // init accum to 1 because multiplying by zero would wipe out everything
                } else if (opIn == '+') {
                    op = opIn; // addition mode
                    accum = 0; // init to 0 for addition
                }
            }

            // Loop through each number row and get the digit (or space) at the column index i
            for (String row : input) {
                if (i < row.length()) {
                    char c = row.charAt(i);
                    if (c == ' ') {
                        // not adding spaces to the buffer is more efficient than using .strip() to remove them later
                        continue;
                    }
                    buffer.append(c);
                }
            }
            buffer.flip();
            String valueStr = buffer.toString();
            buffer.clear();

            if (valueStr.isBlank()) {
                // If we hit an all-blank row, we've reached the end of a group
                // and we can add it to the grand total
                total += accum;
                continue;
            }

            long value = Long.parseLong(valueStr);

            switch (op) {
                case '*':
                    accum *= value;
                    break;
                case '+':
                    accum += value;
                    break;
            }
        }

        // This is here because there's no last operator
        // to add the last accumulated value to the grand total
        total += accum;

        return total;

    }


    public long solvePuzzle2A(List<String> input) {

        // Calculate the longest line. Not all lines have the same length
        int maxCols = input.stream().mapToInt(String::length).max().orElse(0);

        // Remove the last line, containing operators
        String operatorLine = input.remove(input.size() - 1);

        // A buffer to build up digits
//        CharBuffer buffer = CharBuffer.allocate(input.size());

        // Initialize the current operator
        char op = '*';

        // grand total
        long total = 0;

        // accumulates the current running sum or product, depending on current operator
        long accum = 0;

        // Loop through each column
        for (int i = 0; i < maxCols; i++) {

            if (i < operatorLine.length()) { // bounds check
                char opIn = operatorLine.charAt(i);
                if (opIn == '*') {
                    op = opIn; // multiplication mode
                    accum = 1; // init accum to 1 because multiplying by zero would wipe out everything
                } else if (opIn == '+') {
                    op = opIn; // addition mode
                    accum = 0; // init to 0 for addition
                }
            }

            long value = 0;
            boolean hasDigit = false;

            // Loop through each number row and get the digit (or space) at the column index i
            for (String row : input) {
                if (i < row.length()) {
                    char c = row.charAt(i);
                    if (c == ' ') {
                        // not adding spaces to the buffer is more efficient than using .strip() to remove them later
                        continue;
                    }
                    hasDigit = true;
                    value = value * 10 + (c - '0');
                }
            }

            if (!hasDigit) {
                // If we hit an all-blank row, we've reached the end of a group
                // and we can add it to the grand total
                total += accum;
                continue;
            }

            switch (op) {
                case '*':
                    accum *= value;
                    break;
                case '+':
                    accum += value;
                    break;
            }
        }

        // This is here because there's no last operator
        // to add the last accumulated value to the grand total
        total += accum;

        return total;
    }

}
