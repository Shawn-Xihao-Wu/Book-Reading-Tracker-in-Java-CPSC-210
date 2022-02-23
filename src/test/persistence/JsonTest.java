package persistence;

import model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    protected void checkBook(String title, int totalPages, int pagesRead,
                             double progress, List<String> genreTags, Book book) {

        assertEquals(title, book.getTitle());
        assertEquals(totalPages, book.getTotalPages());
        assertEquals(pagesRead, book.getPagesRead());
        assertEquals(progress, book.getProgress());
        assertNotNull(genreTags);
        assertEquals(genreTags.size(), book.getGenreTags().size());
        for (String tag : genreTags) {
            assertTrue(book.containsGenreTag(tag));
        }
    }
}
