package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Spillet;

public class MenuController {

	private Spillet model;
	private Stage owner;
	
	@FXML Menu file;
	@FXML MenuItem newGameItem;
	@FXML MenuItem openItem;
	@FXML MenuItem closeItem;
	
	@FXML Menu help;
	@FXML MenuItem helpItem;
	@FXML MenuItem aboutItem;
	
	public MenuController(Stage owner,Main main) {
		this.owner = owner;
	}
	
	public void setModel(Spillet model) {
		this.model = model;
	}
	
	@FXML public void newGame(ActionEvent e) {
		model.restart();
	}
	
	@FXML public void close(ActionEvent e) {
		owner.close();
	}
	
	@FXML public void open(ActionEvent e) {
		model.loadNewLabyrint();
	}
	
	@FXML public void toggleLights(ActionEvent e) {
		//TODO
		System.out.println("toggleLights not yet working...");
	}
	
	@FXML public void help(ActionEvent e) {
		new HelpController(owner);
	}
	
	@FXML public void about(ActionEvent e) {
		new AboutWinController(owner);
	}
}
