package model;

import model.Book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {

    private Bookshelf testBookShelf;

    @BeforeEach
    void runBefore() {
        testBookShelf = new Bookshelf();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testBookShelf.getNumberOfBooks());
        assertEquals("[]", testBookShelf.getAllBooks().toString());

        assertEquals(0, testBookShelf.getNumberOfGenreTags());
        assertEquals("[]", testBookShelf.getAllGenreTags().toString());

        assertEquals(0.0, testBookShelf.getTotalProgress());

    }

    @Test
    void testAddBook() {
        List<String> testTags = new ArrayList<>();
        testTags.add("Test");

        Book testBook1 = new Book("Crime and Punishment", 782, testTags);

        testBookShelf.addBook(testBook1);
        assertEquals(1, testBookShelf.getNumberOfBooks());
        assertEquals("Crime and Punishment", testBookShelf.getAllBooks().get(0).getTitle());

        Book testBook2 = new Book("Minds and Machines", 45, testTags);

        testBookShelf.addBook(testBook2);
        assertEquals(2, testBookShelf.getNumberOfBooks());
        assertEquals("Minds and Machines", testBookShelf.getAllBooks().get(1).getTitle());
    }


    @Test
    void testTotalProgressUpdate() {
        List<String> testTags = new ArrayList<>();
        testTags.add("Test");

        Book testBook1 = new Book("Crime and Punishment", 782, testTags);
        testBook1.progressUpdate(659);
        testBookShelf.addBook(testBook1);
        testBookShelf.totalProgressUpdate();

        assertEquals(84.3, testBookShelf.getTotalProgress());

        Book testBook2 = new Book("Meditation", 181, testTags);
        testBook2.progressUpdate(67);
        testBookShelf.addBook(testBook2);
        testBookShelf.totalProgressUpdate();

        assertEquals(60.7, testBookShelf.getTotalProgress()); // round up

        Book testBook3 = new Book("Capital", 1465, testTags);
        testBook3.progressUpdate(597);
        testBookShelf.addBook(testBook3);
        testBookShelf.totalProgressUpdate();

        assertEquals(54.0, testBookShelf.getTotalProgress()); // round down

    }

    @Test
    void testTotalGenreTagsUpdate() {


        List<String> testTags1 = new ArrayList<>();
        List<String> testTags2 = new ArrayList<>();
        List<String> testTags3 = new ArrayList<>();
        tagListsGenerator(testTags1, testTags2, testTags3);

        Book testBook1 = new Book("title1", 1000, testTags1);
        Book testBook2 = new Book("title2", 1000, testTags2);
        Book testBook3 = new Book("title3", 1000, testTags3);


        testBookShelf.addBook(testBook1);
        testBookShelf.addBook(testBook2);
        testBookShelf.addBook(testBook3);

        testBookShelf.totalGenreTagsUpdate();
        assertEquals(4, testBookShelf.getNumberOfGenreTags());

        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 1]"));
        assertFalse(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 2]"));

        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 3]"));
        assertFalse(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 2]"));

        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 2]"));
        assertFalse(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 1]"));

        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Novel, 1]"));
        assertFalse(testBookShelf.getAllGenreTags().toString().contains("[Novel, 2]"));

    }

    private void tagListsGenerator(List<String> t1, List<String> t2 , List<String> t3) {
        t1.add("Philosophy");
        t1.add("Psychology");

        t2.add("Psychology");
        t2.add("Statistics");
        t2.add("Novel");

        t3.add("Statistics");
        t3.add("Psychology");
    }

}
