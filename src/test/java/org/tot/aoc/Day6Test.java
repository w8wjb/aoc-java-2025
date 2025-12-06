package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day6Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day6/sample1.txt");

        Day6 day = new Day6();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(4277556L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day6/input1.txt");

        Day6 day = new Day6();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(4693159084994L, result);

    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day6/sample1.txt");

        Day6 day = new Day6();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(3263827L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day6/input1.txt");

        Day6 day = new Day6();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(11643736116335L, result);
    }

}
