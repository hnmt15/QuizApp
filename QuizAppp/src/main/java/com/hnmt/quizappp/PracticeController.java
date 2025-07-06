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
      @FXML
      private Text txtResult;
    private List<Question> questions;
    private int currentQuestion = 0;
    
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
    
    public void handleCheck (ActionEvent event){
        Question q = this.questions.get(this.currentQuestion);
        this.txtResult.getStyleClass().clear(); 
        for(int i = 0; i<q.getChoices().size(); i++){
            if(q.getChoices().get(i).isCorrect()){
                RadioButton r = (RadioButton)this.vboxChoices.getChildren().get(i);
                if (r.isSelected()){
                    this.txtResult.setText("Bravo đúng rồi!!!");
                    this.txtResult.getStyleClass().add("Correct");
                }
                else{
                    this.txtResult.setText("Tiếc quá sai mất giồiii");
                    this.txtResult.getStyleClass().add("Wrong");
                }
                break;
            }
        }
    }
}
