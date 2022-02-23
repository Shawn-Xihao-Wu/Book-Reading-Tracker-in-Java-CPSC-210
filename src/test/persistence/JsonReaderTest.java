package persistence;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.Bookshelf;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Bookshelf bookshelfFromReader = reader.read();
            fail("Expect IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyBookshelf() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookshelf.json");
        try {
            Bookshelf bookshelfFromReader = reader.read();

            assertNotNull(bookshelfFromReader);
            assertEquals(0, bookshelfFromReader.getNumberOfBooks());
            assertTrue(bookshelfFromReader.getAllBooks().isEmpty());
            assertEquals(0, bookshelfFromReader.getNumberOfGenres());
            assertTrue(bookshelfFromReader.getAllGenres().isEmpty());
            assertEquals(0.0, bookshelfFromReader.getTotalProgress());

        } catch (IOException e) {
            fail("No Exception Expected!");
        }
    }

    @Test
    void testReaderGeneralBookshelf() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookshelf.json");
        try {
            Bookshelf bookshelfFromReader = reader.read();

            assertNotNull(bookshelfFromReader);

            List<Book> booksFromReader = bookshelfFromReader.getAllBooks();
            assertEquals(3, booksFromReader.size());

            checkFirstBook(booksFromReader.get(0));
            checkSecondBook(booksFromReader.get(1));
            checkThirdBook(booksFromReader.get(2));

            //checkOtherPropertiesOfBookshelf(bookshelfFromReader);


        } catch (IOException e) {
            fail("No Exception Expected!");
        }
    }

    private void checkFirstBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        checkBook("Minds and Machines", 383, 0, 0.0, genreTags, book);
    }

    private void checkSecondBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        genreTags.add("Russian");
        genreTags.add("Marxism");
        checkBook("Capital", 1456, 0, 0.0, genreTags, book);
    }

    private void checkThirdBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        genreTags.add("History");
        genreTags.add("Russian");
        genreTags.add("Novel");
        checkBook("The Brothers Karamazov", 742, 45, 6.1, genreTags, book);
    }

//    private void checkOtherPropertiesOfBookshelf(Bookshelf bookshelfFromReader) {
//        bookshelfFromReader.updateGenreInfo();
//        bookshelfFromReader.totalProgressUpdate();
//
//        assertEquals(2.0, bookshelfFromReader.getTotalProgress());
//        assertEquals(3, bookshelfFromReader.getNumberOfBooks());
//        assertEquals(4, bookshelfFromReader.getNumberOfGenres());
//
//        List<String> genreTags = bookshelfFromReader.getAllGenres();
//        assertEquals(4, genreTags.size());
//        assertTrue(genreTags.contains("Novel"));
//        assertTrue(genreTags.contains("Marxism"));
//        assertTrue(genreTags.contains("Russian"));
//        assertTrue(genreTags.contains("History"));
//    }

}
