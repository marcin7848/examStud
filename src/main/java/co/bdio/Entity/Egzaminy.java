package co.bdio.Entity;

import java.util.Date;

public class Egzaminy {
    private Integer id_egzaminu;
    private String nazwa;
    private Integer id_grupy;
    private Integer id_egzaminatora;
    private String kod_aktywacyjny;
    private Date data_utowrzenia;
    private Date ostatnia_zmiana_kodu;
    private Integer grupy_id_grupy;
    private Integer grupy_egzaminatorzy_id_egzaminatora;
    private Integer egzaminatorzy_id_egzaminatora;

    public Egzaminy(){super();}
    public Egzaminy(Integer id_egzaminu,String nazwa, Integer id_grupy, Integer id_egzaminatora){
        super();
        this.id_egzaminu=id_egzaminu;
        this.nazwa=nazwa;
        this.id_grupy=id_grupy;
        this.id_egzaminatora=id_egzaminatora;
    }

    public Date getData_utowrzenia(){
        return data_utowrzenia;
    }
    public void setData_utowrzenia(){this.data_utowrzenia=data_utowrzenia;}


    public Date getOstatnia_zmiana_kodu(){
        return ostatnia_zmiana_kodu;
    }
    public void setOstatnia_zmiana_kodu(){ this.ostatnia_zmiana_kodu=ostatnia_zmiana_kodu;}


    public Integer getId_egzaminu() {
        return id_egzaminu;
    }
    public void setId_egzaminu(Integer id_egzaminu){
        this.id_egzaminu=id_egzaminu;
    }


    public String getNazwa(){
        return nazwa;
    }
    public void setNazwa(String nazwa){
        this.nazwa=nazwa;
    }


    public Integer getId_grupy(){
        return id_grupy;
    }
    public void setId_grupy(Integer id_grupy){
        this.id_grupy=id_grupy;
    }



    public Integer getId_egzaminatora(){
        return id_egzaminatora;
    }
    public void setId_egzaminatora(Integer id_egzaminatora){
        this.id_egzaminatora=id_egzaminatora;
    }



    public Integer getGrupy_id_grupy(){
        return grupy_id_grupy;
    }
    public void setGrupy_id_grupy(Integer grupy_id_grupy){
        this.grupy_id_grupy=grupy_id_grupy;
    }



    public Integer getGrupy_egzaminatorzy_id_egzaminatora(){
        return grupy_egzaminatorzy_id_egzaminatora;
    }
    public void setGrupy_egzaminatorzy_id_egzaminatora(){
        this.grupy_egzaminatorzy_id_egzaminatora=grupy_egzaminatorzy_id_egzaminatora;
    }


    public Integer getEgzaminatorzy_id_egzaminatora(){
        return egzaminatorzy_id_egzaminatora;
    }
    public void setEgzaminatorzy_id_egzaminatora(){
        this.egzaminatorzy_id_egzaminatora=egzaminatorzy_id_egzaminatora;
    }



    public String getKod_aktywacyjny(){
        return kod_aktywacyjny;
    }
    public void setKod_aktywacyjny(String kod_aktywacyjny){
        this.kod_aktywacyjny=kod_aktywacyjny;
    }
}

