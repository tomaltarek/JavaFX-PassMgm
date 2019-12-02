package controller;

import java.sql.SQLException;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import model.UserDAO;



public class UserController {
private String oldUser=null; 
@FXML
private TextField txtDescription;
@FXML
private TextField txtUsername;
@FXML
private TextField txtPassword; 
@FXML
private Button insertButton;

@FXML
private TableColumn<User, String> colDescription;
@FXML
private TableColumn<User, String> colUserName; 
@FXML
private TableColumn<User, String> colPassword; 
@FXML
private TableView  userTable; 



@FXML
private void insertUser(ActionEvent event)throws Exception{
	try {
		UserDAO.insertUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText());
		initialize();
		clearFields();
		
	} catch (SQLException e) {
		System.out.println("Hit error while inserting"+e);
		e.printStackTrace();
		throw e; 
	}
	
	
}
@FXML
private void updateUser(ActionEvent event) throws Exception{
	try {
		UserDAO.updatetUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText(), oldUser);
		initialize();
		clearFields();
	} catch (SQLException e) {
		System.out.println("Hit error while updating"+e);
		e.printStackTrace();
		throw e; 
	}
	
}

@FXML
public void clickItem(MouseEvent event)
{
    if (event.getClickCount() == 2) //Checking double click
    {
    	User usr= (User) userTable.getSelectionModel().getSelectedItem();
    	txtDescription.setText(usr.getDescription());
    	txtUsername.setText(usr.getUserName());
    	txtPassword.setText(usr.getPassword());
    	insertButton.setDisable(true);
    	oldUser=usr.getUserName();
    }
}


private void clearFields(){
	txtDescription.setText(null);
	txtUsername.setText(null);
	txtPassword.setText(null);
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
