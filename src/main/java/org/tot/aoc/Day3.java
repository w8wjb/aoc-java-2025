package org.tot.aoc;

import java.util.List;

public class Day3 {

    char[] digits = "9876543210".toCharArray();

    private long largestJoltage(String line) {
        for (char left : digits) {
            int leftIndex = line.indexOf(left);
            if (leftIndex < 0) {
                continue;
            }

            char max = 0;
            for (int i = line.length() - 1; i > leftIndex; i--) {
                char right = line.charAt(i);
                if (right > max) {
                    max = right;
                }
            }
            if (max != 0) {
                return Long.parseLong("" + left + max);
            }
        }
        return 0;
    }

    public long solvePuzzle1(List<String> input) {


        long totalOutputJoltage = 0;

        for (String line : input) {
            totalOutputJoltage += largestJoltage(line);
            ;
        }

        return totalOutputJoltage;
    }


    /**
     * @param input list of lines from file
     * @param jlen  desired length of the joltage number
     */
    public long solvePuzzle2(List<String> input, int jlen) {

        long totalJoltage = 0;

        char[] joltageChars = new char[jlen];
        int[] sourceIndexes = new int[jlen];

        for (String line : input) {

            char[] allDigits = line.toCharArray();

            // Fill sourceIndexes with the last X characters of the input
            for (int i = 0; i < jlen; i++) {
                int sourceIndex = allDigits.length - jlen + i;
                sourceIndexes[i] = sourceIndex;
            }

            // Now we have the 'worst case' scenario loaded in sourceIndex.
            // We must have 12 digits, so worst case would be the last 12 digits

            int prevIndex = -1;

            for (int digit = 0; digit < jlen; digit++) {

                int index = sourceIndexes[digit];
                char max = allDigits[index];

                // For each digit, we're going to see if we can find a larger one to the left
                // But we can only slide left until we reach the previous digit
                for (int check = index; check > prevIndex; check--) {
                    char c = allDigits[check];
                    if (c >= max) {
                        max = c;
                        index = check;
                    }
                }

                prevIndex = index;

                joltageChars[digit] = max;
            }

            long joltage = Long.parseLong(String.valueOf(joltageChars));
            totalJoltage += joltage;
        }


        return totalJoltage;
    }

}
