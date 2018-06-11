package co.bdio.Controller;

import co.bdio.Entity.Exam;
import co.bdio.Entity.Test;
import co.bdio.Model.TestRepository;
import co.bdio.Model.ExamRepo;
import co.bdio.Model.GroupRepo;
import co.bdio.Model.MyRepo;

import org.json.JSONException;
import net.sf.json.JSONObject;

import co.bdio.Model.UserRepo;
import co.bdio.Model.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exams")
public class ExamController {

    private ExamRepo examRepo;
    private GroupRepo groupRepo;
    private TestRepository testRepo;
    private UserRepo userRepo;

    @Autowired
    ExamController(ExamRepo examRepo, GroupRepo groupRepo, UserRepo userRepo, TestRepository testRepo){
        this.examRepo=examRepo;
        this.groupRepo=groupRepo;
        this.userRepo=userRepo;
        this.testRepo=testRepo;
    }

    @GetMapping
    public String listOfExams(Model model) {

        model.addAttribute("showExams", examRepo.getAllExams(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));

        return "showExams";
    }

    @GetMapping(value = "/addExam")
    public String addExam(Model model){

        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));
        model.addAttribute("showGroups", groupRepo.getAllGroups(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));

        return "addExam";
    }

    @PostMapping(value = "/addExam")
    public String addGroup2(Model model, String exam_name, int group_choice){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(1));
        model.addAttribute("showGroups", groupRepo.getAllGroups(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));

        if(exam_name.isEmpty() || group_choice == 0)
        {
            model.addAttribute("error_text", "Uzupełnij nazwę oraz wybierz grupę dla egzaminu!");
            return "addExam";
        }

        int addExamResult = examRepo.addExam(exam_name, group_choice, userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));

        if(addExamResult == 0 )
        {
            model.addAttribute("error_text", "Błąd w przetwarzaniu dodawania egzaminu!");
            return "addExam";
        }
        if(addExamResult == 2)
        {
            model.addAttribute("error_text", "Taki egzamin już istnieje. Nadaj inną nazwę!");
            return "addExam";
        }

        return "redirect:/exams/";
    }

    @PostMapping(value = "/details")
    public String showExamDetails(String exam_choice){
        return "redirect:/exams/details/"+exam_choice;
    }

    @GetMapping(value = "/details/{exam_id}")
    public String showCurrentExamDetails (Model model, @PathVariable int exam_id){
        model.addAttribute("showTests", examRepo.getAllTestsOnExam(exam_id));
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
        model.addAttribute("addTests", testRepo.getAllTestNames());
        model.addAttribute("groupName", examRepo.getExamGroupName(exam_id));

        return "showExamDetails";
    }

    @PostMapping(value = "/details/{exam_id}/changeGroup")
    public String changeExamGroup(Model model, @PathVariable int exam_id){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
        model.addAttribute("showGroups", groupRepo.getAllGroups(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));

        return "changeGroup";
    }

    @PostMapping(value = "/details/{exam_id}/changingGroup")
    public String changingExamGroup(Model model, @PathVariable int exam_id, int group_choice){
        if(examRepo.changeExamGroup(exam_id, group_choice) == 1)
            return "redirect:/exams/details/{exam_id}";
        else
        {
            model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
            model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
            model.addAttribute("showGroups", groupRepo.getAllGroups(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));
            return "redirect:/exams/details/{exam_id}";
        }
    }

    @PostMapping(value = "/details/{exam_id}/addTestApproved")
    public String addTestToExam (@PathVariable int exam_id, int test_choice){
        if(examRepo.addTestToExam(exam_id, test_choice, 1) == 1){       //ID NA SZTYWNO/////////////////////////
            return "redirect:/exams/details/{exam_id}";
        }
        else{
            //errors
            return "redirect:/exams/details/{exam_id}";
        }
    }

    @PostMapping(value = "details/{exam_id}/deleteExam")
    public String deleteExam (Model model, @PathVariable int exam_id){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
        return "deleteExamApproval";
    }

    @PostMapping(value = "details/{exam_id}/deleting_exam")
    public String deleteExamApproved(Model model, @PathVariable int exam_id){
        if(examRepo.deleteExam(exam_id) == 1)
            return "redirect:/exams";
        else
        {
            model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
            model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
            return "redirect:/exams/details/{exam_id}";
        }
    }

    @PostMapping(value = "details/{exam_id}/deleteTest")
    public String deleteTest(Model model, @PathVariable int exam_id, int exam_test_id){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("being_deleted_test", examRepo.getBeingDeletedTestFromExam(exam_test_id));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));
        return "deleteTestApproval";
    }

    @PostMapping(value = "details/{exam_id}/deleting_test")
    public String deleteTestApproved(Model model, @PathVariable int exam_id, int exam_test_id){
        if(examRepo.deleteTestFromExam(exam_id, exam_test_id) == 1)
            return "redirect:/exams/details/{exam_id}";
        else
        {
            model.addAttribute("showTests", examRepo.getAllTestsOnExam(1));
            model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
            model.addAttribute("showExam", examRepo.getExamDetails(exam_id));

            return "redirect:/exams/details/{exam_id}";
        }
    }

    @PostMapping(value = "details/{exam_id}/edit")
    public String editExam(Model model, @PathVariable int exam_id){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));

        return "editExam";
    }


    @PostMapping(value = "details/{exam_id}/editCode")
    public String editExamCode(Model model, @PathVariable int exam_id){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showExam", examRepo.getExamDetails(exam_id));

        return "editExamCode";
    }

    @PostMapping(value = "details/{exam_id}/editCode/save")
    public String updateExamCode(Model model, @PathVariable int exam_id, String exam_activation_code){
        if(exam_activation_code.isEmpty())
        {
            model.addAttribute("showTests", examRepo.getAllTestsOnExam(1));
            model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
            model.addAttribute("showExam", examRepo.getExamDetails(exam_id));

            return "showExamDetails";
        }
        if(examRepo.editExamCode(exam_id, exam_activation_code) == 1)
            return "redirect:/exams/details/{exam_id}";
        //else
        return null;
        //errors
    }








    ////////API DLA ANDROIDA I JAVASCRIPTU////////
    @PostMapping("/api/getExams")
    @ResponseBody
    public String APIgetExams(int id_egzamintora) throws JSONException {

        Map m = new HashMap();

        List<Exam> exams = examRepo.getAllExams(id_egzamintora);

        m.put("success","true");
        m.put("exams", exams);
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/addExam")
    @ResponseBody
    public String APIaddExam(String name, int id_group, int id_examiner) throws JSONException {
        int result = examRepo.addExam(name, id_group,id_examiner);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/deleteExam")
    @ResponseBody
    public String APIdeleteExam(int exam_id) throws JSONException {
        int result = examRepo.deleteExam(exam_id);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/editExam")
    @ResponseBody
    public String APIeditExam(int exam_id, String new_name) throws JSONException {

        int result = examRepo.editExam(exam_id, new_name);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/editExamCode")
    @ResponseBody
    public String APIeditExamCode(int exam_id, String new_exam_code) throws JSONException {

        int result = examRepo.editExamCode(exam_id, new_exam_code);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/addTestToExam")
    @ResponseBody
    public String APIaddTestToExam(int exam_id, int test_id, int examiner_id) throws JSONException {

        int result = examRepo.addTestToExam(exam_id, test_id, examiner_id);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/deleteTestFromExam")
    @ResponseBody
    public String APIdeleteTestFromExam(int exam_id, int test_id) throws JSONException {

        int result = examRepo.deleteTestFromExam(exam_id, test_id);

        Map m = new HashMap();
        if(result == 0)
        {
            m.put("success","false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");
        m.put("error", "0");
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }


    @PostMapping("/api/getExamDetails")
    @ResponseBody
    public String APIgetExamsDetails(int id_egzaminu) throws JSONException {

        Map m = new HashMap();

        Exam exam = examRepo.getExamDetails(id_egzaminu);

        m.put("success","true");
        m.put("exam", exam);
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/getAllTestsOnExam")
    @ResponseBody
    public String APIgetAllTestsOnExam(int exam_id) throws JSONException {

        Map m = new HashMap();

        List<Test> testList = examRepo.getAllTestsOnExam(exam_id);

        m.put("success","true");
        m.put("tests", testList);
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }
}