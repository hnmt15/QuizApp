/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hnmt.services.exams;

import com.hnmt.pojo.Question;
import com.hnmt.services.questions.BaseQuestionServices;
import com.hnmt.services.questions.LevelQuestionDecorator;
import com.hnmt.services.questions.LimitQuestionDecorator;
import com.hnmt.utils.Configs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FixedExamStrategy extends ExamStrategy {

    
    @Override
    public List<Question> getQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        
        for (int i=0; i < Configs.RATES.length; i++) {
            BaseQuestionServices s = new LimitQuestionDecorator(new LevelQuestionDecorator(Configs.questionServices, i+1),(int)(Configs.RATES[i] * Configs.NUM_OF_QUES));
            questions.addAll(s.list());
        }
        return questions;
    }
    
}
