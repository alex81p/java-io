package com.example;

import com.example.exceptions.AppException;
import com.example.model.DirectoryAnalyzer;
import com.example.model.DirectoryReader;
import com.example.model.DirectoryWriter;

import java.io.File;

public class App 
{
    public static void main( String[] args ) {
        if (args.length == 0){
            throw new AppException("The required argument (path) is missing");
        }
        File file = new File(args[0]);
        if (!file.exists()){
            throw new AppException("File or directory doesn't exist: " + file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            DirectoryWriter writer = new DirectoryWriter(args[0], "out" + File.separator + "tree.txt");
            writer.writeDirectoryToFile();
        } else {
            DirectoryReader reader = new DirectoryReader(args[0], "temp", true);
            reader.restoreDirectoryFromFile();
            DirectoryAnalyzer analyzer = new DirectoryAnalyzer(reader.getRootDirectoryPath());
            System.out.println("The number of directories: " + analyzer.countDirectories());
            System.out.println("The number of files: " + analyzer.countFiles());
            System.out.println("The average number of files in the directories:" + analyzer.getAverageNumberOfFilesInDirectories());
            System.out.println("The average length of the filenames: " + analyzer.getAverageLengthOfFilenames());
        }
    }
}
