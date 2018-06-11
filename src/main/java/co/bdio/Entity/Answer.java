package co.bdio.Entity;

public class Answer {
    private int id_odpowiedzi;
    private int id_pytania;
    private char czy_poprawna;
    private String tresc;

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public int getId_odpowiedzi() {
        return id_odpowiedzi;
    }

    public void setId_odpowiedzi(int id_odpowiedzi) {
        this.id_odpowiedzi = id_odpowiedzi;
    }

    public char getCzy_poprawna() {
        return czy_poprawna;
    }

    public void setCzy_poprawna(char czy_poprawna){
        this.czy_poprawna = czy_poprawna;
    }

    public void setId_pytania(int id_pytania) {
        this.id_pytania = id_pytania;
    }

    public int getId_pytania() {
        return id_pytania;
    }



}