package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represent a bookshelf that stores all the books I currently need to read;
// it keeps track of how many books I have and what are they;
// it keeps track of how many distinct genres on the bookshelf,
// how many books under certain genre,
// and has my total reading progress.
public class Bookshelf {

    private int numberOfBooks;
    private List<Book> collectionOfBooks;

    private int numberOfGenres;
    private List<String> collectionOfGenres;

    private double totalProgress;

    //EFFECTS: construct a bookshelf where
    // the # of books on the bookshelf is zero,
    // the list of books is empty but not null,
    // the # of genre tags is zero,
    // the list of genre tags is empty but not null,
    // and the total progress is zero.
    public Bookshelf() {
        this.numberOfBooks = 0;
        this.collectionOfBooks = new LinkedList<>();
        this.numberOfGenres = 0;
        this.collectionOfGenres = new ArrayList<>();
        this.totalProgress = 0.0;
    }

    // REQUIRES: book is not null
    // MODIFIES: this
    // EFFECTS: update the number of books on bookshelf by one
    // and add the book to the collection of books
    public void addBook(Book book) {
        this.numberOfBooks++;
        this.collectionOfBooks.add(book);
    }


    // REQUIRES: genreName is not empty
    // EFFECTS: return a list of books that has the inputting genre name
    public List<Book> booksTaggedBy(String genreName) {
        List<Book> booksTagged = new LinkedList<>();

        for (Book nextBook : collectionOfBooks) {
            if (nextBook.containsGenreTag(genreName)) {
                booksTagged.add(nextBook);
            }
        }
        return booksTagged;
    }

    // REQUIRES: genreName is not empty
    // EFFECTS: return the number of books that is tagged by the inputting genre name
    public int numOfBooksTaggedBy(String genreName) {
        int num = 0;

        for (Book nextBook : collectionOfBooks) {
            if (nextBook.containsGenreTag(genreName)) {
                num++;
            }
        }
        return num;
    }

    //MODIFIES: this
    //EFFECTS: update how many distinct genres there are on the bookshelf
    // and what are they; store the genre names in a list
    public void updateGenreInfo() {
        collectionOfGenres.clear();
        numberOfGenres = 0;

        for (Book b : collectionOfBooks) {
            List<String> bookTags = b.getGenreTags();
            for (String s : bookTags) {
                boolean repeated = checkRepeated(s);
                if (!repeated) {
                    collectionOfGenres.add(s);
                    numberOfGenres++;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: update the total progress by
    // summing up individual book progress and
    // taking the average (rounded to the nearest tenth)
    public void totalProgressUpdate() {
        if (this.numberOfBooks == 0) {
            this.totalProgress = 0.0;
        } else {
            double i = 0.0;
            for (Book next : collectionOfBooks) {
                i = i + next.getProgress();
            }

            this.totalProgress = tenthPercentageHelper(i / this.numberOfBooks / 100);
        }
    }

    // getters
    public List<Book> getAllBooks() {
        return this.collectionOfBooks;
    }

    public int getNumberOfBooks() {
        return this.numberOfBooks;
    }

    public List<String> getAllGenres() {
        return this.collectionOfGenres;
    }

    public int getNumberOfGenres() {
        return this.numberOfGenres;
    }

    public double getTotalProgress() {
        return this.totalProgress;
    }


    // helper functions

    // EFFECTS: round and return the nearest percentage point
    private double tenthPercentageHelper(double i) {
        double hundredth = Math.floor(10000 * i);
        double tenth = 10 * Math.floor(1000 * i);
        double check = hundredth - tenth;

        if (check < 5) {
            return Math.floor(1000 * i) / 10;
        } else {
            return Math.floor(1 + 1000 * i) / 10;
        }
    }

    // REQUIRES: string is not empty
    // EFFECTS: check if the input genre tag has already
    // in the collectionOfGenres
    private boolean checkRepeated(String tag) {
        for (String next : collectionOfGenres) {
            if (next.equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
