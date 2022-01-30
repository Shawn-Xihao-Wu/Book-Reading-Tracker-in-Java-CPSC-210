package ui;

import model.Book;
import model.Bookshelf;


import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        Bookshelf bookshelf = new Bookshelf();

        List<String> tag = new ArrayList<>();
        tag.add("Philosophy");
        tag.add("Math");

        Book book = new Book("title", 135, tag);
        bookshelf.addBook(book);

        List<String> tag1 = new ArrayList<>();
        tag.add("Math");
        tag.add("CS");

        Book book1 = new Book("title1", 34, tag1);

        bookshelf.totalGenreTagsUpdate();
        bookshelf.totalGenreTagsUpdate();



        for (List<Object> next : bookshelf.getAllGenreTags()) {
            System.out.print(next.toString());
        }


    }


}
