package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Spillet;
import view.InfoMessage;

public class MenuController {

	private Spillet model;
	private Stage owner;
	@FXML MenuItem nextLevel;
	
	public MenuController(Stage owner,Main main) {
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
	
	@FXML public void help(ActionEvent e) {
		new HelpController(owner);
	}
	
	@FXML public void about(ActionEvent e) {
		new AboutWinController(owner);
	}
}
