package co.bdio.Entity;

import co.bdio.Entity.Answer;

public class Question {



    private Integer id_pytania = 0;
    private String tresc;
    private Boolean czyotwarte;
    private Integer ilosc_odpowiedzi;
    private Integer czasodp;
    private Integer ilosc_punktow;
    private Integer ilosc_poprawnych;
    private Integer id_testu;
    private char typ;

    public Question(Integer id) {
        this.setId_testu(id);
        this.setIlosc_odpowiedzi(1);

    }
    public Integer getIlosc_odpowiedzi() {
        return ilosc_odpowiedzi;
    }

    public void setIlosc_odpowiedzi(Integer ilosc_odpowiedzi) {
        this.ilosc_odpowiedzi = ilosc_odpowiedzi;
    }
    public Integer getIlosc_punktow() {
        return ilosc_punktow;
    }

    public void setIlosc_punktow(Integer ilosc_punktow) {
        this.ilosc_punktow = ilosc_punktow;
    }
    public char getTyp() {
        return typ;
    }

    public void setTyp(char typ) {
        this.typ = typ;
    }

    public Question() {
    }
    public Integer getId_pytania() {
        return id_pytania;
    }

    public void setId_pytania(Integer id_pytania) {
        this.id_pytania = id_pytania;
    }

    public Integer getIlosc_poprawnych() {
        return ilosc_poprawnych;
    }

    public void setIlosc_poprawnych(Integer ilosc_poprawnych) {
        this.ilosc_poprawnych = ilosc_poprawnych;
    }

    public Integer getCzasOdp() {
        return czasodp;
    }

    public void setCzasOdp(Integer czasodp) {
        this.czasodp = czasodp;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Integer getCzasodp() {
        return czasodp;
    }

    public void setCzasodp(Integer czasodp) {
        this.czasodp = czasodp;
    }

    public Boolean getCzyotwarte() {
        return czyotwarte;
    }

    public void setCzyotwarte(Boolean czyotwarte) {
        this.czyotwarte = czyotwarte;
    }

    public Integer getId_testu() {
        return id_testu;
    }

    public void setId_testu(Integer id_testu) {
        this.id_testu = id_testu;
    }


}
