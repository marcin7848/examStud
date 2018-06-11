package co.bdio.Controller;


import co.bdio.Entity.Question;
import co.bdio.Service.MyService;
import co.bdio.Service.MyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class QuestionController {
    private MyServiceInterface service;
    private int idd;

    @Autowired
    QuestionController(MyService service) {
        this.service = service;
    }

    @RequestMapping(value = "/showTests/showQuestions/{id}")
    public String showQuestion(@PathVariable int id, Model model) {
        int id_egz=1;
        idd = id;
        model.addAttribute("showQuestions", service.getById(id, id_egz));
        model.addAttribute("showAns", service.getodpByIdTestu(id, id_egz));
        return "showQuestions";
    }
    @RequestMapping("/addQuestion/{id}")
    public String addQ(@PathVariable int id, Model model) {
        model.addAttribute("pytanie", new Question(id));
        return "addQuestion";
    }
    @PostMapping(value = "pytanie")
    public String saveQuestion(Question pytanie,@RequestParam String przycisk, @RequestParam("odpowied[]") List<String> to,@RequestParam("pyt")  List<String>  cb) {
        if (przycisk.equals("powrot")) {
            return "redirect:/showTests";
        } else {
            int id = 1;

            int ilosc = 0;
            for (int ilo = 0; ilo < to.size(); ilo++) {
                if (cb.get(ilo).isEmpty() || to.get(ilo).equals(""))
                    return "blad";
            }

            if (pytanie.getTresc() == null || pytanie.getCzasOdp() == null || pytanie.getIlosc_punktow() == null) {
                return "blad";
            } else {

                pytanie.setIlosc_odpowiedzi(to.size());
                if (pytanie.getIlosc_odpowiedzi() == 0) pytanie.setIlosc_odpowiedzi(1);

                pytanie.setIlosc_poprawnych(ilosc);
                if (pytanie.getCzyotwarte() == true) pytanie.setTyp('o');

                else {
                    pytanie.setTyp('z');
                    for (int ill = 0; ill < pytanie.getIlosc_odpowiedzi(); ill++) {
                        if (cb.get(ill).charAt(0) == 't') ilosc++;
                    }
                }
                service.saveQuestion(pytanie, id);
                int io = to.size(), in = 0;
                for (; in < io; in++) {
                    service.saveAnswer(to.get(in), cb.get(in).charAt(0), pytanie, id );
                }
            }
            if (przycisk.equals("dkoncz"))
                return "redirect:/showTests/showQuestions/" + pytanie.getId_testu();
            else   return "redirect:/addQuestion/" + pytanie.getId_testu();
        }
    }

    @RequestMapping(value = "/showTests/showQuestions/addOldQuestion/{id}")
    public String addO(@PathVariable int id, Model model) {
        int id_egz=1;
        model.addAttribute("showQuest", service.findAllQuest(id_egz));
        idd = id;
        return "addOldQuestion";
    }

    @RequestMapping(value = "/showQueest/{id}")
    public String addOldQuestion(@PathVariable int id, Question question) {
        service.selectAndGet(id, idd);
        return "redirect:/showTests/showQuestions/addOldQuestion/" + idd;
    }
    @RequestMapping(value="/editQuestion/{id}")
    public String edittQ(@PathVariable int id, Model model) {
        int id_egz=1;
        model.addAttribute("quest", service.getByIdpyt(id, id_egz));
        return "editQuestion";
    }
    @PostMapping(value = "quest")
    public String editQuestion(Question pytanie,@RequestParam String przycisk, @RequestParam("odpowied[]") List<String> to,@RequestParam("pyt")  List<String>  cb) {
        if (przycisk.equals("powrot")) {
            return "redirect:/showTests/showQuestions/"+ pytanie.getId_testu();
        } else {
            int id = 1;

            int ilosc = 0;
            for (int ilo = 0; ilo < to.size(); ilo++) {
                if (cb.get(ilo).isEmpty() || to.get(ilo).equals(""))
                    return "blad";
            }

            if (pytanie.getTresc() == null || pytanie.getCzasOdp() == null || pytanie.getIlosc_punktow() == null) {
                return "blad";
            } else {
                pytanie.setId_testu(service.getIdtestu(pytanie.getId_pytania()));
                pytanie.setIlosc_odpowiedzi(to.size());
                if (pytanie.getIlosc_odpowiedzi() == 0) pytanie.setIlosc_odpowiedzi(1);

                pytanie.setIlosc_poprawnych(ilosc);
                if (pytanie.getCzyotwarte() == true) pytanie.setTyp('o');

                else {
                    pytanie.setTyp('z');
                    for (int ill = 0; ill < pytanie.getIlosc_odpowiedzi(); ill++) {
                        if (cb.get(ill).charAt(0) == 't') ilosc++;
                    }
                }

                service.dropAns(pytanie.getId_pytania());
                int io = to.size(), in = 0;
                for (; in < io; in++) {
                    service.savepdateAnswer(to.get(in), cb.get(in).charAt(0), pytanie, id, pytanie.getId_pytania());
                }
                service.editQuestion(pytanie, id);
            }
            return "redirect:/showTests/showQuestions/" + pytanie.getId_testu();
        }
    }

    @RequestMapping(value = "/dropQuest/{id}")
    public String dropQuestion(@PathVariable int id) {
        service.dropAns(id);
        service.dropQuestion(id);
        return "redirect:/showTests/showQuestions/" + idd;
    }
}


