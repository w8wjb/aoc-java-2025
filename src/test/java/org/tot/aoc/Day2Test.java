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

        Assert.assertEquals(1227775554L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/input1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(23560874270L, result);

    }

    @Test
    public void testSample2A() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/sample1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2A(lines);

        Assert.assertEquals(4174379265L, result);
    }

    @Test
    public void testSolution2A() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/input1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2A(lines);

        Assert.assertEquals(44143124633L, result);
    }

    @Test
    public void testSample2B() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/sample1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2B(lines);

        Assert.assertEquals(4174379265L, result);
    }

    @Test
    public void testSolution2B() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day2/input1.txt");

        Day2 day = new Day2();

        long result = day.solvePuzzle2B(lines);

        Assert.assertEquals(44143124633L, result);
    }

}
