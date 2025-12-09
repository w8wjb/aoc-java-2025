package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class Day9Test {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day9/sample1.txt");

        Day9 day = new Day9();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(50L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day9/input1.txt");

        Day9 day = new Day9();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(4748769124L, result);
    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day9/sample1.txt");

        Day9 day = new Day9();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(24L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("Day9/input1.txt");

        Day9 day = new Day9();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(12345L, result);
    }

}