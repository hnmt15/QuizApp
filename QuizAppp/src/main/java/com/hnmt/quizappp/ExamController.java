/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.hnmt.quizappp;

import com.hnmt.pojo.Choice;
import com.hnmt.pojo.Question;
import com.hnmt.services.exams.ExamStrategy;
import com.hnmt.services.exams.ExamTypes;
import com.hnmt.services.exams.FixedExamStrategy;
import com.hnmt.services.exams.SpecificExamStrategy;
import com.hnmt.utils.MyAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
public class ExamController implements Initializable {
    @FXML private ComboBox<ExamTypes> cbExamTypes;
    @FXML private TextField txtNum;
    @FXML private ListView<Question> lvQuesions;
    private List<Question> questions;
    private Map<Integer, Choice> answers = new HashMap<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtNum.setVisible(false);
       this.cbExamTypes.setItems(FXCollections.observableArrayList(ExamTypes.values()));
       this.cbExamTypes.getSelectionModel().selectedItemProperty().addListener(e -> {
           if (this.cbExamTypes.getSelectionModel().getSelectedItem() == ExamTypes.SPECIFIC)
               this.txtNum.setVisible(true);
           else
               this.txtNum.setVisible(false);
       });
    }    
    public void handleStart(ActionEvent event) throws SQLException{
        ExamStrategy s = new FixedExamStrategy();
        if (this.cbExamTypes.getSelectionModel().getSelectedItem() == ExamTypes.SPECIFIC)
            s = new SpecificExamStrategy(Integer.parseInt(this.txtNum.getText()));
        this.questions = s.getQuestions();
        
        this.lvQuesions.setItems((FXCollections.observableArrayList(this.questions)));
        
        this.lvQuesions.setCellFactory(param -> new ListCell<Question>(){
            @Override
            protected void updateItem(Question q, boolean empty) {
                super.updateItem(q, empty);                 
                
                if (q == null || empty == true)
                    this.setGraphic(null);
                else {
                    VBox v = new VBox(5);
                    v.setStyle("-fx-padding: 5; -fx-border-color: gray; -fx-border-width: 1");
                    
                    Text t = new Text(q.getContent());
                    
                    v.getChildren().add(t);
                    ToggleGroup g = new ToggleGroup();
                    for (var c:q.getChoices()){
                        RadioButton r = new RadioButton(c.getContent());
                        r.setToggleGroup(g);
                        
                        
                        //update UI
                        if(answers.get(q.getId()) == c)
                            r.setSelected(true);
                        
                        
                        r.setOnAction(e -> {
                            if (r.isSelected() == true)
                                answers.put(q.getId(), c);
                        });
                        v.getChildren().add(r);
                    }
                    this.setGraphic(v);
                }

            }
            
        });
        
    }
    
     public void handleSave(ActionEvent event){
        
    }
     
      public void handleFinish(ActionEvent event){
        if(questions != null && !questions.isEmpty()){
            int count =0;
            for(var c: answers.values())
                if(c.isCorrect() == true)
                    count++;
            MyAlert.getInstance().showMsg(String.format("Correct %d/%d!", count, questions.size()));
                            
        }
    }
}
