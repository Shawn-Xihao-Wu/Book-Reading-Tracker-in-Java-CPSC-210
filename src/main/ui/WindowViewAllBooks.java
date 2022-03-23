package ui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.util.Random;

public class WindowViewAllBooks extends JFrame {
    private JPanel mainPanel;

    private JLabel titleLabel;
    private JLabel pagesReadLabel;
    private JLabel totalPageNumLabel;
    private JLabel genreLabel;
    private JLabel progressLabel;
    private JLabel bookCoverIconLabel;
    private ImageIcon bookCover = new ImageIcon("./data/bookmarkIcon.png");

    private Bookshelf bookshelf;
    private Book book;

    public WindowViewAllBooks(Bookshelf bookshelf) {
        super("View All Books");
        this.bookshelf = bookshelf;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(0,2));

        mainPanel.add(new JLabel(bookCover));
        mainPanel.add(new JLabel(bookCover));
        mainPanel.add(new JLabel(bookCover));

        JScrollPane scrMainPanel = new JScrollPane(mainPanel);
        add(scrMainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
