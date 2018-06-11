package co.bdio.Entity;


import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id_grupy;
    private String nazwa;
    private int id_egzaminatora;
    private String haslo;
    private int egzaminatorzy_id_egzaminatora;

    private List<User> usersList = new ArrayList<User>();

    public Group(){
        this.id_grupy = 0;
    }

    public int getId_grupy() {        return id_grupy;    }

    public void setId_grupy(int id_grupy) {        this.id_grupy = id_grupy;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId_egzaminatora() {
        return id_egzaminatora;
    }

    public void setId_egzaminatora(int id_egzaminatora) {
        this.id_egzaminatora = id_egzaminatora;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getEgzaminatorzy_id_egzaminatora() {
        return egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminatorzy_id_egzaminatora(int egzaminatorzy_id_egzaminatora) {
        this.egzaminatorzy_id_egzaminatora = egzaminatorzy_id_egzaminatora;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public Group(int id_grupy, String nazwa, int id_egzaminatora, String haslo, int egzaminatorzy_id_egzaminatora, List<User> usersList) {
        this.id_grupy = id_grupy;
        this.nazwa = nazwa;
        this.id_egzaminatora = id_egzaminatora;
        this.haslo = haslo;
        this.egzaminatorzy_id_egzaminatora = egzaminatorzy_id_egzaminatora;
        this.usersList = usersList;
    }
}
