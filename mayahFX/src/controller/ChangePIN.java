package controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.UserDAO;
import util.DBUtil;

public class ChangePIN {
@FXML	
private PasswordField passOldPin;
@FXML	
private PasswordField passNewPin;
@FXML
private Label lblStatus;

@FXML
private void initialize()throws Exception{
	lblStatus.setVisible(false);
}
@FXML
private void updatePIN(ActionEvent event) throws Exception{
	
	try {
		if (!isAnyFieldsBlank()) {
			if (passOldPin.getText()==DBUtil.dbGetPin()) {
				UserDAO.updatePin(passOldPin.getText(), passNewPin.getText());
				lblStatus.setVisible(true);
				lblStatus.setText("PIN changed successfully");
			}
			else {
				lblStatus.setVisible(true);
				lblStatus.setText("Old pin mismatch");
			}
		}
									
		
	} catch (SQLException e) {
		System.out.println("Hit error while updating PIN"+e);
		e.printStackTrace();
		throw e; 
	}
	
}
//validation checking if any text field is blank
private boolean isAnyFieldsBlank() {
	boolean status=false;
	if (passOldPin.getText().isEmpty()||passNewPin.getText().isEmpty()) 
	{
		status=true;
	}
	return status; 
}
}
