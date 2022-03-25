package ui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a window that allow user to update pages read for one specific book.
//      User can select a book from a drop-down menu.
public class WindowUpdateProgress extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel selectBookPanel;
    private JPanel updatePagesPanel;

    private JLabel selectBookLabel;
    private JLabel updatePagesLabel;

    private static ImageIcon checkIcon = new ImageIcon("./data/checkIcon.png");

    private static String selectBookString = "Select a book: ";
    private static String updatePagesString = "Enter new pages: ";

    private JButton updatePagesButton;
    private JButton clearFieldsButton;

    private JComboBox selectBookMenu;
    private JTextField updatePagesTextField;

    private Bookshelf bookshelf;
    private Book book;

    // REQUIRES: bookshelf is not null
    // EFFECTS: construct a window that allow user to select a book and update its pages read
    public WindowUpdateProgress(Bookshelf bookshelf) {
        //set up main frame
        super("Update progress");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        this.bookshelf = bookshelf;

        //set up labels
        selectBookLabel = new JLabel(selectBookString);
        updatePagesLabel = new JLabel(updatePagesString);

        //set up drop down menu for book select
        dropDownMenuSetUp();

        //set up text fields for page update
        updatePagesTextField = new JTextField(10);

        //set up buttons for page update
        updatePagesButton = new JButton("Update");
        clearFieldsButton = new JButton("Clear");

        updatePagesButton.addActionListener(this);
        clearFieldsButton.addActionListener(this);

        //set up panels
        panelsSetUp();

        //display the window
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: create a drop-down menu for user to select the book that the user
    //      wants to update its pages read
    private void dropDownMenuSetUp() {
        List<String> bookList = new ArrayList<>();
        String menuStr = "";
        for (Book book : bookshelf.getAllBooks()) {
            menuStr = book.getTitle() + "  (" + book.getPagesRead() + "/" + book.getTotalPages() + ")";
            bookList.add(menuStr);
        }

        selectBookMenu = new JComboBox<>(bookList.toArray());
        selectBookMenu.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: create a panel for the drop-down menu to select a book,
    //      create a panel for updating pages, and create a main panel that contains the two panels before.
    //      Add the labels, the drop-down menu, the buttons, and the text fields to corresponding panels
    private void panelsSetUp() {
        mainPanel = new JPanel(new GridLayout(0,2,10,10));
        selectBookPanel = new JPanel(new GridLayout(0,1,5,5));
        updatePagesPanel = new JPanel(new GridLayout(0,1,5,5));

        //add everything to corresponding panels
        selectBookPanel.add(selectBookLabel);
        selectBookPanel.add(selectBookMenu);

        updatePagesPanel.add(updatePagesLabel);
        updatePagesPanel.add(updatePagesTextField);
        updatePagesPanel.add(updatePagesButton);
        updatePagesPanel.add(clearFieldsButton);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(selectBookPanel);
        mainPanel.add(updatePagesPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: handle user event actions. If the clear field button is clicked, the text field accepting new pages #
    //      is cleared. If the update pages button is clicked, the pages read of the selected book are updated according
    //      to the user input.
    //      After the update is finished, the window closes and a confirmation window pops up.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clearFieldsButton)) {
            updatePagesTextField.setText("");
        } else if (e.getSource().equals(updatePagesButton)) {
            int newPageNum = Integer.parseInt(updatePagesTextField.getText());
            int index = selectBookMenu.getSelectedIndex();
            book = bookshelf.getAllBooks().get(index);
            book.progressUpdate(newPageNum);

            dispose();

            JOptionPane.showMessageDialog(null,"Now on page " + newPageNum + " of "
                    + book.getTotalPages() + " for <" + book.getTitle() + ">!",
                    "Page Update", JOptionPane.INFORMATION_MESSAGE, checkIcon);

        }
    }
}
