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

        Set<JunctionBox> network = new HashSet<>();

        JunctionBox(Point3D location) {
            this.location = location;
            network.add(this);
        }

        @Override
        public String toString() {
            return location.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof JunctionBox)) return false;
            JunctionBox that = (JunctionBox) o;
            return Objects.equals(location, that.location);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(location);
        }
    }


    public long solvePuzzle1(List<String> input, int maxConnections) {

        List<JunctionBox> boxes = new ArrayList<>();

        for (String line : input) {
            boxes.add(new JunctionBox(new Point3D(line)));
        }


        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < boxes.size(); i++) {
            JunctionBox a = boxes.get(i);
            for (int j = i + 1; j < boxes.size(); j++) {
                JunctionBox b = boxes.get(j);
                edges.add(new Edge(a, b));
            }
        }

        Collections.sort(edges);

        for (int connection = 0; connection < maxConnections; connection++) {

            Edge edge = edges.get(connection);

            Set<JunctionBox> networkA = edge.from.network;
            Set<JunctionBox> networkB = edge.to.network;

            if (networkA.contains(edge.to) || networkB.contains(edge.from)) {
                continue;
            }

            Set<JunctionBox> joined = new HashSet<>();
            joined.addAll(networkA);
            joined.addAll(networkB);

            for (var box : joined) {
                box.network = joined;
            }
        }


        IdentityHashMap<Set<JunctionBox>, Integer> networkSizes = new IdentityHashMap<>();

        for (var box : boxes) {
            networkSizes.put(box.network, box.network.size());
        }

        List<Integer> sizes = new ArrayList<>(networkSizes.values());
        Collections.sort(sizes);
        Collections.reverse(sizes);

        int result = 1;

        for (int i = 0; i < 3; i++) {
            result *= sizes.get(i);
        }


        return result;
    }

    public long solvePuzzle2(List<String> input) {

        List<JunctionBox> boxes = new ArrayList<>();

        for (String line : input) {
            boxes.add(new JunctionBox(new Point3D(line)));
        }


        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < boxes.size(); i++) {
            JunctionBox a = boxes.get(i);
            for (int j = i + 1; j < boxes.size(); j++) {
                JunctionBox b = boxes.get(j);
                edges.add(new Edge(a, b));
            }
        }

        int allBoxesCount = boxes.size();
        Collections.sort(edges);

        long xa = 0;
        long xb = 0;

        for (Edge edge : edges) {

            Set<JunctionBox> networkA = edge.from.network;
            Set<JunctionBox> networkB = edge.to.network;

            if (networkA.contains(edge.to) || networkB.contains(edge.from)) {
                continue;
            }

            Set<JunctionBox> joined = new HashSet<>();
            joined.addAll(networkA);
            joined.addAll(networkB);

            if (joined.size() == allBoxesCount) {
                xa = edge.from.location.x;
                xb = edge.to.location.x;
                break;
            }

            for (var box : joined) {
                box.network = joined;
            }
        }


        return xa * xb;

    }

}