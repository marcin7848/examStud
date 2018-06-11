package co.bdio.Entity;

import java.sql.Timestamp;
import java.util.Date;

import java.sql.Timestamp;
import java.util.Date;


import java.sql.Timestamp;
import java.util.Date;

public class Test {
    private int id_testu;
    private Timestamp data_utworzenia;
    private String Nazwa;


    public void setId_testu(int id_testu) {
        this.id_testu = id_testu;
    }

    public void setData_utworzenia(Timestamp data_utworzenia) {
        this.data_utworzenia = data_utworzenia;
    }

    public Date getData_utworzenia() {
        return data_utworzenia;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public int getId_testu() {
        return id_testu;
    }

}
