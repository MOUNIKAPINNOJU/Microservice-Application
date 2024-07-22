package com.telusko.question_service.service;

import com.telusko.question_service.dao.QuestionDao;
import com.telusko.question_service.dto.Question;
import com.telusko.question_service.dto.QuestionWrapper;
import com.telusko.question_service.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(Integer numQ) {
        List<Integer> questionList = questionDao.findRandomQuestionsByNumber(numQ);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question: questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());

            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;

        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer()))
                right++;

        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
