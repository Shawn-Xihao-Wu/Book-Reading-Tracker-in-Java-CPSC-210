package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        List<String> genreTags = new ArrayList<>();
        genreTags.add("Literature");
        testBook = new Book("War and Peace", 521, genreTags);
    }

    @Test
    void testConstructor() {
        assertEquals("War and Peace", testBook.getTitle());
        assertEquals(521, testBook.getTotalPages());
        assertEquals(0, testBook.getPagesRead());
        assertEquals(0.0,testBook.getProgress());
        assertEquals("[Literature]", testBook.getGenreTagsString());
    }

    @Test
    void testAddGenreTags() {
        testBook.addGenreTags("Russian History");
        assertEquals("[Literature, Russian History]", testBook.getGenreTagsString());
        assertEquals(2, testBook.getGenreTagsList().size());

        testBook.addGenreTags("Romantics");
        assertEquals("[Literature, Russian History, Romantics]", testBook.getGenreTagsString());
        assertEquals(3, testBook.getGenreTagsList().size());
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