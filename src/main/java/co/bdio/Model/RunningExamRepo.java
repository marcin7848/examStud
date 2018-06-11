package co.bdio.Model;

import co.bdio.Entity.Exam;
import co.bdio.Entity.Question;
import co.bdio.Entity.RunningExam;
import co.bdio.Entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class RunningExamRepo {
    private JdbcTemplate jdbcTemplate;

    public RunningExamRepo(){
        MyRepo myRepo = new MyRepo();
        jdbcTemplate = myRepo.getJdbcTemplate();
    }


    public List<Exam> getAllRunningExam(int id_egzamintora) {
        String query = "select * from egzaminy where aktywny=1 AND id_egzaminatora="+id_egzamintora;
        List<Exam> examList = new ArrayList<Exam>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {
            Exam exam = new Exam();
            exam.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));
            exam.setNazwa(String.valueOf(empRow.get("nazwa")));
            exam.setId_grupy(Integer.parseInt(String.valueOf(empRow.get("id_grupy"))));
            exam.setId_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("id_egzaminatora"))));
            exam.setAktywny(1);
            exam.setCzas_rozpoczecia(Timestamp.valueOf(String.valueOf(empRow.get("czas_rozpoczęcia"))));

            ExamRepo examRepo = new ExamRepo();

            exam = examRepo.getNameOFGroup(exam);
            exam = examRepo.getCountExamMembers(exam);
            exam = examRepo.getTimeToEndExam(exam);

            examList.add(exam);
        }
        return examList;
    }

    public Exam setCountExamMemeber(Exam exam){
        String query = "select count(*) AS ilosc from trwajace_egzaminy WHERE egzaminy_id_egzaminu="+exam.getId_egzaminu();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map empRow : rows) {
            exam.setIlosc_uczestnikow(Integer.parseInt(String.valueOf(empRow.get("ilosc"))));
        }

        return exam;
    }

    public Exam setTimeToEndExam(Exam exam){

        String query = "SELECT SUM(p.czas) AS ilosc FROM pytania p\n" +
                "JOIN testy t ON p.testy_id_testu = t.id_testu\n" +
                "JOIN test_na_egzaminie tne ON tne.id_testu = t.id_testu\n" +
                "JOIN egzaminy e ON tne.id_egzaminu = e.id_egzaminu AND e.id_egzaminu="+exam.getId_egzaminu()+" GROUP BY p.testy_id_testu;";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        int ilosc = 0;

        List<Integer> time_for_test = new ArrayList<Integer>();
        for (Map empRow : rows) {
            ilosc = Integer.parseInt(String.valueOf(empRow.get("ilosc")));
            time_for_test.add(ilosc);
        }

        if(time_for_test.isEmpty()) {
            exam.setCzas_do_zakonczenia(0); //nie znalazło żadnych testów dla tego egzaminu
        }else{
            Timestamp newDate = new Timestamp(0);
            newDate.setTime(exam.getCzas_rozpoczecia().getTime() + 60 * 1000 * 2 + 60 * 1000 * Collections.max(time_for_test));

            Timestamp currentData = new Timestamp(System.currentTimeMillis());

            int timeToEnd = (int) (newDate.getTime() - currentData.getTime()) / (60 * 1000);

            exam.setCzas_do_zakonczenia(timeToEnd+1);
        }

        return exam;
    }


    public int AddRunningExam(int exam_id){
        String query = "update egzaminy set aktywny=1, czas_rozpoczęcia=now() where id_egzaminu = "+exam_id;

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }

    public RunningExam getTimeToEndTest(RunningExam runningExam){

        Exam exam = new Exam();
        exam.setId_egzaminu(runningExam.getEgzaminy_id_egzaminu());

        ExamRepo examRepo = new ExamRepo();
        exam = examRepo.getExamDetails(exam.getId_egzaminu());

        String query = "SELECT SUM(czas) AS ilosc FROM pytania WHERE testy_id_testu="+runningExam.getTesty_id_testu();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        int ilosc = 0;

        for (Map empRow : rows) {
            ilosc = Integer.parseInt(String.valueOf(empRow.get("ilosc")));
        }

        Timestamp newDate = new Timestamp(0);
        newDate.setTime(exam.getCzas_rozpoczecia().getTime() + 60 * 1000 * 2 + 60 * 1000 * ilosc);

        Timestamp currentData = new Timestamp(System.currentTimeMillis());

        double timeToEnd = newDate.getTime() - currentData.getTime();

        runningExam.setCzas_do_zakonczenia_testu(timeToEnd);

        int min = (int) (timeToEnd / 60000);
        timeToEnd -= min*60000;
        int sec = (int) (timeToEnd / 1000);

        runningExam.setCzas_do_zakonczenia_testu_min(min);
        runningExam.setCzas_do_zakonczenia_testu_sec(sec);

        return runningExam;
    }

    public List<RunningExam> getAllRunningTests(int id_egzaminu) {
        String query = "select * from trwajace_egzaminy where egzaminy_id_egzaminu="+id_egzaminu;

        List<RunningExam> runningExamsList = new ArrayList<RunningExam>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {
            RunningExam runningExam = new RunningExam();
            runningExam.setId_trwajacego_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_trwajacego_egzaminu"))));
            runningExam.setEgzaminy_id_egzaminu(Integer.parseInt(String.valueOf(empRow.get("egzaminy_id_egzaminu"))));
            runningExam.setEgzaminy_grupy_id_grupy(Integer.parseInt(String.valueOf(empRow.get("egzaminy_grupy_id_grupy"))));
            runningExam.setEgzaminy_grupy_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("egzaminy_grupy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setEgzaminy_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("egzaminy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setStudenci_id_studenta(Integer.parseInt(String.valueOf(empRow.get("studenci_id_studenta"))));
            runningExam.setTesty_id_testu(Integer.parseInt(String.valueOf(empRow.get("testy_id_testu"))));
            runningExam.setTesty_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("testy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setEtap_testu(Integer.parseInt(String.valueOf(empRow.get("etap_testu"))));

            runningExam = getTimeToEndTest(runningExam);


            runningExamsList.add(runningExam);
        }

        return runningExamsList;
    }


    public int joinToExam(int student_id, String password)
    {
        String query = "select * from egzaminy where aktywny=1 AND kod_aktywacyjny = '"+password+"'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Exam exam = new Exam();

        for (Map empRow : rows) {
            exam.setId_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_egzaminu"))));

            ExamRepo examRepo = new ExamRepo();
            exam = examRepo.getExamDetails(exam.getId_egzaminu());
        }

        if(exam.getId_egzaminu() == 0)
            return 1; //nie istnieje taki egzamin lub egzamin nie został uruchomiony


        query = "SELECT * FROM trwajace_egzaminy WHERE studenci_id_studenta = "+student_id;

        List<Map<String, Object>> rows2 = jdbcTemplate.queryForList(query);

        RunningExam runningExam = new RunningExam();
        for (Map empRow : rows2) {
            runningExam.setId_trwajacego_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_trwajacego_egzaminu"))));
        }

        if(runningExam.getId_trwajacego_egzaminu() > 0)
            return 2; //bierzesz udzial juz w tym egzaminie

        ExamRepo examRepo = new ExamRepo();

        if(examRepo.getTimeToEndCode(exam) < 0)
            return 3; //czas na dołączenie upłynął

        query = "select * from test_na_egzaminie where id_egzaminu ="+exam.getId_egzaminu();

        rows = jdbcTemplate.queryForList(query);

        List<Integer> listOfIdTests = new ArrayList<Integer>();

        for (Map empRow : rows) {
            int test_id = Integer.parseInt(String.valueOf(empRow.get("id_testu")));

            listOfIdTests.add(test_id);
        }

        int index = new Random().nextInt(listOfIdTests.size());
        int random_test_id = listOfIdTests.get(index);

        query = "INSERT INTO trwajace_egzaminy VALUES(nextval('trwajace_egzaminy_id_trwajacego_egzaminu_seq'), "+exam.getId_egzaminu()+","+exam.getGrupy_id_grupy()+", "+exam.getId_egzaminatora()+", "+exam.getId_egzaminatora()+", " +student_id+", "+random_test_id+", " +exam.getId_egzaminatora()+", 0);";

        if(jdbcTemplate.update(query) > 0)
            return 4; //dodano poprawnie dolaczono do trawajacego egzaminu

        return 0;
    }


    public RunningExam getRunningTestStudent(int id_studenta) {
        String query = "select * from trwajace_egzaminy where studenci_id_studenta="+id_studenta;

        RunningExam runningExam = new RunningExam();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map empRow : rows) {

            runningExam.setId_trwajacego_egzaminu(Integer.parseInt(String.valueOf(empRow.get("id_trwajacego_egzaminu"))));
            runningExam.setEgzaminy_id_egzaminu(Integer.parseInt(String.valueOf(empRow.get("egzaminy_id_egzaminu"))));
            runningExam.setEgzaminy_grupy_id_grupy(Integer.parseInt(String.valueOf(empRow.get("egzaminy_grupy_id_grupy"))));
            runningExam.setEgzaminy_grupy_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("egzaminy_grupy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setEgzaminy_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("egzaminy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setStudenci_id_studenta(Integer.parseInt(String.valueOf(empRow.get("studenci_id_studenta"))));
            runningExam.setTesty_id_testu(Integer.parseInt(String.valueOf(empRow.get("testy_id_testu"))));
            runningExam.setTesty_egzaminatorzy_id_egzaminatora(Integer.parseInt(String.valueOf(empRow.get("testy_egzaminatorzy_id_egzaminatora"))));
            runningExam.setEtap_testu(Integer.parseInt(String.valueOf(empRow.get("etap_testu"))));

            runningExam = getTimeToEndTest(runningExam);
        }

        return runningExam;
    }

    public int saveAnswer(int id_egzaminu, int id_testu, int id_pytania, Timestamp czas_rozpoczecia_pytania, char czy_ocenione, String tresc, int ilosc_przyznanych_pkt, int id_egzaminatora, int id_studenta, int id_grupy, int id_trwajacego_egzaminu){

        String query = "INSERT INTO odpowiedzi_na_zadania VALUES(nextval('odpowiedzi_na_zadania_id_seq'), " +
                ""+id_egzaminu+", "+id_testu+", "+id_pytania+"," +
                " '"+czy_ocenione+"', '"+tresc+"' ,"+ilosc_przyznanych_pkt+", "+id_pytania+"," +
                " "+id_testu+", "+id_egzaminatora+", "+id_egzaminu+", "+id_testu+"," +
                " "+id_egzaminatora+", "+id_studenta+", "+id_grupy+", "+id_egzaminatora+", " +
                ""+id_egzaminatora+", now(), '"+czas_rozpoczecia_pytania+"', "+id_trwajacego_egzaminu+");";

        jdbcTemplate.update(query);

        query = "select max(id) AS max_id from odpowiedzi_na_zadania where id_egzaminu="+id_egzaminu+" AND id_testu="+id_testu+" AND studenci_id_studenta="+id_studenta+";";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        int id_odpowiedzi_na_zadania = 0;

        for (Map empRow : rows) {
            id_odpowiedzi_na_zadania = Integer.parseInt(String.valueOf(empRow.get("max_id")));
        }

        return id_odpowiedzi_na_zadania;
    }

    public int saveAnswerUdzieloneOdp(int id_pytania, int id_odpowiedzi_na_zadanie, int id_odpowiedzi, int id_testu, int id_egzaminatora, int id_egzaminu, int id_studenta, int id_grupy){

        String query = "INSERT INTO udzielone_odpowiedzi VALUES(nextval('udzielone_odpowiedzi_id_seq'), "+id_pytania+", " +
                ""+id_odpowiedzi_na_zadanie+", "+id_odpowiedzi+", "+id_pytania+", "+id_testu+", " +
                ""+id_egzaminatora+", "+id_odpowiedzi_na_zadanie+", "+id_pytania+", "+id_testu+", " +
                ""+id_egzaminatora+", "+id_pytania+","+id_testu+", "+id_egzaminatora+"," +
                ""+id_egzaminu+", "+id_testu+", "+id_egzaminatora+", "+id_studenta+"," +
                ""+id_grupy+", "+id_egzaminatora+","+id_egzaminatora+");";

        if(jdbcTemplate.update(query) > 0)
            return 1; //dodano

        return 0;
    }

    public int incrementEtapTestu(RunningExam runningExam){
        String query = "UPDATE trwajace_egzaminy SET etap_testu = etap_testu + 1 where id_trwajacego_egzaminu="+runningExam.getId_trwajacego_egzaminu()+";";

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }


    public int finishRunningExamByStudent(int id_trwajacego_egzaminu){
        String query = "DELETE FROM trwajace_egzaminy WHERE id_trwajacego_egzaminu ="+id_trwajacego_egzaminu;

        if(jdbcTemplate.update(query) == 1)
            return 1;
        else
            return 0;
    }


    public int checkRunningExamByStudent(int student_id){
        String query = "select * from trwajace_egzaminy where studenci_id_studenta="+student_id;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int exist = 0;

        for (Map empRow : rows) {
            exist = 1;
        }

        if(exist == 0)
            return 0; //student nie bierze udzialu w egzaminie

        return 1; //student juz bierze udzial w egzaminie
    }

    public double getTimeToEndQuestion(int student_id, int examiner_id, int id_trwajacego_egzaminu){

        RunningExam runningExam = getRunningTestStudent(student_id);
        ExamRepo examRepo = new ExamRepo();
        Exam exam = examRepo.getExamDetails(runningExam.getEgzaminy_id_egzaminu());

        String query = "select * from pytania where testy_id_testu="+runningExam.getTesty_id_testu()+" order by id_pytania";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int etap = 0;
        int time = 0;
        int time_for_this_question = 0;

        for (Map empRow : rows) {

            if(runningExam.getEtap_testu() > etap) {
                time += Integer.parseInt(String.valueOf(empRow.get("czas")));
            }else{
                time_for_this_question = Integer.parseInt(String.valueOf(empRow.get("czas")));
                break;
            }
            etap++;
        }


        query = "select * from odpowiedzi_na_zadania where studenci_id_studenta="+student_id+" AND egzaminy_egzaminatorzy_id_egzaminatora="+examiner_id+" AND id_trwajacego_egzaminu="+id_trwajacego_egzaminu;

        rows = jdbcTemplate.queryForList(query);

        double timeOdp = 0;


        for (Map empRow : rows) {

            String query2 = "select * from pytania where id_pytania="+Integer.parseInt(String.valueOf(empRow.get("id_pytania")));

            List<Map<String, Object>> rows2 = jdbcTemplate.queryForList(query2);

            int czas_na_odp = 0;
            for (Map empRow2 : rows2) {
                czas_na_odp = Integer.parseInt(String.valueOf(empRow2.get("czas")));
            }


            double czas_odp = Timestamp.valueOf(String.valueOf(empRow.get("czas_odpowiedzi"))).getTime();
            double czas_rozp = Timestamp.valueOf(String.valueOf(empRow.get("czas_rozpoczęcia_pytania"))).getTime();

            timeOdp += czas_odp - czas_rozp - czas_na_odp*60*1000;

        }



        Timestamp newDate = new Timestamp(0);
        newDate.setTime(exam.getCzas_rozpoczecia().getTime()
                + 60 * 1000 * 2
                + 60 * 1000 * time
                + 60 * 1000 * time_for_this_question
                - (int)timeOdp);



        Timestamp currentData = new Timestamp(System.currentTimeMillis());
        double timeToEndQuestion = newDate.getTime() - currentData.getTime();

        return timeToEndQuestion;
    }


    public String getTimeToEndQuestionAsString(int student_id, int examiner_id, int id_trwajacego_egzaminu){

        double timeToEndQuestion = getTimeToEndQuestion(student_id, examiner_id, id_trwajacego_egzaminu);
        int min = (int) (timeToEndQuestion / 60000);
        timeToEndQuestion -= min*60000;
        int sec = (int) (timeToEndQuestion / 1000);

        return min+":"+sec;
    }


    public int endRunningExam(int id_egzaminu){

        String query = "select * from trwajace_egzaminy where egzaminy_id_egzaminu="+id_egzaminu;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        int exist = 0;
        for (Map empRow : rows) {
            exist = Integer.parseInt(String.valueOf(empRow.get("id_trwajacego_egzaminu")));
        }

        if(exist == 0) {
            query = "UPDATE egzaminy SET aktywny=0 WHERE id_egzaminu=" + id_egzaminu;

            if (jdbcTemplate.update(query) == 1)
                return 1;
            else
                return 0;
        }
        return -1;
    }


}
