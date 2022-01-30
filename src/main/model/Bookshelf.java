package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represent a bookshelf that stores all the books I currently need to read,
// stores all the genre tags and how many times each tag has been used
// that keep track of my total progress
public class Bookshelf {

    private int numberOfBooks;
    private List<Book> books;

    private int numberOfGenreTags;
    private List<List<Object>> genreTagsList;

    private double totalProgress;

    //EFFECTS: construct a bookshelf where
    // the # of books on the bookshelf is zero,
    // the list of books is empty,
    // the # of genre tags is zero,
    // the list of genre tags is empty,
    // the # of genre tags is zero,
    // and the total progress is zero.
    public Bookshelf() {
        this.numberOfBooks = 0;
        this.books = new LinkedList<>();
        this.numberOfGenreTags = 0;
        this.genreTagsList = new LinkedList<>();
        this.totalProgress = 0.0;
    }

    // REQUIRES: book is not empty
    // MODIFIES: this
    // EFFECTS: numberOfBooks++, add to the list of books,
    public void addBook(Book book) {
        this.numberOfBooks++;
        this.books.add(book);
    }

    // MODIFIES: this
    // EFFECTS: update the total progress
    public void totalProgressUpdate() {
        double i = 0.0;
        for (Book next : books) {
            i = i + next.getProgress();
        }

        this.totalProgress = tenthPercentageHelper(i / this.numberOfBooks / 100);

    }

    //MODIFIER: this, genreTagsList
    //EFFECT: update how many times each genre tag has been used,
    //if the genre tag is new, add the new genre tag to the genre list,
    //and update the frequency of this tag;
    //if the genre tag is old, simply update the frequency of the tag.
    public void totalGenreTagsUpdate() {
        genreTagsList.clear();
        numberOfGenreTags = 0;

        for (Book nextBook : books) {

            List<String> currentTagList = nextBook.getGenreTagsList();
            for (String nextTag : currentTagList) {

                boolean repeated = checkRepeated(nextTag);
                if (!repeated) {
                    // not repeated -> add new node with frequency = 1
                    List<Object> newGenre = new ArrayList<>();
                    newGenre.add(nextTag);
                    newGenre.add(1);
                    genreTagsList.add(newGenre);
                    numberOfGenreTags++;

                } else {
                    // repeated! -> locate the genre repeated and then update
                    List<Object> repeatedGenre = locateGenre(nextTag);
                    int frequency = (int) repeatedGenre.get(1);
                    repeatedGenre.remove(1);
                    repeatedGenre.add(++frequency);
                    //pre-increment: increment the value first and then used in the statement
                }
            }
        }
    }


    // getters
    public List<Book> getAllBooks() {
        return this.books;
    }


    public int getNumberOfBooks() {
        return this.numberOfBooks;
    }


    public List<List<Object>> getAllGenreTags() {
        return this.genreTagsList;
    }

    public int getNumberOfGenreTags() {
        return this.numberOfGenreTags;
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

        for (List<Object> nextNode : genreTagsList) {
            if (nextNode.get(0) == tag) {
                return true;
            }
        }

        return false;
    }

    private List<Object> locateGenre(String tag) {


        for (List<Object> next : genreTagsList) {
            if (next.get(0) == tag) {
                return next;
            }
        }

        return null;
    }

}
