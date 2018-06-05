package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessage {

	public ErrorMessage(String errorCategory, String message) {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle("LabyrintSpill - Error");
		error.setHeaderText("Problem with " + errorCategory);
		error.setContentText(message);
		error.show();
	}
	
}
