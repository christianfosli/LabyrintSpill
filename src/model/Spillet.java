package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import application.Main;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import spiller.Spiller;
import view.ErrorMessage;
import view.LabViewGrid;
import view.View;

public class Spillet {
	private Spiller spilleren;
	private Labyrint labyrinten;
	private FileChooser fileChooser;
	private File fileRef;
	private String levelPath;
	private int currentLevel;
	private View view;
	private MenuBar menuBar;
	private Main main;
	
	public Spillet(MenuBar menuBar, Main main) {
		this.menuBar=menuBar;
		this.main=main;
		spilleren = new Spiller();
		currentLevel=1;
	
		try { 				// Try to load first level on launch
			setLevelPath();
			autoLoad();
		} catch (Exception e) {
			fileChooser = new FileChooser();
			fileRef = fileChooser.showOpenDialog(main.getOwner());
			e.printStackTrace();
		}

		try { 				// Creates maze
		labyrinten = new Labyrint(fileRef,this);
		initialize();		// Moves player to start, initializes view
		} catch (IllegalArgumentException e) {
			new ErrorMessage("Setting up maze",e.getMessage());
		}
	}

	public void restart() {
		// Reloads maze from file:
		labyrinten=new Labyrint(fileRef,this);
		// removes previous view (GridPane with maze)
		main.getRoot().getChildren().remove(view.getViewRegion());
		initialize();
	}

	public void loadNextLevel() {
		this.currentLevel++;
		try {
			autoLoad();
			restart();
		}catch (Exception e) {
			new ErrorMessage("loading level"+currentLevel,e.getMessage());
			this.currentLevel--;
		}
	}
	
	public void loadPreviousLevel() {
		this.currentLevel--;
		try {
			if (currentLevel == 0) throw new RuntimeException("You are already at the first level - level "+currentLevel+1);
			autoLoad();
			restart();
		}catch (Exception e) {
			new ErrorMessage("loading level "+currentLevel,e.getMessage());
			this.currentLevel++;
		}
	}
	
	public void loadNewLabyrint() {
		//Opens a File Chooser, and lets user specify which file to open
		
		fileChooser = new FileChooser();
		String oldFilePath = fileRef.getPath(); //If problems go back to oldFilePath
		try {
			fileRef = fileChooser.showOpenDialog(main.getOwner());
			if (fileRef == null)
				throw new NullPointerException("User has not selected file");
			restart();
		} catch (NullPointerException e){
			fileRef = new File(oldFilePath);
			System.out.println("File chooser dialog returned null");
			System.out.println("Not making err message as assumed intentional");
			return;
		}catch (IllegalArgumentException e) {
			fileRef = new File(oldFilePath);
			//view = new Labview(this,main.getRoot());
			restart();
			new ErrorMessage("Loading laborynt from file. \nPrevious file reloaded",e.getMessage());
		}
	}
	
	/**
	 * Flytte spiller langs labyrinten 
	 * @param deltaX
	 * @param deltaY
	 * @return true if moved to end (finishblock)
	 */
	public void move(int deltaX, int deltaY) {
		if (labyrinten.spillerAtExit()) return; //Don't move when maze completed
		
		int[] posisjon = new int[] {spilleren.getPos()[0],spilleren.getPos()[1]};
		posisjon[0] += deltaX;
		posisjon[1] += deltaY;
		
		if (labyrinten.getRute(posisjon).moveHere(spilleren)) view.movePlayer();
		if (!labyrinten.hasLights()) labyrinten.discoverRuter();
		if (labyrinten.spillerAtExit()) {//@WINNING!!!:
			view.finish(spilleren.getPoeng(),labyrinten.getWinner().getPoeng());
			if (spilleren.getPoeng() <= labyrinten.getMaxStepsForScoreList()) 
				makeWinner();
		}
	}
	
	private void makeWinner() {
		TextInputDialog scoreDialog = new TextInputDialog();
		scoreDialog.setHeaderText("You made the high score list!");
		scoreDialog.setContentText("Please enter your name");
		scoreDialog.showAndWait().ifPresent(response -> {
			if (response.equals("")) response = "NONAME";
			spilleren.setNavn(response);
			labyrinten.addWinner(spilleren);
		});;
	}
	
	private void autoLoad() throws URISyntaxException, FileNotFoundException {
		//Opens and loads the current level in specified levelPath
		
		File thisFile = new File(levelPath + "level" + currentLevel + ".txt");
		if (thisFile.exists()) {
			fileRef = thisFile;
			System.out.println("FILE EXISTS! - level "+currentLevel);
		}
		else throw new FileNotFoundException("Unable to load level "+currentLevel+" at " + thisFile);
	}
	
	private void setLevelPath() throws URISyntaxException{
		File thisPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		levelPath = thisPath.getParent() + File.separator + "levels" + File.separator;
	}
	
	/**
	 * Legg inn view, flytter spiller til startposisjon, setter opp auto-resize
	 * @param view - klasse som har ansvar for aa vise laborinten
	 */
	private void initialize() {
		//Move spiller to start:
		labyrinten.getRute(labyrinten.getStartPoint()).moveHere(spilleren);
		spilleren.setPoeng(0);
		//Set up view:
		this.view = new LabViewGrid(this,main.getRoot());
		this.view.makeLab();
		Region viewRegion = view.getViewRegion();
		main.getRoot().setCenter(viewRegion);
	}
	
	public Spiller getSpilleren() {
		return spilleren;
	}
	public Labyrint getLabyrinten() {
		return labyrinten;
	}
	public MenuBar getMenuBar() {
		return menuBar;
	}
	public View getView() {
		return view;
	}
	public boolean hasNextLevel() {
		return (new File(levelPath + "level" + (currentLevel+1) + ".txt").exists());
	}
}
