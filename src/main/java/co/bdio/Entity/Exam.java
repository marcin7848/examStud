package co.bdio.Entity;

import java.sql.Timestamp;

public class Exam {

    private int id_egzaminu;
    private String nazwa;
    private int id_grupy;
    private int id_egzaminatora;
    private String kod_aktywacyjny;
    private Timestamp data_utworzenia;
    private Timestamp ostatnia_zmiana_kodu;
    private int grupy_id_grupy;
    private int grupy_egzaminatorzy_id_egzaminatora;
    private int egzaminatorzy_id_egzaminatora;
    private int aktywny;
    private Timestamp czas_rozpoczecia;

    //nie z bazy
    private String nazwa_grupy;
    private int ilosc_uczestnikow;
    private int czas_do_zakonczenia;


    public Exam(){
        this.id_egzaminu = 0;
    }

    public Exam(int id_egzaminu, String nazwa, int id_grupy, int id_egzaminatora, String kod_aktywacyjny, Timestamp data_utworzenia, Timestamp ostatnia_zmiana_kodu, int grupy_id_grupy, int grupy_egzaminatorzy_id_egzaminatora, int egzaminatorzy_id_egzaminatora, int aktywny, Timestamp czas_rozpoczecia) {
        this.id_egzaminu = id_egzaminu;
        this.nazwa = nazwa;
        this.id_grupy = id_grupy;
        this.id_egzaminatora = id_egzaminatora;
        this.kod_aktywacyjny = kod_aktywacyjny;
        this.data_utworzenia = data_utworzenia;
        this.ostatnia_zmiana_kodu = ostatnia_zmiana_kodu;
        this.grupy_id_grupy = grupy_id_grupy;
        this.grupy_egzaminatorzy_id_egzaminatora = grupy_egzaminatorzy_id_egzaminatora;
        this.egzaminatorzy_id_egzaminatora = egzaminatorzy_id_egzaminatora;
        this.aktywny = aktywny;
        this.czas_rozpoczecia = czas_rozpoczecia;
    }

    public int getId_egzaminu() {
        return id_egzaminu;
    }

    public void setId_egzaminu(int id_egzaminu) {
        this.id_egzaminu = id_egzaminu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId_grupy() {
        return id_grupy;
    }

    public void setId_grupy(int id_grupy) {
        this.id_grupy = id_grupy;
    }

    public int getId_egzaminatora() {
        return id_egzaminatora;
    }

    public void setId_egzaminatora(int id_egzaminatora) {
        this.id_egzaminatora = id_egzaminatora;
    }

    public String getKod_aktywacyjny() {
        return kod_aktywacyjny;
    }

    public void setKod_aktywacyjny(String kod_aktywacyjny) {
        this.kod_aktywacyjny = kod_aktywacyjny;
    }

    public Timestamp getData_utworzenia() {
        return data_utworzenia;
    }

    public void setData_utworzenia(Timestamp data_utworzenia) {
        this.data_utworzenia = data_utworzenia;
    }

    public Timestamp getOstatnia_zmiana_kodu() {
        return ostatnia_zmiana_kodu;
    }

    public void setOstatnia_zmiana_kodu(Timestamp ostatnia_zmiana_kodu) {
        this.ostatnia_zmiana_kodu = ostatnia_zmiana_kodu;
    }

    public int getGrupy_id_grupy() {
        return grupy_id_grupy;
    }

    public void setGrupy_id_grupy(int grupy_id_grupy) {
        this.grupy_id_grupy = grupy_id_grupy;
    }

    public int getGrupy_egzaminatorzy_id_egzaminatora() {
        return grupy_egzaminatorzy_id_egzaminatora;
    }

    public void setGrupy_egzaminatorzy_id_egzaminatora(int grupy_egzaminatorzy_id_egzaminatora) {
        this.grupy_egzaminatorzy_id_egzaminatora = grupy_egzaminatorzy_id_egzaminatora;
    }

    public int getEgzaminatorzy_id_egzaminatora() {
        return egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminatorzy_id_egzaminatora(int egzaminatorzy_id_egzaminatora) {
        this.egzaminatorzy_id_egzaminatora = egzaminatorzy_id_egzaminatora;
    }

    public int getAktywny() {
        return aktywny;
    }

    public void setAktywny(int aktywny) {
        this.aktywny = aktywny;
    }

    public Timestamp getCzas_rozpoczecia() {
        return czas_rozpoczecia;
    }

    public void setCzas_rozpoczecia(Timestamp czas_rozpoczecia) {
        this.czas_rozpoczecia = czas_rozpoczecia;
    }

    public String getNazwa_grupy() {
        return nazwa_grupy;
    }

    public void setNazwa_grupy(String nazwa_grupy) {
        this.nazwa_grupy = nazwa_grupy;
    }

    public int getIlosc_uczestnikow() {
        return ilosc_uczestnikow;
    }

    public void setIlosc_uczestnikow(int ilosc_uczestnikow) {
        this.ilosc_uczestnikow = ilosc_uczestnikow;
    }

    public int getCzas_do_zakonczenia() {
        return czas_do_zakonczenia;
    }

    public void setCzas_do_zakonczenia(int czas_do_zakonczenia) {
        this.czas_do_zakonczenia = czas_do_zakonczenia;
    }
}
