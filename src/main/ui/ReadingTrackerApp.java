package ui;


import model.Book;
import model.Bookshelf;

import java.util.Locale;
import java.util.Scanner;

// Borrowed code skeleton from TellerApp.java
public class ReadingTrackerApp {
    private Book book;
    private Bookshelf bookshelf;
    private Scanner input;

    // EFFECTS: runs the reading tracker application
    public ReadingTrackerApp() {
        runReadingTracker();
    }


    // MODIFIES: this
    // EFFECTS: process user input
    private void runReadingTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }


    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddBooks();
        } else if (command.equals("v")) {
            doViewAllBooks();
        } else if (command.equals("t")) {
            doViewBooksByGenre();
        } else if (command.equals("r")) {
            doUpdateReadingProgress();
        } else if (command.equals("p")) {
            doProgressReport();
        } else {
            System.out.println("Selection is not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a bookshelf
    private void init() {
        bookshelf = new Bookshelf();

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: display main menu for user to choose
    private void displayMenu() {
        System.out.println("================================");
        System.out.println("Hi, I am a book reading tracker.\n \tSelect below for commands:");
        System.out.println("\ta --> add books");
        System.out.println("\tv --> view all books");
        System.out.println("\tg --> view books by genre");
        System.out.println("\tr --> update pages read");
        System.out.println("\tp --> progress report");
        System.out.println("\tq --> quit");
        System.out.println("================================");
    }

    // MODIFIES: this
    // EFFECTS: add a book to bookshelf
    private void doAddBooks() {

    }

    // EFFECTS: view all the book on the bookshelf
    // list the book names and their genre
    private void doViewAllBooks() {

    }

    // EFFECTS: user input genre name and the method
    // gives the book list tag by the genre
    private void doViewBooksByGenre() {

    }

    // MODIFIES: this, Book
    // EFFECTS: select a book and update how many pages read for this book
    private void doUpdateReadingProgress() {

    }

    // EFFECTS:
    // 1. print how many books on the bookshelf
    // 2. list all the distinct genres name and how many books under them
    // 3. print total progress
    // 4. list all the progresses of individual books
    private void doProgressReport() {

    }

}
