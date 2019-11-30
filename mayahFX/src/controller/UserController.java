package controller;

import java.sql.SQLException;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.User;
import model.UserDAO;


public class UserController {
@FXML
private TextField txtDescription;
@FXML
private TextField txtUsername;
@FXML
private TextField txtPassword; 

@FXML
private TableColumn<User, String> colDescription;
@FXML
private TableColumn<User, String> colUserName; 
@FXML
private TableColumn<User, String> colPassword; 
@FXML
private TableView<User>  userTable; 

@FXML
private void insertUser(ActionEvent event)throws ClassNotFoundException,SQLException{
	UserDAO.insertUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText());
}

@FXML
private void initialize()throws Exception{
	//have to change later to approach 2 of below docs 
	//https://stackoverflow.com/questions/30334450/cannot-convert-from-string-to-observablevaluestring
	colDescription.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getDescription()));
	colUserName.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getUserName()));
	colPassword.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getPassword()));
	ObservableList<User> userList=UserDAO.getAllRecords();
	populateTable(userList);
}


private void populateTable(ObservableList<User> userList) {
	userTable.setItems(userList);
}

}
