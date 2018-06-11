package co.bdio.Service;


import co.bdio.Entity.Answer;
import co.bdio.Entity.Question;
import co.bdio.Entity.Test;
import co.bdio.Model.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class MyService implements MyServiceInterface {

    private TestRepository test;
    @Autowired
    MyService(TestRepository test) {
        this.test = test;
    }

    public List<Test> getAllTest(int id) {
        return test.findAll(id);
    }

    public void addTest(Test tescik, int id) {
        test.addTest(tescik, id);
    }

    public int findTestId(String testtt) {
        return test.findTestId(testtt);
    }

    public List<Question> getById(int id, int id1) {
        return test.getById(id, id1);
    }

    public List<Answer> getodpByIdTestu(int id, int id1) {
        return test.getodpByIdTestu(id, id1);
    }

    public void editTest(Test tescik) {
        test.editTest(tescik);
    }

    public Test getByIdt(int id, int id1) {
        return test.getByIdt(id, id1);
    }

    public void saveQuestion(Question question, int id) {
        test.saveQuestion(question, id);
    }

    public List<Question> findAllQuest(int id) {
        return test.findAllQuest(id);
    }

    public void selectAndGet(int id, int idd) {
        test.selectAndGet(id, idd);
    }

    public void saveAnswer(String tekst, char t, Question question, int id_egz) {
        test.saveAnswer(tekst, t, question, id_egz);
    }

    public void editQuestion(Question pytanie, int id) {
        test.editQuestion(pytanie, id);
    }

    public void dropAns(int id) {
        test.dropAns(id);
    }

    public void dropQuestion(int id) {
        test.dropQuestion(id);
    }

    public Question getByIdpyt(int id, int id1) {
        return test.getByIdpyt(id, id1);
    }

    public int getIdPyt(String nazwa) {
        return test.getIdPyt(nazwa);
    }

    public int getIdtestu(int id) {
        return test.getIdtestu(id);
    }

    public void dropTest(int id) {
        test.dropTest(id);
    }

    public void savepdateAnswer(String tekst, char t, Question question, int id_uz, int id_pyt) {
        test.savepdateAnswer(tekst, t, question, id_uz, id_pyt);
    }

}
