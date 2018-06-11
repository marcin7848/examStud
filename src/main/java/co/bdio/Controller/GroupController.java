package co.bdio.Controller;

import co.bdio.Entity.Group;
import co.bdio.Entity.Student;
import co.bdio.Entity.User;
import co.bdio.Model.MyRepo;
import co.bdio.Model.UserRepo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import co.bdio.Model.GroupRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.View;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupRepo groupRepo;
    private UserRepo userRepo;

    @Autowired
    GroupController(GroupRepo groupRepo, UserRepo userRepo){
        this.groupRepo=groupRepo;
        this.userRepo=userRepo;
    }

    @GetMapping
    public String listOfGroups(Model model) {
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        model.addAttribute("showGroups", groupRepo.getAllGroups(userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian"))));

        return "showGroups";
    }

    @PostMapping(value = "/details")
    public String showGroupDetails(String group_choice){
        return "redirect:/groups/details/"+group_choice;
    }

    @GetMapping(value = "/details/{id_grupy}")
    public String showCurrentGroupDetails(Model model, @PathVariable int id_grupy) {
        model.addAttribute("showExaminer", groupRepo.getExaminer(id_grupy));
        model.addAttribute("showMembers", groupRepo.getAllGroupMembers(id_grupy));
        model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(id_grupy));

        return "showGroupDetails";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(value = "details/{id_group}/deleteGroup")
    public String deleteGroup(Model model, @PathVariable int id_group) {
        model.addAttribute("showExaminer", groupRepo.getExaminer(id_group));
        model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(id_group));
        model.addAttribute("groupID", id_group);

        return "deleteGroupApproval";
    }

    @PostMapping(value="details/{group_id}/deleting_group")
    public String deleteGroupApproved(Model model, @PathVariable int group_id){
        if(groupRepo.deleteGroup(group_id) == 1)
            return "redirect:/groups";
        else {
            model.addAttribute("showExaminer", groupRepo.getExaminer(group_id));
            model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(group_id));
            model.addAttribute("showMembers", groupRepo.getAllGroupMembers(group_id));
            return "showGroupDetails";
        }
    }

    @PostMapping(value = "details/{group_id}/deleteMember")
    public String deleteMember(Model model, @PathVariable int group_id, int group_member_id) {
        model.addAttribute("showExaminer", groupRepo.getExaminer(group_id));
        model.addAttribute("delete_member_identity", groupRepo.getBeingDeletedGroupMember(group_member_id));
        model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(group_id));

        return "deleteMemberApproval";
    }

    @PostMapping(value = "details/{group_id}/deleting_member")
    public String deleteMemberApproved(Model model, @PathVariable int group_id, int group_member_id) {
        if (groupRepo.deleteMember(group_id, group_member_id) == 1)
            return "redirect:/groups/details/{group_id}";
        else {
            model.addAttribute("showExaminer", groupRepo.getExaminer(group_id));
            model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(group_id));
            model.addAttribute("showMembers", groupRepo.getAllGroupMembers(group_id));
            return "showGroupDetails";
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(value = "details/{id_group}/edit")
    public String editGroup(Model model, @PathVariable int id_group) {
        model.addAttribute("showExaminer", groupRepo.getExaminer(id_group));
        model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(id_group));

        return "editGroup";
    }

    @PostMapping(value = "details/{id_group}/edit/save")
    public String updateEditedGroup(Model model, @PathVariable int id_group, String group_name, String group_password) {
        if (group_name.isEmpty() || group_password.isEmpty()) {
            model.addAttribute("showExaminer", groupRepo.getExaminer(id_group));
            model.addAttribute("showGroupDetails", groupRepo.getGroupDetails(id_group));
            model.addAttribute("showMembers", groupRepo.getAllGroupMembers(id_group));
            return "redirect:/groups/details/{id_group}";
        }

        if (groupRepo.editGroup(id_group, group_name, group_password) == 1)
            return "redirect:/groups/details/{id_group}";

        return null;
    }

    @GetMapping(value = "/addGroup")
    public String addGroup(Model model) {

        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));

        return "addGroup";
    }

    @PostMapping(value = "/addGroup")
    public String addGroup2(Model model, String group_name, String group_password){
        model.addAttribute("showExaminer", groupRepo.getExaminerFromIDUser(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));
        if(group_name.isEmpty() || group_password.isEmpty())
        {
            model.addAttribute("error_text", "Uzupełnij wszystkie pola podczas dodawania grupy!");
            return "addGroup";
        }

        int database = groupRepo.addGroup(group_name, group_password, userRepo.getExaminerIDFromCredentials(userRepo.getIDFromCredentials("marian@gmail.com", "marian")));

        if (database == 1)
            return "redirect:/groups/";
        if (database == 2) {
            model.addAttribute("error_text", "Już takie hasło istnieje! Wpisz unikalne hasło!");
            return "addGroup";
        }


        model.addAttribute("error_text", "Błąd w przetwarzaniu w bazie danych!");
        return "addGroup";
    }

    @GetMapping(value = "/joinToGroup")
    public String joinToGroup(Model model) {

        return "joinToGroup";
    }

    @PostMapping(value = "/joinToGroup")
    public String joinToGroup2(Model model, String group_password) {

        if (group_password.isEmpty()) {
            model.addAttribute("error_text", "Wpisz jakieś hasło!");
            return "joinToGroup";
        }

        int add_user_to_group = groupRepo.joinToGroup(11, group_password);

        if (add_user_to_group == 1) {
            model.addAttribute("error_text", "Nie istnieje grupa z takim hasłem!");
            return "joinToGroup";
        }
        if (add_user_to_group == 2) {
            model.addAttribute("error_text", "Jesteś już dodany do tej grupy!");
            return "joinToGroup";
        }
        if (add_user_to_group == 3)
            return "redirect:/";

        model.addAttribute("error_text", "Błąd w przetwarzaniu w bazie danych!");
        return "joinToGroup";
    }


    ////////API DLA ANDROIDA I JAVASCRIPTU////////

    @PostMapping("/api/addGroup")
    @ResponseBody
    public String APIaddGroup(int id_egzamintora, String nazwa_grupy, String haslo_grupy) throws JSONException {

        Map m = new HashMap();
        if (nazwa_grupy.isEmpty() || haslo_grupy.isEmpty()) {
            m.put("success", "false");
            m.put("error", "1"); //nazwa lub haslo są puste
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        int database = groupRepo.addGroup(nazwa_grupy, haslo_grupy, id_egzamintora);

        if (database == 1) {
            m.put("success", "true");
            m.put("error", "0");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }
        if (database == 2) {
            m.put("success", "false");
            m.put("error", "2"); //takie haslo juz istnieje w bazie
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }


        m.put("success", "false");
        m.put("error", "3"); //blad w przetwarzaniu w bazie danych
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/getGroups")
    @ResponseBody
    public String APIgetGroups(int id_egzamintora) throws JSONException {

        Map m = new HashMap();

        List<Group> groups = groupRepo.getAllGroups(id_egzamintora);

        for (Group group : groups) {
            List<User> users = groupRepo.getAllGroupMembers(group.getId_grupy());
            group.setUsersList(users);
        }

        m.put("success","true");
        m.put("groups", groups);
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }

    @PostMapping("/api/deleteMember")
    @ResponseBody
    public String APIdeleteMember(int id_grupy, int id_uzytkownika) throws JSONException {

        int resultDelete = groupRepo.deleteMember(id_grupy, id_uzytkownika);

        Map m = new HashMap();
        if (resultDelete == 1) {
            m.put("success", "true");
            m.put("error", "0");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("error", "1"); //nie usunieto, blad przetwarzania
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;

    }

    @PostMapping("/api/deleteGroup")
    @ResponseBody
    public String APIdeleteGroup(int id_grupy) throws JSONException {

        int resultDeleteGroup = groupRepo.deleteGroup(id_grupy);

        Map m = new HashMap();
        if (resultDeleteGroup == 1) {
            m.put("success", "true");
            m.put("error", "0");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("error", "1"); //blad przetwarzania
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;

    }


    @PostMapping("/api/editGroup")
    @ResponseBody
    public String APIeditGroup(int id_grupy, String nazwa, String password) throws JSONException {

        Map m = new HashMap();
        if(nazwa.isEmpty() || password.isEmpty())
        {
            m.put("success", "false");
            m.put("error", "1");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }


        int resultEditGroup = groupRepo.editGroup(id_grupy, nazwa, password);

        if (resultEditGroup == 1) {
            m.put("success", "true");
            m.put("error", "0");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("error", "1"); //blad przetwarzania
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;

    }

    @PostMapping("/api/addMemberToGroup")
    @ResponseBody
    public String APIAddMemberToGroup(int id_uzytkownika, String haslo) throws JSONException {

        Map m = new HashMap();
        if (haslo.isEmpty()) {
            m.put("success", "false");
            m.put("error", "1"); //nie podano hasla
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        int add_user_to_group = groupRepo.joinToGroup(id_uzytkownika, haslo);

        if (add_user_to_group == 1) {
            m.put("success", "false");
            m.put("error", "2"); //nie istnieje grupa z takim haslem
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }
        if (add_user_to_group == 2) {
            m.put("success", "false");
            m.put("error", "3"); //jestes juz dodany do tej grupy
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }
        if (add_user_to_group == 3) {
            m.put("success", "true");
            m.put("error", "0"); //poprawnie dodano do grupy
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success", "false");
        m.put("error", "4"); //blad w przetwarzaniu bazy danych
        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }
}
