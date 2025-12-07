package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day7Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day7/sample1.txt");

        Day7 day = new Day7();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(21L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day7/input1.txt");

        Day7 day = new Day7();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(1499L, result);

    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day7/sample1.txt");

        Day7 day = new Day7();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(40L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day7/input1.txt");

        Day7 day = new Day7();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(24743903847942L, result);
    }

}
