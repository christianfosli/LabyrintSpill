package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import application.Main;
import javafx.scene.control.MenuBar;
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
	private View view;
	private MenuBar menuBar;
	private Main main;
	
	public Spillet(MenuBar menuBar, Main main) {

		this.menuBar=menuBar;
		this.main=main;
		spilleren = new Spiller();
	
		try {
			autoLoad();
		} catch (Exception e) {
			fileChooser = new FileChooser();
			fileRef = fileChooser.showOpenDialog(main.getOwner());
			e.printStackTrace();
		}

		try {
		labyrinten = new Labyrint(fileRef,this);
		} catch (IllegalArgumentException e) {
			new ErrorMessage("Setting up maze",e.getMessage());
		}
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
		else throw new FileNotFoundException();
	}
	
	/**
	 * Legg inn view, og flytter spiller til startposisjon
	 * @param view - klasse som har ansvar for aa vise laborinten
	 */
	public void initialize(View view) {
		this.view = view;
		moveToStart();
		this.view.makeLab();
		main.getKeyListener().setIsActive(true);
		
		//Vindustrls lyttere for automatisk resize:
		main.getRoot().widthProperty().addListener(e -> {
			view.reSize();
		});
		main.getRoot().heightProperty().addListener(e -> {
			view.reSize();
		});
	}

	/**
	 * Reloads maze from file (for performance reasons),
	 * and places everything back to usual`
	 */
	public void restart() {
		labyrinten=new Labyrint(fileRef,this);
		main.getRoot().getChildren().remove(view.getViewRegion());
		view = new LabViewGrid(this,main.getRoot());
		initialize(view);
		Region viewRegion = view.getViewRegion();
		main.getRoot().setCenter(viewRegion);
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
		
		if (labyrinten.getRute(posisjon).moveHere(spilleren)) view.movePlayer();
		
		if (!labyrinten.hasLights()) labyrinten.discoverRuter();
		
		if (labyrinten.getRute(posisjon) instanceof Utgang) {
			view.finish();
			return true;
		}return false;
	}
	
	public void moveToStart() {
		labyrinten.getRute(labyrinten.getStartPoint()).moveHere(spilleren);
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
}
