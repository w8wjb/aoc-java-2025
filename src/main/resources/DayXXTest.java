package org.tot.aoc;

import org.junit.Assert;
import org.junit.Test;
import org.tot.helper.ResourceHelper;

import java.util.List;

public class DayXXTest {

    @Test
    public void testSample1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("DayXX/sample1.txt");

        DayXX day = new DayXX();

        long result = day.solvePuzzle1(lines);

        Assert.assertEquals(12345L, result);
    }

    @Test
    public void testSolution1() {

        List<String> lines = ResourceHelper.loadLinesFromFile("DayXX/input1.txt");

        DayXX day = new DayXX();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(12345L, result);
    }

    @Test
    public void testSample2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("DayXX/sample1.txt");

        DayXX day = new DayXX();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(12345L, result);
    }

    @Test
    public void testSolution2() {

        List<String> lines = ResourceHelper.loadLinesFromFile("DayXX/input1.txt");

        DayXX day = new DayXX();

        long result = day.solvePuzzle2(lines);

        Assert.assertEquals(12345L, result);
    }

}