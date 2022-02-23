package persistence;

import model.Bookshelf;
import model.Book;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            JsonWriter writer = new JsonWriter("./data/\0illegal:fileName.json");
            writer.open();
            fail("Expect IOExpection");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyBookshelf() {
        try {
            Bookshelf bookshelfToWriter = new Bookshelf();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookshelf.json");
            writer.open();
            writer.write(bookshelfToWriter);
            writer.close();


            JsonReader reader = new JsonReader("./data/testWriterEmptyBookshelf.json");
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
    void testWriterGeneralBookshelf() {
        try {
            Bookshelf bookshelfToWriter = new Bookshelf();
            addBooksToBookshelf(bookshelfToWriter);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookshelf.json");
            writer.open();
            writer.write(bookshelfToWriter);
            writer.close();


            JsonReader reader = new JsonReader("./data/testWriterGeneralBookshelf.json");
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

    private void addBooksToBookshelf(Bookshelf bookshelf) {
        bookshelf.addBook(new Book("Nineteen Eighty-Four", 355));

        Book book1 = new Book("Pride and Prejudice", 427);
        book1.addGenreTag("Romance");
        book1.addGenreTag("Novel");
        bookshelf.addBook(book1);

        Book book2 = new Book("Crime and Punishment", 792);
        book2.addGenreTag("Thrill");
        book2.addGenreTag("Russian");
        book2.addGenreTag("Novel");
        book2.progressUpdate(456);
        bookshelf.addBook(book2);

//        bookshelf.updateGenreInfo();
//        bookshelf.totalProgressUpdate();
    }

    private void checkFirstBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        checkBook("Nineteen Eighty-Four", 355, 0, 0.0, genreTags, book);
    }

    private void checkSecondBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        genreTags.add("Romance");
        genreTags.add("Novel");
        checkBook("Pride and Prejudice", 427, 0, 0.0, genreTags, book);
    }

    private void checkThirdBook(Book book) {
        List<String> genreTags = new ArrayList<>();
        genreTags.add("Thrill");
        genreTags.add("Russian");
        genreTags.add("Novel");
        checkBook("Crime and Punishment", 792, 456, 57.6, genreTags, book);
    }

//    private void checkOtherPropertiesOfBookshelf(Bookshelf bookshelfFromReader) {
//        assertEquals(19.2, bookshelfFromReader.getTotalProgress());
//        assertEquals(3, bookshelfFromReader.getNumberOfBooks());
//        assertEquals(4, bookshelfFromReader.getNumberOfGenres());
//
//        List<String> genreTags = bookshelfFromReader.getAllGenres();
//        assertEquals(4, genreTags.size());
//        assertTrue(genreTags.contains("Novel"));
//        assertTrue(genreTags.contains("Romance"));
//        assertTrue(genreTags.contains("Russian"));
//        assertTrue(genreTags.contains("Thrill"));
//    }
}
