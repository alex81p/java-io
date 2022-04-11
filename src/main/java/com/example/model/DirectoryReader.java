package com.example.model;

import com.example.exceptions.DirectoryReaderException;
import com.example.exceptions.DirectoryWriterException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryReader {

    private static final String VERSION = "DirectoryTreeProcessor v1.0";

    private String readFromFilePath;
    private String writeToDirectoryPath;
    private boolean deleteOnExit;
    private String rootDirectoryPath;

    public DirectoryReader(String readFromFilePath, String writeToDirectoryPath, boolean deleteOnExit) {
        this.readFromFilePath = readFromFilePath;
        this.writeToDirectoryPath = writeToDirectoryPath;
        this.deleteOnExit = deleteOnExit;
    }

    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }

    public void restoreDirectoryFromFile() {
        List<String> strings = readFile();
        File rootDirectory = createRootDirectory(strings);
        createFilesFromStrings(strings, rootDirectory);
    }

    private List<String> readFile(){
        List<String> strings;
        try (BufferedReader reader = new BufferedReader(new FileReader(readFromFilePath))) {
            strings = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new DirectoryReaderException("Unable to read the file: " + readFromFilePath, e);
        }
        if (strings.isEmpty() || !strings.get(0).equals(VERSION)) {
            throw new DirectoryReaderException("The file is not supported");
        }
        return strings;
    }

    private File createRootDirectory(List<String> strings){
        File rootDirectory = new File(writeToDirectoryPath + File.separator + strings.get(1));
        if (!rootDirectory.mkdir()) {
            throw new DirectoryReaderException("Unable to create root directory");
        }
        if (deleteOnExit) {
            rootDirectory.deleteOnExit();
        }
        rootDirectoryPath = rootDirectory.getAbsolutePath();
        return rootDirectory;
    }

    private void createFilesFromStrings(List<String> strings, File directory){
        int depth = 0;
        for (int i = 2; i < strings.size(); i++) {
            String string = strings.get(i).substring(depth * 7);
            if (string.startsWith(" |-----")){
                directory = new File(directory.getAbsolutePath() + File.separator + string.substring(7));
                if (directory.mkdir()) {
                    depth++;
                } else {
                    throw new DirectoryReaderException("Unable to create the directory: " + directory.getAbsolutePath());
                }
                if (deleteOnExit){
                    directory.deleteOnExit();
                }
            }
            if (string.startsWith("   ")){
                File newFile = new File(directory.getAbsolutePath() + File.separator + string.substring(3));
                try {
                    newFile.createNewFile();
                } catch (IOException | SecurityException e) {
                    throw new DirectoryWriterException("Unable to create the file", e);
                }
                if (deleteOnExit){
                    newFile.deleteOnExit();
                }
            }
            if (string.isEmpty()){
                directory = directory.getParentFile();
                depth--;
            }
        }
    }
}
