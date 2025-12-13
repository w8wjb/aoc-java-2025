package org.tot.aoc;

import org.tot.aoc.grid.HashGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {


    static class PresentShape {

        HashGrid<Character> shape;
        int minArea = 0;

        PresentShape(List<String> lines) {
            shape = HashGrid.fromList(lines);

            for (var entry : shape.entrySet()) {
                if (entry.getValue() == '#') {
                    minArea++;
                }
            }
        }

    }

    static class Region {

        int width;
        int length;

        int[] presentQuantities;

        static Pattern regionDefPattern = Pattern.compile("(\\d+)x(\\d+):\\s+([^.]*)");

        Region(String regionDef) {

            Matcher matcher = regionDefPattern.matcher(regionDef);

            if (matcher.find()) {
                width = Integer.parseInt(matcher.group(1));
                length = Integer.parseInt(matcher.group(2));

                String[] countStrings = matcher.group(3).split("\\s");
                presentQuantities = Arrays.stream(countStrings)
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }


        }

        int getArea() {
            return length * width;
        }

    }

    private List<PresentShape> shapes = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();


    private void parseInput(List<String> input) {

        shapes.clear();

        for (int i  = 0; i < input.size(); i++) {
            String line = input.get(i);

            if (line.contains("x")) {
                Region region = new Region(line);
                regions.add(region);

            } if (line.endsWith(":")) {
                  PresentShape shape = new PresentShape(input.subList(i + 1, i + 4));
                  shapes.add(shape);

                  i += 4;
            }

        }


    }

    public long solvePuzzle1(List<String> input) {

        parseInput(input);

        long regionCount = 0;

        for (Region region : regions) {

            int necessaryArea = 0;
            int totalQty = 0;
            for (int i = 0; i < region.presentQuantities.length; i++) {
                int qty = region.presentQuantities[i];
                totalQty += totalQty;
                PresentShape shape = shapes.get(i);

                necessaryArea += shape.minArea * qty;
            }

            if (necessaryArea > region.getArea()) {
                continue;
            }

            int naiveCells = region.length / region.width / 3;

            if (totalQty <= naiveCells) {
                regionCount++;
            }


        }


        return regionCount;
    }

    public long solvePuzzle2(List<String> input) {

        return 0;

    }

}