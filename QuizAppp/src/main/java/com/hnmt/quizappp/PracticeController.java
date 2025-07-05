/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.hnmt.quizappp;

import com.hnmt.pojo.Question;
import com.hnmt.services.QuestionServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class PracticeController implements Initializable {

    @FXML
    private Text txtContent;
    @FXML 
    private VBox vboxChoices;
    @FXML
    private TextField txtNum;
    private List<Question> questions;
    private int currentQuestion;
    
    private static final QuestionServices questionServices = new QuestionServices();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.questions = questionServices.getQuestions(2);
        } catch (SQLException ex) {
            Logger.getLogger(PracticeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void loadQuestion(){
        Question q = this.questions.get(this.currentQuestion);
        
        this.txtContent.setText(q.getContent());
        vboxChoices.getChildren().clear();
        ToggleGroup g = new ToggleGroup();
        
        for (var c: q.getChoices()){
            RadioButton r = new RadioButton(c.getContent());
            r.setToggleGroup(g);
            
            vboxChoices.getChildren().add(r);
        }
    }
    
    public void handleStart(ActionEvent event){
        try {
            this.questions = questionServices.getQuestions(Integer.parseInt(this.txtNum.getText()));
            this.loadQuestion();
        } catch (SQLException ex) {
            Logger.getLogger(PracticeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleNext(ActionEvent event){
        if(this.currentQuestion < this.questions.size() - 1){
            this.currentQuestion++;
            this.loadQuestion();
        }
    }
}
