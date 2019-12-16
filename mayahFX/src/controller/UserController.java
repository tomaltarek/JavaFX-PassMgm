/*
 * In JavaFx application, every scene should have one controller. In our application we have
 * three scenes, therefore have three controllers. This UserController is the main one. If you
 * use scene builder, you have to mention which controllers the scene objects referring to. 
 * In a crud application, the controller of the scene that deals with database may have following methods:
 * 1. Methods for insert update delete
 * 2. Event handler methods (All action event methods)
 * 3. Validation methods
 * 4.One initialize method (defines properties while scene will open)
 * 5.Any other methods that deal with the scene. 

 */
package controller;

import java.sql.SQLException;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
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

public class UserController {
	// the @FXML notation means the item has an fxid in scene
	@FXML
	private TextField filterField;
	private String oldDesc = null;
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
	private TableView userTable;

	@FXML
	private void insertUser(ActionEvent event) throws Exception {
		try {
			if (!isAnyFieldsBlank()) { 
				// replace is used to avoid error on string with single quote
				UserDAO.insertUser(txtDescription.getText().replace("'", "''"),
						txtUsername.getText().replace("'", "''"), txtPassword.getText().replace("'", "''"));
				refresh();
			}

		} catch (SQLException e) {
			System.out.println("Hit error while inserting" + e);

			e.printStackTrace();
			throw e;
		}

	}

	@FXML
	private void updateUser(ActionEvent event) throws Exception {
		try {
			if (!isAnyFieldsBlank()) {
				// replace is used to avoid error if string contains signle qoute
				UserDAO.updatetUser(txtDescription.getText().replace("'", "''"),
						txtUsername.getText().replace("'", "''"), txtPassword.getText().replace("'", "''"),
						oldDesc.replace("'", "''"));
				refresh();
			}

		} catch (SQLException e) {
			System.out.println("Hit error while updating" + e);
			e.printStackTrace();
			throw e;
		}
	}

	@FXML
	private void deleteUser(ActionEvent event) throws Exception {
		try {

			if (!(txtDescription.getText().isEmpty())) {
				UserDAO.deletetUser(txtDescription.getText().replace("'", "''"));
				refresh();
			}

		} catch (SQLException e) {
			System.out.println("Hit error while deleting" + e);
			e.printStackTrace();
			throw e;
		}
	}

	@FXML
	public void clickItem(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{
			User usr = (User) userTable.getSelectionModel().getSelectedItem();
			txtDescription.setText(usr.getDescription());
			txtUsername.setText(usr.getUserName());
			txtPassword.setText(usr.getPassword());
			insertButton.setDisable(true);
			oldDesc = usr.getDescription();
		}
	}

	@FXML
	private void refresh() throws Exception {
		clearFields();
		if (insertButton.isDisable()) {
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
	private void initialize() throws Exception {	
		colDescription.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
		colUserName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));
		colPassword.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPassword()));
		// to implement the tableview filterable 
		// 1
		FilteredList<User> filteredData = new FilteredList<>(UserDAO.getAllRecords(), p -> true);
		// 2
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

		// 3
		SortedList<User> sortedData = new SortedList<>(filteredData);
		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(userTable.comparatorProperty());
		// ObservableList<User> userList=UserDAO.getAllRecords();
		populateTable(sortedData);

		// to implement prompt text functionality while the field is even focused 
		filterField.setPromptText("Search by Description, Userid or Passwords                                    🔍 ");

		PseudoClass empty = PseudoClass.getPseudoClass("empty");
		filterField.pseudoClassStateChanged(empty, true);
		filterField.textProperty().addListener((obs, oldText, newText) -> {
			filterField.pseudoClassStateChanged(empty, newText.isEmpty());
		});

	}

	private void populateTable(ObservableList<User> userList) {

		userTable.setItems(userList);

	}

	// validation checking if any text field is blank
	private boolean isAnyFieldsBlank() {
		boolean status = false;
		if (txtDescription.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
			status = true;
		}
		return status;
	}

	// to call another scene for PIN management 
	@FXML
	private void ChangePIN(ActionEvent event) throws Exception {

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/controller/ChangePin.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("PIN Management");
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.show();

	}

}
