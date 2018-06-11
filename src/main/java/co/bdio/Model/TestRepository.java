package co.bdio.Model;

import co.bdio.Entity.Answer;
import co.bdio.Entity.Question;
import co.bdio.Entity.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {
    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    private JdbcTemplate jdbc;

public TestRepository(){
    MyRepo myRepo = new MyRepo();
    jdbc = myRepo.getJdbcTemplate();
}
    public List<Test> findAll(int id) {

        String query = "select t.id_testu, t.data_utworzenia, t.nazwa from testy t join egzaminatorzy e on t.egzaminatorzy_id_egzaminatora=e.id_egzaminatora where e.użytkownicy_id_użytkownika="+id+" order by id_testu;";
        List<Test> lista = new ArrayList<Test>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(query);
        for (Map wiersz : obiekt) {
            Test tescik = new Test();
            tescik.setId_testu(Integer.parseInt(String.valueOf(wiersz.get("id_testu"))));
            tescik.setNazwa(String.valueOf(wiersz.get("nazwa")));
            tescik.setData_utworzenia(Timestamp.valueOf(String.valueOf(wiersz.get("data_utworzenia"))));
            lista.add(tescik);
        }
        return lista;
    }

    public void addTest(Test tescik, int id) {
        String sql = "select MAX(id_testu) from testy;";
        int tab1last_id=0;
        String id1=jdbc.queryForObject(sql, String.class);
        String sql1="select id_egzaminatora from egzaminatorzy where użytkownicy_id_użytkownika="+id+";";
        int id_egz=jdbc.queryForObject(sql1, Integer.class);
        if (id1==null) tab1last_id=0;
        else tab1last_id=Integer.parseInt(id1);
        tab1last_id++;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.jdbc.update(
                "insert into testy  (id_testu, data_utworzenia, nazwa,egzaminatorzy_id_egzaminatora) values (?, ?,?,?)",
                tab1last_id, timestamp, tescik.getNazwa(), id_egz);
    }
    public int findTestId(String nazwa)
    {
        String query="select id_testu from testy where nazwa='"+nazwa+"';";
        int id = jdbc.queryForObject(query, Integer.class);
        return id;
    }

    public List<Question> getById(int id, int id1) {
        String query = "select p.id_pytania, p.typ, p.treść, p.czas, p.ilość_punktów from pytania p join  testy t on p.testy_id_testu=t.id_testu join egzaminatorzy e on e.id_egzaminatora=t.egzaminatorzy_id_egzaminatora where p.testy_id_testu=" + id + " and e.użytkownicy_id_użytkownika="+id1+" order by p.id_pytania;";
        List<Question> lista = new ArrayList<Question>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(query);
        for (Map wiersz : obiekt) {
            Question pyt = new Question();
            pyt.setId_pytania(Integer.parseInt(String.valueOf(wiersz.get("id_pytania"))));
            pyt.setTresc(String.valueOf(wiersz.get("treść")));
            pyt.setCzasodp(Integer.parseInt(String.valueOf(wiersz.get("czas"))));
            pyt.setIlosc_punktow(Integer.parseInt(String.valueOf(wiersz.get("ilość_punktów"))));
            pyt.setTyp(String.valueOf(wiersz.get("typ")).charAt(0));
            lista.add(pyt);
        }
        return lista;
    }

    public List<Answer> getodpByIdTestu(int id, int id1){
        String query = "select o.id_odpowiedzi, o.treść,o.pytania_id_pytania, o.czy_poprawna from odpowiedzi o join  pytania p on o.pytania_id_pytania=p.id_pytania join testy t on p.testy_id_testu=t.id_testu join egzaminatorzy e on t.egzaminatorzy_id_egzaminatora=e.id_egzaminatora where o.pytania_testy_id_testu=" + id + " and e.użytkownicy_id_użytkownika="+id1+" order by o.id_odpowiedzi;";
        List<Answer> lista = new ArrayList<Answer>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(query);
        for (Map wiersz : obiekt) {
            Answer odp = new Answer();
            odp.setTresc(String.valueOf(wiersz.get("treść")));
            odp.setId_pytania(Integer.parseInt(String.valueOf(wiersz.get("pytania_id_pytania"))));
            odp.setCzy_poprawna(String.valueOf(wiersz.get("czy_poprawna")).charAt(0));
            odp.setId_odpowiedzi(Integer.parseInt(String.valueOf(wiersz.get("id_odpowiedzi"))));
            lista.add(odp);
        }
        return lista;
    }

    public void editTest(Test test) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "update testy set nazwa=?, data_utworzenia=? where id_testu =?";
        this.jdbc.update(sql, test.getNazwa(), timestamp, test.getId_testu());
    }

    public Test getByIdt(int id, int id1) {
        String query = "select t.nazwa from testy t join egzaminatorzy e on e.id_egzaminatora=t.egzaminatorzy_id_egzaminatora where t.id_testu=" + id + " and e.użytkownicy_id_użytkownika="+id1+";";
        String tresc = jdbc.queryForObject(query, String.class);
        Test tescik = new Test();
        tescik.setNazwa(tresc);
        tescik.setId_testu(id);
        return tescik;
    }
    public void saveQuestion(Question question, int id) {
        String sql = "select MAX(id_pytania) from pytania;";
        int tab1last_id=0;
        String id1=jdbc.queryForObject(sql, String.class);
        String sql1="select id_egzaminatora from egzaminatorzy where użytkownicy_id_użytkownika="+id+";";
        int id_egz=jdbc.queryForObject(sql1, Integer.class);
        if (id1==null) tab1last_id=0;
        else tab1last_id=Integer.parseInt(id1);
        tab1last_id++;
        String sql2 = "insert into pytania (id_pytania,czas,ilość_punktów, typ,treść,ilość_odpowiedzi,ilość_poprawnych_odpowiedzi, testy_id_testu, testy_egzaminatorzy_id_egzaminatora) values(?,?,?,?,?,?,?,?,?)";
        this.jdbc.update(sql2, tab1last_id, question.getCzasOdp(),
                question.getIlosc_punktow(), question.getTyp(), question.getTresc(), question.getIlosc_odpowiedzi(), question.getIlosc_poprawnych(), question.getId_testu(),id_egz );
    }

    public List<Question> findAllQuest(int id) {
        String query = "select p.id_pytania, p.treść, p.czas, p.ilość_punktów, p.testy_id_testu from pytania p join testy t on p.testy_id_testu=t.id_testu join egzaminatorzy e on t.egzaminatorzy_id_egzaminatora=e.id_egzaminatora where e.użytkownicy_id_użytkownika="+id+" order by testy_id_testu, id_pytania;";
        List<Question> lista = new ArrayList<Question>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(query);
        for (Map wiersz : obiekt) {
            Question pyt = new Question();
            pyt.setId_pytania(Integer.parseInt(String.valueOf(wiersz.get("id_pytania"))));
            pyt.setTresc(String.valueOf(wiersz.get("treść")));
            pyt.setCzasodp(Integer.parseInt(String.valueOf(wiersz.get("czas"))));
            pyt.setIlosc_punktow(Integer.parseInt(String.valueOf(wiersz.get("ilość_punktów"))));
            pyt.setId_testu(Integer.parseInt(String.valueOf(wiersz.get("testy_id_testu"))));
            lista.add(pyt);
        }
        return lista;

    }

    public void selectAndGet(int id, int idd) {
        String sql1 = "select MAX(id_pytania) from pytania;";
        int tab2last_id = jdbc.queryForObject(sql1, Integer.class)+1;
        String sql3 = "select MAX(id_odpowiedzi) from odpowiedzi;";
        int tab3last_id = jdbc.queryForObject(sql3, Integer.class)+1;
        String sql2 = "insert into pytania (czas,ilość_punktów, typ,treść," +
                "ilość_odpowiedzi,ilość_poprawnych_odpowiedzi, testy_id_testu, " +
                "testy_egzaminatorzy_id_egzaminatora, id_pytania) " +
                "SELECT " +
                "czas,ilość_punktów, typ,treść," +
                "ilość_odpowiedzi,ilość_poprawnych_odpowiedzi, "+idd +
                ",testy_egzaminatorzy_id_egzaminatora," +tab2last_id+" from pytania where id_pytania="+id+";";
        this.jdbc.update(sql2);

        String sql4="select id_odpowiedzi from odpowiedzi where id_pytania="+id+";";
        List<Integer> lista = new ArrayList<Integer>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(sql4);

        for (Map wiersz : obiekt) {
            int lol=Integer.parseInt(String.valueOf(wiersz.get("id_odpowiedzi")));
            lista.add(lol);}
        for(int i=0; i<lista.size();i++){
            String sql5="insert into odpowiedzi (id_odpowiedzi,id_pytania, treść, czy_poprawna,pytania_id_pytania,pytania_testy_id_testu, pytania_testy_egzaminatorzy_id_egzaminatora)" +
                    "      select " + tab3last_id + ", " + tab2last_id + ", " +
                    "       treść, czy_poprawna," + tab2last_id+","+idd + " ,pytania_testy_egzaminatorzy_id_egzaminatora from odpowiedzi where id_odpowiedzi=" + lista.get(i) + ";";
            this.jdbc.update(sql5);
            tab3last_id++;
        }
    }

    public void saveAnswer(String tekst, char t,Question question, int id_uz) {
        String sql1 = "select MAX(id_pytania) from pytania;";
        int tab2last_id = jdbc.queryForObject(sql1, Integer.class);
        String sql = "select MAX(id_odpowiedzi) from odpowiedzi;";
        int tab1last_id=0;
        String id1=jdbc.queryForObject(sql, String.class);
        String sql2="select id_egzaminatora from egzaminatorzy where użytkownicy_id_użytkownika="+id_uz+";";
        int id_egz=jdbc.queryForObject(sql2, Integer.class);

        if (id1==null) tab1last_id=0;
        else tab1last_id=Integer.parseInt(id1);
        tab1last_id++;
        String sql3 = "insert into odpowiedzi (id_odpowiedzi,id_pytania, treść, czy_poprawna,pytania_id_pytania,pytania_testy_id_testu, pytania_testy_egzaminatorzy_id_egzaminatora) values(?,?,?,?,?,?,?)";
        this.jdbc.update(sql3, tab1last_id, tab2last_id,
                tekst,t, tab2last_id,question.getId_testu(), id_egz);

    }

    public void editQuestion(Question question,int id){
        String sql = "update pytania set czas = ?,treść=?, ilość_punktów=?, ilość_poprawnych_odpowiedzi=?, typ=?, ilość_odpowiedzi=? where id_pytania=?";
        this.jdbc.update(sql, question.getCzasOdp(), question.getTresc(), question.getIlosc_punktow(), question.getIlosc_poprawnych(), question.getTyp(),question.getIlosc_odpowiedzi(), question.getId_pytania());


    }
    public void dropAns(int id){
        String sql = "delete from odpowiedzi where pytania_id_pytania =?";
        this.jdbc.update(sql, id);
    }
    public void dropQuestion(int id){
        String sql= "delete from pytania where id_pytania =?";
        this.jdbc.update(sql, id);
    }

    public void  dropTest(int id){
        String sql = "delete from odpowiedzi where pytania_testy_id_testu =?";
        this.jdbc.update(sql, id);
        String sql1= "delete from pytania where testy_id_testu =?";
        this.jdbc.update(sql1, id);
        String sql2= "delete from testy where id_testu =?";
        this.jdbc.update(sql2, id);

    }

    public Question getByIdpyt(int id, int id_uz){
        String query = "select treść from pytania where id_pytania=" + id + ";";
        String tresc = jdbc.queryForObject(query, String.class);
        String query2 = "select ilość_odpowiedzi from pytania where id_pytania=" + id + ";";
        Integer ile = jdbc.queryForObject(query2, Integer.class);
        String query3 = "select czas from pytania where id_pytania=" + id + ";";
        Integer czas = jdbc.queryForObject(query3, Integer.class);
        String query4 = "select ilość_punktów from pytania where id_pytania=" + id + ";";
        Integer maxpkt = jdbc.queryForObject(query4, Integer.class);
        String query5= "select typ from pytania where id_pytania=" + id + ";";
        String typpy=jdbc.queryForObject(query5, String.class);
        String query7 = "select testy_id_testu from pytania where id_pytania=" + id + ";";
        Integer idtestu = jdbc.queryForObject(query7, Integer.class);

        Question quest = new Question();
        quest.setTresc(tresc);
        quest.setIlosc_odpowiedzi(ile);
        quest.setCzasOdp(czas);
        quest.setIlosc_punktow(maxpkt);
        quest.setId_pytania(id);
        quest.setId_testu(idtestu);
        quest.setTyp(typpy.charAt(0));

        return quest;
    }
    public int getIdPyt(String nazwa)
    {
        String query="select id_pytania from pytania where treść='"+nazwa+"';";
        int id = jdbc.queryForObject(query, Integer.class);
        return id;
    }
    public int getIdtestu(int id)
    {
        String query="select testy_id_testu from pytania where id_pytania='"+id+"';";
        int id2 = jdbc.queryForObject(query, Integer.class);
        return id2;
    }
    public void savepdateAnswer(String tekst, char t,Question question, int id_uz, int id_pyt) {
        String sql = "select MAX(id_odpowiedzi) from odpowiedzi;";
        int tab1last_id=0;
        String id1=jdbc.queryForObject(sql, String.class);
        String sql2="select id_egzaminatora from egzaminatorzy where użytkownicy_id_użytkownika="+id_uz+";";
        int id_egz=jdbc.queryForObject(sql2, Integer.class);

        if (id1==null) tab1last_id=0;
        else tab1last_id=Integer.parseInt(id1);
        tab1last_id++;
        String sql3 = "insert into odpowiedzi (id_odpowiedzi,id_pytania, treść, czy_poprawna,pytania_id_pytania,pytania_testy_id_testu, pytania_testy_egzaminatorzy_id_egzaminatora) values(?,?,?,?,?,?,?)";
        this.jdbc.update(sql3, tab1last_id, id_pyt,
                tekst,t, id_pyt,question.getId_testu(), id_egz);

    }

    public List<Answer> getOdpToAnswer(int id_pytania){
        String query = "select o.id_odpowiedzi, o.treść,o.pytania_id_pytania, o.czy_poprawna from odpowiedzi o where o.id_pytania = "+id_pytania+" order by o.id_odpowiedzi;";

        List<Answer> listaOdp = new ArrayList<Answer>();
        List<Map<String, Object>> obiekt = getJdbcTemplate().queryForList(query);
        for (Map wiersz : obiekt) {
            Answer odp = new Answer();

            odp.setTresc(String.valueOf(wiersz.get("treść")));
            odp.setId_pytania(Integer.parseInt(String.valueOf(wiersz.get("pytania_id_pytania"))));
            odp.setCzy_poprawna(String.valueOf(wiersz.get("czy_poprawna")).charAt(0));
            odp.setId_odpowiedzi(Integer.parseInt(String.valueOf(wiersz.get("id_odpowiedzi"))));

            listaOdp.add(odp);
        }
        return listaOdp;
    }


    public List<Test> getAllTestNames(){
        String query = "select id_testu, nazwa from testy order by nazwa;";

        List<Test> testList = new ArrayList<Test>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
        for (Map empRow : rows) {
            Test test = new Test();
            test.setId_testu(Integer.parseInt(String.valueOf(empRow.get("id_testu"))));
            test.setNazwa(String.valueOf(empRow.get("nazwa")));
            testList.add(test);
        }
        return testList;
    }
}
