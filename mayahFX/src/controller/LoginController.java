/*
 * the function of this controller is validate if PIN is ok and call main user interface scene
 *
 */

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.DBUtil;

public class LoginController {
	@FXML
	private TextField txtPIN;
	// this label will show various message to users
	@FXML
	private Label lblStatus;
	@FXML
	private Button btnLogin;

	@FXML
	private void initialize() throws Exception {
		lblStatus.setVisible(false);
		btnLogin.setDefaultButton(true);
		// to implement prompt text while the PIN filed is even focused
		PseudoClass empty = PseudoClass.getPseudoClass("empty");
		txtPIN.pseudoClassStateChanged(empty, true);
		txtPIN.textProperty().addListener((obs, oldText, newText) -> {
			txtPIN.pseudoClassStateChanged(empty, newText.isEmpty());
		});
	}

	// to call main scene where users will do most of work
	@FXML
	private void login(ActionEvent event) throws Exception {
		if (!txtPIN.getText().isEmpty()) {

			if (txtPIN.getText().equals(DBUtil.dbGetPin())) {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/controller/Fx1.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Joyee's Password Management System");
				primaryStage.resizableProperty().setValue(Boolean.FALSE);
				primaryStage.show();
				// to close the login scene while the main scene will open
				closeLogin();
			} else {
				lblStatus.setVisible(true);
				lblStatus.setTextFill(Color.rgb(210, 39, 30));
				lblStatus.setText("PIN mismatch,try again");
			}

		} else {
			lblStatus.setVisible(true);
			lblStatus.setTextFill(Color.rgb(210, 39, 30));
			lblStatus.setText("PIN cannot be blank");
		}
	}

	//
	private void closeLogin() {
		// this means the stage is the one that has txtPIN object
		Stage stage = (Stage) txtPIN.getScene().getWindow();
		// closing the login stage
		stage.close();
	}

}
