/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.hnmt.quizappp;

import com.hnmt.pojo.Category;
import com.hnmt.pojo.Level;
import com.hnmt.services.CategoryServices;
import com.hnmt.services.LevelServices;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    @FXML private ComboBox<Category> cbCates;
    @FXML private ComboBox<Level> cbLevels;
    
    @FXML private VBox vboxChoices;
    private static final CategoryServices cateServices = new CategoryServices();
    private static final LevelServices levelServices = new LevelServices();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCates.setItems(FXCollections.observableList(cateServices.getCates()));
            this.cbLevels.setItems(FXCollections.observableList(levelServices.getLevels()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
    public void addChoice(ActionEvent event){
        HBox h = new HBox();
        h.getStyleClass().add("Main");
        
        RadioButton r = new RadioButton();
        TextField txt = new TextField();
        txt.getStyleClass().add("Input");
        
        h.getChildren().addAll(r, txt);
        
        this.vboxChoices.getChildren().add(h);
                
    }
}