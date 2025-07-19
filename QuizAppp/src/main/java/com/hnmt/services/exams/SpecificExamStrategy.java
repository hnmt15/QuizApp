/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hnmt.services.exams;

import com.hnmt.pojo.Question;
import com.hnmt.services.questions.BaseQuestionServices;
import com.hnmt.services.questions.LimitQuestionDecorator;
import com.hnmt.utils.Configs;
import java.io.ObjectInputFilter;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class SpecificExamStrategy extends ExamStrategy{

    private int num;

    public SpecificExamStrategy(int num) {
        this.num = num;
    }
    
    
    @Override
    public List<Question> getQuestions() throws SQLException{
        BaseQuestionServices s = new LimitQuestionDecorator(Configs.questionServices, this.num);
        return s.list();
    }
    
}
