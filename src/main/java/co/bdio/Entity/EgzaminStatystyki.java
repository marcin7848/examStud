package co.bdio.Entity;

public class EgzaminStatystyki {
    private Integer ileZaliczylo;
    private Integer ileNieZaliczylo;
    private double sredniaPkt;
    private int srednOcena;
    private int sumaPkt;


    public double getIleNieZaliczylo() {
        return ileNieZaliczylo;
    }

    public void setIleNieZaliczylo(Integer ileNieZaliczylo) {
        this.ileNieZaliczylo = ileNieZaliczylo;
    }

    public double getIleZaliczylo() {
        return ileZaliczylo;
    }

    public void setIleZaliczylo(Integer ileZaliczylo) {
        this.ileZaliczylo = ileZaliczylo;
    }

    public double getSredniaPkt() {
        return sredniaPkt;
    }

    public void setSrednOcena(int srednOcena) {
        this.srednOcena = srednOcena;
    }

    public int getSrednOcena() {
        return srednOcena;
    }

    public void setSredniaPkt(double sredniaPkt) {
        this.sredniaPkt = sredniaPkt;
    }

    public int getSumaPkt() {
        return sumaPkt;
    }

    public void setSumaPkt(int sumapkt) {
        this.sumaPkt = sumapkt;
    }
}


