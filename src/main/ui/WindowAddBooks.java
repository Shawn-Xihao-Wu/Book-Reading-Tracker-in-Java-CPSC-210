package ui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowAddBooks extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JLabel totalPageNumLabel;
    private JLabel genreLabel;

    private static String titleString = "Title: ";
    private static String totalPageNumString = "Total Page #: ";
    private static String genreString = "Genres (separated by ;): ";

    private JTextField titleTextField;
    private JTextField totalPageNumTextField;
    private JTextField genreTextField;

    private JButton addBookButton;
    private JButton clearFieldsButton;

    private JPanel mainPanel;
    private JPanel labelPanel;
    private JPanel fieldPanel;
    private JPanel buttonPanel;

    private static ImageIcon addBookIcon = new ImageIcon("./data/addBookIcon.jpg");

    private Bookshelf bookshelf;
    private Book book;

    public WindowAddBooks(Bookshelf bookshelf) {
        //Set up the main frame
        super("Add a book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        this.bookshelf = bookshelf;

        //create labels
        labelsSetUp();

        //create text fields
        textFieldsSetUp();

        //create button
        buttonsSetUp();

        //create panels
        panelsSetUp();

        //display frame
        pack();
        setVisible(true);
    }

    private void panelsSetUp() {
        mainPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel(new GridLayout(0,1,5,5));
        fieldPanel = new JPanel(new GridLayout(0,1,5,5));
        buttonPanel = new JPanel();

        //add everything to panels
        labelPanel.add(titleLabel);
        labelPanel.add(totalPageNumLabel);
        labelPanel.add(genreLabel);

        fieldPanel.add(titleTextField);
        fieldPanel.add(totalPageNumTextField);
        fieldPanel.add(genreTextField);

        buttonPanel.add(addBookButton);
        buttonPanel.add(clearFieldsButton);

        //set boarder
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //add panels to frame
        mainPanel.add(labelPanel, BorderLayout.CENTER);
        mainPanel.add(fieldPanel, BorderLayout.LINE_END);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void buttonsSetUp() {
        addBookButton = new JButton("Add a book");
        clearFieldsButton = new JButton("Clear fields");

        //action listener
        addBookButton.addActionListener(this);
        clearFieldsButton.addActionListener(this);
    }

    private void textFieldsSetUp() {
        titleTextField = new JTextField(50);
        totalPageNumTextField = new JTextField(50);
        genreTextField = new JTextField(50);
    }

    private void labelsSetUp() {
        titleLabel = new JLabel(titleString);
        totalPageNumLabel = new JLabel(totalPageNumString);
        genreLabel = new JLabel(genreString);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clearFieldsButton)) {

            titleTextField.setText("");
            totalPageNumTextField.setText("");
            genreTextField.setText("");
        } else if (e.getSource().equals(addBookButton)) {

            String bookTitle = titleTextField.getText();
            String totalPageStr = totalPageNumTextField.getText();
            int totalPageNum = Integer.parseInt(totalPageStr);
            book = new Book(bookTitle, totalPageNum);

            if (!genreTextField.getText().equals("")) {
                addGenreToBook();
            }

            bookshelf.addBook(book);

            JOptionPane.showMessageDialog(null,"You added <" + bookTitle + "> to bookshelf!",
                    "Confirmation",JOptionPane.INFORMATION_MESSAGE, addBookIcon);
        }
    }

    private void addGenreToBook() {
        String[] genreTags = genreTextField.getText().split(";");
        for (String tag : genreTags) {
            book.addGenreTag(tag);
        }
    }
}
