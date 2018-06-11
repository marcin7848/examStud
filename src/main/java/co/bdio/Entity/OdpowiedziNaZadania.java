package co.bdio.Entity;

public class OdpowiedziNaZadania {
    private Integer id;
    private Integer id_egzaminu;
    private Integer id_testu;
    private Integer id_pytania;
    private Integer czas_odpowiedzi;
    private char[] czy_ocenione;
    private String tresc;
    private Integer ilosc_przyznanych_pkt;
    private Integer pytania_id_pytania;
    private Integer pytania_testy_id_testu;
    private Integer pytania_testy_egzaminatorzy_id_egzaminatora;
    private Integer egzaminy_id_egzaminu;
    private Integer testy_id_testu;
    private Integer testy_egzaminatorzy_id_egzaminatora;
    private Integer studenci_id_studenta;
    private Integer egzaminy_grupy_id_grupy;
    private Integer egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    private Integer egzaminy_egzaminatorzy_id_egzaminatora;


    public OdpowiedziNaZadania(){super();}

    public OdpowiedziNaZadania(Integer id, Integer id_egzaminu, Integer id_testu, Integer id_pytania, Integer czas_odpowiedzi, char[] czy_ocenione, String treść, Integer ilość_przyznanych_pkt, Integer pytania_id_pytania, Integer pytania_testy_id_testu, Integer pytania_testy_egzaminatorzy_id_egzaminatora, Integer egzaminy_id_egzaminu, Integer testy_id_testu, Integer testy_egzaminatorzy_id_egzaminatora, Integer studenci_id_studenta, Integer egzaminy_grupy_id_grupy, Integer egzaminy_grupy_egzaminatorzy_id_egzaminatora, Integer egzaminy_egzaminatorzy_id_egzaminatora) {
        this.id = id;
        this.id_egzaminu = id_egzaminu;
        this.id_testu = id_testu;
        this.id_pytania = id_pytania;
        this.czas_odpowiedzi = czas_odpowiedzi;
        this.czy_ocenione = czy_ocenione;
        this.tresc = tresc;
        this.ilosc_przyznanych_pkt = ilosc_przyznanych_pkt;
        this.pytania_id_pytania = pytania_id_pytania;
        this.pytania_testy_id_testu = pytania_testy_id_testu;
        this.pytania_testy_egzaminatorzy_id_egzaminatora = pytania_testy_egzaminatorzy_id_egzaminatora;
        this.egzaminy_id_egzaminu = egzaminy_id_egzaminu;
        this.testy_id_testu = testy_id_testu;
        this.testy_egzaminatorzy_id_egzaminatora = testy_egzaminatorzy_id_egzaminatora;
        this.studenci_id_studenta = studenci_id_studenta;
        this.egzaminy_grupy_id_grupy = egzaminy_grupy_id_grupy;
        this.egzaminy_grupy_egzaminatorzy_id_egzaminatora = egzaminy_grupy_egzaminatorzy_id_egzaminatora;
        this.egzaminy_egzaminatorzy_id_egzaminatora = egzaminy_egzaminatorzy_id_egzaminatora;
    }

    public Integer getPytania_testy_egzaminatorzy_id_egzaminatora() {
        return pytania_testy_egzaminatorzy_id_egzaminatora;
    }

    public void setPytania_testy_egzaminatorzy_id_egzaminatora(Integer pytania_testy_egzaminatorzy_id_egzaminatora) {
        this.pytania_testy_egzaminatorzy_id_egzaminatora = pytania_testy_egzaminatorzy_id_egzaminatora;
    }

    public Integer getId_egzaminu() {
        return id_egzaminu;
    }

    public void setId_egzaminu(Integer id_egzaminu) {
        this.id_egzaminu = id_egzaminu;
    }

    public Integer getId_testu() {
        return id_testu;
    }

    public void setId_testu(Integer id_testu) {
        this.id_testu = id_testu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pytania() {
        return id_pytania;
    }

    public void setId_pytania(Integer id_pytania) {
        this.id_pytania = id_pytania;
    }

    public Integer getCzas_odpowiedzi() {
        return czas_odpowiedzi;
    }

    public void setCzas_odpowiedzi(Integer czas_odpowiedzi) {
        this.czas_odpowiedzi = czas_odpowiedzi;
    }

    public char[] getCzy_ocenione() {
        return czy_ocenione;
    }

    public void setCzy_ocenione(char[] czy_ocenione) {
        this.czy_ocenione = czy_ocenione;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Integer getIlosc_przyznanych_pkt() {
        return ilosc_przyznanych_pkt;
    }

    public void setIlosc_przyznanych_pkt(Integer ilosc_przyznanych_pkt) {
        this.ilosc_przyznanych_pkt = ilosc_przyznanych_pkt;
    }

    public Integer getPytania_id_pytania() {
        return pytania_id_pytania;
    }

    public void setPytania_id_pytania(Integer pytania_id_pytania) {
        this.pytania_id_pytania = pytania_id_pytania;
    }

    public Integer getPytania_testy_id_testu() {
        return pytania_testy_id_testu;
    }

    public void setPytania_testy_id_testu(Integer pytania_testy_id_testu) {
        this.pytania_testy_id_testu = pytania_testy_id_testu;
    }

    public Integer getEgzaminy_id_egzaminu() {
        return egzaminy_id_egzaminu;
    }

    public void setEgzaminy_id_egzaminu(Integer egzaminy_id_egzaminu) {
        this.egzaminy_id_egzaminu = egzaminy_id_egzaminu;
    }

    public Integer getTesty_id_testu() {
        return testy_id_testu;
    }

    public void setTesty_id_testu(Integer testy_id_testu) {
        this.testy_id_testu = testy_id_testu;
    }

    public Integer getTesty_egzaminatorzy_id_egzaminatora() {
        return testy_egzaminatorzy_id_egzaminatora;
    }

    public void setTesty_egzaminatorzy_id_egzaminatora(Integer testy_egzaminatorzy_id_egzaminatora) {
        this.testy_egzaminatorzy_id_egzaminatora = testy_egzaminatorzy_id_egzaminatora;
    }

    public Integer getStudenci_id_studenta() {
        return studenci_id_studenta;
    }

    public void setStudenci_id_studenta(Integer studenci_id_studenta) {
        this.studenci_id_studenta = studenci_id_studenta;
    }

    public Integer getEgzaminy_grupy_id_grupy() {
        return egzaminy_grupy_id_grupy;
    }

    public void setEgzaminy_grupy_id_grupy(Integer egzaminy_grupy_id_grupy) {
        this.egzaminy_grupy_id_grupy = egzaminy_grupy_id_grupy;
    }

    public Integer getEgzaminy_grupy_egzaminatorzy_id_egzaminatora() {
        return egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminy_grupy_egzaminatorzy_id_egzaminatora(Integer egzaminy_grupy_egzaminatorzy_id_egzaminatora) {
        this.egzaminy_grupy_egzaminatorzy_id_egzaminatora = egzaminy_grupy_egzaminatorzy_id_egzaminatora;
    }

    public Integer getEgzaminy_egzaminatorzy_id_egzaminatora() {
        return egzaminy_egzaminatorzy_id_egzaminatora;
    }

    public void setEgzaminy_egzaminatorzy_id_egzaminatora(Integer egzaminy_egzaminatorzy_id_egzaminatora) {
        this.egzaminy_egzaminatorzy_id_egzaminatora = egzaminy_egzaminatorzy_id_egzaminatora;
    }


}
