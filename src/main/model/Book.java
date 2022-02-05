package model;



import java.util.ArrayList;
import java.util.List;

// Represents a book that I am interested in reading that has
// a title, total page #, # of pages I have read, progress in terms of percentage,
// and the genres it belongs to.
public class Book {

    private String title;
    private int totalPages;
    private int pagesRead;
    private double progress;
    private List<String> genreTags;

    // REQUIRES: title is not empty, totalPages >= 1
    // EFFECTS: construct a book with a given title, total # of pages.
    // Initially, zero pages are read, progress is zero, and the genreTags
    // list is empty but not null;
    public Book(String title, int totalPages) {
        this.title = title;
        this.totalPages = totalPages;
        this.pagesRead = 0;
        this.progress = 0.0;
        this.genreTags = new ArrayList<>();
    }

    // REQUIRES: newPages > # of pages read
    // MODIFIES: this
    // EFFECTS: Update the # of pages I have read
    // and calculate my new progress,
    // the progress is in percentage rounded to the nearest tenth
    public void progressUpdate(int newPage) {
        this.pagesRead = newPage;
        progress = percentageHelper();
    }

    // REQUIRES: the input string cannot be empty
    // MODIFIES: this
    // EFFECTS: add the name of genre to the genreTags
    public void addGenreTag(String genreTag) {
        if (!this.genreTags.contains(genreTag)) {
            this.genreTags.add(genreTag);
        }

    }


    // REQUIRES: genreTag is not empty
    // MODIFIES: this
    // EFFECTS: add new genre tag to a book
    public boolean containsGenreTag(String genreTag) {
        return this.genreTags.contains(genreTag);
    }



    // getters

    public String getTitle() {
        return this.title;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getPagesRead() {
        return this.pagesRead;
    }

    public double getProgress() {
        return this.progress;
    }

    public List<String> getGenreTags() {
        return this.genreTags;
    }

//    public String getGenreTagsString() {
//        return this.genreTags.toString();
//    }

    private double percentageHelper() {
        double hundredth = Math.floor(10000 * this.pagesRead / this.totalPages);
        double tenth = 10 * Math.floor(1000 * this.pagesRead / this.totalPages);
        double check = hundredth - tenth;

        if (check < 5) {
            return Math.floor(1000 * this.pagesRead / this.totalPages) / 10;
        } else {
            return Math.floor(1 + 1000 * this.pagesRead / this.totalPages) / 10;
        }

    }

}
