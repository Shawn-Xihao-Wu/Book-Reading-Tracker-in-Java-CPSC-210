package model;


// Represents a book that I am interested in reading that has
// a title, total page #, # of pages I have read, progress in terms of percentage
public class Book {

    private String title;
    private int totalPages;
    private int pagesRead;
    private double progress;

    // REQUIRES: title is not empty AND totalPages > 1
    // EFFECTS: construct a book with given title and total # of pages,
    // and zero pages read and zero progress
    public Book(String title, int totalPages) {
        this.title = title;
        this.totalPages = totalPages;
        this.pagesRead = 0;
        this.progress = 0.0;
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
