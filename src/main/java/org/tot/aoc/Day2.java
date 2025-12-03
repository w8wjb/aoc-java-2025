package org.tot.aoc;

import java.util.*;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

public class Day2 {

    final long[] pow10;

    public Day2() {

        // Precompute powers of 10 (0 to 10)
        long[] tmpPow10 = new long[11];
        tmpPow10[0] = 1;
        for (int i = 1; i <= 10; i++) tmpPow10[i] = tmpPow10[i - 1] * 10;
        this.pow10 = tmpPow10;
    }

    public long solvePuzzle1(List<String> input) {

        long invalidIDsum = 0;

        String inputLine = input.get(0);

        StringTokenizer tokens = new StringTokenizer(inputLine, ",-");

        while (tokens.hasMoreTokens()) {

            long start = Long.parseLong(tokens.nextToken());
            long end = Long.parseLong(tokens.nextToken());

            for (long num = start; num <= end; num++) {

                String numString = String.valueOf(num);
                int digitCount = numString.length();

                if (digitCount % 2 != 0) {
                    continue; // Skip numbers with odd number of digits
                }

                if (numString.endsWith(numString.substring(0, digitCount / 2))) {
                    invalidIDsum += num;
                }

            }

        }

        return invalidIDsum;
    }


    /**
     * This was my first solution. The approach is to find divisible chunk sizes and then generate a
     * string that repeats the chunks. For example, a 10-digit number has valid chunk sizes of 5, 2, and 1.
     * So, when checking chunk 5, grab the first 5 digits from the overall number, repeat it 2 times and then
     * compare it to the original. If it doesn't match, repeat chunk 2 x 5 and compare, and so on.
     */
    public long solvePuzzle2A(List<String> input) {

        long invalidIDsum = 0;

        String inputLine = input.get(0);

        StringTokenizer tokens = new StringTokenizer(inputLine, ",-");

        while (tokens.hasMoreTokens()) {

            long start = Long.parseLong(tokens.nextToken());
            long end = Long.parseLong(tokens.nextToken());

            for (long num = start; num <= end; num++) {

                String numString = String.valueOf(num);
                int digitCount = numString.length();

                // Maximum chunk size is HALF of the number string.
                // Work from largest chunk to smallest
                for (int chunkSize = digitCount / 2; chunkSize > 0; chunkSize--) {
                    if (digitCount % chunkSize != 0) {
                        continue; // skip chunk sizes that don't evenly divide up the whole
                    }

                    String pattern = numString.substring(0, chunkSize);
                    String compare = StringUtils.repeat(pattern, digitCount / chunkSize);

                    if (compare.equals(numString)) {
                        invalidIDsum += num;
                        chunkSize = 0; // Found one; skip the any remaining chunk sizes
                    }

                }

            }

        }

        return invalidIDsum;
    }

    private long buildRepeatingNumber(long pattern, int digitCount, int repetitions) {
        long value = 0;

        for (int r = 0; r < repetitions; r++) {
            value = value * pow10[digitCount] + pattern;
        }

        return value;
    }

    List<Long> generateRepeatingNumbers(int maxDigits) {
        List<Long> results = new ArrayList<>();

        // pattern length d = 1..(maxDigits/2)
        for (int digitCount = 1; digitCount <= maxDigits / 2; digitCount++) {

            long minPattern = pow10[digitCount - 1];
            long maxPattern = pow10[digitCount] - 1;

            for (long pattern = minPattern; pattern <= maxPattern; pattern++) {

                for (int repetitions = 2; repetitions * digitCount <= maxDigits; repetitions++) {
                    long number = buildRepeatingNumber(pattern, digitCount, repetitions);
                    results.add(number);
                }
            }
        }

        return results;
    }

    /**
     * This approach is the opposite of solution 2A. Instead of looping through the ranges and checking each number
     * for a repeating pattern, this generates a list of all possible repeating numbers and then checks each one
     * to see if they fall in any of the ranges.
     * <p>
     * In comparison, the advantage this has is that all the numbers stay numbers; it doesn't construct
     * a lot of garbage strings. This makes it about 10x faster.
     * </p>
     */
    public long solvePuzzle2B(List<String> input) {

        long invalidIDsum = 0;
        int maxDigits = 10;

        String inputLine = input.get(0);

        StringTokenizer tokens = new StringTokenizer(inputLine, ",-");

        List<Long> repeatingNumbers = generateRepeatingNumbers(maxDigits);

        List<Range<Long>> ranges = new ArrayList<>();
        while (tokens.hasMoreTokens()) {
            long start = Long.parseLong(tokens.nextToken());
            long end = Long.parseLong(tokens.nextToken());
            ranges.add(Range.between(start, end));
        }

        Set<Long> invalidNumbers = new HashSet<>();

        for (var num: repeatingNumbers) {
            for (var range : ranges) {
                if (range.contains(num)) {
                    invalidNumbers.add(num);
                }
            }
        }

        invalidIDsum = invalidNumbers.stream().reduce(0L, Long::sum);

        return invalidIDsum;
    }

}
