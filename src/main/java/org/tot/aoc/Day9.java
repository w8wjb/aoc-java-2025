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

        SortedSet<Long> xSet = new TreeSet<>();
        SortedSet<Long> ySet = new TreeSet<>();

        for (String line : input) {
            String[] split = line.split(",");
            long x = Long.parseLong(split[0]);
            long y = Long.parseLong(split[1]);

            xSet.add(x);
            ySet.add(y);

            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);

            Point p = new Point(x,y);
            redTileList.add(p);
        }


        long[] xAxis = xSet.stream().mapToLong(Long::longValue).toArray();
        long[] yAxis = ySet.stream().mapToLong(Long::longValue).toArray();

        List<Point> compressedTiles = redTileList
                .stream()
                .map(p -> {
                    long xs = Arrays.binarySearch(xAxis, p.x);
                    long ys = Arrays.binarySearch(yAxis, p.y);
                    return new Point(xs,ys);
                })
                .collect(Collectors.toList());

        HashGrid<Character> grid = new HashGrid<>('.');

        for (int i = 0; i < compressedTiles.size(); i++) {
            Point from = compressedTiles.get(i);
            Point to = compressedTiles.get((i + 1) % compressedTiles.size());

            grid.put(from, '#');
            grid.put(to, '#');

            long dx = to.x - from.x;
            long dy = to.y - from.y;
            if (dx != 0) {
                dx /= Math.abs(dx);
            }
            if (dy != 0) {
                dy /= Math.abs(dy);
            }

            Vector dir =  new Vector(dx, dy);

            Point next = from.add(dir);
            while (!next.equals(to)) {
                grid.put(next, 'O');
                next = next.add(dir);
            }

        }

        floodFill(grid);

        grid.print();

        return 0;

    }

    void floodFill(HashGrid<Character> grid) {

        long midX = 2 + grid.minX + (grid.maxX - grid.minX) / 3;
        long midY = 2 + grid.minY + (grid.maxY - grid.minY) / 2;
        Point start = new Point(midX, midY);

        Deque<Point> queue = new ArrayDeque<>();
        queue.push(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            grid.put(current, '0');
            for (Vector v : Vector.CARDINAL) {
                Point neighbor = current.add(v);
                if (grid.isWithinBounds(neighbor)) {
                    char c = grid.get(neighbor);
                    if (c == '.') {
                        grid.put(neighbor, '0');
                        queue.add(neighbor);
                    }
                }
            }

        }


    }

}