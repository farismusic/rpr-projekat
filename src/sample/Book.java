package sample;

import java.util.Objects;

public class Book implements Comparable<Book>{
    String name, author, genre;
    int id, numberOfPages, numberOfBooks;

    public Book(int id, String name, String author, String genre, int numberOfPages, int numberOfBooks) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.numberOfBooks = numberOfBooks;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Book setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Book setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public Book setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.getName().equalsIgnoreCase(book.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getId() {
        return id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Book o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
