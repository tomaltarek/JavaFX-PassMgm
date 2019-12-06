package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
private void initialize()throws Exception{
	lblStatus.setVisible(false);
}
@FXML
private void login(ActionEvent event) throws Exception {
	
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
		lblStatus.setText("PIN mismatch,try again");
	}
	
}



private void closeLogin(){
	  // get a handle to the stage
	  Stage stage = (Stage) txtPIN.getScene().getWindow();
	  // do what you have to do
	  stage.close();
	}
}
