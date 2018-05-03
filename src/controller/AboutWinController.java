package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import view.ErrorMessage;

public class AboutWinController {
	Stage stage;
	@FXML TextArea textArea;
	
	/**
	 * Konstruktoer laster inn tekstfil og aapner vindu
	 * @param owner
	 */
	public AboutWinController(Stage owner){
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("/view/AboutWin.fxml"));
		loader.setController(this);
		
		try {
			Parent root = loader.load();
			placeText(new ActionEvent());
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.initOwner(owner);
			stage.setScene(scene);
			stage.setTitle("About Labyrintspillet");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			new ErrorMessage("loading about window", e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@FXML public void placeText(ActionEvent e) throws FileNotFoundException,IOException {
		textArea.setText(loadText());
		textArea.setEditable(false);
	}
	
	public String loadText() throws FileNotFoundException,IOException {
		String text = "";
		
		InputStream innStream = getClass().getResourceAsStream("/view/about.md");
		InputStreamReader reader = new InputStreamReader(innStream);
		BufferedReader inn = new BufferedReader(reader);
		
		Object[] lines = inn.lines().toArray();
		for (Object o: lines) {
			text += o.toString() + "\n";
		}
		
		innStream.close();
		reader.close();
		inn.close();
		
		return text;
	}
	
}
