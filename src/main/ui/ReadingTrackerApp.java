package ui;

import model.Book;
import model.Bookshelf;

import java.util.List;
import java.util.Scanner;

// Modelled after TellerApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// Reading Tracker Application
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

        System.out.println("\nThank you for using Reading Tracker App!");
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
        System.out.println("\n================================================");
        System.out.println("Hi, I am a book reading tracker! "
                + "\nI help you to manage your books on your bookshelf!");
        System.out.println("\tSelect below for commands:");
        System.out.println("\t\ta --> add books to the bookshelf");
        System.out.println("\t\tv --> view all books on the bookshelf");
        System.out.println("\t\tg --> view books by genre");
        System.out.println("\t\tr --> view and update pages read");
        System.out.println("\t\tp --> progress report");
        System.out.println("\t\tq --> quit");
        System.out.println("================================================");
    }

    // MODIFIES: this
    // EFFECTS: add a book to bookshelf and add genre tags if intended
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


    // EFFECTS: view all the book on the bookshelf
    // list the book names and their genre
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
    // gives the book list tag by the genre
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

    // MODIFIES: this, Book
    // EFFECTS: select a book from the list of book printed
    // and update how many pages read for this book
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

    // EFFECTS:
    // 1. print how many books on the bookshelf
    // 2. list all the distinct genres name and how many books under them
    // 3. print total progress
    // 4. list all the progresses of individual books
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

    // EFFECTS: print an overview of the info on the books on the bookshelf:
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
