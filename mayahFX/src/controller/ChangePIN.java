/*
 * The function of this controller is validate old PIN and allow saving new PIN
 *
 */

package controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
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
	private Button btnSave;

	@FXML
	private void initialize() throws Exception {
		lblStatus.setVisible(false);
		btnSave.setDefaultButton(true);
	}

	@FXML
	private void updatePIN(ActionEvent event) throws Exception {

		try {
			if (!isAnyFieldsBlank()) {
				if (passOldPin.getText().equals(DBUtil.dbGetPin())) {
					UserDAO.updatePin(passOldPin.getText(), passNewPin.getText());
					lblStatus.setVisible(true);
					lblStatus.setText("PIN changed successfully");
				} else {
					lblStatus.setVisible(true);
					lblStatus.setText(passOldPin.getText());
					System.out.println("tomal" + DBUtil.dbGetPin() + "tomal");
				}
			}

			else {
				lblStatus.setVisible(true);
				lblStatus.setTextFill(Color.rgb(210, 39, 30));
				lblStatus.setText("Fields cannot be blank");
			}

		} catch (SQLException e) {
			System.out.println("Hit error while updating PIN" + e);
			e.printStackTrace();
			throw e;
		}

	}

	// validation checking if any text field is blank
	private boolean isAnyFieldsBlank() {
		boolean status = false;
		if (passOldPin.getText().isEmpty() || passNewPin.getText().isEmpty()) {
			status = true;
		}
		return status;
	}
}
