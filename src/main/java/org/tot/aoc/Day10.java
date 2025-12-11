package org.tot.aoc;

import javax.crypto.Mac;
import java.util.*;
import java.util.regex.Pattern;

public class Day10 {


    static class Machine {
        int target = 0;
        List<Integer> buttons = new ArrayList<>();
        String joltage = null;
    }

    List<Machine> machines = new ArrayList<>();

    static class MachineState {
        Machine machine;
        int depth;
        int currentLights;
        int nextButton;

        MachineState(Machine machine, int nextButton) {
            this.machine = machine;
            this.nextButton= nextButton;
            this.depth = 1;
        }

        MachineState(MachineState state, int nextButton) {
            this.machine = state.machine;
            this.currentLights = state.currentLights;
            this.nextButton= nextButton;
            this.depth = state.depth + 1;
        }


        boolean press() {
            int buttonValue = machine.buttons.get(nextButton);
            currentLights ^= buttonValue;
            return currentLights == machine.target;
        }

    }

    private void parseInput(List<String> input) {

        Pattern majorSectionsPattern = Pattern.compile("\\[(.+)]\\s(\\(.+\\))\\s\\{(.+)}");

        for (String line : input) {

            var matcher = majorSectionsPattern.matcher(line);
            if (!matcher.find()) {
                continue;
            }

            Machine machine = new Machine();

            String targetString = matcher.group(1);

            for (int i = 0; i < targetString.length(); i++) {
                if (targetString.charAt(i) == '#') {
                    machine.target |= 1 << i;
                }
            }

            String buttonDef = matcher.group(2);
            int buttonValue = 0;

            for (char c : buttonDef.toCharArray()) {
                if (Character.isDigit(c)) {
                    buttonValue |= (1 << (c - '0'));
                } else if (c == '(') {
                    buttonValue = 0;
                } else if (c == ')') {
                    machine.buttons.add(buttonValue);
                }

            }

            machine.joltage = matcher.group(3);

            machines.add(machine);

        }

    }

    public long solvePuzzle1(List<String> input) {

        parseInput(input);

        int total = 0;

        for (Machine machine : machines) {

            Deque<MachineState>  queue = new ArrayDeque<>();

            for (int i = 0; i < machine.buttons.size(); i++) {
                var state = new MachineState(machine, i);
                queue.offer(state);
            }

            MachineState state;
            while ((state = queue.poll()) != null) {

                if (state.press()) {
                    total += state.depth;
                    break;
                }

                for (int i = 0; i < machine.buttons.size(); i++) {
                    if (state.nextButton == i) {
                        continue; // Don't press a button twice in a row
                    }
                    var nextState = new MachineState(state, i);
                    queue.offer(nextState);
                }


            }

        }


        return total;
    }

    public long solvePuzzle2(List<String> input) {

        return 0;

    }

}