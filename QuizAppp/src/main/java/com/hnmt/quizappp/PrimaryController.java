package com.hnmt.quizappp;

import com.hnmt.utils.MyAlert;
import com.hnmt.utils.MyStage;
import com.hnmt.utils.theme.DefaultThemeFactory;
import com.hnmt.utils.theme.Theme;
import com.hnmt.utils.theme.ThemeManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    @FXML private ComboBox<Theme> cbThemes;

    
    public void changeTheme(ActionEvent event){
       this.cbThemes.getSelectionModel().getSelectedItem().updateTheme(this.cbThemes.getScene());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbThemes.setItems(FXCollections.observableArrayList(Theme.values()));
        }
    
    
    
    public void handleQuestionManageMent (ActionEvent event) throws IOException{
//       Scene scene = new Scene ( new FXMLLoader(App.class.getResource("questions.fxml")).load());
//       Stage stage = new Stage();
//       stage.setScene(scene);
//       stage.setTitle("Quiz App");
//       stage.show();
        MyStage.getInstance().showStage("question.fxml");
        
    }
    
    
    
    public void handlePractice(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }
}
