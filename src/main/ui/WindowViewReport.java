package ui;

import model.Bookshelf;

import javax.swing.*;
import java.awt.*;

// Represents a window for view a report about all the books on the bookshelf.
//      It lists the total number books on the bookshelf, the number of distinct genres and what are they,
//      and the total progress in percentage with a progress bar.
public class WindowViewReport extends JFrame {

    private JPanel mainPanel;

    private static ImageIcon reportIcon = new ImageIcon("./data/reportIcon.png");

    private JLabel iconLabel;
    private JLabel introLabel;
    private JLabel numOfBooksLabel;
    private JLabel numOfGenreLabel;
    private JLabel genresLabel;
    private JLabel progressLabel;

    private JProgressBar progressBar;

    private Bookshelf bookshelf;

    // REQUIRES: bookshelf is not null
    // EFFECTS: construct a window for presenting the report about all the books on bookshelf
    public WindowViewReport(Bookshelf bookshelf) {
        // create main frame
        super("View Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 500);

        this.bookshelf = bookshelf;
        this.bookshelf.totalProgressUpdate();
        this.bookshelf.updateGenreInfo();

        // create labels
        labelsSetUp();

        // create progress bar
        progressBarSetUp();

        // add labels to main pane
        addToPanels();

        // add everything else to main frame
        add(iconLabel, BorderLayout.PAGE_START);
        JScrollPane scrMainPanel = new JScrollPane(mainPanel); // make main pane scrollable
        add(scrMainPanel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.PAGE_END);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up all the necessary text labels for presenting the report
    private void labelsSetUp() {
        iconLabel = new JLabel(reportIcon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setSize(300,30);

        introLabel = new JLabel("You have...");
        introLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));

        numOfBooksLabel = new JLabel(bookshelf.getNumberOfBooks() + " books on bookshelf");
        numOfGenreLabel = new JLabel(bookshelf.getNumberOfGenres() + " distinct genres");

        genresLabel = new JLabel(genresLabelString());
        genresLabel.setFont(new Font("TimesRoman", Font.ITALIC,15));

        progressLabel = new JLabel("Total progress so far...");
    }

    // MODIFIES: this
    // EFFECTS: set up the progress bar for viewing the total progress
    private void progressBarSetUp() {
        progressBar = new JProgressBar(0,100);
        progressBar.setValue((int) bookshelf.getTotalProgress());
        progressBar.setStringPainted(true);
        progressBar.setString(bookshelf.getTotalProgress() + "%");
        progressBar.setSize(300,20);
    }

    // MODIFIES: this
    // EFFECTS: create a main panel to present the report. Add the labels to the main panel.
    private void addToPanels() {
        mainPanel = new JPanel(new GridLayout(0,1));
        mainPanel.setSize(300, 450);
        mainPanel.add(introLabel);
        mainPanel.add(numOfBooksLabel);
        mainPanel.add(numOfGenreLabel);
        mainPanel.add(genresLabel);
        mainPanel.add(progressLabel);
    }

    // EFFECTS: generate a string of distinct genre names in HTML format, so that each genre name
    //      is on a new line.
    private String genresLabelString() {
        StringBuilder str = new StringBuilder("<html>There are...<br/>");
        for (String tag : bookshelf.getAllGenres()) {
            str.append("\"").append(tag).append("\"").append("<br/>");
        }
        str.append("<html>");
        return str.toString();
    }
}
