package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Book("War and Peace", 521);
    }

    @Test
    void testConstructor() {

        assertEquals("War and Peace", testBook.getTitle());
        assertEquals(521, testBook.getTotalPages());
        assertEquals(0, testBook.getPagesRead());
        assertEquals(0.0,testBook.getProgress());

        assertNotNull(testBook.getGenreTags());
        assertEquals(0,testBook.getGenreTags().size());
    }

    @Test
    void testAddGenreTag() {
        testBook.addGenreTag("Literature");

        assertEquals("Literature", testBook.getGenreTags().get(0));
        assertEquals(1, testBook.getGenreTags().size());

        testBook.addGenreTag("Political Science");
        assertEquals("Political Science", testBook.getGenreTags().get(1));
        assertEquals(2, testBook.getGenreTags().size());
    }



    @Test
    void testContainsGenreTag() {
        testBook.addGenreTag("Russian");
        assertTrue(testBook.containsGenreTag("Russian"));

        testBook.addGenreTag("History");
        assertTrue(testBook.containsGenreTag("History"));

    }


    @Test
    void testProgressUpdate() {

        // round down
        testBook.progressUpdate(230);
        assertEquals(230, testBook.getPagesRead());
        assertEquals(44.1, testBook.getProgress());

        // round up
        testBook.progressUpdate( 341);
        assertEquals(341, testBook.getPagesRead());
        assertEquals(65.5, testBook.getProgress());
    }
}