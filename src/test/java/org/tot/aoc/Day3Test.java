package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day3Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day3/sample1.txt");

        Day3 day = new Day3();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(357L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day3/input1.txt");

        Day3 day = new Day3();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(17443L, result);

    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day3/sample1.txt");

        Day3 day = new Day3();

        long result = day.solvePuzzle2(lines, 12);

        Assert.assertEquals(3121910778619L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day3/input1.txt");

        Day3 day = new Day3();

        long result = day.solvePuzzle2(lines, 12);

        Assert.assertEquals(172167155440541L, result);
    }


}
