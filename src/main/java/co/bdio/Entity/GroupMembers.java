package co.bdio.Entity;

public class GroupMembers {
    private int id;
    private int id_studenta;
    private int id_grupy;
    private int studenci_id_studenta;
    private int grupy_id_grupy;
    private int grupy_id_egzaminatora1;
    private int studenci_id_studenta1;
    private int grupy_id_grupy1;
    private int grupy_egzaminatorzy_id_egzaminatora;

    public GroupMembers(){}

    public GroupMembers(int id, int id_studenta, int id_grupy, int studenci_id_studenta, int grupy_id_grupy, int grupy_id_egzaminatora1, int studenci_id_studenta1, int grupy_id_grupy1, int grupy_egzaminatorzy_id_egzaminatora) {
        this.id = id;
        this.id_studenta = id_studenta;
        this.id_grupy = id_grupy;
        this.studenci_id_studenta = studenci_id_studenta;
        this.grupy_id_grupy = grupy_id_grupy;
        this.grupy_id_egzaminatora1 = grupy_id_egzaminatora1;
        this.studenci_id_studenta1 = studenci_id_studenta1;
        this.grupy_id_grupy1 = grupy_id_grupy1;
        this.grupy_egzaminatorzy_id_egzaminatora = grupy_egzaminatorzy_id_egzaminatora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_studenta() {
        return id_studenta;
    }

    public void setId_studenta(int id_studenta) {
        this.id_studenta = id_studenta;
    }

    public int getId_grupy() {
        return id_grupy;
    }

    public void setId_grupy(int id_grupy) {
        this.id_grupy = id_grupy;
    }

    public int getStudenci_id_studenta() {
        return studenci_id_studenta;
    }

    public void setStudenci_id_studenta(int studenci_id_studenta) {
        this.studenci_id_studenta = studenci_id_studenta;
    }

    public int getGrupy_id_grupy() {
        return grupy_id_grupy;
    }

    public void setGrupy_id_grupy(int grupy_id_grupy) {
        this.grupy_id_grupy = grupy_id_grupy;
    }

    public int getGrupy_id_egzaminatora1() {
        return grupy_id_egzaminatora1;
    }

    public void setGrupy_id_egzaminatora1(int grupy_id_egzaminatora1) {
        this.grupy_id_egzaminatora1 = grupy_id_egzaminatora1;
    }

    public int getStudenci_id_studenta1() {
        return studenci_id_studenta1;
    }

    public void setStudenci_id_studenta1(int studenci_id_studenta1) {
        this.studenci_id_studenta1 = studenci_id_studenta1;
    }

    public int getGrupy_id_grupy1() {
        return grupy_id_grupy1;
    }

    public void setGrupy_id_grupy1(int grupy_id_grupy1) {
        this.grupy_id_grupy1 = grupy_id_grupy1;
    }

    public int getGrupy_egzaminatorzy_id_egzaminatora() {
        return grupy_egzaminatorzy_id_egzaminatora;
    }

    public void setGrupy_egzaminatorzy_id_egzaminatora(int grupy_egzaminatorzy_id_egzaminatora) {
        this.grupy_egzaminatorzy_id_egzaminatora = grupy_egzaminatorzy_id_egzaminatora;
    }
}
