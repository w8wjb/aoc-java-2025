package org.tot.aoc;


import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {


    public long solvePuzzle1(List<String> input) {


        char[] prev = null;
        char[] out = null;

        long splits = 0;

        for (String row : input) {
            if (prev == null) {
                prev = row.replace('S', '|').toCharArray();
                out = new char[prev.length];
                Arrays.fill(out, '.');
                continue;
            }

            char[] splitters = row.toCharArray();

            for (int i = 0; i < prev.length; i++) {

                char in = prev[i];
                char dest = splitters[i];

                if (in == '|') {
                    if (dest == '^') {
                        splits++;

                        int iLeft = i - 1;
                        int iRight = i + 1;

                        out[i] = '.';
                        if (iLeft >= 0) {
                            out[iLeft] = in;
                        }
                        if (iRight < out.length) {
                            out[iRight] = in;
                        }

                    } else {
                        out[i] = in;
                    }
                }

            }
            prev = out;
        }


        return splits;
    }


    public long solvePuzzle2(List<String> input) {

        // Need to grab the first line to get the sizing for the arrays
        String first = input.get(0);
        // Find where in the 'S' is
        int start = first.indexOf('S');

        // Create 2 arrays that will hold the counts of tachyons in each column
        // The flow is source ---splitters--> sink
        long[] source = new long[first.length()];
        long[] sink = new long[source.length];

        // Mark the starting tachyon
        source[start] = 1;

        // Iterate through each row, skipping the first one
        for (int row = 1; row < input.size(); row++) {
            char[] splitters = input.get(row).toCharArray();

            // Clear out any data from previous pass
            Arrays.fill(sink, 0L);

            // Iterate through each column
            for (int col = 0; col < source.length; col++) {

                long in = source[col];
                char splitter = splitters[col];

                if (splitter == '^') {
                    // Add the numbers to the left and right of the splitter
                    sink[col] = 0;
                    sink[col - 1] += in;
                    sink[col + 1] += in;
                    // Note: I originally included bounds checking here, but Eric was kind to us
                    // and included an empty band around the puzzle so it never runs off the edge

                } else {
                    sink[col] += in;
                }

            }

            // Swap source/sink
            // This is done to avoid allocating new arrays all the time.
            // Sink becomes the new source and the old source will be zeroed out
            long[] tmp = source;
            source = sink;
            sink = tmp;

        }

        long sum = 0L;
        for (long v : source) {
            sum += v;
        }
        return sum;
    }

}
