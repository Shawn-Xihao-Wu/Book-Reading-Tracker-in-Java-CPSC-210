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
    void testTotalProgressUpdate() {


        book1.progressUpdate(659);
        testBookShelf.addBook(book1);
        testBookShelf.totalProgressUpdate();

        assertEquals(84.3, testBookShelf.getTotalProgress());

        book2.progressUpdate(67);
        testBookShelf.addBook(book2);
        testBookShelf.totalProgressUpdate();

        assertEquals(60.7, testBookShelf.getTotalProgress()); // round up

        book3.progressUpdate(597);
        testBookShelf.addBook(book3);
        testBookShelf.totalProgressUpdate();

        assertEquals(54.0, testBookShelf.getTotalProgress()); // round down

    }

    @Test
    void testBooksTaggedBy() {
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
    void testUpdateGenreInfo() {
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


        book3.addGenreTag("Political Science");
        book3.addGenreTag("Russian");

        testBookShelf.addBook(book3);

        testBookShelf.updateGenreInfo();
        List<String> genreList2 = testBookShelf.getAllGenres();
        assertEquals(4, testBookShelf.getNumberOfGenres());
        assertEquals(4, genreList2.size());
        assertTrue(genreList2.contains("Political Science"));
        assertTrue(genreList2.contains("Russian"));
        assertTrue(genreList2.contains("Philosophy"));
        assertTrue(genreList2.contains("Metaphysics"));

    }
//
//    @Test
//    void testTotalGenreTagsUpdate() {
//
//        init1();
//
//        //testBookShelf.totalGenreTagsUpdate();
//        assertEquals(3, testBookShelf.getNumberOfGenreTags());
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 1]"));
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 2]"));
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 1]"));
//
//        init2();
//
//        //testBookShelf.totalGenreTagsUpdate();
//        assertEquals(4, testBookShelf.getNumberOfGenreTags());
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Philosophy, 3]"));
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Psychology, 2]"));
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Statistics, 2]"));
//        assertTrue(testBookShelf.getAllGenreTags().toString().contains("[Novel, 1]"));
//
//
//    }
//
//    private void init1() {
//        List<String> testTags1 = new ArrayList<>();
//        List<String> testTags2 = new ArrayList<>();
//        tagListsGenerator1(testTags1, testTags2);
//
//        Book testBook1 = new Book("title1", 1000, testTags1);
//        Book testBook2 = new Book("title2", 1000, testTags2);
//        testBookShelf.addBook(testBook1);
//        testBookShelf.addBook(testBook2);
//    }
//
//    private void init2() {
//        List<String> testTags3 = new ArrayList<>();
//        List<String> testTags4 = new ArrayList<>();
//        tagListsGenerator2(testTags3, testTags4);
//
//        Book testBook3 = new Book("title3", 1000, testTags3);
//        Book testBook4 = new Book("title4", 1000, testTags4);
//        testBookShelf.addBook(testBook3);
//        testBookShelf.addBook(testBook4);
//    }
//
//    private void tagListsGenerator1(List<String> t1, List<String> t2) {
//
//        t1.add("Philosophy");
//        t1.add("Psychology");
//
//        t2.add("Psychology");
//        t2.add("Statistics");
//
//    }
//
//    private void tagListsGenerator2(List<String> t1, List<String> t2) {
//
//        t1.add("Philosophy");
//        t1.add("Statistics");
//
//        t2.add("Novel");
//        t2.add("Philosophy");
//    }

}
