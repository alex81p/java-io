package com.example.model;

import com.example.exceptions.DirectoryWriterException;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DirectoryWriter {

    private static final String VERSION = "DirectoryTreeProcessor v1.0";
    private String rootDirectoryPath;
    private String writeToFilePath;

    public DirectoryWriter(String rootDirectoryPath, String writeToFilePath) {
        this.rootDirectoryPath = rootDirectoryPath;
        this.writeToFilePath = writeToFilePath;
    }

    public void writeDirectoryToFile() {
        File rootDirectory = new File(rootDirectoryPath);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(writeToFilePath)))){
            writer.println(VERSION);
            writer.println(rootDirectory.getName());
            printDirectory(rootDirectory, writer, new StringBuilder());
        } catch (IOException e) {
            throw new DirectoryWriterException("Unable to write to the file: " + writeToFilePath, e);
        }

    }

    private void printDirectory(File file, PrintWriter writer, StringBuilder tree) {
        List<File> files = Arrays.asList(file.listFiles());
        files.sort(Comparator.comparingInt(o -> o.isFile() ? 1 : 0));
        for (int i = 0; i < files.size(); i++) {
            writer.println(tree + (files.get(i).isDirectory() ? " |-----" : "   ") + files.get(i).getName());
            if (files.get(i).isDirectory()) {
                tree.append((i == files.size() - 1) ? "       " : " |     ");
                printDirectory(files.get(i), writer, tree);
                writer.println(tree);
                tree.delete(tree.length() - 7, tree.length());
            }
        }
    }
}
