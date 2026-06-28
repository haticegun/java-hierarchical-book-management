package datastructureproject;

import java.util.Scanner;

public class DataStructureProject {

    public static void main(String[] args) {

        Book book = new Book();
        book.loadSampleData();
        book.printHierarchy();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            Utilities.print("\nEnter a command (Add, 2.2.3, Title | Print | Exit):");

            String input = scanner.nextLine();
            input = Utilities.trimText(input);
            String inputLower = Utilities.toLowerCaseTurkish(input);

            if (Utilities.equalsText(inputLower, "exit")) {
                Utilities.print("Exiting...");
                Utilities.print("Program terminated successfully.");
                break;
            }

            if (Utilities.equalsText(inputLower, "print")) {
                book.printHierarchy();
                continue;
            }

            String[] parts = Utilities.splitText(input, ',');

            if (Utilities.arraySize(parts) < 3) {
                Utilities.print("Invalid command! Example: Add, 2.2.3, Bubble Sort");
                continue;
            }

            String command = Utilities.trimText(parts[0]);
            String position = Utilities.trimText(parts[1]);

            String title = Utilities.trimText(parts[2]);
            int partCount = Utilities.arraySize(parts);

            int i = 3;
            while (i < partCount) {
                title = title + "," + parts[i];
                i++;
            }

            title = Utilities.trimText(title);

            String commandLower = Utilities.toLowerCaseTurkish(command);

            if (Utilities.equalsText(commandLower, "add")) {

                if (!Utilities.validateHierarchy(position)) {
                    Utilities.print("Invalid hierarchy format! Example: 2.2.3");
                    continue;
                }

                book.addTitle(position, title);

            } else {
                Utilities.print("Unknown command.");
            }
        }

        scanner.close();
    }
}
