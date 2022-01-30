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

        init1();

        testBookShelf.totalGenreTagsUpdate();
        assertEquals(3, testBookShelf.getNumberOfGenreTags());
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 1]"));
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 2]"));
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 1]"));

        init2();

        testBookShelf.totalGenreTagsUpdate();
        assertEquals(4, testBookShelf.getNumberOfGenreTags());
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 3]"));
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 2]"));
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 2]"));
        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Novel, 1]"));


    }

    private void init1() {
        List<String> testTags1 = new ArrayList<>();
        List<String> testTags2 = new ArrayList<>();
        tagListsGenerator1(testTags1, testTags2);

        Book testBook1 = new Book("title2", 1000, testTags1);
        Book testBook2 = new Book("title2", 1000, testTags2);
        testBookShelf.addBook(testBook1);
        testBookShelf.addBook(testBook2);
    }

    private void init2() {
        List<String> testTags3 = new ArrayList<>();
        List<String> testTags4 = new ArrayList<>();
        tagListsGenerator2(testTags3, testTags4);

        Book testBook3 = new Book("title1", 1000, testTags3);
        Book testBook4 = new Book("title2", 1000, testTags4);
        testBookShelf.addBook(testBook3);
        testBookShelf.addBook(testBook4);
    }

    private void tagListsGenerator1(List<String> t1, List<String> t2) {

        t1.add("Philosophy");
        t1.add("Psychology");

        t2.add("Psychology");
        t2.add("Statistics");

    }

    private void tagListsGenerator2(List<String> t1, List<String> t2) {

        t1.add("Philosophy");
        t1.add("Statistics");

        t2.add("Novel");
        t2.add("Philosophy");
    }

}
