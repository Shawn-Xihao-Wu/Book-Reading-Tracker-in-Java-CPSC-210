package persistence;

import model.Book;
import model.Bookshelf;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Modelled the save and load function after WorkRoomApp.java
// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads bookshelf from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bookshelf from file and returns it;
    //      if an error occurs reading data from file, throws IOException.
    public Bookshelf read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookshelf(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses bookshelf from JSON object and returns it
    private Bookshelf parseBookshelf(JSONObject jsonObject) {
        Bookshelf bookshelf = new Bookshelf();
        addBooks(bookshelf, jsonObject);
        return bookshelf;
    }

    // MODIFIES: bookshelf
    // EFFECTS: parses books from JSON object and adds them to bookshelf
    private void addBooks(Bookshelf bookshelf, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("collectionOfBooks");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bookshelf, nextBook);
        }
    }

    // MODIFIES: bookshelf
    // EFFECTS: parses a book from JSON object and adds it to bookshelf
    private void addBook(Bookshelf bookshelf, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int totalPages = jsonObject.getInt("totalPages");
        Book book = new Book(title, totalPages);

        int pagesRead = jsonObject.getInt("pagesRead");
        book.progressUpdate(pagesRead);

        JSONArray jsonArray = jsonObject.getJSONArray("genreTags");
        for (int i = 0; i < jsonArray.length(); i++) {
            book.addGenreTag(jsonArray.getString(i));
        }

        bookshelf.addBook(book);
    }





}
