package co.bdio.Model;

import co.bdio.Entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {
    private JdbcTemplate jdbcTemplate;

    public UserRepo(){
        MyRepo myRepo = new MyRepo();
        jdbcTemplate = myRepo.getJdbcTemplate();
    }

    public User logowanie(String email, String password) {

        String query = "select * from użytkownicy where email = '"+email+"' AND hasło='"+password+"'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        User user = new User();

        for (Map empRow : rows) {
            user.setId_uzytkownika(Integer.parseInt(String.valueOf(empRow.get("id_użytkownika"))));
            user.setEmail(String.valueOf(empRow.get("email")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
            user.setHaslo(String.valueOf(empRow.get("hasło")));
            user.setImie(String.valueOf(empRow.get("imię")));
        }

        return user;
    }

    public int rejestracja(String nazwisko, String imie, String haslo, String email, int status)
    {
        String query = "INSERT INTO użytkownicy VALUES(nextval('\"użytkownicy_id_użytkownika_seq\"'), '"+imie+"','"+nazwisko+"', '"+haslo+"', '"+email+"');";

        if(jdbcTemplate.update(query) > 0)
        {
            query = "select * from użytkownicy ORDER BY id_użytkownika DESC LIMIT 1;";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
            User user = new User();

            for (Map empRow : rows) {
                user.setId_uzytkownika(Integer.parseInt(String.valueOf(empRow.get("id_użytkownika"))));
            }

            if(status == 0) {
                query = "INSERT INTO egzaminatorzy VALUES(nextval('egzaminatorzy_id_egzaminatora_seq'), "+user.getId_uzytkownika()+");";
            }
            else{
                query = "INSERT INTO studenci VALUES(nextval('studenci_id_studenta_seq'), "+user.getId_uzytkownika()+");";
            }

            if(jdbcTemplate.update(query) > 0)
            {
                return 1; //poprawnie dodano użytkownika
            }

            return 0;
        }

        return 0; //błąd w dodwaniu użytkownika
    }

    public User checkExaminerAndGetID(User user)
    {

        String query = "SELECT id_egzaminatora FROM egzaminatorzy WHERE użytkownicy_id_użytkownika="+user.getId_uzytkownika();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map empRow : rows) {
            user.setId_studenta_or_egzamintora(Integer.parseInt(String.valueOf(empRow.get("id_egzaminatora"))));
            user.setStatus(0);
        }

        if(user.getId_studenta_or_egzamintora() == 0)
        {
            query = "SELECT id_studenta FROM studenci WHERE użytkownicy_id_użytkownika="+user.getId_uzytkownika();

            List<Map<String, Object>> rows2 = jdbcTemplate.queryForList(query);

            for (Map empRow2 : rows2) {
                user.setId_studenta_or_egzamintora(Integer.parseInt(String.valueOf(empRow2.get("id_studenta"))));
                user.setStatus(1);
            }
        }

        return user;
    }

    public User getUserFromStudentID(int id_student)
    {
        String query = "select użytkownicy_id_użytkownika from studenci where id_studenta = "+id_student+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int user_id = 0;

        for (Map empRow : rows) {
            user_id = Integer.parseInt(String.valueOf(empRow.get("użytkownicy_id_użytkownika")));
        }

        query = "select * from użytkownicy where id_użytkownika = "+user_id+";";

        rows = jdbcTemplate.queryForList(query);
        User user = new User();

        for (Map empRow : rows) {
            user.setId_uzytkownika(user_id);
            user.setImie(String.valueOf(empRow.get("imię")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
        }
        return user;

    }

    public User getUserFromExaminerID(int id_egzaminatora)
    {
        String query = "select użytkownicy_id_użytkownika from egzaminatorzy where id_egzaminatora = "+id_egzaminatora+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int user_id = 0;

        for (Map empRow : rows) {
            user_id = Integer.parseInt(String.valueOf(empRow.get("użytkownicy_id_użytkownika")));
        }

        query = "select * from użytkownicy where id_użytkownika = "+user_id+";";

        rows = jdbcTemplate.queryForList(query);
        User user = new User();

        for (Map empRow : rows) {
            user.setId_uzytkownika(user_id);
            user.setImie(String.valueOf(empRow.get("imię")));
            user.setNazwisko(String.valueOf(empRow.get("nazwisko")));
        }
        return user;

    }
    public int getIDFromCredentials (String email, String password){
        int user_id = -1;
        String query = "select id_użytkownika from użytkownicy where email = '"+
                email+"' and hasło = '"+password+"';";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map empRow : rows) {
            user_id = Integer.parseInt(String.valueOf(empRow.get("id_użytkownika")));
        }

        if(user_id != -1)
            return user_id;
        else
            //error
            return -1;
    }

    public int getExaminerIDFromCredentials(int user_id){
        int examiner_id = -1;
        String query = "select id_egzaminatora from egzaminatorzy where użytkownicy_id_użytkownika = "+user_id+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map empRow : rows) {
            examiner_id = Integer.parseInt(String.valueOf(empRow.get("id_egzaminatora")));
        }

        if(examiner_id != -1)
            return examiner_id;
        else
            //error
            return -1;
    }
}
