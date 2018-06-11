package co.bdio.Entity;

public class Student {
    private int id_studenta;
    private int uzytkownicy_id_uzytkownika;

    public Student(){}

    public Student(int id_studenta, int uzytkownicy_id_uzytkownika) {
        this.id_studenta = id_studenta;
        this.uzytkownicy_id_uzytkownika = uzytkownicy_id_uzytkownika;
    }

    public int getId_studenta() {
        return id_studenta;
    }

    public void setId_studenta(int id_studenta) {
        this.id_studenta = id_studenta;
    }

    public int getUzytkownicy_id_uzytkownika() {
        return uzytkownicy_id_uzytkownika;
    }

    public void setUzytkownicy_id_uzytkownika(int uzytkownicy_id_uzytkownika) {
        this.uzytkownicy_id_uzytkownika = uzytkownicy_id_uzytkownika;
    }
}
