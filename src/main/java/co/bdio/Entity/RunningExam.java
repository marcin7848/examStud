package co.bdio.Entity;

import java.sql.Timestamp;

public class RunningExam {
    private int id_trwajacego_egzaminu;
    private int egzaminy_id_egzaminu;
    private int egzaminy_grupy_id_grupy;
    private int egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    private int egzaminy_egzaminatorzy_id_egzaminatora;
    private int studenci_id_studenta;
    private int testy_id_testu;
    private int testy_egzaminatorzy_id_egzaminatora;
    private int etap_testu;
    private double czas_do_zakonczenia_testu;
    private int czas_do_zakonczenia_testu_min;
    private int czas_do_zakonczenia_testu_sec;

    public RunningExam(){
        this.id_trwajacego_egzaminu = 0;
    }

    public RunningExam(int id_trwajacego_egzaminu, int egzaminy_id_egzaminu, int egzaminy_grupy_id_grupy, int egzaminy_grupy_egzaminatorzy_id_egzaminatora, int egzaminy_egzaminatorzy_id_egzaminatora, int studenci_id_studenta, int testy_id_testu, int testy_egzaminatorzy_id_egzaminatora, int etap_testu, double czas_do_zakonczenia_testu, int czas_do_zakonczenia_testu_min, int czas_do_zakonczenia_testu_sec) {
        this.id_trwajacego_egzaminu = id_trwajacego_egzaminu;
        this.egzaminy_id_egzaminu = egzaminy_id_egzaminu;
        this.egzaminy_grupy_id_grupy = egzaminy_grupy_id_grupy;
        this.egzaminy_grupy_egzaminatorzy_id_egzaminatora = egzaminy_grupy_egzaminatorzy_id_egzaminatora;
        this.egzaminy_egzaminatorzy_id_egzaminatora = egzaminy_egzaminatorzy_id_egzaminatora;
        this.studenci_id_studenta = studenci_id_studenta;
        this.testy_id_testu = testy_id_testu;
        this.testy_egzaminatorzy_id_egzaminatora = testy_egzaminatorzy_id_egzaminatora;
        this.etap_testu = etap_testu;
        this.czas_do_zakonczenia_testu = czas_do_zakonczenia_testu;
        this.czas_do_zakonczenia_testu_min = czas_do_zakonczenia_testu_min;
        this.czas_do_zakonczenia_testu_sec = czas_do_zakonczenia_testu_sec;
    }

    public int getId_trwajacego_egzaminu() {
        return id_trwajacego_egzaminu;
    }

    public void setId_trwajacego_egzaminu(int id_trwajacego_egzaminu) {
        this.id_trwajacego_egzaminu = id_trwajacego_egzaminu;
    }

    public int getEgzaminy_id_egzaminu() {
        return egzaminy_id_egzaminu;
    }

    public void setEgzaminy_id_egzaminu(int egzaminy_id_egzaminu) {
        this.egzaminy_id_egzaminu = egzaminy_id_egzaminu;
    }

    public int getEgzaminy_grupy_id_grupy() {
        return egzaminy_grupy_id_grupy;
    }

    public void setEgzaminy_grupy_id_grupy(int egzaminy_grupy_id_grupy) {
        this.egzaminy_grupy_id_grupy = egzaminy_grupy_id_grupy;
    }

    public int getEgzaminy_grupy_egzaminatorzy_id_egzaminatora() {
        return egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminy_grupy_egzaminatorzy_id_egzaminatora(int egzaminy_grupy_egzaminatorzy_id_egzaminatora) {
        this.egzaminy_grupy_egzaminatorzy_id_egzaminatora = egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    }

    public int getEgzaminy_egzaminatorzy_id_egzaminatora() {
        return egzaminy_egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminy_egzaminatorzy_id_egzaminatora(int egzaminy_egzaminatorzy_id_egzaminatora) {
        this.egzaminy_egzaminatorzy_id_egzaminatora = egzaminy_egzaminatorzy_id_egzaminatora;
    }

    public int getStudenci_id_studenta() {
        return studenci_id_studenta;
    }

    public void setStudenci_id_studenta(int studenci_id_studenta) {
        this.studenci_id_studenta = studenci_id_studenta;
    }

    public int getTesty_id_testu() {
        return testy_id_testu;
    }

    public void setTesty_id_testu(int testy_id_testu) {
        this.testy_id_testu = testy_id_testu;
    }

    public int getTesty_egzaminatorzy_id_egzaminatora() {
        return testy_egzaminatorzy_id_egzaminatora;
    }

    public void setTesty_egzaminatorzy_id_egzaminatora(int testy_egzaminatorzy_id_egzaminatora) {
        this.testy_egzaminatorzy_id_egzaminatora = testy_egzaminatorzy_id_egzaminatora;
    }

    public int getEtap_testu() {
        return etap_testu;
    }

    public void setEtap_testu(int etap_testu) {
        this.etap_testu = etap_testu;
    }

    public double getCzas_do_zakonczenia_testu() {
        return czas_do_zakonczenia_testu;
    }

    public void setCzas_do_zakonczenia_testu(double czas_do_zakonczenia_testu) {
        this.czas_do_zakonczenia_testu = czas_do_zakonczenia_testu;
    }

    public int getCzas_do_zakonczenia_testu_min() {
        return czas_do_zakonczenia_testu_min;
    }

    public void setCzas_do_zakonczenia_testu_min(int czas_do_zakonczenia_testu_min) {
        this.czas_do_zakonczenia_testu_min = czas_do_zakonczenia_testu_min;
    }

    public int getCzas_do_zakonczenia_testu_sec() {
        return czas_do_zakonczenia_testu_sec;
    }

    public void setCzas_do_zakonczenia_testu_sec(int czas_do_zakonczenia_testu_sec) {
        this.czas_do_zakonczenia_testu_sec = czas_do_zakonczenia_testu_sec;
    }
}
