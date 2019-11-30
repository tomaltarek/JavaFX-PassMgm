package controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.UserDAO;


public class UserController {
@FXML
private TextField txtDescription;
@FXML
private TextField txtUsername;
@FXML
private TextField txtPassword; 
@FXML
private void insertUser(ActionEvent event)throws ClassNotFoundException,SQLException{
	UserDAO.insertUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText());
}

}
