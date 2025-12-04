package org.tot.aoc;

import org.tot.aoc.grid.HashGrid;
import org.tot.aoc.grid.Point;
import org.tot.aoc.grid.StringGrid;
import org.tot.aoc.grid.Vector;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {

    public long solvePuzzle1(List<String> input) {

        StringGrid grid = new StringGrid(input);

        long accessible = 0;

        for (Point p : grid) {

            if (grid.get(p) != '@') {
                continue;
            }

            int adjacentRolls = 0;
            for (Vector v : Vector.ORDINAL) {
                Point check = p.add(v);
                if (grid.get(check) == '@') {
                    adjacentRolls++;
                }
                if (adjacentRolls >= 4) {
                    break;
                }
            }

            if (adjacentRolls < 4) {
                accessible++;
            }

        }

        return accessible;
    }

    private HashGrid<Character> markRollsForRemoval(HashGrid<Character> input) {

        HashGrid<Character> out = new HashGrid<Character>(input);

        for (Point p : input) {

            if (input.get(p) != '@') {
                continue;
            }

            int adjacentRolls = 0;
            for (Vector v : Vector.ORDINAL) {
                Point check = p.add(v);
                if (input.get(check) == '@') {
                    adjacentRolls++;
                }
                if (adjacentRolls >= 4) {
                    break;
                }
            }

            if (adjacentRolls < 4) {
                out.put(p, 'x');
            }

        }

        return out;
    }

    public long solvePuzzle2(List<String> input) {

        HashGrid<Character> grid = HashGrid.fromList(input);

        long totalRemoved = 0;

        int removed = 0;
        do {

            // Marking phase
            grid = markRollsForRemoval(grid);

            // Removal phase
            removed = 0;
            for (Point p : grid) {
                if (grid.get(p) == 'x') {
                    grid.remove(p);
                    removed++;
                }
            }

            totalRemoved += removed;

        } while (removed > 0);

        return totalRemoved;
    }

    public long solvePuzzle2A(List<String> input) {

        HashGrid<Character> grid = HashGrid.fromList(input);

        char threshold = '@' + 4;

        // Queue of cells that are currently "accessible" (deg < 4).
        ArrayDeque<Point> queue = new ArrayDeque<>();

        // First pass through the entire grid to calculate the 'height' of each point
        // Rather than keep this information in a separate data structure, I decided to
        // encode it directly in the character. So, at maximum, a roll surrounded by 8 neighbors
        // would increase from '@' up to  'H'
        for (var entry : grid.entrySet()) {
            Point p = entry.getKey();
            char c = entry.getValue();
            if (c == '.') continue; // Skip empty spaces

            for (Vector v : Vector.ORDINAL) {
                Point n = p.add(v);
                if (grid.get(n) != '.') {
                    // Each non-empty neighbor increases the height
                    c++;
                }
            }
            // Update the point in the grid
            grid.put(p, c);

            // If we found a roll that's ready to remove, then add to the queue
            if (c < threshold) {
                queue.add(p);
            }
        }

        long removed = 0;

        // Process the queue of rolls to be removed
        while (!queue.isEmpty()) {
            Point p = queue.poll();

            // Might have been removed already via another path.
            if (grid.get(p) == '.') {
                continue;
            }

            // Remove this roll.
            grid.remove(p);
            removed++;

            // Update neighbors' degrees.
            for (Vector v : Vector.ORDINAL) {
                Point n = p.add(v);
                char adjacent = grid.get(n);
                if (adjacent == '.') continue; // Skip empty space

                // When a roll is removed, it decreases the height of its neighbors by 1
                adjacent--;
                grid.put(n, adjacent);

                // If the adjacent point drops below the threshold, then it's ready to be removed
                if (adjacent < threshold) {
                    queue.add(n);
                }
            }
        }

        return removed;
    }


}
