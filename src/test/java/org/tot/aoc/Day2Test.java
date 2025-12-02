package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day2Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/sample1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(1227775554l, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/input1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(23560874270l, result);

    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/sample1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(4174379265l, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/input1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(44143124633l, result);
    }

}
