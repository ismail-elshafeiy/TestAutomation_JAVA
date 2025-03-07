package com.engine.actions;

import com.engine.Helper;
import com.google.common.hash.Hashing;
import com.engine.reports.Logger;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.sikuli.basics.FileManager;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.engine.reports.Logger.failAction;
import static com.engine.reports.Logger.passAction;
import static org.testng.FileAssert.fail;

public class FileActions {

    private static final String ERROR_CANNOT_CREATE_DIRECTORY = "Could not create directory: ";

    public static FileActions getInstance() {
        return new FileActions();
    }

    //********************************************************************************************************************************************************************************//
    //**************************************************************************************** Folder Action Methods ******************************************************************//
    public void createFolder(String folderPath) {
        try {
            FileUtils.forceMkdir(new File(folderPath));
            passAction("Target Folder: \"" + folderPath + "\"");
        } catch (IOException rootCauseException) {
            failAction(rootCauseException);
        }
    }

    public void copyFolder(String sourceFolderPath, String destinationFolderPath) {
        File sourceFolder = new File(sourceFolderPath);
        File destinationFolder = new File(destinationFolderPath);
        try {
            FileUtils.copyDirectory(sourceFolder, destinationFolder);
            passAction("Source Folder: \"" + sourceFolderPath + "\" | Destination Folder: \"" + destinationFolder + "\"");
        } catch (IOException rootCauseException) {
            failAction(rootCauseException);
        }
    }

    public void deleteFolder(String folderPath) {
        deleteFile(folderPath);
    }

    public void copyFolderFromJar(String sourceFolderPath, String destinationFolderPath) {
        try {
            URL url = new URL(sourceFolderPath.replace("file:", "jar:file:"));
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarConnection.getJarFile();
            /*
             * Iterate all entries in the jar file.
             */
            for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); ) {
                JarEntry jarEntry = e.nextElement();
                String jarEntryName = jarEntry.getName();
                String jarConnectionEntryName = jarConnection.getEntryName();
                /*
                 * Extract files only if they match the path.
                 */
                if (jarEntryName.startsWith(jarConnectionEntryName)) {
                    String filename = jarEntryName.startsWith(jarConnectionEntryName) ? jarEntryName.substring(jarConnectionEntryName.length()) : jarEntryName;
                    File currentFile = new File(destinationFolderPath, filename);
                    if (!currentFile.toPath().normalize().startsWith(new File(destinationFolderPath).toPath())) {
                        throw new Exception("Bad zip entry");
                    }
                    if (jarEntry.isDirectory()) {
                        boolean success = currentFile.mkdirs();
                        if (success) {
                            Logger.logError("Directory Created successfully...");
                        }
                    } else {
                        InputStream is = jarFile.getInputStream(jarEntry);
                        OutputStream out = FileUtils.openOutputStream(currentFile);
                        IOUtils.copy(is, out);
                        is.close();
                        out.close();
                    }
                }
            }
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
    }


    private void zipFolder(String srcFolder, String destZipFile) {
        /*
         * create the output stream to zip file result
         */
        try (FileOutputStream fileWriter = new FileOutputStream(destZipFile);
             ZipOutputStream zip = new ZipOutputStream(fileWriter)) {

            /*
             * add the folder to the zip
             */
            addFolderToZip("", srcFolder, zip);
            /*
             * close the zip objects
             */
            zip.flush();
        } catch (IOException rootCauseException) {
            failAction(rootCauseException);
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
        File folder = new File(srcFolder);

        /*
         * check the empty folder
         */
        if (Objects.requireNonNull(folder.list()).length == 0) {
            addFileToZip(path, srcFolder, zip, true);
        } else {
            /*
             * list the files in the folder
             */
            for (String fileName : Objects.requireNonNull(folder.list())) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + FileSystems.getDefault().getSeparator() + fileName, zip,
                            false);
                } else {
                    addFileToZip(path + FileSystems.getDefault().getSeparator() + folder.getName(),
                            srcFolder + FileSystems.getDefault().getSeparator() + fileName, zip, false);
                }
            }
        }
    }

    //********************************************************************************************************************************************************************************//
    //*************************************************************************** File Action Methods **********************************************************************************************************//
    public void createFile(String folderPath, String fileName) {
        try {
            FileUtils.forceMkdir(new File(folderPath));
            FileUtils.touch(new File(folderPath + fileName));
            passAction("Target Folder: \"" + folderPath + "\", Target File: \"" + fileName + "\"");
        } catch (IOException rootCauseException) {
            failAction(rootCauseException);
        }
    }

    /**
     * Tests whether the file or directory denoted by this abstract pathname exists.
     *
     * @param fileFolderName  The location of the folder that contains the target
     *                        file, relative to the project's root folder, ending
     *                        with a /
     * @param fileName        The name of the target file (including its extension
     *                        if any)
     * @param numberOfRetries number of times to try to find the file, given that
     *                        each retry is separated by a 500 millisecond wait time
     * @return true if the file exists, false if it doesn't
     */
    public boolean doesFileExist(String fileFolderName, String fileName, int numberOfRetries) {
        boolean doesFileExit = false;
        int i = 0;
        while (i < numberOfRetries) {
            try {
                doesFileExit = (new File(fileFolderName + fileName)).getAbsoluteFile().exists();
            } catch (Exception rootCauseException) {
                Logger.logError(rootCauseException.getMessage());
            }

            if (Boolean.FALSE.equals(doesFileExit)) {
                try {
                    Thread.sleep(500);
                } catch (Exception rootCauseException) {
                    Logger.logThrowable(rootCauseException);
                }
            }
            i++;
        }
        passAction("File Path: \"" + fileFolderName + fileName + "\"");
        return doesFileExit;
    }

    public boolean doesFileExist(String targetFile) {
        boolean doesFileExit = false;
        try {
            doesFileExit = (new File(targetFile)).getAbsoluteFile().exists();
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
        passAction("File Path: \"" + targetFile + "\"");
        return doesFileExit;
    }

    public byte[] readFileAsByteArray(String pathToTargetImage) {
        byte[] data = new byte[0];
        String absoluteFilePath = getAbsolutePath(pathToTargetImage);
        Path filePath = Paths.get(absoluteFilePath);

        try {
            data = Files.readAllBytes(filePath);
            passAction("File Path: \"" + filePath + "\"");
        } catch (IOException rootCauseException) {
            failAction(rootCauseException);
        }
        return data;
    }

    public String readFile(String pathToTargetFile) {
        String absoluteFilePath = getAbsolutePath(pathToTargetFile);
        String text = FileManager.readFileToString(new File(absoluteFilePath));
        passAction("File Path: \"" + absoluteFilePath + "\"", text);
        return text;
    }

    public String readFile(String fileFolderName, String fileName) {
        return readFile(fileFolderName + fileName);
    }

    public void writeToFile(String fileFolderName, String fileName, byte[] content) {
        String absoluteFilePath = getAbsolutePath(fileFolderName, fileName);
        try {
            Path filePath = Paths.get(absoluteFilePath);
            Path parentDir = filePath.getParent();
            if (!parentDir.toFile().exists()) {
                Files.createDirectories(parentDir);
            }
            Files.write(filePath, content);
            passAction("Target File Path: \"" + filePath + "\"", Arrays.toString(content));
        } catch (InvalidPathException | IOException rootCauseException) {

            failAction("Folder Name: \"" + fileFolderName + "\", File Name \"" + fileName + "\".", rootCauseException);
        }
    }

    public void writeToFile(String fileFolderName, String fileName, String text) {
        byte[] textToBytes = text.getBytes();
        writeToFile(fileFolderName, fileName, textToBytes);
    }

    public void writeToFile(String fileFolderName, String fileName, List<String> text) {
        byte[] textToBytes = String.join(System.lineSeparator(), text).getBytes();
        writeToFile(fileFolderName, fileName, textToBytes);
    }

    /**
     * Deletes a file from the local storage
     *
     * @param targetFilePath the full (absolute) path of the source file that will
     *                       be deleted
     */
    public void deleteFile(String targetFilePath) {
        boolean wasFileDeleted = FileUtils.deleteQuietly(new File(targetFilePath));
        String negation = wasFileDeleted ? "" : "not";
        passAction("Target File Path: \"" + targetFilePath + "\", file was " + negation + " deleted.");
    }

    /**
     * This method is used to copy a certain file from a remote machine (dockerized
     * or not) to the current execution machine.
     *
     * @param terminalSession                    provides information about the
     *                                           machine which contains the target
     *                                           file
     * @param targetFileFolderPath               the full absolute path of the
     *                                           folder that contains the target
     *                                           file, must end with a slash "/"
     * @param targetFileName                     the name and extension of the
     *                                           target file
     * @param pathToTempDirectoryOnRemoteMachine [OPTIONAL] to be used in case of
     *                                           dockerized target machine. This is
     *                                           a temporary directory that will be
     *                                           created on the remote machine to
     *                                           extract a file from inside a
     *                                           docker, and will be deleted
     *                                           afterward
     * @return a string that holds the full absolute path (inside a temporary
     * folder) for the file that was copied to the local machine
     */
    public String copyFileToLocalMachine(TerminalActions terminalSession, String targetFileFolderPath,
                                         String targetFileName, String... pathToTempDirectoryOnRemoteMachine) {
        String targetFilePath = targetFileFolderPath + targetFileName;
        TerminalActions terminalSessionForRemoteMachine = new TerminalActions();

        // fetch file from inside docker to the machine itself
        if (terminalSession.isDockerizedTerminal()) {
            terminalSessionForRemoteMachine = new TerminalActions(terminalSession.getSshHostName(),
                    terminalSession.getSshPortNumber(), terminalSession.getSshUsername(),
                    terminalSession.getSshKeyFileFolderName(), terminalSession.getSshKeyFileName());

            // copy from docker to machine
            terminalSessionForRemoteMachine.performTerminalCommand("rm -r " + pathToTempDirectoryOnRemoteMachine[0]);
            terminalSessionForRemoteMachine
                    .performTerminalCommand("mkdir -p " + pathToTempDirectoryOnRemoteMachine[0] + targetFileFolderPath);
            terminalSessionForRemoteMachine.performTerminalCommand("docker cp " + terminalSession.getDockerName() + ":"
                    + targetFilePath + " " + pathToTempDirectoryOnRemoteMachine[0] + targetFilePath);
            targetFilePath = pathToTempDirectoryOnRemoteMachine[0] + targetFilePath;
        }

        // fetch file from terminal session to local machine
        if (terminalSession.isRemoteTerminal()) {
            // remote regular
            String sshParameters = "-i " + FileActions.getInstance().getAbsolutePath(terminalSession.getSshKeyFileFolderName(),
                    terminalSession.getSshKeyFileName()) + " -P " + terminalSession.getSshPortNumber();

            String pathToRemoteFileThatWillBeCopied = targetFilePath;
            String source = terminalSession.getSshUsername() + "@" + terminalSession.getSshHostName() + ":"
                    + pathToRemoteFileThatWillBeCopied;

            // creating local temp directory
            String pathToLocalParentFolder = FileActions.getInstance().getAbsolutePath("target/temp");
            FileActions.getInstance().deleteFolder(pathToLocalParentFolder);
            FileActions.getInstance().createFolder(pathToLocalParentFolder);

            String destination = pathToLocalParentFolder + "/" + targetFileName;
            targetFilePath = destination;

            String command = "scp -v -o StrictHostKeyChecking=no " + sshParameters + " -r " + source + " "
                    + destination;

            // restricting file access to bypass jenkins issue
            (new TerminalActions()).performTerminalCommand("chmod 400 " + FileActions
                    .getInstance().getAbsolutePath(terminalSession.getSshKeyFileFolderName(), terminalSession.getSshKeyFileName()));

            (new TerminalActions()).performTerminalCommand(command);
        }
        // else local regular
        // it's already on the local machine so no need to do anything here


        // clean temp directory on remote machine
        if (terminalSession.isDockerizedTerminal() && terminalSession.isRemoteTerminal()) {
            terminalSessionForRemoteMachine.performTerminalCommand("rm -r " + Arrays.toString(pathToTempDirectoryOnRemoteMachine));
        }

        passAction("Target File Path: \"" + targetFilePath + "\"");
        return targetFilePath;
    }

    /**
     * Copies a file from sourceFilePath to destinationFilePath on the local storage
     *
     * @param sourceFilePath      the full (absolute) path of the source file that
     *                            will be copied
     * @param destinationFilePath the full (absolute) path of the desired location
     *                            and file name for the newly created copied file
     */
    public void copyFile(String sourceFilePath, String destinationFilePath) {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        copyFile(sourceFile, destinationFile);
        passAction("Source File: \"" + sourceFilePath + "\" | Destination File: \"" + destinationFilePath + "\"");
    }

    private void copyFile(File sourceFile, File destinationFile) {
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException rootCauseException) {

            failAction(rootCauseException);
        }
    }

    public void copyFileFromJar(String sourceFolderPath, String destinationFolderPath, String fileName) {
        try {
            URL url = new URL(sourceFolderPath.replace("file:", "jar:file:"));
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarConnection.getJarFile();

            /*
             * Iterate all entries in the jar file.
             */
            for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); ) {

                JarEntry jarEntry = e.nextElement();
                String jarEntryName = jarEntry.getName();
                String jarConnectionEntryName = jarConnection.getEntryName();

                /*
                 * Extract files only if they match the path.
                 */
                if (jarEntryName.startsWith(jarConnectionEntryName) && jarEntryName.contains(fileName)) {

                    String filename = jarEntryName.startsWith(jarConnectionEntryName) ? jarEntryName.substring(jarConnectionEntryName.length()) : jarEntryName;
                    File currentFile = new File(destinationFolderPath, filename);
                    if (!currentFile.toPath().normalize().startsWith(new File(destinationFolderPath).toPath())) {
                        throw new Exception("Bad zip entry");
                    }
                    if (jarEntry.isDirectory()) {
                        boolean success = currentFile.mkdirs();
                        if (success) {
                            Logger.logError("Directory Created successfully...");
                        }
                    } else {
                        InputStream is = jarFile.getInputStream(jarEntry);
                        OutputStream out = FileUtils.openOutputStream(currentFile);
                        IOUtils.copy(is, out);
                        is.close();
                        out.close();
                    }
                }
            }
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
    }

    public String listFilesInDirectory(String targetDirectory) {
        StringBuilder files = new StringBuilder();
        try {
            Collection<File> filesList = FileUtils.listFiles(new File(targetDirectory), TrueFileFilter.TRUE,
                    TrueFileFilter.TRUE);
            filesList.forEach(file -> files.append(file.getName()).append(System.lineSeparator()));
        } catch (IllegalArgumentException rootCauseException) {
            failAction("Failed to list files in this directory: \"" + targetDirectory + "\"", rootCauseException);
        }
        Logger.logConsole("Target Directory: \"" + targetDirectory + "\"" + files.toString().trim());
        return files.toString().trim();
    }

    public StringBuilder listFilesInDirectoryAsList(String targetDirectory) {
        StringBuilder files = new StringBuilder();
        try {
            Collection<File> filesList = FileUtils.listFiles(new File(targetDirectory), TrueFileFilter.TRUE, TrueFileFilter.TRUE);
            filesList.forEach(file -> files.append(file.getName()).append(System.lineSeparator()));
        } catch (IllegalArgumentException rootCauseException) {
            failAction("Failed to list files in this directory: \"" + targetDirectory + "\"", rootCauseException);
        }
        Logger.logConsole("Target Directory: \"" + targetDirectory + "\"" + files.toString().trim());
        return files;
    }

    public String listFilesInDirectory(String targetDirectory, TrueFileFilter recursively) {
        StringBuilder files = new StringBuilder();
        try {
            Collection<File> filesList = FileUtils.listFiles(new File(targetDirectory), TrueFileFilter.TRUE, recursively);
            filesList.forEach(file -> files.append(file.getName()).append(System.lineSeparator()));
        } catch (IllegalArgumentException rootCauseException) {
            failAction("Failed to list files in this directory: \"" + targetDirectory + "\"", rootCauseException);
        }
        passAction("Target Directory: \"" + targetDirectory + "\"", files.toString().trim());
        return files.toString().trim();
    }


    public void renameFile(String sourceFilePath, String destinationFilePath) {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        sourceFile.renameTo(destinationFile);
        passAction("Source File: \"" + sourceFilePath + "\" | Destination File: \"" + destinationFilePath + "\"");
    }

    /**
     * This method is used to compute the SHA256 checksum for any file. This
     * checksum can be used to compare two files and confirm that they are
     * identical, regardless of the file type.
     *
     * @param terminalSession                    provides information about the
     *                                           machine which contains the target
     *                                           file
     * @param targetFileFolderPath               the full absolute path of the
     *                                           folder that contains the target
     *                                           file, must end with a slash "/"
     * @param targetFileName                     the name and extension of the
     *                                           target file
     * @param pathToTempDirectoryOnRemoteMachine [OPTIONAL] to be used in case of
     *                                           dockerized target machine. This is
     *                                           a temporary directory that will be
     *                                           created on the remote machine to
     *                                           extract a file from inside a
     *                                           docker, and will be deleted
     *                                           afterward
     * @return a string that holds the SHA256 checksum for the target file
     */
    public String getFileChecksum(TerminalActions terminalSession, String targetFileFolderPath,
                                  String targetFileName, String... pathToTempDirectoryOnRemoteMachine) {

        String targetFilePath = copyFileToLocalMachine(terminalSession, targetFileFolderPath, targetFileName,
                pathToTempDirectoryOnRemoteMachine);

        // read file
        byte[] fileBytes;
        String sha256 = "";
        try {
            fileBytes = Files.readAllBytes(Paths.get(targetFilePath));
            sha256 = Hashing.sha256().hashBytes(fileBytes).toString();
        } catch (IOException rootCauseException) {

            failAction("Failed to read file \"" + targetFilePath + "\"", rootCauseException);
        }
        passAction("Target File: \"" + targetFilePath + "\" | SHA-256: \"" + sha256 + "\"");
        return sha256;
    }

    public Collection<File> getFileList(String targetDirectory) {
        StringBuilder files = new StringBuilder();
        Collection<File> filesList = new ArrayList<>();
        try {
            filesList = FileUtils.listFiles(new File(targetDirectory), TrueFileFilter.TRUE, TrueFileFilter.TRUE);
            filesList.forEach(file -> files.append(file.getAbsolutePath()).append(System.lineSeparator()));
        } catch (IllegalArgumentException rootCauseException) {
            failAction("Failed to list absolute file paths in this directory: \"" + targetDirectory + "\"", rootCauseException);
        }
        passAction("Target Directory: \"" + targetDirectory + "\"", files.toString().trim());
        return filesList;
    }

    public void convertFileToCSV(String excelFilePath, String csvFilePath) {
        //Create an instance of Workbook class
        Workbook workbook = new Workbook();
        //Load an Excel file
        workbook.loadFromFile(excelFilePath);
        //Get the first worksheet
        Worksheet sheet = workbook.getWorksheets().get(0);
        //Save the worksheet as CSV
        sheet.saveToFile(csvFilePath, ",");
    }

    public File getFileLastModified(String folderPath) {
        File dir = new File(folderPath);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile)).max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
            if (opFile.isPresent()) {
                Logger.logInfoStep("getFileLastModified: " + opFile.get().getPath());
                return opFile.get();
            } else {
                Logger.logInfoStep("getFileLastModified: " + opFile.get().getPath());
                return null;
            }
        }
        return null;
    }

    public File getFileLastModified2(String folderPath) {
        File dir = new File(folderPath);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(Objects.requireNonNull(dir.listFiles(File::isFile))).max(Comparator.comparingLong(File::lastModified));
            if (opFile.isPresent()) {
                Logger.logInfoStep("getFileLastModified: " + opFile.get().getPath());
                return opFile.get();
            } else {
                Logger.logInfoStep("getFileLastModified: " + opFile.get().getPath());
                return null;
            }
        }
        return null;
    }
    public URL downloadFile(String targetFileURL, String destinationFilePath) {
        return downloadFile(targetFileURL, destinationFilePath, 0, 0);
    }

    public URL downloadFile(String targetFileURL, String destinationFilePath, int connectionTimeout, int readTimeout) {
        if (targetFileURL != null && destinationFilePath != null) {
            try {
                Logger.logInfoStep("Downloading a file from this url \"" + targetFileURL + "\" to this directory \""
                        + destinationFilePath + "\", please wait as downloading may take some time...");
                FileUtils.copyURLToFile(new URL(targetFileURL), new File(destinationFilePath), connectionTimeout,
                        readTimeout);
                Logger.logInfoStep("Downloading completed successfully.");
                URL downloadedFile = new File(destinationFilePath).toURI().toURL();
                passAction("Target File URL\"" + targetFileURL + "\" | Destination Folder: \"" + destinationFilePath
                        + "\" | Connection Timeout: \"" + connectionTimeout + "\" | Read Timeout: \"" + readTimeout
                        + "\"");

                return downloadedFile;
            } catch (IOException rootCauseException) {

                failAction("Target File URL: \"" + targetFileURL + "\", and Destination File Path: \""
                        + destinationFilePath + "\"", rootCauseException);
                return null;
            }
        } else {
            failAction("Target File URL: \"" + targetFileURL + "\", and Destination File Path: \"" + destinationFilePath
                    + "\"");
            return null;
        }
    }


    private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag)
            throws IOException {
        /*
         * create the file object for inputs
         */
        File folder = new File(srcFile);

        /*
         * if the folder is empty add empty folder to the Zip file
         */
        if (flag) {
            zip.putNextEntry(new ZipEntry(path + FileSystems.getDefault().getSeparator() + folder.getName()
                    + FileSystems.getDefault().getSeparator()));
        } else { /*
         * if the current name is directory, recursively traverse it to get the files
         */
            if (folder.isDirectory()) {
                /*
                 * if folder is not empty
                 */
                addFolderToZip(path, srcFile, zip);
            } else {
                /*
                 * write the file to the output
                 */
                try (FileInputStream in = new FileInputStream(srcFile)) {
                    byte[] buf = new byte[1024];
                    int len;
                    zip.putNextEntry(new ZipEntry(path + FileSystems.getDefault().getSeparator() + folder.getName()));
                    while ((len = in.read(buf)) > 0) {
                        /*
                         * Write the Result
                         */
                        zip.write(buf, 0, len);
                    }
                } catch (Exception rootCauseException) {
                    failAction(rootCauseException);
                }
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean zipFiles(String srcFolder, String destZipFile) {
        boolean result = false;
        try (var fileWalker = Files.walk(Paths.get(srcFolder))) {
            Logger.logError("Archiving the following files:\n");
            zipFolder(srcFolder, destZipFile);
            result = true;
            passAction("Target Folder: \"" + srcFolder + "\" | Destination Archive: \"" + destZipFile + "\"");
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
        return result;
    }

    @SuppressWarnings("UnusedReturnValue")
    public File unpackArchive(URL url, String destinationFolderPath) {
        File targetDir = new File(destinationFolderPath);
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            failAction("file: " + url.toString() + " to directory: " + destinationFolderPath);
        }
        File unpacked = null;
        try (InputStream in = new BufferedInputStream(url.openStream(), 1024)) {
            // make sure we get the actual file
            File zip = File.createTempFile("archive_", url.toString().substring(url.toString().length() - 4), targetDir);
            zip.deleteOnExit();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
            copyInputStream(in, out);
            out.close();
            unpacked = unpackArchive(zip, targetDir);
            passAction("Target URL\"" + url + "\" | Destination Folder: \"" + destinationFolderPath + "\"");
        } catch (IOException rootCauseException) {

            failAction("file: " + url + " to directory: " + destinationFolderPath, rootCauseException);
        }
        return unpacked;
    }
    //***************************************************************************************************************************************************************//
    //************************************************************************* Helper Methods *********************************************************************//

    /**
     * @return Get the path to your source directory with a / at the end
     */
    public static String getDir() {
        return System.getProperty("user.dir") + File.separator;
    }

    /**
     * Returns the full (absolute) file/folder path using the project-relative
     * fileFolderName and the fileName
     *
     * @param fileFolderName The location of the folder that contains the target
     *                       file, relative to the project's root directory, ending
     *                       with a /
     * @param fileName       The name of the target file (including its extension if
     *                       any)
     * @return a string value that represents the full/absolute file/folder path
     */
    public String getAbsolutePath(String fileFolderName, String fileName) {
        String filePath = "";
        try {
            filePath = (new File(fileFolderName + fileName)).getAbsolutePath();
            passAction("Relative File Path: \"" + fileFolderName + fileName + "\"", filePath);
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
        return filePath;
    }

    /**
     * Returns the full (absolute) file/folder path using the project-relative relativePath
     *
     * @param relativePath The location of the target file or folder, relative to the project's root directory, ending with a / if it's a folder
     * @return a string value that represents the full/absolute file/folder path
     */
    public String getAbsolutePath(String relativePath) {
        relativePath = Helper.appendTestDataToRelativePath(relativePath);

        String filePath = "";
        try {
            filePath = (new File(relativePath)).getAbsolutePath();
            passAction("Relative Folder Path: \"" + relativePath + "\"", filePath);
        } catch (Exception rootCauseException) {
            failAction(rootCauseException);
        }
        return filePath;
    }


    private File unpackArchive(File theFile, File targetDir) throws IOException {
        if (!theFile.exists()) {
            throw new IOException(theFile.getAbsolutePath() + " does not exist");
        }
        if (buildDirectory(targetDir)) {
            throw new IOException(ERROR_CANNOT_CREATE_DIRECTORY + targetDir);
        }

        try (ZipFile zipFile = new ZipFile(theFile)) {
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = entries.nextElement();
                File file = new File(targetDir, File.separator + entry.getName());
                if (!file.toPath().normalize().startsWith(targetDir.toPath()))
                    throw new IOException("Bad zip entry");
                if (buildDirectory(file.getParentFile())) {
                    throw new IOException(ERROR_CANNOT_CREATE_DIRECTORY + file.getParentFile());
                }
                if (!entry.isDirectory()) {
                    copyInputStream(zipFile.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(file)));
                } else {
                    if (buildDirectory(file)) {
                        throw new IOException(ERROR_CANNOT_CREATE_DIRECTORY + file);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        passAction("Target File\"" + theFile.getAbsolutePath() + "\" | Destination Folder: \"" + targetDir + "\"");
        return theFile;
    }

    private void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len >= 0) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }

    private boolean buildDirectory(File file) {
        return !file.exists() && !file.mkdirs();
    }
}
