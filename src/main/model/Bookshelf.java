package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represent a bookshelf that stores all the books I currently need to read,
// keeps track of how many distinct genres on the bookshelf,
// how many books under certain genre
// and has my total reading progress
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
        this.collectionOfBooks = new ArrayList<>();
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

    // MODIFIES: this
    // EFFECTS: update the total progress by
    // summing up individual book progress and
    // taking the average
    public void totalProgressUpdate() {
        double i = 0.0;
        for (Book next : collectionOfBooks) {
            i = i + next.getProgress();
        }

        this.totalProgress = tenthPercentageHelper(i / this.numberOfBooks / 100);

    }

    // REQUIRES: genreName not empty
    // EFFECTS: return a list of books that has the input genre name
    public List<Book> booksTaggedBy(String genreName) {
        List<Book> booksTagged = new LinkedList<>();

        for (Book nextBook : collectionOfBooks) {
            if (nextBook.containsGenreTag(genreName)) {
                booksTagged.add(nextBook);
            }
        }
        return booksTagged;
    }

    //MODIFIES: this
    //EFFECTS:
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



    //EFFECTS: generate a new list of Genre class where each entry stores
    //distinct genre names and how many books under it.
    //The function iterates through the collection of books, for each book
    //if the genre tag is new, add the new genre tag to the genre list,
    //and update the frequency of this tag;
    //if the genre tag is old, simply update the frequency of the tag.
//    public void totalGenreTagsUpdate() {
//        genreTagsList.clear();
//        numberOfGenreTags = 0;
//
//        for (Book nextBook : books) {
//
//            List<String> currentTagList = nextBook.getGenreTagsList();
//            for (String nextTag : currentTagList) {
//
//                boolean repeated = checkRepeated(nextTag);
//                if (!repeated) {
//                    // not repeated -> add new node with frequency = 1
//                    List<Object> newGenre = new ArrayList<>();
//                    newGenre.add(nextTag);
//                    newGenre.add(1);
//                    genreTagsList.add(newGenre);
//                    numberOfGenreTags++;
//
//                } else {
//                    // repeated! -> locate the genre repeated and then update
//                    List<Object> repeatedGenre = locateGenre(nextTag);
//                    assert repeatedGenre != null;
//                    int frequency = (int) repeatedGenre.get(1);
//                    repeatedGenre.remove(1);
//                    repeatedGenre.add(++frequency);
//                    //pre-increment: increment the value first and then used in the statement
//                }
//            }
//        }
//    }


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


    private boolean checkRepeated(String tag) {
        for (String next : collectionOfGenres) {
            if (next == tag) {
                return true;
            }
        }
        return false;
    }

//    private Genre locateGenre(String tag) {
//        for (Genre next : collectionOfGenres) {
//            if (next.getGenreName() == tag) {
//                return next;
//            }
//        }
//        return null;
//    }

}
