package co.bdio.Model;

import co.bdio.Entity.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GroupRepo {

    private JdbcTemplate jdbcTemplate;

    public GroupRepo(){
        MyRepo myRepo = new MyRepo();
        jdbcTemplate = myRepo.getJdbcTemplate();
    }

    public List<Group> getAllGroups(int id_egzaminatora) {
        String query = "select id_grupy, nazwa, id_egzaminatora, hasło, egzaminatorzy_id_egzaminatora from grupy where egzaminatorzy_id_egzaminatora="+id_egzaminatora+"order by id_grupy;";
        List<Group> groupList = new ArrayList<Group>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {
            Group group = new Group();
            group.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            group.setNazwa(String.valueOf(empRow.get("nazwa")));
            group.setId_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            group.setHaslo(String.valueOf(empRow.get("hasło")));
            group.setEgzaminatorzy_id_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("egzaminatorzy_id_egzaminatora"))));

            groupList.add(group);
        }
        return groupList;
    }

    public List<User> getAllGroupMembers(int id_grupy) {
        String query = "select * from użytkownicy where id_użytkownika in\n" +
                "(select użytkownicy_id_użytkownika from studenci where id_studenta in\n" +
                "(select id_studenta from członkowie_grup where id_grupy = "+id_grupy+")) order by nazwisko;";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int id_uzytkownika;
        String imie;
        String nazwisko;

        List<User> usersList = new ArrayList<User>();

        for (Map empRow : rows) {
            User us = new User();
            id_uzytkownika = Integer.parseInt(String.valueOf(empRow.get("id_użytkownika")));
            imie = String.valueOf(empRow.get("imię"));
            nazwisko = String.valueOf(empRow.get("nazwisko"));

            us.setId_uzytkownika(id_uzytkownika);
            us.setImie(imie);
            us.setNazwisko(nazwisko);

            usersList.add(us);
        }
        return usersList;
    }

    public Group getGroupDetails(int id_grupy){
        String query = "select * from grupy where id_grupy = "+id_grupy;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Group group = new Group();

        for (Map empRow : rows) {
            group.setId_grupy(id_grupy);
            group.setNazwa(String.valueOf(empRow.get("nazwa")));
            group.setId_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("id_egzaminatora"))));
            group.setHaslo(String.valueOf(empRow.get("hasło")));
            group.setEgzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("egzaminatorzy_id_egzaminatora"))));
        }

        return group;
    }

    public User getExaminer(int id_grupy){
        String query = "select imię, nazwisko, id_użytkownika from użytkownicy where id_użytkownika in\n" +
                "(select id_egzaminatora from grupy where id_grupy = "+id_grupy+");";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        User user = new User();

        for (Map empRow : rows) {
            user.setImie(String.valueOf(empRow.get("imię")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
        }
        return user;
    }

    public User getExaminerFromIDUser(int id_użytkownika){
        String query = "select imię, nazwisko from użytkownicy where id_użytkownika = "+id_użytkownika+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        User user = new User();

        for (Map empRow : rows) {
            user.setImie(String.valueOf(empRow.get("imię")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
        }
        return user;
    }

    public int deleteMember(int id_grupy, int id_uzytkownika){
        String query = "delete from członkowie_grup where id_grupy = "+id_grupy+" and id_studenta IN (" +
                "select id_studenta from studenci where użytkownicy_id_użytkownika = "+id_uzytkownika+");";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public int addGroup(String name, String password, int examiner_id)
    {

        String query = "select * from grupy where hasło = '"+password+"'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Group group = new Group();

        for (Map empRow : rows) {
            group.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
        }

        if(group.getId_grupy() != 0)
            return 2; //istnieje juz takie haslo


        query = "INSERT INTO grupy VALUES(nextval('grupy_id_grupy_seq'), '"+name+"', "+examiner_id+", '"+password+"', "+examiner_id+");";

        if(jdbcTemplate.update(query) > 0)
            return 1; //dodano

        return 0;
    }
    public int joinToGroup(int user_id, String password)
    {
        String query = "select * from grupy where hasło = '"+password+"'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Group group = new Group();

        for (Map empRow : rows) {
            group.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            group.setId_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("id_egzaminatora"))));
        }

        if(group.getId_grupy() == 0)
            return 1; //nie istnieje grupa z takim hasłem



        query = "SELECT * FROM członkowie_grup WHERE id_grupy = "+group.getId_grupy()+" AND id_studenta IN(\n" +
                "SELECT id_studenta FROM studenci WHERE użytkownicy_id_użytkownika = "+user_id+");";

        List<Map<String, Object>> rows2 = jdbcTemplate.queryForList(query);
        GroupMembers groupMembers = new GroupMembers();

        for (Map empRow : rows2) {
            groupMembers.setId(Integer.parseInt(String.valueOf(empRow.get("id"))));
        }

        if(groupMembers.getId() > 0)
            return 2; //jesteś już dodany do tej grupy


        query = "select * from studenci where użytkownicy_id_użytkownika = "+user_id;

        List<Map<String, Object>> rows3 = jdbcTemplate.queryForList(query);
        Student student = new Student();

        for (Map empRow : rows3) {
            student.setId_studenta(Integer.parseInt(String.valueOf(empRow.get("id_studenta"))));
        }

        query = "INSERT INTO członkowie_grup VALUES(nextval('członkowie_grup_id_seq'), "+student.getId_studenta()+","+group.getId_grupy()+", "+student.getId_studenta()+", "+group.getId_grupy()+", " +group.getId_egzaminatora()+", "+student.getId_studenta()+", " +group.getId_grupy()+", "+group.getId_egzaminatora()+");";

        if(jdbcTemplate.update(query) > 0)
            return 3; //dodano poprawnie do grupy użytkownika

        return 0;
    }


    public User getBeingDeletedGroupMember(int id_uzytkownika){
        String query = "select imię, nazwisko from użytkownicy where id_użytkownika = "
                +id_uzytkownika+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        User user = new User();


        for (Map empRow : rows) {
            user.setId_uzytkownika(id_uzytkownika);
            user.setImie(String.valueOf(empRow.get("imię")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
        }
        return user;
    }

    public int deleteGroup(int id_grupy){
        String query = "delete from grupy where id_grupy = "+id_grupy+";" +
                "delete from członkowie_grup where id_grupy = "+id_grupy+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public int editGroup(int id_grupy, String nazwa, String haslo){

        String query = "select hasło from grupy;";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Group group = new Group();

        for (Map empRow : rows) {
            group.setHaslo(String.valueOf(empRow.get("hasło")));
            if(group.getHaslo() == haslo)
                return 0;
        }

        query = "UPDATE grupy SET nazwa = '"+nazwa+"', hasło='"+haslo+"' where id_grupy="+id_grupy+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }
}