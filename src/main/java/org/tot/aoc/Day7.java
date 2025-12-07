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

        String first = input.remove(0);
        int start = first.indexOf('S');

        long[] source = new long[first.length()];
        source[start] = 1;

        for (String row : input) {

            char[] splitters = row.toCharArray();
            long[] sink = new long[source.length];

            for (int i = 0; i < source.length; i++) {
                long in = source[i];
                char splitter = splitters[i];

                if (splitter == '^') {
                    int iLeft = i - 1;
                    int iRight = i + 1;

                    sink[i] = 0;
                    if (iLeft >= 0) {
                        sink[iLeft] += in;
                    }
                    if (iRight < sink.length) {
                        sink[iRight] += in;
                    }

                } else {
                    sink[i] += in;
                }

            }

            source = sink;

        }

        return Arrays.stream(source).sum();

    }

}
