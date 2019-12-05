package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
@FXML
private TextField txtPIN;

@FXML
private void login(ActionEvent event) throws IOException {
	if (txtPIN.getText().equals("0000")) {
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/controller/Fx1.fxml"));
		Scene scene= new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Log in");
		primaryStage.show();
		closeLogin();
	}
	
}

private void closeLogin(){
	  // get a handle to the stage
	  Stage stage = (Stage) txtPIN.getScene().getWindow();
	  // do what you have to do
	  stage.close();
	}
}
