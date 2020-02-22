package sample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime stringToLocalDateTime(String datumVrijeme){
        String[] niz = datumVrijeme.split(" ");

        String[] dMG = niz[0].split("\\.");
        String[] vSS = niz[1].split(":");
        LocalDate dmg1 = LocalDate.of(Integer.parseInt(dMG[2]), Integer.parseInt(dMG[1]), Integer.parseInt(dMG[0]));
        LocalTime vss1 = LocalTime.of(Integer.parseInt(vSS[0]), Integer.parseInt(vSS[1]), Integer.parseInt(vSS[2]));
        return LocalDateTime.of(dmg1, vss1);
    }

    public String localDateTimeToString(LocalDateTime ldt){
        return ldt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"));
    }

}
