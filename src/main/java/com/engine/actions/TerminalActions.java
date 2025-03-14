package com.engine.actions;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.engine.reports.Logger;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.engine.reports.Logger.failAction;
import static com.engine.reports.Logger.passAction;

@SuppressWarnings("unused")
public class TerminalActions {
    private String sshHostName = "";
    private String sshUsername;
    private String sshKeyFileFolderName;
    private String sshKeyFileName;
    private int sshPortNumber = 22;

    private final String dockerName = "";
    private String dockerUsername;

    private boolean asynchronous = false;
    private boolean verbose = false;

    /**
     * This constructor is used for local terminal actions.
     */
    public TerminalActions() {
    }

    /**
     * This constructor is used for local terminal actions.
     *
     * @param asynchronous true for asynchronous execution of commands in a separate thread
     */
    public TerminalActions(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    private TerminalActions(boolean asynchronous, boolean verbose) {
        this.asynchronous = asynchronous;
        this.verbose = verbose;
    }


    /**
     * This constructor is used for remote terminal actions.
     *
     * @param sshHostName          the IP address or host name for the remote
     *                             machine you want to execute the terminal command
     *                             on.
     * @param sshPortNumber        the port that's used for the SSH service on the
     *                             target machine. Default is 22.
     * @param sshUsername          the username which will be used to access the
     *                             target machine via ssh. Must have the
     *                             access/privilege to execute the terminal command
     * @param sshKeyFileFolderName the directory that holds the ssh key file
     *                             (usually it's somewhere in the test data of the
     *                             current project)
     * @param sshKeyFileName       the name of the ssh key file
     */
    public TerminalActions(String sshHostName, int sshPortNumber, String sshUsername, String sshKeyFileFolderName,
                           String sshKeyFileName) {
        this.sshHostName = sshHostName;
        this.sshPortNumber = sshPortNumber;
        this.sshUsername = sshUsername;
        this.sshKeyFileFolderName = sshKeyFileFolderName;
        this.sshKeyFileName = sshKeyFileName;
    }

    /**
     * This constructor is used for remote terminal actions inside a docker.
     *
     * @param sshHostName          the IP address or host name for the remote
     *                             machine you want to execute the terminal command
     *                             on.
     * @param sshPortNumber        the port that's used for the SSH service on the
     *                             target machine. Default is 22.
     * @param sshUsername          the username which will be used to access the
     *                             target machine via ssh. Must have the
     *                             access/privilege to execute the terminal command
     * @param sshKeyFileFolderName the directory that holds the ssh key file
     *                             (usually it's somewhere in the test data of the
     *                             current project)
     * @param sshKeyFileName       the name of the ssh key file
     * @param dockerName           the name of the docker instance that you want to
     *                             execute the terminal command inside
     * @param dockerUsername       the username which will be used to access the
     *                             docker instance. Must have the access/privilege
     *                             to execute the terminal command
     */
    public TerminalActions(String sshHostName, int sshPortNumber, String sshUsername, String sshKeyFileFolderName,
                           String sshKeyFileName, String dockerName, String dockerUsername) {
        this.sshHostName = sshHostName;
        this.sshPortNumber = sshPortNumber;
        this.sshUsername = sshUsername;
        this.sshKeyFileFolderName = sshKeyFileFolderName;
        this.sshKeyFileName = sshKeyFileName;
    }

    public static TerminalActions getInstance() {
        return new TerminalActions();
    }

    public static TerminalActions getInstance(boolean asynchronous) {
        return new TerminalActions(asynchronous);
    }

    public static TerminalActions getInstance(boolean asynchronous, boolean verbose) {
        return new TerminalActions(asynchronous, verbose);
    }

    private static String reportActionResult(String actionName, String testData, String log, Boolean passFailStatus, Exception... rootCauseException) {
        actionName = actionName.substring(0, 1).toUpperCase() + actionName.substring(1);
        String message;
        if (Boolean.TRUE.equals(passFailStatus)) {
            message = "Terminal Action \"" + actionName + "\" successfully performed.";
        } else {
            message = "Terminal Action \"" + actionName + "\" failed.";
        }

        List<List<Object>> attachments = new ArrayList<>();
        if (testData != null && testData.length() >= 500) {
            List<Object> actualValueAttachment = Arrays.asList("Terminal Action Test Data - " + actionName,
                    "Actual Value", testData);
            attachments.add(actualValueAttachment);
        } else if (testData != null && !testData.isEmpty()) {
            message = message + " With the following test data \"" + testData + "\".";
        }

        if (log != null && !log.trim().equals("")) {
            attachments.add(Arrays.asList("Terminal Action Actual Result", "Command Log", log));
        }

        if (rootCauseException != null && rootCauseException.length >= 1) {
            List<Object> actualValueAttachment = Arrays.asList("Terminal Action Exception - " + actionName,
                    "Stacktrace", Logger.formatStackTraceToLogEntry(rootCauseException[0]));
            attachments.add(actualValueAttachment);
        }

        if (!attachments.equals(new ArrayList<>())) {
            Logger.logError(message);
        } else {
            Logger.logError(message);
        }
        return message;
    }

    public boolean isRemoteTerminal() {
        return !sshHostName.equals("");
    }

    public boolean isDockerizedTerminal() {
        return !dockerName.equals("");
    }

    public String performTerminalCommands(List<String> commands) {
        var internalCommands = commands;

        // Build long command and refactor for dockerized execution if needed
        String longCommand = buildLongCommand(internalCommands);

        if (internalCommands.size() == 1) {
            if (internalCommands.get(0).contains(" && ")) {
                internalCommands = List.of(internalCommands.get(0).split(" && "));
            } else if (internalCommands.get(0).contains(" ; ")) {
                internalCommands = List.of(internalCommands.get(0).split(" ; "));
            }
        }

        // Perform command
        List<String> exitLogs = isRemoteTerminal() ? executeRemoteCommand(internalCommands, longCommand) : executeLocalCommand(internalCommands, longCommand);

        String log = exitLogs.get(0);
        String exitStatus = exitLogs.get(1);

        // Prepare final log message
        StringBuilder reportMessage = new StringBuilder();
        if (!sshHostName.equals("")) {
            reportMessage.append("Host Name: \"").append(sshHostName).append("\"");
            reportMessage.append(" | SSH Port Number: \"").append(sshPortNumber).append("\"");
            reportMessage.append(" | SSH Username: \"").append(sshUsername).append("\"");
        } else {
            reportMessage.append("Host Name: \"" + "localHost" + "\"");
        }
        if (sshKeyFileName != null && !sshKeyFileName.equals("")) {
            reportMessage.append(" | Key File: \"").append(sshKeyFileFolderName).append(sshKeyFileName).append("\"");
        }
        reportMessage.append(" | Command: \"").append(longCommand).append("\"");
        reportMessage.append(" | Exit Status: \"").append(exitStatus).append("\"");

        if (log != null) {
            passAction(reportMessage.toString(), log);
            return log;
        } else {
            return "";
        }
    }

    public String performTerminalCommand(String command) {
        return performTerminalCommands(Collections.singletonList(command));
    }

    public String getSshHostName() {
        return sshHostName;
    }

    public String getSshUsername() {
        return sshUsername;
    }

    public String getSshKeyFileFolderName() {
        return sshKeyFileFolderName;
    }

    public String getSshKeyFileName() {
        return sshKeyFileName;
    }

    public int getSshPortNumber() {
        return sshPortNumber;
    }

    public String getDockerName() {
        return dockerName;
    }

    public String getDockerUsername() {
        return dockerUsername;
    }


    private Session createSSHsession() {
        Session session = null;
        String testData = sshHostName + ", " + sshPortNumber + ", " + sshUsername + ", " + sshKeyFileFolderName + ", "
                + sshKeyFileName;
        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            if (sshKeyFileName != null && !sshKeyFileName.equals("")) {
                jsch.addIdentity(FileActions.getInstance().getAbsolutePath(sshKeyFileFolderName, sshKeyFileName));
            }
            session = jsch.getSession(sshUsername, sshHostName, sshPortNumber);
            session.setConfig(config);
            session.connect();
            Logger.logError("Successfully created SSH Session.");
        } catch (JSchException rootCauseException) {
            failAction(testData, rootCauseException);
        }
        return session;
    }

    private String buildLongCommand(List<String> commands) {
        StringBuilder command = new StringBuilder();
        // build long command
        for (Iterator<String> i = commands.iterator(); i.hasNext(); ) {
            if (command.length() == 0) {
                command.append(i.next());
            } else {
                command.append(" && ").append(i.next());
            }
        }
        return command.toString();
    }

    private List<String> executeLocalCommand(List<String> commands, String longCommand) {
        StringBuilder logs = new StringBuilder();
        StringBuilder exitStatuses = new StringBuilder();
        // local execution
//        ReportManager.logDiscrete("Attempting to execute the following command locally. Command: \"" + longCommand + "\"");
        boolean isWindows = SystemUtils.IS_OS_WINDOWS;
        String directory;
        LinkedList<String> internalCommands;
        if (commands.size() > 1 && commands.get(0).startsWith("cd ")) {
            directory = commands.get(0).replace("cd ", "");
            internalCommands = new LinkedList<>(commands);
            internalCommands.remove(0);
        } else {
            directory = System.getProperty("user.dir");
            internalCommands = new LinkedList<>(commands);
        }
        FileActions.getInstance().createFolder(directory.replace("\"", ""));
        String finalDirectory = directory;
        internalCommands.forEach(command -> {
            command = command.contains(".bat") && !command.contains(".\\") ? ".\\" + command : command;
            Logger.logConsole("Executing: \"" + command + "\" locally.");
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(new File(finalDirectory));

                // https://stackoverflow.com/a/10954450/12912100
                if (isWindows) {
                    if (asynchronous && verbose) {
                        pb.command("powershell.exe", "Start-Process powershell.exe '-NoExit -WindowStyle Minimized \"[Console]::Title = ''SHAFT_Engine''; " + command + "\"'");
                    } else {
                        pb.command("powershell.exe", "-Command", command);
                    }
                } else {
                    pb.command("sh", "-c", command);
                }
                if (!asynchronous) {
                    pb.redirectErrorStream(true);
                    Process localProcess = pb.start();
                    // output logs
                    String line;
                    InputStreamReader isr = new InputStreamReader(localProcess.getInputStream());
                    BufferedReader rdr = new BufferedReader(isr);
                    while ((line = rdr.readLine()) != null) {
                        if (Boolean.TRUE.equals(verbose)) {
                            Logger.logError(line);
                        }
                        logs.append(line);
                        logs.append("\n");
                    }
                    isr = new InputStreamReader(localProcess.getErrorStream());
                    rdr = new BufferedReader(isr);
                    while ((line = rdr.readLine()) != null) {
                        if (Boolean.TRUE.equals(verbose)) {
                            Logger.logError(line);
                        }
                        logs.append("\n");
                        logs.append(line);
                    }
                    // Wait for the process to complete
                    localProcess.waitFor(60, TimeUnit.MINUTES);
                    // Retrieve the exit status of the executed command and destroy open sessions
                    exitStatuses.append(localProcess.exitValue());
                } else {
                    exitStatuses.append("asynchronous");
                    ScheduledExecutorService asynchronousProcessExecution = Executors.newScheduledThreadPool(1);
                    asynchronousProcessExecution.schedule(() -> {
                        try {
                            pb.start();
                            asynchronousProcessExecution.shutdown();
                        } catch (Throwable throwable) {
                            asynchronousProcessExecution.shutdownNow();
                        }
                    }, 0, TimeUnit.SECONDS);
                    if (!asynchronousProcessExecution.awaitTermination(60, TimeUnit.MINUTES)) {
                        asynchronousProcessExecution.shutdownNow();
                    }
                }
            } catch (IOException | InterruptedException exception) {
                failAction(longCommand, exception);
            }
        });
        return Arrays.asList(logs.toString().trim(), exitStatuses.toString());
    }

    private List<String> executeRemoteCommand(List<String> commands, String longCommand) {
        StringBuilder logs = new StringBuilder();
        StringBuilder exitStatuses = new StringBuilder();
        int sessionTimeout = Integer.parseInt(String.valueOf(60 * 1000));
        // remote execution
        Logger.logError(
                "Attempting to perform the following command remotely. Command: \"" + longCommand + "\"");
        Session remoteSession = createSSHsession();
        if (remoteSession != null) {
            try {
                remoteSession.setTimeout(sessionTimeout);
                ChannelExec remoteChannelExecutor = (ChannelExec) remoteSession.openChannel("exec");
                remoteChannelExecutor.setCommand(longCommand);
                remoteChannelExecutor.connect();

                // Capture logs and close readers
                BufferedReader reader = new BufferedReader(new InputStreamReader(remoteChannelExecutor.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(remoteChannelExecutor.getErrStream()));
                logs.append(readConsoleLogs(reader));
                logs.append(readConsoleLogs(errorReader));

                // Retrieve the exit status of the executed command and destroy open sessions
                exitStatuses.append(remoteChannelExecutor.getExitStatus());
                remoteSession.disconnect();
            } catch (JSchException | IOException exception) {
                failAction(longCommand, exception);
            }
        }
        return Arrays.asList(logs.toString(), exitStatuses.toString());
    }

    private String readConsoleLogs(BufferedReader reader) throws IOException {
        StringBuilder logBuilder = new StringBuilder();
        if (reader != null) {
            String logLine;
            while ((logLine = reader.readLine()) != null) {
                if (logBuilder.length() == 0) {
                    logBuilder.append(logLine);
                } else {
                    logBuilder.append(System.lineSeparator()).append(logLine);
                }
            }
            reader.close();
        }
        return logBuilder.toString();
    }
}
