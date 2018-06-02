package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InfoMessage {

	public InfoMessage(String header, String message) {
		Alert error = new Alert(AlertType.INFORMATION);
		error.setTitle("LabyrintSpill - Information");
		
		error.setHeaderText(header);
		error.setContentText(message);
		
		error.show();
	}
	
}
