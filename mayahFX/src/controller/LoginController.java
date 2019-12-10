package controller;


import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DBUtil;

public class LoginController {
@FXML
private TextField txtPIN;
@FXML
private Label lblStatus; 
@FXML
private Button btnLogin;



@FXML
private void initialize()throws Exception{
	lblStatus.setVisible(false);
	btnLogin.setDefaultButton(true);
	
	txtPIN.setPromptText("0000 by default");

     PseudoClass empty = PseudoClass.getPseudoClass("empty");
     txtPIN.pseudoClassStateChanged(empty, true);
     txtPIN.textProperty().addListener((obs, oldText, newText) -> {
    	 txtPIN.pseudoClassStateChanged(empty, newText.isEmpty());
     });
		
}

@FXML
private void login(ActionEvent event) throws Exception {
	if (!txtPIN.getText().isEmpty()) {
		
		if (txtPIN.getText().equals(DBUtil.dbGetPin())) {
			Stage primaryStage=new Stage();
			Parent root=FXMLLoader.load(getClass().getResource("/controller/Fx1.fxml"));
			Scene scene= new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Joyee's Password Management System");
			primaryStage.show();
			closeLogin();
		}
		else {
			lblStatus.setVisible(true);
			lblStatus.setStyle("-fx-color: red");
			lblStatus.setText("PIN mismatch,try again");
		}		
		
	}
	else {
		lblStatus.setVisible(true);
		lblStatus.setStyle("-fx-color: red");
		lblStatus.setText("PIN cannot be blank");
	}
		
}



private void closeLogin(){
	  // get a handle to the stage
	  Stage stage = (Stage) txtPIN.getScene().getWindow();
	  // do what you have to do
	  stage.close();
	}

}
