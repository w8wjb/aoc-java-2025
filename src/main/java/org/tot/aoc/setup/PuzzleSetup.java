package org.tot.aoc.setup;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

public class PuzzleSetup {

    private final int year;

    public static void main(String[] args) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        if (args.length > 0) {
            year = Integer.parseInt(args[0]);
        }

        try {
            new PuzzleSetup(year).run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }


    }

    PuzzleSetup(int year) {
        this.year = year;
    }

    private void run() throws IOException {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.year);

        int endDay = year < 2025 ? 25 : 12;

        for (int day = 1; day <= endDay; day++) {
            cal.set(Calendar.DAY_OF_MONTH, day);

            Date date = cal.getTime();
            Date now = new Date();

            if (date.after(now)) {
                continue;
            }

            setupMissingDays(day);
        }

    }

    private void setupMissingDays(int day) throws IOException {

        makeSolutionTemplate(day);
        makeTestTemplate(day);
        downloadInput(day);

    }

    private void makeSolutionTemplate(int day) throws IOException {

        Path packagePath = Path.of("src/main/java/org/tot/aoc");
        if (!Files.exists(packagePath)) {
            Files.createDirectories(packagePath);
        }

        Path codePath = packagePath.resolve(String.format("Day%d.java", day));
        if (Files.exists(codePath)) {
            return;
        }

        Path template = Path.of("src/main/resources/DayXX.java");
        String templateContent = Files.readString(template);

        templateContent = templateContent.replace("DayXX", String.format("Day%d", day));

        System.out.println("Making " + codePath);
        Files.writeString(codePath, templateContent);

    }

    private void makeTestTemplate(int day) throws IOException {

        Path packagePath = Path.of("src/test/java/org/tot/aoc");
        if (!Files.exists(packagePath)) {
            Files.createDirectories(packagePath);
        }

        Path codePath = packagePath.resolve(String.format("Day%dTest.java", day));
        if (Files.exists(codePath)) {
            return;
        }

        Path template = Path.of("src/main/resources/DayXXTest.java");
        String templateContent = Files.readString(template);

        templateContent = templateContent.replace("DayXX", String.format("Day%d", day));

        System.out.println("Making " + codePath);
        Files.writeString(codePath, templateContent);
    }

    private void downloadInput(int day) throws IOException {

        Path packagePath = Path.of(String.format("src/test/resources/Day%d", day));
        if (!Files.exists(packagePath)) {
            Files.createDirectories(packagePath);
        }

        Path inputPath = packagePath.resolve("input1.txt");
        if (Files.exists(inputPath)) {
            return;
        }

        String session = System.getenv("AOC_SESSION");
        if (session == null) {
            throw new IOException("Missing $AOC_SESSION");
        }

        String url = String.format("https://adventofcode.com/%d/day/%d/input", this.year, day);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", "session=" + session)
                .build();

        try {
            System.out.println("Downloading " + inputPath);
            HttpClient client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch " + url);
            }
            String fileContent = response.body();
            Files.writeString(inputPath, fileContent);

        } catch (InterruptedException e) {
            throw new IOException(e);
        }

    }

}
