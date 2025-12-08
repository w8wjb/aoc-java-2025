package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day8Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/sample1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle1(lines, 10);

        Assert.assertEquals(40L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/input1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle1(lines, 1000);

        Assert.assertEquals(133574L, result);
    }

    @Test
    public void testSample1A() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/sample1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle1A(lines, 10);

        Assert.assertEquals(40L, result);
    }

    @Test
    public void testSolution1A() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/input1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle1A(lines, 1000);

        Assert.assertEquals(133574L, result);
    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/sample1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(25272L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day8/input1.txt");

        Day8 day = new Day8();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(2435100380L, result);
    }

}