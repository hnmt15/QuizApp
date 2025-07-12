/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hnmt.services.questions;

import com.hnmt.pojo.Level;
import java.util.List;

/**
 *
 * @author admin
 */
public class LevelQuestionDecorator extends QuestionDecorator{
    private Level level;
    public LevelQuestionDecorator(BaseQuestionServices decorator, Level lv) {
        super(decorator);
        this.level = lv;
    }
    public LevelQuestionDecorator(BaseQuestionServices decorator, int lvId) {
        super(decorator);
        this.level = new Level(lvId);
    }

    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + " AND level_id=?";
        params.add(this.level.getId());
        
        return sql;
    }
    
}
