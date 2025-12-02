package org.tot.aoc;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class Day2 {

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

    public long solvePuzzle2(List<String> input) {

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
                        System.out.println(numString);
                        invalidIDsum += num;
                        chunkSize = 0; // Found one; skip the any remaining chunk sizes
                    }

                }

            }

        }

        return invalidIDsum;
    }

}
