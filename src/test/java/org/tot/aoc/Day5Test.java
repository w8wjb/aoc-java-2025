package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day5Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day5/sample1.txt");

        Day5 day = new Day5();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(3, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day5/input1.txt");

        Day5 day = new Day5();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(661, result);

    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day5/sample1.txt");

        Day5 day = new Day5();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(14, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day5/input1.txt");

        Day5 day = new Day5();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(359526404143208L, result);
    }

}
