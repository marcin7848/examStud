package co.bdio.Service;

import co.bdio.Entity.Answer;
import co.bdio.Entity.Question;
import co.bdio.Entity.Test;

import java.util.List;

public interface MyServiceInterface {

    public List<Test> getAllTest(int id);

    public void addTest(Test test, int id);

    public int findTestId(String test);

    public List<Question> getById(int id, int id1);

    public List<Answer> getodpByIdTestu(int id, int id1);

    public void editTest(Test tescik);

    public Test getByIdt(int id, int id1);

    public void saveQuestion(Question question, int id);

    public List<Question> findAllQuest(int id);

    public void selectAndGet(int id, int idd);

    public void saveAnswer(String tekst, char t, Question question, int id_egz);

    public void editQuestion(Question pytanie,int id);

    public void dropAns(int id);

    public void dropQuestion(int id);

    public Question getByIdpyt(int id, int id1);

    public int getIdPyt(String nazwa);

    public int getIdtestu(int id);

    public void  dropTest(int id);

    public void savepdateAnswer(String tekst, char t,Question question, int id_uz, int id_pyt);

}