package ui;

import model.Book;
import model.Bookshelf;


import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        Bookshelf bookshelf = new Bookshelf();
        List<String> tag = new ArrayList<>();
        tag.add("ege");
        tag.add("tah");

        Book book = new Book("title", 135, tag);
        bookshelf.addBook(book);

        bookshelf.totalGenreTagsUpdate();


        for (List<Object> next : bookshelf.getAllGenreTags()) {
            System.out.print(next.toString());
        }


    }


}
