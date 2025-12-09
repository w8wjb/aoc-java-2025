package org.tot.aoc;

import org.tot.aoc.grid.HashGrid;
import org.tot.aoc.grid.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    public long solvePuzzle1(List<String> input) {

        List<Point> points = new ArrayList<>();

        for (String line : input) {
            String[] split = line.split(",");
            points.add(new Point(
                    Long.parseLong(split[0]),
                    Long.parseLong(split[1])
            ));
        }


        long maxArea = 0;

        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);

                long dx = Math.max(p1.x, p2.x) - Math.min(p1.x, p2.x) + 1;
                long dy = Math.max(p1.y, p2.y) - Math.min(p1.y, p2.y) + 1;

                long area = dx * dy;
                maxArea = Math.max(area, maxArea);
            }
        }

        return maxArea;
    }

    public long solvePuzzle2(List<String> input) {

        HashGrid<Character> grid = new HashGrid<Character>('.');

        for (String line : input) {
            String[] split = line.split(",");
            Point p = new Point(
                    Long.parseLong(split[0]),
                    Long.parseLong(split[1])
            );
            grid.put(p, '#');
        }

        return 0;

    }

}