package sample;

import java.time.format.DateTimeFormatter;

public class Notification {

    private Renting renting;

    public Notification(Renting renting) {
        this.renting = renting;
    }


    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Rok za vraćanje knjige: " + "\" " + renting.getKnjiga().getName() + " \"" + "je istekao " + renting.getKraj().format(formater);
    }
}
