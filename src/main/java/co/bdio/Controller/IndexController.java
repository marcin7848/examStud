package co.bdio.Controller;

import co.bdio.Entity.User;
import co.bdio.Model.GroupRepo;
import co.bdio.Model.MyRepo;
import co.bdio.Model.UserRepo;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class IndexController {

    private MyRepo re;
    private UserRepo userRepo;

    @Autowired
    IndexController(MyRepo re, UserRepo userRepo) {
        this.re = re;
        this.userRepo=userRepo;
    }

    @RequestMapping("/")
        public  String index() {
            return "index";
        }


    @PostMapping("/logowanie")
    @ResponseBody
    public String logowanie(String haslo, String email) throws JSONException {

        User user = new User();
        user = userRepo.logowanie(email, haslo);

        Map m = new HashMap();

        if(user.getId_uzytkownika() == 0)
        {
            m.put("success","false");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        user = userRepo.checkExaminerAndGetID(user);

        m.put("success","true");
        m.put("id_uzytkownika",user.getId_uzytkownika());
        m.put("nazwisko",user.getNazwisko());
        m.put("imie",user.getImie());
        m.put("haslo",user.getHaslo());
        m.put("email",user.getEmail());
        m.put("status",user.getStatus());
        m.put("id_studenta_or_egzamintora", user.getId_studenta_or_egzamintora());

        JSONObject json = JSONObject.fromObject(m);

        String mess = json.toString();
        return mess;
    }

    @PostMapping("/rejestracja")
    @ResponseBody
    public String rejestracja(String nazwisko, String imie, String haslo, String email, int status) throws JSONException {

        int createNewUser = userRepo.rejestracja(nazwisko, imie, haslo, email, status);

        Map m = new HashMap();

        if(createNewUser == 0)
        {
            m.put("success","false");
            JSONObject json = JSONObject.fromObject(m);
            String mess = json.toString();
            return mess;
        }

        m.put("success","true");

        JSONObject json = JSONObject.fromObject(m);
        String mess = json.toString();
        return mess;
    }
}

