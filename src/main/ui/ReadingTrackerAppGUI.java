package ui;

import model.Book;
import model.Bookshelf;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadingTrackerAppGUI extends JFrame implements ActionListener {

    private JLabel label;
    private JMenuBar menuBar;

    private JMenu addBooksMenu;
    private JMenu viewBooksMenu;
    private JMenu progressBooksMenu;
    private JMenu saveBooksMenu;
    private JMenu loadBooksMenu;

    private JMenuItem addBooksItem;
    private JMenuItem viewAllBooksItem;
    private JMenuItem viewBooksByGenreItem;
    private JMenuItem progressUpdateItem;
    private JMenuItem progressReportItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;

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
        menuBar.add(progressBooksMenu);
        menuBar.add(saveBooksMenu);
        menuBar.add(loadBooksMenu);

        setJMenuBar(menuBar);

        eventsSetUp();
    }

    private void menuSetUp() {
        addBooksMenu = new JMenu("Add");
        viewBooksMenu = new JMenu("View");
        progressBooksMenu = new JMenu("Progress");
        saveBooksMenu = new JMenu("Save");
        loadBooksMenu = new JMenu("Load");
    }

    private void menuItemsSetUp() {

        addBooksItem = new JMenuItem("Add a book");
        viewAllBooksItem = new JMenuItem("View all books");
        viewBooksByGenreItem = new JMenuItem("View books by genre");
        progressUpdateItem = new JMenuItem("Progress update");
        progressReportItem = new JMenuItem("Progress report");
        saveItem = new JMenuItem("Save current books");
        loadItem = new JMenuItem("Load previous books");

        addBooksMenu.add(addBooksItem);
        viewBooksMenu.add(viewAllBooksItem);
        viewBooksMenu.add(viewBooksByGenreItem);
        progressBooksMenu.add(progressUpdateItem);
        progressBooksMenu.add(progressReportItem);
        saveBooksMenu.add(saveItem);
        loadBooksMenu.add(loadItem);
    }

    private void eventsSetUp() {
        addBooksItem.addActionListener(this);
        viewAllBooksItem.addActionListener(this);
        viewBooksByGenreItem.addActionListener(this);
        progressUpdateItem.addActionListener(this);
        progressReportItem.addActionListener(this);
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addBooksItem)) {
            System.out.println("addBook");
        }
        if (e.getSource().equals(viewAllBooksItem)) {
            System.out.println("all books");
        }
        if (e.getSource().equals(viewBooksByGenreItem)) {
            System.out.println("genre");
        }
        if (e.getSource().equals(progressUpdateItem)) {
            System.out.println("updating..");
        }
        if (e.getSource().equals(progressReportItem)) {
            System.out.println("reporting..");
        }
        if (e.getSource().equals(saveItem)) {
            System.out.println("saving...");
        }
        if (e.getSource().equals(loadItem)) {
            System.out.println("loading..");
        }
    }
}
