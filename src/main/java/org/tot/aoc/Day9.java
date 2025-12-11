package org.tot.aoc;

import org.tot.aoc.grid.Point;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    List<Point> points = new ArrayList<>();

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


    private static class Rectangle {

        final Point tileA;
        final Point tileB;

        final long minX; // left
        final long maxX; // right
        final long minY; // top
        final long maxY; // bottom
        public final long width;
        public final long height;

        public Rectangle(Point a, Point b) {

            if (a.x < b.x) {
                tileA = a;
                tileB = b;
            } else {
                tileA = b;
                tileB = a;
            }

            minX = Math.min(tileA.x, tileB.x);
            maxX = Math.max(tileA.x, tileB.x);
            minY = Math.min(tileA.y, tileB.y);
            maxY = Math.max(tileA.y, tileB.y);
            this.height = maxY - minY;
            this.width = maxX - minX;
        }

        public boolean intersects(Rectangle other) {
            return this.minX < other.maxX &&
                    this.maxX > other.minX &&
                    this.minY < other.maxY &&
                    this.maxY > other.minY;
        }

        long area() {
            return Math.abs(maxX - minX + 1) * Math.abs(maxY - minY + 1);
        }

    }


    public long solvePuzzle2(List<String> input) {

        Point loopStart = new Point(0, Long.MAX_VALUE);
        int loopStartIndex = -1;
        List<Rectangle> whitespace = new ArrayList<>();

        long boundsMinX = Long.MAX_VALUE;
        long boundsMaxX = 0;
        long boundsMinY = Long.MAX_VALUE;
        long boundsMaxY = 0;

        for (String line : input) {
            String[] split = line.split(",");
            long x = Long.parseLong(split[0]);
            long y = Long.parseLong(split[1]);

            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);

            Point p = new Point(x, y);
            points.add(p);

            if (p.y <= loopStart.y) {
                loopStart = p;
                loopStartIndex = points.size() - 1;
            }
        }

        for (int offset = 0; offset < points.size(); offset++) {
            int i = (loopStartIndex + offset) % points.size();
            int iNext = (loopStartIndex + offset + 1) % points.size();

            Point p1 = points.get(i);
            Point p2 = points.get(iNext);

            if (p1.x == p2.x) { // vertical
                Point nw;
                Point se;
                if (p1.y < p2.y) { // down
                    nw = new Point(p1.x, p1.y);
                    se = new Point(boundsMaxX, p2.y);
                } else { // up
                    nw = new Point(boundsMinX, p2.y);
                    se = new Point(p1.x, p1.y);
                }
                whitespace.add(new Rectangle(nw, se));
            }
        }


        long maxArea = 0;

        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);

                Rectangle rect = new Rectangle(p1, p2);
                if (rect.area() < maxArea) {
                    continue;
                }

                boolean valid = true;
                for (Rectangle r : whitespace) {
                    if (rect.intersects(r)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    maxArea = Math.max(maxArea, rect.area());
                }
            }
        }

        return maxArea;
    }


}