package org.tot.aoc;

import org.tot.aoc.grid.HashGrid;
import org.tot.aoc.grid.Point;
import org.tot.aoc.grid.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day9 {

    private Point[] redTiles;
//    List<Corner> redTiles = new ArrayList<>();
//    Map<Character, List<Corner>> cornerTypeMap = new HashMap<>();

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

    private static class Corner {

        final Point location;
        char type = '.';

        Corner(Point location) {
            this.location = location;
        }

        void setType(Point prev, Point next) {
            Vector in = new Vector(0, 0);
            if (prev.y < location.y) {
                in = in.add(Vector.N);
            } else if (prev.y > location.y) {
                in = in.add(Vector.S);
            } else if (prev.x < location.x) {
                in = in.add(Vector.E);
            } else {
                in = in.add(Vector.W);
            }

            Vector out = new Vector(0, 0);
            if (next.y < location.y) {
                out = out.add(Vector.N);
            } else if (next.y > location.y) {
                out = out.add(Vector.S);
            } else if (next.x < location.x) {
                out = out.add(Vector.E);
            } else {
                out = out.add(Vector.W);
            }

            Vector dir = in.add(out);
            if (dir.equals(Vector.SW)) {
                this.type = '⌜';
            } else if (dir.equals(Vector.SE)) {
                this.type = '⌝';
            } else if (dir.equals(Vector.NE)) {
                this.type = '⌟';
            } else if (dir.equals(Vector.NW)) {
                this.type = '⌞';
            }
        }

        @Override
        public String toString() {
            return String.format("%s %s", location.toString(), type);
        }
    }

    public long solvePuzzle2(List<String> input) {


        List<Point> redTileList = new ArrayList<>();

        long minX = Long.MAX_VALUE;
        long maxX = 0;
        long minY = Long.MAX_VALUE;
        long maxY = 0;

        for (String line : input) {
            String[] split = line.split(",");
            long x = Long.parseLong(split[0]);
            long y = Long.parseLong(split[1]);

            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);

            Point p = new Point(x,y);
            redTileList.add(p);
        }

        redTiles = redTileList.toArray(new Point[0]);

        long maxArea = 0L;

        // Clockwise
        for (int t = 0; t < redTiles.length; t++) {

            Point start = redTiles[t];
            Point next = redTiles[Math.floorMod(t + 1, redTiles.length)];

            long dx = next.x - start.x;
            long dy = next.y - start.y;

            Point opp = null;

            if (dy < 0) { // up
                opp = seekOppositeCorner(t, true, p -> p.y > start.y);

            } else if (dx > 0) { // right

                opp = seekOppositeCorner(t, true, p -> p.x < start.x);

            } else if (dx < 0) { // left

                opp = seekOppositeCorner(t, true, p -> p.y < start.y);

            } else { // down

                opp = seekOppositeCorner(t, true, p -> p.x < start.x);

            }

            if (opp != null) {
                maxArea = Math.max(maxArea, area(start, opp));
            }


        }


        return maxArea;

    }

    Point seekOppositeCorner(int startIndex, boolean forward, Predicate<Point> stopWhen) {
        Point last = null;

        for (int offset = 2; offset <= redTiles.length; offset++) {

            int check;
            if (forward) {
                check = Math.floorMod(startIndex + offset, redTiles.length);
            } else {
                check = Math.floorMod(startIndex - offset, redTiles.length);
            }

            Point p = redTiles[check];
            if (stopWhen.test(p)) {
                break;
            }
            last = p;
        }

        return last;
    }

    long area(Point a, Point b) {
        long minX = Math.min(a.x, b.x);
        long maxX = Math.max(a.x, b.x);
        long minY = Math.min(a.y, b.y);
        long maxY = Math.max(a.y, b.y);
        return (maxX - minX) * (maxY - minY);
    }

}