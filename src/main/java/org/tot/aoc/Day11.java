package org.tot.aoc;

import java.util.*;

public class Day11 {

    Map<String, List<String>> devices = new HashMap<>();

    Map<String, Long> pathCounts = new HashMap<>();

    private static Map<String, List<String>> parseInput(List<String> input) {
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

        long stepCount;
        String currentDeviceId;

        Path(String currentDeviceId) {
            this.currentDeviceId = currentDeviceId;
            stepCount = 0;
        }

        Path(Path prev, String currentDeviceId) {
            this.currentDeviceId = currentDeviceId;
            stepCount = prev.stepCount + 1;
        }

    }

    public long solvePuzzle1(List<String> input) {

        this.devices = parseInput(input);


        Queue<Path> queue = new ArrayDeque<>();

        queue.add(new Path("you"));

        long pathCount = 0;

        while (!queue.isEmpty()) {
            Path path = queue.poll();

            List<String> outputs = devices.get(path.currentDeviceId);

            for (String outputDeviceId : outputs) {
                switch (outputDeviceId) {
                    case "out":
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

        this.devices = parseInput(input);

        return pathsToOut("svr", false, false);
    }

    private long pathsToOut(String deviceId, boolean fftSeen, boolean dacSeen) {

        String memoKey = String.format("%s%d%s", deviceId, fftSeen ? 1 : 0, dacSeen ? 1 : 0);

        Long memoCount = pathCounts.get(memoKey);
        if (memoCount != null) {
            return memoCount;
        }

        List<String> outputs = devices.get(deviceId);


        long count = 0;
        for (String outputDeviceId : outputs) {
            if ("out".equals(outputDeviceId)) {
                return fftSeen && dacSeen ? 1 : 0;
            }

            if ("dac".equals(outputDeviceId)) {
                count += pathsToOut(outputDeviceId, fftSeen, true);
            } else if ("fft".equals(outputDeviceId)) {
                count += pathsToOut(outputDeviceId, true, dacSeen);
            } else {
                count += pathsToOut(outputDeviceId, fftSeen, dacSeen);
            }

        }

        pathCounts.put(memoKey, count);
        return count;
    }


}