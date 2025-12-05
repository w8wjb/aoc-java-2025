package org.tot.aoc;


import java.util.*;

public class Day5 {

    static class Range {

        long min;
        long max;

        Range(long min, long max) {
            this.min = Math.min(min, max);
            this.max = Math.max(min, max);
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }

        boolean contains(long element) {
            return element >= min && element <= max;
        }

        @Override
        public String toString() {
            return String.format("%d-%d", min, max);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Range)) return false;
            Range range = (Range) o;
            return min == range.min && max == range.max;
        }

        @Override
        public int hashCode() {
            return Objects.hash(min, max);
        }
    }

    public long solvePuzzle1(List<String> input) {

        List<Range> ranges = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();

        boolean inRanges = true;
        for (String line : input) {
            if (line.isBlank()) {
                inRanges = false;
                continue;
            }

            if (inRanges) {
                try (Scanner scanner = new Scanner(line).useDelimiter("-")) {
                    long left = scanner.nextLong();
                    long right = scanner.nextLong();
                    ranges.add(new Range(left, right));
                }
            } else {
                ingredients.add(Long.parseLong(line));
            }
        }

        long fresh = 0;

        for (long ingredientId : ingredients) {
            for (Range range : ranges) {
                if (range.contains(ingredientId)) {
                    fresh++;
                    break;
                }
            }
        }

        return fresh;
    }



    public long solvePuzzle2(List<String> input) {

        List<Range> ranges = new ArrayList<>();

        for (String line : input) {
            if (line.isBlank()) {
                break;
            }

            final var parts = line.split("-");
            ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }

        ranges.sort(Comparator.comparingLong(Range::getMin));

        long fresh = 1;
        long marker = ranges.get(0).min;

        for (Range range : ranges) {

            if (marker >= range.min  && marker <= range.max) {
                // marker is contained within range

                // add everything from the marker to the end of the range
                fresh += range.max - marker;

                // move the marker
                marker = range.max;


            } else {
                // marker is not contained within range

                if (marker >= range.max) {
                    continue; // We've already passed this range
                }

                // the marker will jump ahead to the end of the range
                marker = range.max;

                // and we'll add the entire span
                fresh += (range.max - range.min + 1);

            }

        }

        return fresh;
    }

}
