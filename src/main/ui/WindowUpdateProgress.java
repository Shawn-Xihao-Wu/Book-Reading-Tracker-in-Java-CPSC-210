package ui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class WindowUpdateProgress extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel selectBookPanel;
    private JPanel updatePagesPanel;

    private JLabel selectBookLabel;
    private JLabel updatePagesLabel;

    private static String selectBookString = "Select a book: ";
    private static String updatePagesString = "Enter new pages: ";

    private JButton updatePagesButton;
    private JButton clearFieldsButton;

    private JComboBox selectBookMenu;
    private JTextField updatePagesTextField;

    private Bookshelf bookshelf;
    private Book book;

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


        pack();
        setVisible(true);
    }

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
                    "Page Update", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
