/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hnmt.services.questions;

import com.hnmt.pojo.Category;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryQuestionDecorator extends QuestionDecorator{
    private Category category;

    public CategoryQuestionDecorator(BaseQuestionServices decorator, Category category ) {
        super(decorator);
        this.category = category;
    }

    public CategoryQuestionDecorator(BaseQuestionServices decorator, int cateId) {
        super(decorator);
        this.category = new Category(cateId);
    }
    

    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + " AND category_id=?";
        params.add(this.category.getId());
        
        return sql;
    }
    
    
}
