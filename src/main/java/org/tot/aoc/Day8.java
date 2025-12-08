package org.tot.aoc;

import java.util.*;

public class Day8 {

    static class Point3D {

        final long x;
        final long y;
        final long z;

        Point3D(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Point3D(String commaSeparated) {
            String[] parts = commaSeparated.split(",");
            this.x = Long.parseLong(parts[0]);
            this.y = Long.parseLong(parts[1]);
            this.z = Long.parseLong(parts[2]);
        }

        double distance(Point3D other) {
            long dx = other.x - this.x;
            long dy = other.y - this.y;
            long dz = other.z - this.z;

            return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point3D)) return false;
            Point3D other = (Point3D) o;
            return x == other.x && y == other.y && z == other.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return String.format("%d,%d,%d", x, y, z);
        }
    }

    static class Edge implements Comparable<Edge> {
        final JunctionBox from;
        final JunctionBox to;
        final double distance;

        Edge(JunctionBox from, JunctionBox to) {
            this.from = from;
            this.to = to;
            this.distance = from.location.distance(to.location);
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Edge)) return false;
            Edge other = (Edge) o;
            return this.from.equals(other.from) && this.to.equals(other.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public int compareTo(Edge other) {
            if (this.distance < other.distance) {
                return -1;
            } else if (this.distance > other.distance) {
                return 1;
            }
            return 0;
        }


        @Override
        public String toString() {
            return String.format("%s -> %s (%.0f)", from.toString(), to.toString(), distance);
        }
    }

    static class JunctionBox {

        final Point3D location;
        int circuit = -1;

        JunctionBox(Point3D location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return String.format("%s [%d]", location.toString(), circuit);
        }
    }


    public long solvePuzzle1(List<String> input, int maxConnections) {

        List<JunctionBox> boxes = new ArrayList<>();

        for (String line : input) {
            boxes.add(new JunctionBox(new Point3D(line)));
        }


        TreeSet<Edge> edges = new TreeSet<>();

        for (int i = 0; i < boxes.size(); i++) {
            JunctionBox a = boxes.get(i);
            for (int j = i + 1; j < boxes.size(); j++) {
                JunctionBox b = boxes.get(j);
                edges.add(new Edge(a, b));
            }
        }

        int circuit = 0;

        int connected = 0;
        for (Edge edge : edges) {
            if (edge.from.circuit < 0) {
                if (edge.to.circuit < 0) {
                    circuit++;
                    edge.from.circuit = circuit;
                    edge.to.circuit = circuit;
                } else {
                    edge.from.circuit = edge.to.circuit;
                }
            } else {
                if (edge.to.circuit < 0) {
                    edge.to.circuit = edge.from.circuit;
                }
            }
            connected++;

            if (connected >= maxConnections) {
                break;
            }

        }

        Map<Integer, Integer> circuitCounts = new HashMap<>();
        for (var box : boxes) {
            if (box.circuit >= 0) {
                int count = circuitCounts.getOrDefault(box.circuit, 0);
                circuitCounts.put(box.circuit, count + 1);
            }
        }
        List<Integer> counts = new ArrayList<>(circuitCounts.values());
        Collections.sort(counts);

        int result = 1;
        for (int i = 0; i < 3; i++) {
            result *= counts.get(i);
        }

        return result;
    }

    public long solvePuzzle2(List<String> input) {

        return 0;

    }

}