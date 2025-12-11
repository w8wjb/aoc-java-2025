package org.tot.aoc;

import java.util.*;

public class Day11 {

    private Map<String, List<String>> parseInput(List<String> input) {
        Map<String, List<String>> devices = new HashMap<>();

        for (String line : input) {

            var tokenizer = new StringTokenizer(line, ": ");

            String deviceId = tokenizer.nextToken();
            List<String> outputs = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                String output = tokenizer.nextToken();
                outputs.add(output);
            }


            devices.put(deviceId, outputs);
        }

        return devices;
    }

    static class Path {

        final List<String> steps;
        String currentDeviceId;

        Path(String currentDeviceId) {
            this.currentDeviceId = currentDeviceId;
            steps = new ArrayList<>();
            steps.add(currentDeviceId);
        }

        Path(Path prev, String currentDeviceId) {
            this.currentDeviceId = currentDeviceId;
            steps = new ArrayList<>(prev.steps);
            steps.add(currentDeviceId);
        }

    }

    public long solvePuzzle1(List<String> input) {

        Map<String, List<String>> devices = parseInput(input);


        Queue<Path> queue = new ArrayDeque<>();

        queue.add(new Path("you"));

        long pathCount = 0;

        while (!queue.isEmpty()) {
            Path path = queue.poll();

            List<String> outputs = devices.get(path.currentDeviceId);

            for (String outputDeviceId : outputs) {
                switch (outputDeviceId) {
                    case "out":
                        System.out.println(path.steps);
                        pathCount++;
                        // fallthrough
                    case "you":
                        continue;
                    default:
                        queue.add(new Path(path, outputDeviceId));
                }

            }

        }

        return pathCount;
    }

    public long solvePuzzle2(List<String> input) {

        return 0;

    }

}