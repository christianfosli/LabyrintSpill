package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Spillet;
import spiller.Spiller;
import view.ErrorMessage;
import view.InfoMessage;

public class Controller {

	private Spillet model;
	private Stage owner;
	private AboutWin aboutWin;
	private HelpWin helpWin;
	@FXML MenuItem nextLevel;
	
	public Controller(Stage owner,Main main) {
		this.owner = owner;
	}
	
	public void setModel(Spillet model) {
		this.model = model;
	}
	
	@FXML public void newGame(ActionEvent e) {
		model.restart();
	}
	
	@FXML public void nextLevel(ActionEvent e) {
		model.loadNextLevel();
		if (!model.hasNextLevel()) {
			nextLevel.setDisable(true);
			new InfoMessage("You have selected the last level",
					"You can create more levels, if you like. See the help section");
		}
	}
	
	@FXML public void previousLevel(ActionEvent e) {
		model.loadPreviousLevel();
		if (nextLevel.isDisable()) if (model.hasNextLevel()) nextLevel.setDisable(false);
	}
	
	@FXML public void close(ActionEvent e) {
		owner.close();
	}
	
	@FXML public void open(ActionEvent e) {
		model.loadNewLabyrint();
	}
	
	@FXML public void scores(ActionEvent e) {
		String scoreStr = "";
		for (Spiller s : model.getLabyrinten().getWinners()) scoreStr += s + "\n";
		Alert scoreWin = new Alert(AlertType.INFORMATION,scoreStr);
		scoreWin.setHeaderText("High score list for current level");
		scoreWin.show();
	}
	
	@FXML public void help(ActionEvent e) {
		if (helpWin == null) helpWin = new HelpWin();
		helpWin.show();
	}
	
	@FXML public void about(ActionEvent e) {
		if (aboutWin == null) aboutWin = new AboutWin();
		aboutWin.show();
	}
	
	public String loadText(String filename) throws FileNotFoundException, IOException {
		String text = "";
		
		try ( InputStream innStream = getClass().getResourceAsStream(filename);
		InputStreamReader reader = new InputStreamReader(innStream);
		BufferedReader inn = new BufferedReader(reader);
				){
		Object[] lines = inn.lines().toArray();
		for (Object o: lines) text += o.toString() +"\n";
		}
		return text;
	}
	
	private class AboutWin{
		Stage stage;
		@FXML TextArea textArea;
		
		public AboutWin() {
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
			} catch (IOException e) {
				new ErrorMessage("loading about window", e.getMessage());
			}
			
		}
		
		public void show() {
			stage.show();
		}
		
		@FXML public void placeText(ActionEvent e) throws FileNotFoundException, IOException{
			textArea.setText(loadText("/view/about.md"));
			textArea.setEditable(false);
		}
	}
	
	private class HelpWin{
		private Stage stage;
		@FXML ComboBox<String> chooser;
		@FXML Label title;
		@FXML TextArea helpText;

		public HelpWin() {
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
			} catch (IOException e) {
				new ErrorMessage("opening Help window", e.getMessage());
			}	
		}
		
		public void show() {
			stage.show();
		}
		
		@FXML public void placeText(ActionEvent e) {
			String filename="";
			if (chooser.getValue().equals("How to play"))
				filename = "/view/howtoPlay.md";
			else if (chooser.getValue().equals("Make your own maze"))
				filename = "/view/howtoMake.md";
			try {
			helpText.setText(loadText(filename));
			}catch (IOException err) {
				new ErrorMessage("loading help text",err.getMessage());
			}

		}
	}
}
