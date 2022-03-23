package ui;

import model.Book;
import model.Bookshelf;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadingTrackerAppGUI extends JFrame implements ActionListener {

    private JLabel label;
    private JMenuBar menuBar;

    private JMenu addBooksMenu;
    private JMenu viewBooksMenu;
    private JMenu updateBooksMenu;
    private JMenu saveBooksMenu;
    private JMenu loadBooksMenu;

    private JMenuItem addBooksItem;
    private JMenuItem viewAllBooksItem;
    private JMenuItem viewBooksByGenreItem;
    private JMenuItem viewReportItem;
    private JMenuItem updateProgressItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;

    private static final String JSON_STORE = "./data/bookshelf.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Bookshelf bookshelf;
    private Book book;

    public ReadingTrackerAppGUI() {
        //Set up the window using JFrame.
        super("Reading Tracker Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the main page using JLabel
        mainPageSetUp();

        //Set up the menu bar
        menuBarSetUp();

        // display the window
        pack();
        setVisible(true);

        //initial Bookshelf, JsonReader, and JsonWriter
        try {
            init();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: FILE NOT FOUND!");
        }
    }

    private void init() throws FileNotFoundException {
        bookshelf = new Bookshelf();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void mainPageSetUp() {
        //Create an image icon
        ImageIcon icon = new ImageIcon("./data/bookIcon.jpg");

        //Create a label
        label = new JLabel();
        label.setText("Welcome to Reading Tracker Application!");
        label.setIcon(icon);

        label.setFont(new Font("Monospaced", Font.ITALIC, 20));

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);

        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setPreferredSize(new Dimension(600, 600));
        label.setBackground(Color.white);
        label.setOpaque(true); // display background color

        getContentPane().add(label, BorderLayout.CENTER); // add label to frame
    }

    private void menuBarSetUp() {
        //create a menu bar.
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(600, 25));

        menuSetUp();

        menuItemsSetUp();

        menuBar.add(addBooksMenu);
        menuBar.add(viewBooksMenu);
        menuBar.add(updateBooksMenu);
        menuBar.add(saveBooksMenu);
        menuBar.add(loadBooksMenu);

        setJMenuBar(menuBar);

        eventsSetUp();
    }

    private void menuSetUp() {
        addBooksMenu = new JMenu("Add");
        viewBooksMenu = new JMenu("View");
        updateBooksMenu = new JMenu("Update");
        saveBooksMenu = new JMenu("Save");
        loadBooksMenu = new JMenu("Load");
    }

    private void menuItemsSetUp() {

        addBooksItem = new JMenuItem("Add a book");
        viewAllBooksItem = new JMenuItem("View all books");
        viewBooksByGenreItem = new JMenuItem("View books by genre");
        viewReportItem = new JMenuItem("View report");
        updateProgressItem = new JMenuItem("Update progress");
        saveItem = new JMenuItem("Save current books");
        loadItem = new JMenuItem("Load previous books");

        addBooksMenu.add(addBooksItem);
        viewBooksMenu.add(viewAllBooksItem);
        viewBooksMenu.add(viewBooksByGenreItem);
        viewBooksMenu.add(viewReportItem);
        updateBooksMenu.add(updateProgressItem);
        saveBooksMenu.add(saveItem);
        loadBooksMenu.add(loadItem);
    }

    private void eventsSetUp() {
        addBooksItem.addActionListener(this);
        viewAllBooksItem.addActionListener(this);
        viewBooksByGenreItem.addActionListener(this);
        viewReportItem.addActionListener(this);
        updateProgressItem.addActionListener(this);
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addBooksItem)) {
            new WindowAddBooks(bookshelf);
        } else if (e.getSource().equals(viewAllBooksItem)) {
            new WindowViewBooks(bookshelf);
        } else if (e.getSource().equals(viewBooksByGenreItem)) {
            String genre = JOptionPane.showInputDialog("Input a genre name: ");
            if (genre != null) {
                new WindowViewBooks(bookshelf, genre);
            }
        } else if (e.getSource().equals(viewReportItem)) {
            new WindowViewReport(bookshelf);
        } else if (e.getSource().equals(updateProgressItem)) {
            new WindowUpdateProgress(bookshelf);
        } else if (e.getSource().equals(saveItem)) {
            doSaveBookshelf();
        } else if (e.getSource().equals(loadItem)) {
            doLoadBookshelf();
        }
    }

    // EFFECTS: saves the bookshelf to file and pop up a confirmation window;
    //      if unable to write to the destination file,
    //      catch FileNotFoundException and pop up an error window.
    private void doSaveBookshelf() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookshelf);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,"Saved your bookshelf to " + JSON_STORE,
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE,
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads bookshelf from file and pop up a confirmation window;
    //      if unable to read from file,
    //      catch IOException and pop up an error window.
    private void doLoadBookshelf() {
        try {
            bookshelf = jsonReader.read();
            JOptionPane.showMessageDialog(null,"Loaded previous bookshelf from " + JSON_STORE,
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE,
                    "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
