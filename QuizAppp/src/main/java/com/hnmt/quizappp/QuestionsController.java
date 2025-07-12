/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.hnmt.quizappp;

import com.hnmt.pojo.Category;
import com.hnmt.pojo.Choice;
import com.hnmt.pojo.Level;
import com.hnmt.pojo.Question;
import com.hnmt.services.questions.BaseQuestionServices;
import com.hnmt.services.questions.CategoryQuestionDecorator;
import com.hnmt.services.questions.KeywordQuestionDecorator;
import com.hnmt.services.questions.LevelQuestionDecorator;
import com.hnmt.utils.Configs;
import com.hnmt.utils.MyAlert;
import java.net.URL;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//
//

///**
// * FXML Controller class
// *
// * @author admin
// */
//public class QuestionsController implements Initializable {
//
//    private ComboBox<Category> cbCates;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//
//        try {
//            //Buoc1: nap driver
//            Class.forName("com.mysql.cj.jbdc.Driver");
//
//            //Buoc2: Mo ket noi
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quizdb", "root", "root");
//
//            //Buoc3: Xu ly truy van
//            Statement stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery("SELECT * FROM category");
//            List<Category> cates = new ArrayList<>();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.printf("%d - %s\n", id, name);
//            }
//            //Buoc4: Dong ket noi
//            conn.close();
//            this.cbCates.setItems(FXCollections.observableArrayList(cates));
//        } catch (ClassNotFoundException | SQLException ex) {
//        }
//    }
//}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionsController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private ComboBox<Level> cbLevels;
    @FXML
    private TextArea txtContent;
    @FXML
    private TableView<Question> tbQuestions;
    @FXML
    private VBox vboxChoices;
    @FXML
    private ToggleGroup toggleChoice;
    @FXML 
    private TextField txtSearch;
    @FXML
    private ComboBox<Category> cbSearchCates;
    @FXML
    private ComboBox<Level> cbSearchLevels;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCates.setItems(FXCollections.observableList(Configs.cateServices.getCates()));
            this.cbSearchCates.setItems(FXCollections.observableList(Configs.cateServices.getCates()));
            this.cbSearchLevels.setItems(FXCollections.observableList(Configs.levelServices.getLevels()));
            this.cbLevels.setItems(FXCollections.observableList(Configs.levelServices.getLevels()));
            
            this.loadColumns();
            this.loadQuestions(Configs.questionServices.list());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.txtSearch.textProperty().addListener((e) -> {
            try {
                BaseQuestionServices s = new KeywordQuestionDecorator(Configs.questionServices, this.txtSearch.getText());
                this.loadQuestions(s.list());
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        });
        this.cbSearchCates.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                BaseQuestionServices s = new CategoryQuestionDecorator(Configs.questionServices, this.cbSearchCates.getSelectionModel().getSelectedItem());
                this.loadQuestions(s.list());
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        
        this.cbSearchLevels.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                BaseQuestionServices s = new LevelQuestionDecorator(Configs.questionServices, this.cbSearchLevels.getSelectionModel().getSelectedItem());
                this.loadQuestions(s.list());
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    public void addChoice(ActionEvent event) {
        HBox h = new HBox();
        h.getStyleClass().add("Main");

        RadioButton r = new RadioButton();
        r.setToggleGroup(toggleChoice);

        TextField txt = new TextField();
        txt.getStyleClass().add("Input");

        h.getChildren().addAll(r, txt);

        this.vboxChoices.getChildren().add(h);

    }

    public void addQuestion(ActionEvent event) {
        try {
            Question.Builder b = new Question.Builder(this.txtContent.getText(),
                    this.cbCates.getSelectionModel().getSelectedItem(),
                    this.cbLevels.getSelectionModel().getSelectedItem());

            for (var c : vboxChoices.getChildren()) {
                HBox h = (HBox) c;
                Choice choice = new Choice(((TextField) h.getChildren().get(1)).getText(),
                        ((RadioButton) h.getChildren().get(0)).isSelected());
                b.addChoice(choice);
            }

            Configs.uQServices.addQuestion(b.build());

            MyAlert.getInstance().showMsg("Thêm câu hỏi thành công!");
        } catch (SQLException ex) {
            MyAlert.getInstance().showMsg("Thêm câu hỏi thất bại, lý do: " + ex.getMessage());
        } catch (Exception ex) {
            MyAlert.getInstance().showMsg("Dữ liệu có lỗi!");

        }
    }
    
    private void loadQuestions(List<Question> questions){
        this.tbQuestions.setItems(FXCollections.observableList(questions));
    }
    
    private void loadColumns(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(80);
        
        TableColumn colContent = new TableColumn("Question");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(250);
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(e -> {
            TableCell cell = new TableCell();
            
            Button btn = new Button("Delete");
            btn.setOnAction(event -> {
                Optional<ButtonType> t = MyAlert.getInstance().showMsg("Delete the question will also detete the choice. Are you sure to delete?", Alert.AlertType.CONFIRMATION);
                if (t.isPresent() && t.get().equals(ButtonType.OK)){
                    Question q = (Question) cell.getTableRow().getItem();
                    try {
                        if (Configs.uQServices.deleteQuestion(q.getId()) == true){
                            MyAlert.getInstance().showMsg("Delete successfully");
                            this.tbQuestions.getItems().remove(q);
                        }} catch (SQLException ex) {
                        Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            });
            
            cell.setGraphic(btn);
            return cell;
        });
        
        this.tbQuestions.getColumns().addAll(colId, colContent, colAction);
    }
}