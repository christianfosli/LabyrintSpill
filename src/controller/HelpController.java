package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import view.ErrorMessage;

public class HelpController {
	
	private Stage stage;
	
	@FXML ComboBox<String> chooser;
	@FXML Label title;
	@FXML TextArea helpText;
	
	/**
	 * Controller aapner Help vindu
	 * @param owner
	 */
	public HelpController(Stage owner) {
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("/view/HelpWin.fxml"));
		loader.setController(this);
		
		try {
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.initOwner(owner);
			stage.setScene(scene);
			stage.setTitle("Help Labyrintspillet");
			helpText.setEditable(false);
			helpText.deselect();
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			new ErrorMessage("opening Help window", e.getMessage());
			e.printStackTrace();
		}	
	}
	
	@FXML public void loadFromFile(ActionEvent e) {
		String filename = "";
		//TODO: OPPDATER TIL STREAM TILSV ABOUTWIN
		if (chooser.getValue().equals("How to play"))
			filename = "/view/howtoPlay.md";
		else if (chooser.getValue().equals("Make your own maze"))
			filename = "/view/howtoMake.md";
		
		try {
		InputStream innStream = getClass().getResourceAsStream(filename);
		InputStreamReader reader = new InputStreamReader(innStream);
		BufferedReader inn = new BufferedReader(reader);
		
		helpText.clear();
		
		Object[] textlines = inn.lines().toArray();
		for (Object o : textlines) {
			if (o.toString().startsWith("#"))
				title.setText(o.toString().substring(2));
			else
				helpText.setText(helpText.getText()+o.toString()+"\n");
		}
		innStream.close();
		reader.close();
		inn.close();
		
		}catch (NullPointerException e2) {
			new ErrorMessage("Loading how-to-play from textfile", e2.getMessage());
			e2.printStackTrace();
		}catch (IOException e2){
			new ErrorMessage("For info - Error closing file!, i think :-o", e2.getMessage());
			e2.printStackTrace();
		}
	}

}
