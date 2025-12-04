package org.tot.aoc;

import org.tot.aoc.grid.HashGrid;
import org.tot.aoc.grid.Point;
import org.tot.aoc.grid.StringGrid;
import org.tot.aoc.grid.Vector;

import java.util.List;

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

}
