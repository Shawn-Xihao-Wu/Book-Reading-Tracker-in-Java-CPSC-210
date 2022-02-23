package ui;

import model.Book;
import model.Bookshelf;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;



// Modelled the code structure after TellerApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Reading Tracker Application
public class ReadingTrackerApp {
    private static final String JSON_STORE = "./data/bookshelf.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;

    private Book book;
    private Bookshelf bookshelf;

    // EFFECTS: runs the reading tracker application;
    //      if destination file cannot be opened for writing when initialing,
    //      catch FileNotFoundException and terminate the application with printed prompt.
    public ReadingTrackerApp() {
        try {
            runReadingTracker();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: FILE NOT FOUND!");
        }
    }


    // MODIFIES: this
    // EFFECTS: process user input.
    private void runReadingTracker() throws FileNotFoundException {

        init();

        boolean keepGoing = true;
        String command;

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
        System.out.println("\nThank you for using Reading Tracker App!");
    }


    // MODIFIES: this
    // EFFECTS: initializes a bookshelf, Scanner, JsonWriter and JsonReader
    private void init() throws FileNotFoundException {
        bookshelf = new Bookshelf();

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: display the main menu for user to choose
    private void displayMenu() {
        System.out.println("\n================================================");
        System.out.println("Hi, I am a book reading tracker! "
                + "\nI help you to manage your books on your bookshelf!");
        System.out.println("\tSelect below for commands:");
        System.out.println("\t\ta --> add books to the bookshelf");
        System.out.println("\t\tv --> view all books on the bookshelf");
        System.out.println("\t\tg --> view books by genre");
        System.out.println("\t\tr --> view and update pages read");
        System.out.println("\t\tp --> progress report");
        System.out.println("\t\ts --> save current bookshelf to file");
        System.out.println("\t\tl --> load previous bookshelf from file");
        System.out.println("\t\tq --> quit");
        System.out.println("================================================");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddBooks();
        } else if (command.equals("v")) {
            doViewAllBooks();
        } else if (command.equals("g")) {
            doViewBooksByGenre();
        } else if (command.equals("r")) {
            doUpdateReadingProgress();
        } else if (command.equals("p")) {
            doProgressReport();
        } else if (command.equals("s")) {
            doSaveBookshelf();
        } else if (command.equals("l")) {
            doLoadBookshelf();
        } else {
            System.out.println("Selection is not valid...");
        }
    }

    // REQUIRES: don't add the same book twice unless user wants to.
    // MODIFIES: this
    // EFFECTS: add a book with a title and known total page # to the bookshelf;
    // user can also add genre tags to the book if user wants to
    // if user enters the same genre tag twice for one book, the tag will only count once
    private void doAddBooks() {

        System.out.println("Enter book title:");
        String bookTitle = input.next();

        System.out.println("Enter total page number");
        int bookPages = input.nextInt();

        book = new Book(bookTitle, bookPages);

        System.out.println("Do you want to add genre tags for this book? (y/n)");
        String commandForTagging = input.next();

        if (commandForTagging.equals("y")) {

            boolean continueAddingTags = true;

            while (continueAddingTags) {
                System.out.println("Enter a genre tag:");
                String tag = input.next();
                book.addGenreTag(tag);

                System.out.println("Keep adding tags? (y/n)");
                String commandForKeepTagging = input.next();
                if (commandForKeepTagging.equals("n")) {
                    continueAddingTags = false;
                }
            }
        }

        bookshelf.addBook(book);
        System.out.println("\nAdded \"" + bookTitle + "\" to the bookshelf!");
    }

    // MODIFIES: this
    // EFFECTS: view all the book on the bookshelf;
    // print out 1) an overview of books on the bookshelf,
    // i.e. the number of books, the number of genres, and a list of distinct genre names;
    // print out 2) a list of all books, i.e. all book titles and their genres
    private void doViewAllBooks() {
        if (bookshelf.getNumberOfBooks() == 0) {
            System.out.println("No books on the bookshelf! Add books first!");
        } else {
            System.out.println("\n### BOOK INFO REPORT ###");
            printGeneralBookInfo();
            printAllBooksAndTheirGenres();
        }
    }

    // EFFECTS: user input genre name, and it
    // prints out 1) number of books tagged by such genre
    // and 2) all the book titles of books tagged by such genre;
    // if no such book present, it prints that the number of such book is 0 with no book titles shown
    private void doViewBooksByGenre() {
        if (bookshelf.getNumberOfBooks() == 0) {
            System.out.println("No books on the bookshelf! Add books first!");
        } else {
            System.out.println("Enter a genre name:");
            String genreName = input.next();
            System.out.println("The number of books tagged by \"" + genreName + "\" on the bookshelf"
                    + " is " + bookshelf.numOfBooksTaggedBy(genreName) + ":");
            for (Book next : bookshelf.booksTaggedBy(genreName)) {
                System.out.println("<" + next.getTitle() + ">");
            }
        }

    }

    // REQUIRES: new # of pages read is always positive AND
    // should not be greater than the total # of pages
    // MODIFIES: this
    // EFFECTS: print a list of books, total pages of each book
    // and how many pages read for each book;
    // user can select a book from the printed list and update its pages read;
    // user can also exit without changing anything
    private void doUpdateReadingProgress() {
        if (bookshelf.getNumberOfBooks() == 0) {
            System.out.println("No books on bookshelf! Add books first!");
        } else {
            int num = 1;
            for (Book next : bookshelf.getAllBooks()) {
                System.out.println("\n" + num + ": <" + next.getTitle() + ">");
                System.out.println("Pages Read: " + next.getPagesRead() + "  Total Pages: " + next.getTotalPages());
                num++;
            }

            System.out.println("\nSelect the book you want to update by the code: (0 for exit)");
            int index = input.nextInt();

            if (index == 0) {
                System.out.println("No books were changed!");
            } else {
                book = bookshelf.getAllBooks().get(--index);
                System.out.println("\nYou have selected <" + book.getTitle() + ">!");

                System.out.println("\nEnter new # of pages read:");
                int pagesRead = input.nextInt();

                book.progressUpdate(pagesRead);
                System.out.println("New # of pages read for this book is: " + pagesRead);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:
    // 1. print the total progress of all the books on the bookshelf
    // 2. print and list all the progresses of individual books with progress bar
    private void doProgressReport() {
        bookshelf.totalProgressUpdate();

        System.out.println("\n### BOOK READING PROGRESS REPORT ###");

        String str1 = "◆◆◆◆◆◆◆◆◆◆";
        String str2 = "◇◇◇◇◇◇◇◇◇◇";
        System.out.println("\nTotal Progress: " + bookshelf.getTotalProgress() + "%\n");
        for (Book next : bookshelf.getAllBooks()) {
            int i = (int) Math.floor(next.getProgress() / 10);
            System.out.println("<" + next.getTitle() + ">: " + next.getProgress() + "% "
                    + str1.substring(0, i) + str2.substring(i));
        }
    }

    // EFFECTS: saves the bookshelf to file;
    //      if unable to write to the destination file,
    //      catch FileNotFoundException and print status.
    private void doSaveBookshelf() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookshelf);
            jsonWriter.close();
            System.out.println("Saved your bookshelf to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads bookshelf from file;
    //      if unable to read from file,
    //      catch IOException and print status.
    private void doLoadBookshelf() {
        try {
            bookshelf = jsonReader.read();
            System.out.println("Loaded previous bookshelf from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: generate a new list of genre names from all the books on bookshelf;
    // print an overview of the info on the books on the bookshelf:
    // the number of books, the number of genres, and distinct genre names
    private void printGeneralBookInfo() {
        bookshelf.updateGenreInfo();
        List<String> genreList = bookshelf.getAllGenres();
        System.out.println("\nNumber of books: " + bookshelf.getNumberOfBooks());
        System.out.println("\nNumber of book genres: " + bookshelf.getNumberOfGenres());
        System.out.println("\nNames of book genre:");
        for (int i = 0; i < genreList.size(); i++) {
            if ((i + 1) == genreList.size()) {
                System.out.print(genreList.get(i) + "\n");
            } else {
                System.out.print(genreList.get(i) + ", ");
            }
        }
    }

    // EFFECTS: print all books on the bookshelf and their corresponding genres
    private void printAllBooksAndTheirGenres() {
        for (Book next : bookshelf.getAllBooks()) {
            System.out.println("\n<" + next.getTitle() + ">");
            if (next.getGenreTags().isEmpty()) {
                System.out.println("Genre: None");
            } else {
                System.out.print("Genre: ");
                List<String> tagList = next.getGenreTags();
                for (int i = 0; i < tagList.size(); i++) {
                    if (i == (tagList.size() - 1)) {
                        System.out.print(tagList.get(i) + "\n");
                    } else {
                        System.out.print(tagList.get(i) + ", ");
                    }
                }
            }
        }
    }

}
