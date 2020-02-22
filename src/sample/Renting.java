package sample;

import java.time.LocalDateTime;

public class Renting {

    private int id;
    private User iznajmljivac;
    private LocalDateTime pocetak, kraj;
    private Book knjiga;

    public Renting(int id, User iznajmljivac, LocalDateTime pocetak, LocalDateTime kraj, Book knjiga) {
        this.id = id;
        this.iznajmljivac = iznajmljivac;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.knjiga = knjiga;
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

    public User getIznajmljivac() {
        return iznajmljivac;
    }

    public Renting setIznajmljivac(User iznajmljivac) {
        this.iznajmljivac = iznajmljivac;
        return this;
    }

    public LocalDateTime getPocetak() {
        return pocetak;
    }

    public Renting setPocetak(LocalDateTime pocetak) {
        this.pocetak = pocetak;
        return this;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    public Renting setKraj(LocalDateTime kraj) {
        this.kraj = kraj;
        return this;
    }

    public Book getKnjiga() {
        return knjiga;
    }

    public Renting setKnjiga(Book knjiga) {
        this.knjiga = knjiga;
        return this;
    }
}
