package co.bdio.Entity;

public class User {
    private int id_uzytkownika;
    private String imie;
    private String nazwisko;
    private String haslo;
    private String email;
    private int status; //0 egzamintor, 1- student
    private int id_studenta_or_egzamintora; //zawiera ID studenta lub egzamintora w odpowiedniej bazie

    public User() {
        this.id_uzytkownika = 0;
        this.id_studenta_or_egzamintora = 0;
    }

    public User(int id_uzytkownika, String imie, String nazwisko, String haslo, String email, int status, int id_studenta_or_egzamintora) {
        this.id_uzytkownika = id_uzytkownika;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.haslo = haslo;
        this.email = email;
        this.status = status;
        this.id_studenta_or_egzamintora = id_studenta_or_egzamintora;
    }


    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    public void setId_uzytkownika(int id_uzytkownika) {
        this.id_uzytkownika = id_uzytkownika;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_studenta_or_egzamintora() {
        return id_studenta_or_egzamintora;
    }

    public void setId_studenta_or_egzamintora(int id_studenta_or_egzamintora) {
        this.id_studenta_or_egzamintora = id_studenta_or_egzamintora;
    }
}
