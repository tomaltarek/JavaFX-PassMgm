package controller;

import java.sql.SQLException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import model.UserDAO;



public class UserController {
private String oldDesc=null; 
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
		if (!isAnyFieldsBlank())
		{
			UserDAO.insertUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText());			
			refresh();
		}			
		
	} catch (SQLException e) {
		System.out.println("Hit error while inserting"+e);
		
		e.printStackTrace();
		throw e; 
	}
	
	
}
@FXML
private void updateUser(ActionEvent event) throws Exception{
	try {
		if (!isAnyFieldsBlank())
		{
			UserDAO.updatetUser(txtDescription.getText(), txtUsername.getText(), txtPassword.getText(), oldDesc);
			refresh();
		}		
		
	} catch (SQLException e) {
		System.out.println("Hit error while updating"+e);
		e.printStackTrace();
		throw e; 
	}
	
}

@FXML
private void deleteUser(ActionEvent event) throws Exception{
	try {
		
		if (!(txtDescription.getText().isEmpty()))
		{
			UserDAO.deletetUser(txtDescription.getText());
			refresh();
		}
		
		
	} catch (SQLException e) {
		System.out.println("Hit error while deleting"+e);
		e.printStackTrace();
		throw e; 
	}
	
}

@FXML
private void searchUser(ActionEvent event) throws Exception{
    ObservableList<User> userList=UserDAO.searchUser(txtDescription.getText());    
	populateTable(userList);
	
	
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
    	oldDesc=usr.getDescription();
    }
}

@FXML
private void refresh() throws Exception{
	txtDescription.setText("");
	txtUsername.setText("");
	txtPassword.setText("");
	if (insertButton.isDisable())
	{
	insertButton.setDisable(false);
    }
	initialize();
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
	//noticeLabel.setVisible(false);
	
	
}

private void populateTable(ObservableList<User> userList) {
	
	userTable.setItems(userList);
	
	 
}

//validation checking if any text field is blank
private boolean isAnyFieldsBlank() {
	boolean status=false;
	if (txtDescription.getText().isEmpty()||txtUsername.getText().isEmpty()||txtPassword.getText().isEmpty()) 
	{
		status=true;
	}
	return status; 
}

}
