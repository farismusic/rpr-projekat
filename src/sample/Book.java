package sample;

import java.util.Objects;

public class Book {
    String name, author, genre;
    int id, brojStranica, brojKnjiga;

    public Book(int id, String name, String author, String genre, int brojStranica, int brojKnjiga) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.brojStranica = brojStranica;
        this.brojKnjiga = brojKnjiga;
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

    public int getBrojStranica() {
        return brojStranica;
    }

    public Book setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
        return this;
    }

    public int getBrojKnjiga() {
        return brojKnjiga;
    }

    public Book setBrojKnjiga(int brojKnjiga) {
        this.brojKnjiga = brojKnjiga;
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
}
