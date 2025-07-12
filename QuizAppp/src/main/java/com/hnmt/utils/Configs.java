/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hnmt.utils;

import com.hnmt.services.CategoryServices;
import com.hnmt.services.LevelServices;
import com.hnmt.services.questions.BaseQuestionServices;
import com.hnmt.services.questions.QuestionServices;
import com.hnmt.services.questions.UpdateQuestionServices;

/**
 *
 * @author admin
 */
public class Configs {

    public static final LevelServices levelServices = new LevelServices();
    public static final BaseQuestionServices questionServices = new QuestionServices();
    public static final CategoryServices cateServices = new CategoryServices();
    public static final UpdateQuestionServices uQServices = new UpdateQuestionServices();
    
}
