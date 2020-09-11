package sample;

import java.time.LocalDateTime;

public class Renting {

    private int id;
    private User renter;
    private LocalDateTime dateBegin, dateEnd;
    private Book book;

    public Renting(int id, User renter, LocalDateTime dateBegin, LocalDateTime dateEnd, Book book) {
        this.id = id;
        this.renter = renter;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.book = book;
    }

    public Renting() {
    }

    public int getId() {
        return id;
    }

    public Renting setId(int id) {
        this.id = id;
        return this;
    }

    public User getRenter() {
        return renter;
    }

    public Renting setRenter(User renter) {
        this.renter = renter;
        return this;
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public Renting setDateBegin(LocalDateTime dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public Renting setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Renting setBook(Book book) {
        this.book = book;
        return this;
    }



}
