package co.bdio.Controller;

import co.bdio.Entity.*;
import co.bdio.Model.*;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/runningExams")
public class RunningExamController {
    private ExamRepo examRepo;
    private GroupRepo groupRepo;
    private RunningExamRepo runningExamRepo;
    private UserRepo userRepo;
    private TestRepository testRepository;

    @Autowired
    RunningExamController(ExamRepo examRepo, GroupRepo groupRepo, RunningExamRepo runningExamRepo, UserRepo userRepo, TestRepository testRepository){
        this.examRepo=examRepo;
        this.groupRepo=groupRepo;
        this.runningExamRepo=runningExamRepo;
        this.userRepo=userRepo;
        this.testRepository=testRepository;
    }

    @GetMapping
    public String listOfRunningExams(Model model) {

        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));
        model.addAttribute("showRunningExams", runningExamRepo.getAllRunningExam(1));
        model.addAttribute("showExams", examRepo.getAllExams(1));

        return "showRunningExams";
    }

    @PostMapping(value = "/addRunningExam")
    public String addRunningExam (Model model, int exam_choice) {
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));
        model.addAttribute("showRunningExams", runningExamRepo.getAllRunningExam(1));
        model.addAttribute("showExams", examRepo.getAllExams(1));


        Exam exam = examRepo.getExamDetails(exam_choice);

        if (exam.getAktywny() == 1) {
            model.addAttribute("error_text", "Ten egzamin już jest uruchomiony!");
            return "showRunningExams";
        }

        if (exam.getKod_aktywacyjny().isEmpty()) {
            model.addAttribute("error_text", "Egzamin nie ma przypisanego kodu!");
            return "showRunningExams";
        }

        if(examRepo.getAllTestsOnExam(exam.getId_egzaminu()).isEmpty()) {
            model.addAttribute("error_text", "Egzamin musi mieć co najmniej 1 test!");
            return "showRunningExams";
        }

        int addRunningExamResult = runningExamRepo.AddRunningExam(exam.getId_egzaminu());

        model.addAttribute("showRunningExams", runningExamRepo.getAllRunningExam(1));

        if(addRunningExamResult == 1)
            return "redirect:/runningExams/details/"+exam.getId_egzaminu();

        model.addAttribute("error_text", "Błąd podczas przetwarzania bazy danych!");
        return "showRunningExams";
    }


    @GetMapping(value = "/details/{exam_id}")
    public String showRunningExamDetails (Model model, @PathVariable int exam_id){

        Exam exam = examRepo.getExamDetails(exam_id);

        if(exam.getAktywny() == 0) {
            model.addAttribute("error", "Ten egzamin nie został uruchomiony!");
            return "showRunningExamError";
        }
        model.addAttribute("exam", exam);
        model.addAttribute("group", groupRepo.getGroupDetails(exam.getId_grupy()));
        model.addAttribute("timeToEndCode", examRepo.getTimeToEndCodeAsString(exam));

        if(examRepo.getTimeToEndCode(exam) > 0){
            return "showRunningExamBeforeExaminer";
        }

        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));

        exam = examRepo.getTimeToEndExam(exam);
        model.addAttribute("timeToEndExam", exam.getCzas_do_zakonczenia());

        model.addAttribute("showsAllRunningTests", runningExamRepo.getAllRunningTests(exam.getId_egzaminu()));

        return "showRunningExamDetails";
    }


    @GetMapping(value = "/joinToExam")
    public String joinToRunningExam(Model model){

        int resultCheckStudentRunningExam = runningExamRepo.checkRunningExamByStudent(1);

        if(resultCheckStudentRunningExam == 1)
            return "redirect:/runningExams/doRunningExam";


        return "joinToRunningExam";
    }

    @PostMapping(value = "/joinToExam")
    public String joinToRunningExam2(Model model, String exam_password){
        if(exam_password.isEmpty())
        {
            model.addAttribute("error_text", "Wpisz jakieś hasło!");
            return "joinToRunningExam";
        }

        int resultJoin = runningExamRepo.joinToExam(1, exam_password);

        if(resultJoin == 1) {
            model.addAttribute("error_text", "Nie istnieje egzamin z takim hasłem lub egzamin nie został aktywowany!");
            return "joinToRunningExam";
        }
        if(resultJoin == 2) {
            model.addAttribute("error_text", "Bierzesz już udział w tym egzaminie");
            return "joinToRunningExam";
        }
        if(resultJoin == 3) {
            model.addAttribute("error_text", "Czas na dołączenie już upłynął");
            return "joinToRunningExam";
        }
        if(resultJoin == 4)
            return "redirect:/runningExams/doRunningExam";

        model.addAttribute("error_text", "Błąd w przetwarzaniu w bazie danych!");
        return "joinToRunningExam";
    }


    @GetMapping(value = "/doRunningExam")
    public String doRunningExam(Model model){

        RunningExam runningExam = runningExamRepo.getRunningTestStudent(1);
        model.addAttribute("studentId", 1);
        model.addAttribute("examinerId", 1);

        if(runningExam.getId_trwajacego_egzaminu() == 0)
        {
            model.addAttribute("error", "Nie dołączyłeś do żadnego egzaminu!");
            return "showRunningExamError";
        }
        Exam exam = examRepo.getExamDetails(runningExam.getEgzaminy_id_egzaminu());

        model.addAttribute("exam", exam);
        model.addAttribute("group", groupRepo.getGroupDetails(exam.getId_grupy()));
        model.addAttribute("timeToEndCode", examRepo.getTimeToEndCodeAsString(exam));
        Timestamp currentData = new Timestamp(System.currentTimeMillis());
        model.addAttribute("czas_rozpoczecia_pytania", currentData);

        if(examRepo.getTimeToEndCode(exam) > 0){
            return "doRunningExamBefore";
        }

        model.addAttribute("runningExam", runningExam);
        model.addAttribute("user", userRepo.getUserFromStudentID(1));
        model.addAttribute("timeToEndQuestion", runningExamRepo.getTimeToEndQuestionAsString(1, 1, runningExam.getId_trwajacego_egzaminu()));


        User user = userRepo.getUserFromExaminerID(1);

        List<Question> questions = testRepository.getById(runningExam.getTesty_id_testu(),user.getId_uzytkownika());

        if(questions.size() > runningExam.getEtap_testu()) {
            Question question = questions.get(runningExam.getEtap_testu());

            List<Answer> answers = testRepository.getOdpToAnswer(question.getId_pytania());

            model.addAttribute("question", question);
            model.addAttribute("answers", answers);

            if (question.getTyp() == 'o')
                return "doRunningExamOpen";

            return "doRunningExam";
        }
        else
        {
            runningExamRepo.finishRunningExamByStudent(runningExam.getId_trwajacego_egzaminu());
            runningExamRepo.endRunningExam(runningExam.getEgzaminy_id_egzaminu());
            return "doRunningExamEnd";
        }
    }


    @PostMapping(value = "/saveAnswer")
    public String saveAnswer(Model model, String answer, int typ_pytania, String czas_rozpoczecia_pytania){

        Timestamp czas_rozpoczecia_pytania2 = Timestamp.valueOf(String.valueOf(czas_rozpoczecia_pytania));

        RunningExam runningExam = runningExamRepo.getRunningTestStudent(1);
        Exam exam = examRepo.getExamDetails(runningExam.getEgzaminy_id_egzaminu());

        User user = userRepo.getUserFromExaminerID(1);
        List<Question> questions = testRepository.getById(runningExam.getTesty_id_testu(), user.getId_uzytkownika());
        Question question = questions.get(runningExam.getEtap_testu());

        if(typ_pytania == 1) {
            int id_odpowiedzi_na_zadania = runningExamRepo.saveAnswer(exam.getId_egzaminu(), runningExam.getTesty_id_testu(),
                    question.getId_pytania(), czas_rozpoczecia_pytania2, '0', "-",
                    0, exam.getId_egzaminatora(),
                    runningExam.getStudenci_id_studenta(), runningExam.getEgzaminy_grupy_id_grupy(), runningExam.getId_trwajacego_egzaminu());

            if(answer != null) {
                String[] answers = answer.split(",");
                for (int i = 0; i < answers.length; i++) {

                    List<Answer> listaAns = testRepository.getodpByIdTestu(runningExam.getTesty_id_testu(), user.getId_uzytkownika());

                    int odpowiedz_id = 0;
                    for (Answer ans : listaAns) {
                        if (ans.getId_odpowiedzi() == Integer.parseInt(String.valueOf(answers[i]))) {
                            odpowiedz_id = ans.getId_odpowiedzi();
                            break;
                        }
                    }

                    runningExamRepo.saveAnswerUdzieloneOdp(question.getId_pytania(),
                            id_odpowiedzi_na_zadania, odpowiedz_id, runningExam.getTesty_id_testu(),
                            runningExam.getEgzaminy_egzaminatorzy_id_egzaminatora(), runningExam.getEgzaminy_id_egzaminu(),
                            runningExam.getStudenci_id_studenta(), runningExam.getEgzaminy_grupy_id_grupy());
                }
            }
        }
        else{
            if(answer == null){
                answer = "-";
            }
            runningExamRepo.saveAnswer(exam.getId_egzaminu(), runningExam.getTesty_id_testu(),
                    question.getId_pytania(), czas_rozpoczecia_pytania2, '0', answer,
                    0, exam.getId_egzaminatora(),
                    runningExam.getStudenci_id_studenta(), runningExam.getEgzaminy_grupy_id_grupy(), runningExam.getId_trwajacego_egzaminu());
        }

        runningExamRepo.incrementEtapTestu(runningExam);


        return "redirect:/runningExams/doRunningExam";
    }




    ////////API DLA ANDROIDA I JAVASCRIPTU////////

    @PostMapping("/api/getTimeToEndCode")
    @ResponseBody
    public String APIGetTimeToEndCode(int exam_id) throws JSONException {

        Exam exam = examRepo.getExamDetails(exam_id);

        String timeToEndCodeString = examRepo.getTimeToEndCodeAsString(exam);
        double timeToEndCode = examRepo.getTimeToEndCode(exam);

        Map m = new HashMap();

        if(timeToEndCodeString.isEmpty())
        {
            m.put("success","false");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        if(timeToEndCode < 0)
        {
            timeToEndCodeString = "END";
        }

        m.put("success","true");
        m.put("timeToEndCode", timeToEndCodeString);

        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/getTimeToEndQuestion")
    @ResponseBody
    public String APIGetTimeToEndQuestion(int student_id, int examiner_id, int id_trwajacego_egzaminu) throws JSONException {

        double time = runningExamRepo.getTimeToEndQuestion(student_id, examiner_id, id_trwajacego_egzaminu);
        String timeString = runningExamRepo.getTimeToEndQuestionAsString(student_id, examiner_id, id_trwajacego_egzaminu);


        if(time < 0)
        {
            timeString = "END";
        }

        Map m = new HashMap();
        m.put("success","true");
        m.put("timeToEndQuestion", timeString);

        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }



}
