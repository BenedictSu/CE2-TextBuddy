import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

/**
 * TextBuddy
 * 
 * This class is used for storing and retrieving lines of texts. An example
 * interaction is shown below:
 * 
 * 
 * Welcome to TextBuddy. mytextfile.txt is ready for use command: add little
 * brown fox added to mytextfile.txt: "little brown fox" command: display 1.
 * little brown fox command: add jumped over the moon added to mytextfile.txt:
 * "jumped over the moon" command: display 1. little brown fox 2. jumped over
 * the moon command: delete 2 deleted from mytextfile.txt:
 * "jumped over the moon" command: display 1. little brown fox command: clear
 * all content deleted from mytextfile.txt command: display mytextfile.txt is
 * empty command: exit
 */
public class TextBuddy {

    private static BufferedReader inputFile;
    private static BufferedWriter outputFile;
    private static String originalFileName = "test.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static int firstLineNumber = 1;

    private static final String MESSAGE_INVALID_FORMAT = "invalid command format :%1$s";
    private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
    private static final String MESSAGE_ADD = "added to %1$s: \"";
    private static final String MESSAGE_DELETE = "deleted from %1$s: \"";
    private static final String MESSAGE_CLEAR = "all content deleted from %1$s";

    // These are the possible command types
    enum CommandType {
        ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID
    };

    public static void main(String[] args) throws IOException {
        originalFileName = args[0];
        clear();
        displayToUser(String.format(MESSAGE_WELCOME, originalFileName));
        userInstruction();
    }

    private static void userInstruction() throws IOException {
        while (true) {
            String userCommand = userInput();
            String feedback = executeCommand(userCommand);
            displayToUser(feedback);
        }
    }

    private static void displayToUser(String message) {
        System.out.println(message);
    }

    /**
     * userInput
     * 
     * @return the command entered by the user
     */
    private static String userInput() {
        System.out.print("command: ");
        String command = scanner.nextLine();
        return command;
    }

    /**
     * executeCommand
     * 
     * @param userCommand the command entered by the user
     * @return the feedback to the user
     */
    private static String executeCommand(String userCommand) throws IOException {
        if (userCommand.trim().isEmpty()) {
            return String.format(MESSAGE_INVALID_FORMAT, userCommand);
        }

        String commandTypeString = getFirstWord(userCommand);

        CommandType commandType = determineCommandType(commandTypeString);

        switch (commandType) {
        case ADD:
            return add(userCommand);
        case DISPLAY:
            return display();
        case DELETE:
            return delete(userCommand);
        case CLEAR:
            return clear();
        case INVALID:
            return String.format(MESSAGE_INVALID_FORMAT, userCommand);
        case EXIT:
            System.exit(0);
        default:
            throw new Error("Unrecognized command type");
        }
    }

    private static String getFirstWord(String userCommand) {
        String commandTypeString = userCommand.trim().split("\\s+")[0];
        return commandTypeString;
    }

    private static CommandType determineCommandType(String commandTypeString) {
        if (commandTypeString == null) {
            throw new Error("command type string cannot be null!");
        }

        if (commandTypeString.equalsIgnoreCase("add")) {
            return CommandType.ADD;
        } else if (commandTypeString.equalsIgnoreCase("display")) {
            return CommandType.DISPLAY;
        } else if (commandTypeString.equalsIgnoreCase("delete")) {
            return CommandType.DELETE;
        } else if (commandTypeString.equalsIgnoreCase("clear")) {
            return CommandType.CLEAR;
        } else if (commandTypeString.equalsIgnoreCase("exit")) {
            return CommandType.EXIT;
        } else {
            return CommandType.INVALID;
        }
    }

    /**
     * add
     * 
     * @param userCommand the command entered by the user
     * @return the feedback to the user confirming a successful add
     */
    public static String add(String userCommand) throws IOException {
        readInputFile(originalFileName);
        String newInput = (removeFirstWord(userCommand));
        String nextLine = inputFile.readLine();
        String fileContent = "";
        try {
            fileContent = readAndAdd(newInput, nextLine);
            writeToFile(fileContent);
            return String.format(MESSAGE_ADD + newInput + "\"",
                    originalFileName);
        } catch (IOException e) {
            throw e;
        }
    }

    private static String readAndAdd(String newInput, String nextLine)
            throws IOException {
        String fileContent;
        if (nextLine != null) {
            fileContent = nextLine;
            nextLine = inputFile.readLine();
            while (nextLine != null) {
                fileContent = fileContent + "\n" + nextLine;
                nextLine = inputFile.readLine();
            }
            fileContent = fileContent + "\n" + newInput;
        } else {
            fileContent = newInput;
        }
        inputFile.close();
        return fileContent;
    }

    /**
     * display
     * 
     * @return the content of the text file
     */
    public static String display() throws IOException {
        readInputFile(originalFileName);
        String nextLine = inputFile.readLine();
        String fileContent = "";
        try {
            fileContent = readAndFormat(nextLine, fileContent);
            inputFile.close();
            if (fileContent.isEmpty()) {
                fileContent = originalFileName + " is empty";
            }
            return fileContent;
        } catch (IOException e) {
            throw e;
        }
    }

    private static String readAndFormat(String nextLine, String fileContent)
            throws IOException {
        int lineNumber = 1;
        if (nextLine != null) {
            fileContent = lineNumber + ". " + nextLine;
            nextLine = inputFile.readLine();
            lineNumber++;
        }
        while (nextLine != null) {
            fileContent = fileContent + "\n" + lineNumber + ". " + nextLine;
            nextLine = inputFile.readLine();
            lineNumber++;
        }
        return fileContent;
    }

    /**
     * delete
     * 
     * @param userCommand the command entered by the user
     * @return the feedback to the user confirming a successful delete
     */
    public static String delete(String userCommand) throws IOException {
        readInputFile(originalFileName);
        String newInput = (removeFirstWord(userCommand));
        int inputNumber = Integer.valueOf(newInput);
        String nextLine = inputFile.readLine();
        String fileContent = "";
        int lineNumber = firstLineNumber;

        try {
            while (nextLine != null) {
                if (lineNumber == inputNumber) {
                    newInput = nextLine;
                    nextLine = inputFile.readLine();
                    inputNumber = 0;
                } else {
                    if (lineNumber == firstLineNumber) {
                        fileContent = nextLine;
                    } else {
                        fileContent = fileContent + "\n" + nextLine;
                    }
                    nextLine = inputFile.readLine();
                    lineNumber++;
                }
            }
            inputFile.close();
            writeToFile(fileContent);
            return String.format(MESSAGE_DELETE + newInput + "\"",
                    originalFileName);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * clear()
     * 
     * @return the feedback to the user confirming a successful clearing of the text file
     */
    public static String clear() throws IOException {
        writeOutputFile(originalFileName);
        try {
            outputFile.write("");
            outputFile.close();
            return String.format(MESSAGE_CLEAR, originalFileName);
        } catch (IOException e) {
            throw e;
        }
    }

    private static String removeFirstWord(String userCommand) {
        String remainingCommand = userCommand.replace(getFirstWord(userCommand), "").trim();
        return remainingCommand;
    }

    private static void readInputFile(String fileName) throws IOException {
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    private static void writeToFile(String fileContent) throws IOException {
        writeOutputFile(originalFileName);
        outputFile.write(fileContent);
        outputFile.close();
    }

    private static void writeOutputFile(String fileName) throws IOException {
        try {
            outputFile = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw e;
        }
    }

}