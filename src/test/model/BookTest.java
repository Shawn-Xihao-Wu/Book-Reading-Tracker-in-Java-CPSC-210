package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    void testProgressUpdate() {
        testBook.progressUpdate(230);
        assertEquals(230, testBook.getPagesRead());
        assertEquals(44.1, testBook.getProgress());

        testBook.progressUpdate( 341);
        assertEquals(341, testBook.getPagesRead());
        assertEquals(65.5, testBook.getProgress());
    }
}