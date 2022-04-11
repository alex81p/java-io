package com.example.model;

import com.example.exceptions.DirectoryAnalyzerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryAnalyzer {

    private String rootDirectory;

    public DirectoryAnalyzer(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public long countFiles(){
        try {
            return Files
                    .walk(Paths.get(rootDirectory))
                    .filter(Files::isRegularFile)
                    .count();
        } catch (IOException e) {
            throw new DirectoryAnalyzerException("Unable to count the files", e);
        }
    }

    public long countDirectories() {
        try {
            return Files
                    .walk(Paths.get(rootDirectory))
                    .filter(Files::isDirectory)
                    .count();
        } catch (IOException e) {
            throw new DirectoryAnalyzerException("Unable to count the directories", e);
        }
    }

    public double getAverageNumberOfFilesInDirectories(){
        return (double) (countFiles() * 1000 / countDirectories()) / 1000;
    }

    public double getAverageLengthOfFilenames(){
        long totalLength;
        try {
            totalLength = Files
                    .walk(Paths.get(rootDirectory))
                    .filter(Files::isRegularFile)
                    .mapToInt(o -> o.getFileName().toString().length())
                    .sum();
        } catch (IOException e) {
            throw new DirectoryAnalyzerException("Unable to count the directories", e);
        }
        return (double) (totalLength * 1000 / countFiles()) / 1000;
    }
}
