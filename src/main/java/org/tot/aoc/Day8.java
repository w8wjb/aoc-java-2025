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

        long distanceSquared(Point3D other) {
            long dx = other.x - this.x;
            long dy = other.y - this.y;
            long dz = other.z - this.z;
            return dx * dx + dy * dy + dz * dz;
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

    static class IndexEdge implements Comparable<IndexEdge> {
        final int a;
        final int b;
        final double distance;

        IndexEdge(int a, int b, double distance) {
            this.a = a;
            this.b = b;
            this.distance = distance;
        }

        @Override
        public int compareTo(IndexEdge other) {
            if (this.distance < other.distance) {
                return -1;
            } else if (this.distance > other.distance) {
                return 1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IndexEdge)) return false;
            IndexEdge indexEdge = (IndexEdge) o;
            return a == indexEdge.a && b == indexEdge.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    static class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                // Everything starts as its own parent
                parent[i] = i;
                // Every node starts with a size 1 because they start disconnected
                size[i] = 1;
            }
        }

        int findRoot(int x) {
            if (parent[x] != x) {
                // Recursively walk the tree back up to the root.
                // This compresses the tree in the process.
                // So, if there is a tree 5 -> 3 -> 9, after compression, 5 -> 9
                parent[x] = findRoot(parent[x]);
            }
            return parent[x];
        }


        boolean union(int a, int b) {
            int rootA = findRoot(a);
            int rootB = findRoot(b);
            if (rootA == rootB) {
                // If both nodes already share the same root node, then they're in the same network
                return false; // already connected
            }

            // Take the smaller tree and attach it to the larger tree.
            // This attempts to keep the distance from a leaf to the root short
            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            // Make the root A the parent of root B, thereby attaching the two subtrees
            parent[rootB] = rootA;
            // Update the size of the root, now that it contains new nodes
            size[rootA] += size[rootB];
            return true;
        }

        List<Integer> getNetworkSizes() {
            List<Integer> sizes = new ArrayList<>();

            for (int i = 0; i < parent.length; i++) {
                if (i == parent[i]) {
                    // Only find root nodes
                    sizes.add(this.size[i]);
                }
            }

            return sizes;
        }

    }

    public long solvePuzzle1A(List<String> input, int maxConnections) {

        JunctionBox[] boxes = new JunctionBox[input.size()];

        // Parse the input into junction box structures
        for (int i = 0; i< input.size(); i++) {
            String line = input.get(i);
            boxes[i] = new JunctionBox(new Point3D(line));
        }

        List<IndexEdge> edges = new ArrayList<>();

        // Generate a set of edges by matching up each element with every other element
        // Unlike my first solution, this just uses the index of the boxes; After calculating
        // the distance between them, the coordinate is no longer needed
        for (int i = 0; i < boxes.length; i++) {
            JunctionBox a = boxes[i];
            for (int j = i + 1; j < boxes.length; j++) {
                JunctionBox b = boxes[j];
                edges.add(new IndexEdge(i, j, a.location.distanceSquared(b.location)));
            }
        }

        // The natural sort of the edges is shortest distance first
        Collections.sort(edges);

        DisjointSet disjointSet = new DisjointSet(boxes.length);

        for (int connection = 0; connection < maxConnections; connection++) {
            IndexEdge edge = edges.get(connection);
            // This is the magic. As we process each edge, we union separate subsets (networks) together
            disjointSet.union(edge.a, edge.b);
        }

        // Once the connections are made, this function finds all the 'root' nodes in the tree
        // and adds their sizes to a list
        List<Integer> sizes = disjointSet.getNetworkSizes();
        sizes.sort(Collections.reverseOrder());

        int result = 1;

        // Multiply the largest three together
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