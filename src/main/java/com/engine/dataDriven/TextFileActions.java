package com.engine.dataDriven;

import com.engine.reports.CustomReporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TextFileActions {

    public TextFileActions() {
        super();
    }

    /**
     * This method will create a text file and write the text in it
     *
     * @param filepath the path of the text file
     * @param text     the text to be written in the text file
     */
    public static void setTextFile(String filepath, String text) {
        try {
            File file = new File(filepath);
            while (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text + "\n" + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will return the text file as a string based on the line number
     *
     * @param filepath the path of the text file
     */
    public static void getTextFile(String filepath) {
        try {
            File file = new File(filepath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                CustomReporter.logConsole("Text File Reader: " + line);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            CustomReporter.logError("File not found: [ " + filepath + " ] " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            CustomReporter.logError("IO Exception: [ " + filepath + " ] " + e.getMessage());
        }
    }

    /**
     * This method will return the text file as a string based on the line number
     *
     * @param filepath the path of the text file
     * @param line     the line number of the text file
     * @return the text file as a string
     */

    public static String getTextFile(String filepath, int line) {
        List<String> lines;
        String value;
        try {
            lines = Files.readAllLines(new File(filepath).toPath());
            value = lines.get(line - 1);
            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method will return all the text file as a string
     *
     * @param filepath the path of the text file
     * @return the text file as a string
     */
    public static String getAllTextFile(String filepath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(new File(filepath).toPath());
            return lines.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(String sourceFilePath, String targetFilePath) {
        try {
            Files.copy(Paths.get(sourceFilePath), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFileLastModified(String folderPath) {
        File dir = new File(folderPath);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile)).max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
            if (opFile.isPresent()) {
                CustomReporter.logConsole("getFileLastModified: " + opFile.get().getPath());
                return opFile.get();
            } else {
                CustomReporter.logConsole("getFileLastModified: " + opFile.get().getPath());
                return null;
            }
        }
        return null;
    }

}
