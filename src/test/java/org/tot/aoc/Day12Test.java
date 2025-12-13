package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day12Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day12/sample1.txt");

        Day12 day = new Day12();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(12345L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day12/input1.txt");

        Day12 day = new Day12();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(575L, result);
    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day12/sample1.txt");

        Day12 day = new Day12();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(12345L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day12/input1.txt");

        Day12 day = new Day12();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(575L, result);
    }

}