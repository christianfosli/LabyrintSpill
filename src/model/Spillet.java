package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import application.Main;
import controller.HeightListener;
import controller.WidthListener;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import spiller.Spiller;
import view.ErrorMessage;
import view.Labview;

public class Spillet {

	private Spiller spilleren;
	private Labyrint labyrinten;
	private FileChooser fileChooser;
	private File fileRef;
	private Labview view;
	private MenuBar menuBar;
	private Main main;
	private boolean lights;
	
	
	public Spillet(MenuBar menuBar, Main main) {

		this.menuBar=menuBar;
		this.main=main;
		spilleren = new Spiller();
		lights = true;
	
		try {
			autoLoad();
		} catch (Exception e) {
			fileChooser = new FileChooser();
			fileRef = fileChooser.showOpenDialog(main.getOwner());
			e.printStackTrace();
		}

		labyrinten = new Labyrint(fileRef,this);
	}

	/**
	 * Automatisk aapne fil ved navn 'labyrint.txt' i samme mappe som .JAR fil om den eksisterer
	 * @return true-filen eksisterer, er satt til fileREf  // false-filen eksisterer ikke
	 * @throws URISyntaxException
	 * @throws FileNotFoundException 
	 */
	public void autoLoad() throws URISyntaxException, FileNotFoundException {
		
		File thisPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		String filepath = thisPath.getParent() + File.separator + "labyrint.txt";
		File thisFile = new File(filepath);
		
		if (thisFile.exists()) {
			fileRef = thisFile;
			System.out.println("FILE EXISTS!");
		}
		else
			throw new FileNotFoundException();
	
	}
	
	/**
	 * Legg inn view, og flytter spiller til startposisjon
	 * @param view - klasse som har ansvar for aa vise laborinten
	 */
	public void initialize(Labview view) {
		this.view = view;
		moveToStart();
		this.view.makeLab();
		this.view.drawSpiller();
		main.getKeyListener().setIsActive(true);
		
		//Vindustrls lyttere for automatisk resize:
		main.getRoot().widthProperty().addListener(new WidthListener(view));
		main.getRoot().heightProperty().addListener(new HeightListener(view));
	}

	/**
	 * Reloads maze from file (for performance reasons),
	 * and places everything back to usual`
	 */
	public void restart() {
		labyrinten=new Labyrint(fileRef,this);
		main.getRoot().getChildren().remove(view.get());
		view = new Labview(this,main.getRoot());
		initialize(view);
		GridPane viewPane = view.get();
		main.getRoot().setCenter(viewPane);
	}
	
	public void loadNewLabyrint() {
		fileChooser = new FileChooser();
		String oldFilePath = fileRef.getPath(); //I tilfelle problemer med aa laste ny fil resette til gamle
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
	public boolean move(int deltaX, int deltaY) {
		int[] posisjon = new int[] {spilleren.getPos()[0],spilleren.getPos()[1]};
		posisjon[0] += deltaX;
		posisjon[1] += deltaY;
		
		if (labyrinten.getRute(posisjon).moveHere(spilleren))
				view.animateSpiller(deltaX, deltaY);
		
		if (!lights)
			view.switchOffTheLights();
		
		
		if (labyrinten.getRute(posisjon) instanceof Utgang) {
			view.gameFinished();
			return true;
		}return false;
	}
	
	public void moveToStart() {
		labyrinten.getRute(labyrinten.getStartPoint()).moveHere(spilleren);
	}
	
	public void toggleLights() {
		this.lights = !Boolean.valueOf(lights);
		
		if (lights)
			view.turnTheLightsOn();
		else {
			new ErrorMessage("nothing, but.. Warning: Turning off the lights may cause slowness/freezing", 
					"if you have problems please turn the lights back on");
			view.switchOffTheLights();
		}
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
	
	public Labview getView() {
		return view;
	}
	
}
