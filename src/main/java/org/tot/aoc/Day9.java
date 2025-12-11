package org.tot.aoc;

import org.tot.aoc.grid.Point;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    List<Point> points = new ArrayList<>();
    List<Line> lines = new ArrayList<>();

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

    private static class Line {

        final Point a;
        final Point b;

        Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        boolean intersects(Line other) {
            return Line2D.linesIntersect(a.x, a.y, b.x, b.y, other.a.x, other.a.y, other.b.x, other.b.y);
        }
    }

    private static class Rectangle {

        final Point cornerA;
        final Point cornerB;

        final long minX;
        final long maxX;
        final long minY;
        final long maxY;
        final long height;
        final long width;

        public Rectangle(Point cornerA, Point cornerB) {
            this.cornerA = cornerA;
            this.cornerB = cornerB;
            minX = Math.min(cornerA.x, cornerB.x);
            maxX = Math.max(cornerA.x, cornerB.x);
            minY = Math.min(cornerA.y, cornerB.y);
            maxY = Math.max(cornerA.y, cornerB.y);
            this.height = maxY - minY;
            this.width = maxX - minX;
        }

        boolean isInside(Point p) {
            return p.x > minX && p.x < maxX &&
                    p.y > minY && p.y < maxY;
        }

        boolean intersects(Line line) {
            // If either endpoint is inside the rectangle, we count that as intersection
            if (isInside(line.a) || isInside(line.b)) {
                return true;
            }

            // W
            if (Line2D.linesIntersect(this.minX, this.minY, this.minX, this.maxY,
                    line.a.x, line.a.y, line.b.x, line.b.y)) {
                return true;
            }

            // E
            if (Line2D.linesIntersect(this.maxX, this.minY, this.maxX, this.maxY,
                    line.a.x, line.a.y, line.b.x, line.b.y)) {
                return true;
            }

            // N
            if (Line2D.linesIntersect(this.minX, this.minY, this.maxX, this.minY,
                    line.a.x, line.a.y, line.b.x, line.b.y)) {
                return true;
            }

            // S
            if (Line2D.linesIntersect(this.minX, this.maxY, this.maxX, this.maxY,
                    line.a.x, line.a.y, line.b.x, line.b.y)) {
                return true;
            }

            return false;
        }

        long area() {
            return Math.abs(maxX - minX + 1) * Math.abs(maxY - minY + 1);
        }

    }


    public long solvePuzzle2(List<String> input) {

        Point last = null;

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

            if (last == null) {
                last = p;
            } else {
                lines.add(new Line(last, p));
            }
        }
        lines.add(new Line(last, points.get(0)));

        long maxArea = 0;

        Point lastA = null;
        Point lastB = null;
        Rectangle largestRectangle = null;

        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);

                Rectangle rect = new Rectangle(p1, p2);

                long area = rect.area();
                if (area <= maxArea) {
                    continue;
                }

                if (isValid(rect, p1, p2)) {
                    maxArea = Math.max(maxArea, area);
                    lastA = p1;
                    lastB = p2;
                    largestRectangle = rect;
                }
            }
        }


        if (largestRectangle != null) {
            System.out.printf("%d, %d %d,%d%n", largestRectangle.minX, largestRectangle.minY, largestRectangle.width, largestRectangle.height);
        }


        return maxArea;

    }

    boolean isValid(Rectangle rect, Point p1, Point p2) {
        for (Point p : points) {
            if (rect.isInside(p)) {
                return false;
            }
        }


        for (Line line : lines) {
            if (line.a.equals(p1)
                    || line.b.equals(p1)
                    || line.a.equals(p2)
                    || line.b.equals(p2)) {
                continue;
            }

            if (rect.intersects(line)) {
                return false;
            }
        }
        return true;
    }


}