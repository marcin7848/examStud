package co.bdio.Model;

import co.bdio.Entity.*;
import co.bdio.Model.TestRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExamRepo {

    private JdbcTemplate jdbcTemplate;

    public ExamRepo(){
        MyRepo myRepo = new MyRepo();
        jdbcTemplate = myRepo.getJdbcTemplate();
    }

    public List<Exam> getAllExams(int id_egzaminatora) {
        String query = "select * from egzaminy where egzaminatorzy_id_egzaminatora="+id_egzaminatora+"order by id_egzaminu;";
        List<Exam> examList = new ArrayList<Exam>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {
            Exam exam = new Exam();
            exam.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));
            exam.setNazwa(String.valueOf(empRow.get("nazwa")));
            exam.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            exam.setId_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setKod_aktywacyjny(String.valueOf(empRow.get("kod_aktywacyjny")));
            exam.setData_utworzenia(Timestamp.valueOf(String.valueOf(empRow.get("data_utworzenia"))));
            exam.setOstatnia_zmiana_kodu(Timestamp.valueOf(String.valueOf(empRow.get("ostatnia_zmiana_kodu"))));
            exam.setGrupy_id_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            exam.setGrupy_egzaminatorzy_id_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setEgzaminatorzy_id_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setAktywny(Integer.parseInt(String.valueOf(empRow.get("aktywny"))));
            exam.setCzas_rozpoczecia(Timestamp.valueOf(String.valueOf(empRow.get("czas_rozpoczęcia"))));

            examList.add(exam);
        }

        return examList;
    }

    public int addExam(String name, int id_group, int id_examiner)
    {
        String query = "select * from egzaminy where nazwa = '"+name+"'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Exam exam = new Exam();

        for (Map empRow : rows) {
            exam.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));
        }

        if(exam.getId_egzaminu() != 0)
            return 2; //istnieje juz taka grupa

        query = "INSERT INTO egzaminy VALUES(nextval('egzaminy_id_egzaminu_seq'), '"+name+"', "+id_group+", "+id_examiner+", '', now(), now(), "+id_group+", "+id_examiner+", "+id_examiner+", 0, now());";

        if(jdbcTemplate.update(query) > 0)
            return 1; //dodano

        return 0;
    }

    public Exam getExamDetails(int id_egzaminu){
        String query = "select * from egzaminy where id_egzaminu="+id_egzaminu+";";
        Exam exam = new Exam();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map empRow : rows){
            exam.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));
            exam.setNazwa(String.valueOf(empRow.get("nazwa")));
            exam.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            exam.setId_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setKod_aktywacyjny(String.valueOf(empRow.get("kod_aktywacyjny")));
            exam.setData_utworzenia(Timestamp.valueOf(String.valueOf(empRow.get("data_utworzenia"))));
            exam.setOstatnia_zmiana_kodu(Timestamp.valueOf(String.valueOf(empRow.get("ostatnia_zmiana_kodu"))));
            exam.setGrupy_id_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            exam.setGrupy_egzaminatorzy_id_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setEgzaminatorzy_id_egzaminatora(Integer.valueOf(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setAktywny(Integer.parseInt(String.valueOf(empRow.get("aktywny"))));
            exam.setCzas_rozpoczecia(Timestamp.valueOf(String.valueOf(empRow.get("czas_rozpoczęcia"))));
        }
        return exam;
    }

    public int deleteExam(int exam_id){
        String query = "delete from egzaminy where id_egzaminu = "+exam_id+"" +
                " and aktywny = 0;";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
            //może error
    }

    public int deleteTestFromExam(int exam_id, int test_id){
        String query = "delete from test_na_egzaminie where id_egzaminu = "+exam_id+
                " and id_testu = "+test_id+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
        //może error
    }

    public Test getBeingDeletedTestFromExam(int test_id) {
        String query = "select id_testu, nazwa from testy where id_testu = " + test_id + ";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Test test = new Test();


        for (Map empRow : rows) {
            test.setNazwa(String.valueOf(empRow.get("nazwa")));
            test.setId_testu(Integer.parseInt(String.valueOf(empRow.get("id_testu"))));
        }
        return test;
    }

    public int editExam(int exam_id, String new_name){
        String query = "update egzaminy set nazwa = '"+new_name+"' where id_egzaminu = "+exam_id+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public int editExamCode(int exam_id, String new_exam_code){
        //java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        //jest now() więc raczej nie powinno tego tu być

        String query = "update egzaminy set kod_aktywacyjny = '"+new_exam_code+"', " +
                "ostatnia_zmiana_kodu = now() where id_egzaminu = "+exam_id+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public List<Test> getAllTestsOnExam(int exam_id) {
        String query = "select id_testu, nazwa from testy where id_testu IN\n" +
                "(select id_testu from test_na_egzaminie where id_egzaminu = "+exam_id+")" +
                "order by nazwa ASC;";
        List<Test> testList = new ArrayList<Test>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {
            Test test = new Test();
            test.setNazwa(String.valueOf(empRow.get("nazwa")));
            test.setId_testu(Integer.parseInt(String.valueOf(empRow.get("id_testu"))));
            testList.add(test);
        }
        return testList;
    }

    public int addTestToExam(int exam_id, int test_id, int examiner_id){
        String query = "select * from test_na_egzaminie where id_egzaminu =" +
                ""+exam_id+" AND id_testu = "+test_id+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        ExamTest examTest = new ExamTest();

        for (Map empRow : rows) {
            examTest.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));
            examTest.setId_testu(Integer.parseInt(String.valueOf(empRow.get("id_testu"))));
            if(examTest.getId_egzaminu() == exam_id && examTest.getId_testu() == test_id)
                return 0;
        }

        query = "insert into test_na_egzaminie values(nextval('egzaminy_id_egzaminu_seq')," +
                ""+exam_id+", "+test_id+", "+test_id+", "+examiner_id+", "+exam_id+", 1, "+examiner_id+", "+examiner_id+");";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public String getExamGroupName(int exam_id){
        String query = "select nazwa from grupy where id_grupy IN" +
                "(select id_grupy from egzaminy where id_egzaminu = "+exam_id+");";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        String groupName = new String();

        for (Map empRow : rows) {
            groupName = String.valueOf(empRow.get("nazwa"));
        }

        return groupName;
    }

    public int changeExamGroup(int exam_id, int new_group_id){
        String query = "update egzaminy set id_grupy = "+new_group_id+" where id_egzaminu = "+exam_id+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public Exam getNameOFGroup(Exam exam) {

        GroupRepo groupRepo = new GroupRepo();
        Group group = groupRepo.getGroupDetails(exam.getId_grupy());
        exam.setNazwa_grupy(group.getNazwa());
        return exam;
    }

    public Exam getCountExamMembers(Exam exam){
        RunningExamRepo runningExamRepo = new RunningExamRepo();

        exam.setIlosc_uczestnikow(runningExamRepo.setCountExamMemeber(exam).getIlosc_uczestnikow());

        return exam;
    }

    public Exam getTimeToEndExam(Exam exam) {

        RunningExamRepo runningExamRepo = new RunningExamRepo();

        exam.setCzas_do_zakonczenia(runningExamRepo.setTimeToEndExam(exam).getCzas_do_zakonczenia());

        return exam;
    }

    public double getTimeToEndCode(Exam exam){
        Timestamp currentData = new Timestamp(System.currentTimeMillis());
        double timeToEndCode = exam.getCzas_rozpoczecia().getTime()+ 60 * 1000 * 2 - currentData.getTime();

        return timeToEndCode;
    }

    public String getTimeToEndCodeAsString(Exam exam){

        double timeToEndCode = getTimeToEndCode(exam);

        int min = (int) (timeToEndCode / 60000);
        timeToEndCode -= min*60000;
        int sec = (int) (timeToEndCode / 1000);

        return min+":"+sec;
    }
}