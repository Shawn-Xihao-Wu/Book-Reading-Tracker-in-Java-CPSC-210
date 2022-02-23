package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class BookshelfTest {

    private Bookshelf testBookShelf;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void runBefore() {
        testBookShelf = new Bookshelf();

        book1 = new Book("Crime and Punishment", 782);
        book2 = new Book("Meditation", 181);
        book3 = new Book("Capital", 1465);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testBookShelf.getNumberOfBooks());
        assertNotNull(testBookShelf.getAllBooks());
        assertEquals(0,testBookShelf.getAllBooks().size());

        assertEquals(0, testBookShelf.getNumberOfGenres());
        assertNotNull(testBookShelf.getAllGenres());
        assertEquals(0, testBookShelf.getAllGenres().size());

        assertEquals(0.0, testBookShelf.getTotalProgress());

    }

    @Test
    void testAddBook() {

        testBookShelf.addBook(book1);
        assertEquals(1, testBookShelf.getNumberOfBooks());
        assertEquals("Crime and Punishment", testBookShelf.getAllBooks().get(0).getTitle());

        testBookShelf.addBook(book2);
        assertEquals(2, testBookShelf.getNumberOfBooks());
        assertEquals("Meditation", testBookShelf.getAllBooks().get(1).getTitle());

        testBookShelf.addBook(book3);
        assertEquals(3, testBookShelf.getNumberOfBooks());
        assertEquals("Capital", testBookShelf.getAllBooks().get(2).getTitle());
    }

    @Test
    void testBooksTaggedByWhenNoBooks() {
        assertTrue(testBookShelf.booksTaggedBy("test").isEmpty());
        assertTrue(testBookShelf.booksTaggedBy("story").isEmpty());
    }

    @Test
    void testBooksTaggedByWhenHasBooksButNoSuchTaggedBooks() {
        book1.addGenreTag("Russian");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Metaphysics");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Russian");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);

        assertTrue(testBookShelf.booksTaggedBy("Novel").isEmpty());
        assertTrue(testBookShelf.booksTaggedBy("Stat").isEmpty());
    }

    @Test
    void testBooksTaggedByWhenHasBooksAndOnlyOneSuchTaggedBooks() {
        book1.addGenreTag("Russian");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Metaphysics");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Russian");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);

        List<Book> metaphysicsBooks = testBookShelf.booksTaggedBy("Metaphysics");
        assertEquals(1, metaphysicsBooks.size());
        assertTrue(metaphysicsBooks.get(0).containsGenreTag("Metaphysics"));

        List<Book> politicalSciBooks = testBookShelf.booksTaggedBy("Political Science");
        assertEquals(1, politicalSciBooks.size());
        assertTrue(politicalSciBooks.get(0).containsGenreTag("Political Science"));
    }

    @Test
    void testBooksTaggedByWhenHasBooksAndMoreThanOneSuchTaggedBooks() {
        book1.addGenreTag("Russian");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Metaphysics");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Russian");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);


        List<Book> philosophyBooks = testBookShelf.booksTaggedBy("Philosophy");
        assertEquals(2, philosophyBooks.size());
        assertTrue(philosophyBooks.get(0).containsGenreTag("Philosophy"));
        assertTrue(philosophyBooks.get(1).containsGenreTag("Philosophy"));

        List<Book> russianBooks = testBookShelf.booksTaggedBy("Russian");
        assertEquals(2, russianBooks.size());
        assertTrue(russianBooks.get(0).containsGenreTag("Russian"));
        assertTrue(russianBooks.get(1).containsGenreTag("Russian"));
    }

    @Test
    void testNumberOfBooksTaggedByWhenNoBooks() {
        assertEquals(0,testBookShelf.numOfBooksTaggedBy("Philosophy"));
        assertEquals(0, testBookShelf.numOfBooksTaggedBy("Social Science"));
    }

    @Test
    void testNumberOfBooksTaggedByWhenHasBooksButNoSuchTaggedBooks() {
        book1.addGenreTag("German");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Epistemology");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Philosophy");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);

        assertEquals(0, testBookShelf.numOfBooksTaggedBy("Linguistics"));
        assertEquals(0, testBookShelf.numOfBooksTaggedBy("Math"));

    }

    @Test
    void testNumberOfBooksTaggedByWhenHasBooksAndOnlyOneSuchTaggedBook() {
        book1.addGenreTag("German");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Epistemology");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Philosophy");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);

        assertEquals(1, testBookShelf.numOfBooksTaggedBy("German"));
        assertEquals(1, testBookShelf.numOfBooksTaggedBy("Epistemology"));
    }

    @Test
    void testNumberOfBooksTaggedByWhenHasBooksAndMoreThanOneSuchTaggedBooks() {
        book1.addGenreTag("German");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Epistemology");

        book3.addGenreTag("Political Science");
        book3.addGenreTag("Philosophy");
        book3.addGenreTag("German");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);
        testBookShelf.addBook(book3);

        assertEquals(3, testBookShelf.numOfBooksTaggedBy("Philosophy"));
        assertEquals(2,testBookShelf.numOfBooksTaggedBy("German"));
    }

    @Test
    void testUpdateGenreInfoWhenNoBooks() {
        testBookShelf.updateGenreInfo();
        assertEquals(0, testBookShelf.getNumberOfGenres());
        assertNotNull(testBookShelf.getAllGenres());
        assertTrue(testBookShelf.getAllGenres().isEmpty());
    }

    @Test
    void testUpdateGenreInfoWhenHasBooksButBooksHaveNoGenreTags() {
        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);


        testBookShelf.updateGenreInfo();
        assertEquals(0, testBookShelf.getNumberOfGenres());
        assertNotNull(testBookShelf.getAllGenres());
        assertTrue(testBookShelf.getAllGenres().isEmpty());

        testBookShelf.addBook(book3);

        testBookShelf.updateGenreInfo();
        assertEquals(0, testBookShelf.getNumberOfGenres());
        assertNotNull(testBookShelf.getAllGenres());
        assertTrue(testBookShelf.getAllGenres().isEmpty());
    }

    @Test
    void testUpdateGenreInfoWhenHasBooksButBooksHaveNoRepeatedGenreTags() {
        book1.addGenreTag("German");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Epistemology");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);

        testBookShelf.updateGenreInfo();
        List<String> genreList1 = testBookShelf.getAllGenres();
        assertEquals(3, testBookShelf.getNumberOfGenres());
        assertEquals(3, genreList1.size());
        assertTrue(genreList1.contains("German"));
        assertTrue(genreList1.contains("Philosophy"));
        assertTrue(genreList1.contains("Epistemology"));

        book3.addGenreTag("Russian");

        testBookShelf.addBook(book3);

        testBookShelf.updateGenreInfo();
        List<String> genreList2 = testBookShelf.getAllGenres();
        assertEquals(4, testBookShelf.getNumberOfGenres());
        assertEquals(4, genreList1.size());
        assertTrue(genreList2.contains("German"));
        assertTrue(genreList2.contains("Philosophy"));
        assertTrue(genreList2.contains("Epistemology"));
        assertTrue(genreList2.contains("Russian"));
    }

    @Test
    void testUpdateGenreInfoWhenHasBooksAndBooksHaveRepeatedGenreTags() {
        book1.addGenreTag("Russian");
        book1.addGenreTag("Philosophy");

        book2.addGenreTag("Philosophy");
        book2.addGenreTag("Metaphysics");

        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);

        testBookShelf.updateGenreInfo();
        List<String> genreList1 = testBookShelf.getAllGenres();
        assertEquals(3, testBookShelf.getNumberOfGenres());
        assertEquals(3, genreList1.size());
        assertTrue(genreList1.contains("Russian"));
        assertTrue(genreList1.contains("Philosophy"));
        assertTrue(genreList1.contains("Metaphysics"));


        book3.addGenreTag("Russian");

        testBookShelf.addBook(book3);

        testBookShelf.updateGenreInfo();
        List<String> genreList2 = testBookShelf.getAllGenres();
        assertEquals(3, testBookShelf.getNumberOfGenres());
        assertEquals(3, genreList2.size());
        assertTrue(genreList2.contains("Russian"));
        assertTrue(genreList2.contains("Philosophy"));
        assertTrue(genreList2.contains("Metaphysics"));
    }

    @Test
    void testTotalProgressUpdateWhenNoBooks() {
        testBookShelf.totalProgressUpdate();
        assertEquals(0.0, testBookShelf.getTotalProgress());
    }

    @Test
    void testTotalProgressUpdateWhenHasBooksButNoneRead() {
        testBookShelf.addBook(book1);
        testBookShelf.addBook(book2);

        testBookShelf.totalProgressUpdate();
        assertEquals(0.0, testBookShelf.getTotalProgress());

        testBookShelf.addBook(book3);

        testBookShelf.totalProgressUpdate();
        assertEquals(0.0, testBookShelf.getTotalProgress());
    }


    @Test
    void testTotalProgressUpdateWhenHasBooksAndSomeReadWithRoundingUp() {


        book1.progressUpdate(659);
        testBookShelf.addBook(book1);
        testBookShelf.totalProgressUpdate();

        assertEquals(84.3, testBookShelf.getTotalProgress());

        book2.progressUpdate(67);
        testBookShelf.addBook(book2);
        testBookShelf.totalProgressUpdate();

        assertEquals(60.7, testBookShelf.getTotalProgress()); // round up



    }

    @Test
    void testTotalProgressUpdateWhenHasBooksAndSomeReadWithRoundingDown() {
        book1.progressUpdate(659);
        testBookShelf.addBook(book1);
        testBookShelf.totalProgressUpdate();

        assertEquals(84.3, testBookShelf.getTotalProgress());

        book2.progressUpdate(67);
        testBookShelf.addBook(book2);
        testBookShelf.totalProgressUpdate();

        assertEquals(60.7, testBookShelf.getTotalProgress());
        book3.progressUpdate(597);
        testBookShelf.addBook(book3);
        testBookShelf.totalProgressUpdate();

        assertEquals(54.0, testBookShelf.getTotalProgress()); // round down
    }
}
