package org.tot.aoc;

import java.util.List;

public class Day1 {

    public int solvePuzzle1(List<String> input) {

        int dial = 50;
        int answer = 0;

        for (String line : input) {

            int distance = Integer.parseInt(
                    line.replace("L", "-")
                            .replace("R", ""));

            dial = dial + distance;
            dial = Math.floorMod(dial, 100);

            if (dial == 0) {
                answer++;
            }

        }

        return answer;
    }

    public int solvePuzzle2(List<String> input) {

        int dial = 50;
        int answer = 0;

        for (String line : input) {

            int distance = Integer.parseInt(
                    line.replace("L", "-")
                            .replace("R", ""));

            int last = dial;

            dial = dial + distance;
            dial = Math.floorMod(dial, 100);

            int start = distance + last;
            if (last > 0 && start <= 0) {
                start -= 100;
            }
            answer += Math.abs(start) / 100;

        }

        return answer;
    }

}
