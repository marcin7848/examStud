package co.bdio.Controller;

import co.bdio.Entity.Test;
import co.bdio.Service.MyService;
import co.bdio.Service.MyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {
    MyServiceInterface service;


    @Autowired
    TestController(MyService service) {
        this.service = service;
    }

    @RequestMapping("/showTests")
    public String showTest(Model model){
        int id1=1;
        model.addAttribute("showTests", service.getAllTest(id1));
        return "showTests";
    }
    @RequestMapping("/addTest")
    public String addT(Model model) {
        model.addAttribute("tescik", new Test());
        return "addTest";
    }

    @PostMapping(value = "tescik")
    public String addTest(Test test, @RequestParam String przycisk) {
        int id1=1;
        if (przycisk.equals("powrot")) {
            return "redirect:/showTests";}
        else {
            if (test.getNazwa()=="") return "bladTest";
            else{
                service.addTest(test, id1);
                String naz=test.getNazwa();
                int id=service.findTestId(naz);
                if   (przycisk.equals("dodajnast")) {
                    return "redirect:/addQuestion/"+id;
                }
                else if   (przycisk.equals("dodajkoncz")){
                    return "redirect:/showTests/showQuestions/" +id;
                }
                return "redirect:/showTests/";
            }
        }
    }
    @RequestMapping("/editTest/{id}")
    public String editT(@PathVariable int id, Model model) {
        int id1=1;
        model.addAttribute("testuj", service.getByIdt(id, id1));
        return "editTest";
    }

    @PostMapping(value = "testuj")
    public String editTest(Test test) {
        if (test.getNazwa() =="") return "bladTest";
        else {
            service.editTest(test);
            return "redirect:/showTests/";
        }
    }
    @RequestMapping(value = "/dropTest/{id}")
    public String dropTest(@PathVariable int id) {
        service.dropTest(id);
        return "redirect:/showTests/";
    }
}
