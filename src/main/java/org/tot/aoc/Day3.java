package org.tot.aoc;

import java.util.List;

public class Day3 {

    char[] digits = "9876543210".toCharArray();

    private long largestJoltage(String line) {
        for (char left : digits) {

            // The concept here is that the maximum value is '99'. The first number is obviously more
            // important because it's in the 10's place, so we find that first.
            // Once we find the largest number on the left, we scan the remaining digits from the right

            // Starting with 9 and counting down, find the first instance of digit from the left.
            int leftIndex = line.indexOf(left);
            if (leftIndex < 0) {
                // If it wasn't found, go to the next number down
                continue;
            }

            // Once we find that, then look for the largest digit that appears to the right, starting from the end
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

        int digitCount = input.get(0).length();
        int[] sourceIndexesInit = new int[jlen];
        for (int i = 0; i < sourceIndexesInit.length; i++) {
            sourceIndexesInit[i] = digitCount - jlen + i;
        }

        for (String line : input) {

            char[] allDigits = line.toCharArray();

            // Reset sourceIndexes to the indexes of last X characters of the input
            System.arraycopy(sourceIndexesInit, 0, sourceIndexes, 0, sourceIndexes.length);

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

            long joltage = 0L;
            for (int i = 0; i < jlen; i++) {
                joltage = joltage * 10 + (joltageChars[i] - '0');
            }
            totalJoltage += joltage;
        }


        return totalJoltage;
    }

}
