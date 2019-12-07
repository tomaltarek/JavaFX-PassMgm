package controller;

import java.sql.SQLException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import model.UserDAO;
import util.DBUtil;



public class UserController {
@FXML
private TextField filterField;	
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

//@FXML
//private void searchUser(ActionEvent event) throws Exception{
//    ObservableList<User> userList=UserDAO.searchUser(txtDescription.getText());    
//	populateTable(userList);
//	clearFields();
//}

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
	clearFields();
	if (insertButton.isDisable())
	{
	insertButton.setDisable(false);
    }
	initialize();
}


private void clearFields() {
	txtDescription.setText("");
	txtUsername.setText("");
	txtPassword.setText("");
}


@FXML
private void initialize()throws Exception{
	//have to change later to approach 2 of below docs 
	//https://stackoverflow.com/questions/30334450/cannot-convert-from-string-to-observablevaluestring
	colDescription.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getDescription()));
	colUserName.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getUserName()));
	colPassword.setCellValueFactory(cellData->new ReadOnlyStringWrapper( cellData.getValue().getPassword()));
	//1
	FilteredList<User> filteredData = new FilteredList<>(UserDAO.getAllRecords(), p -> true);
	//2
	 filterField.textProperty().addListener((observable, oldValue, newValue) -> {
         filteredData.setPredicate(user -> {
             // If filter text is empty, display all persons.
             if (newValue == null || newValue.isEmpty()) {
                 return true;
             }
             
             // Compare first name and last name of every person with filter text.
             String lowerCaseFilter = newValue.toLowerCase();
             
             if (user.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                 return true; // Filter matches description.
             } else if (user.getUserName().toLowerCase().contains(lowerCaseFilter)) {
                 return true; // Filter matches user name.
             } else if (user.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                 return true; // Filter matches password.
             }
             clearFields();
             return false; // Does not match.
         });
     });
     
	//3
    SortedList<User> sortedData = new SortedList<>(filteredData);
    // 4. Bind the SortedList comparator to the TableView comparator.
    sortedData.comparatorProperty().bind(userTable.comparatorProperty());
	//ObservableList<User> userList=UserDAO.getAllRecords();
    populateTable(sortedData);
	//populateTable(userList);
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

@FXML
private void ChangePIN(ActionEvent event) throws Exception {
	
	
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/controller/ChangePin.fxml"));
		Scene scene= new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("PIN Management");
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.show();
			
}

}
