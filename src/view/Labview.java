package view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Spillet;

public class Labview {

	private GridPane grid;
	private BorderPane root;
	private Spillet model;
	private Shape spillerDrawing;
	private Text exitSign;
	private Text winning;
	private Shape darkShape;
	
	boolean blockAnimating = false;
	
	public Labview(Spillet model, BorderPane root) {
		this.root = root;
		this.model = model;
		grid = new GridPane();
		
		//Sette opp for dynamisk sizing:
		
		grid.prefWidthProperty().bind(this.root.widthProperty());
		grid.prefHeightProperty().bind(this.root.heightProperty().subtract(this.model.getMenuBar().heightProperty()));
	}

	public void makeLab() {
		
		if (!(winning == null)) //Fjerne 'You made it' ved restart
			grid.getChildren().remove(winning);
		
		int antBredde = model.getLabyrinten().getWidth();
		int antHoeyde = model.getLabyrinten().getHeight();
		
		for (int x=0; x<model.getLabyrinten().getWidth(); x++)
			for (int y = 0; y<model.getLabyrinten().getHeight(); y++) {
				//Tegne ruter:
				Rectangle labRute =(Rectangle) model.getLabyrinten().getRuter()[x][y].draw();
				//Dynamisk binde storrelse:
				labRute.heightProperty().bind(grid.prefHeightProperty().divide(antHoeyde));
				labRute.widthProperty().bind(grid.prefWidthProperty().divide(antBredde));
				//Legge til i grid:
				grid.add(labRute, x, y);
			}
		
		drawExitSign();
		
	}
	
	public void reDraw(int[] pos1, int[] pos2) {
		Rectangle labRute1 = (Rectangle) model.getLabyrinten().getRute(pos1).draw();
		Rectangle labRute2 = (Rectangle) model.getLabyrinten().getRute(pos2).draw();
		
		labRute1.widthProperty().bind(grid.prefWidthProperty().divide(model.getLabyrinten().getWidth()));
		labRute1.heightProperty().bind(grid.prefHeightProperty().divide(model.getLabyrinten().getHeight()));
		
		labRute2.widthProperty().bind(grid.prefWidthProperty().divide(model.getLabyrinten().getWidth()));
		labRute2.heightProperty().bind(grid.prefHeightProperty().divide(model.getLabyrinten().getHeight()));
		
		grid.add(labRute1, pos1[0], pos1[1]);
		grid.add(labRute2, pos2[0], pos2[1]);
		
	}
	
	/**
	 * Redraw elements which do not scale dynamically:
	 */
	public void reSize() {
		drawSpiller();
		drawExitSign();
	}
	
	/**
	 * Add "Exit" text over utang rute
	 */
	public void drawExitSign() {
		int[] pos = model.getLabyrinten().getSluttPoint();

		if (exitSign != null)
			grid.getChildren().remove(exitSign);
		
		exitSign = new Text("Exit");
		exitSign.setFont(new Font(getLowestRuteSize()*0.60));
		exitSign.setFill(Color.WHITE);
		exitSign.setStroke(Color.WHITE);
		grid.add(exitSign, pos[0], pos[1]);
	}
	
	public void drawSpiller() {
		int xPos = model.getSpilleren().getPos()[0];
		int yPos = model.getSpilleren().getPos()[1];
		int size = getLowestRuteSize();
		
		
		if (spillerDrawing != null) 
			grid.getChildren().remove(spillerDrawing);
		
		spillerDrawing = model.getSpilleren().draw(size);
		grid.add(spillerDrawing, xPos, yPos);
	
	}
	
	public void animateSpiller(int deltaX, int deltaY) {
		double ruteWidth = grid.getWidth() / grid.getColumnCount();
		double ruteHeight = grid.getHeight() / grid.getRowCount();
		
		TranslateTransition tt = new TranslateTransition(Duration.millis(100), spillerDrawing);
		tt.setToX(deltaX*ruteWidth);
		tt.setToY(deltaY*ruteHeight);
		tt.play();
		
		tt.setOnFinished((ActionEvent event) -> {
			drawSpiller();
		});
	}
	
	/**
	 * Nyttig til aa finne stoerrelse til Shapes som kun tar inn 1 stoerrelse parameter
	 * @return int lavest av ruteBredde og ruteHoyde, rundet ned
	 */
	private int getLowestRuteSize() {
		double size;
		
		if (grid.getPrefHeight() < grid.getPrefWidth())
			size = grid.getPrefHeight() / grid.getRowCount();
		else
			size = grid.getPrefWidth() / grid.getColumnCount();
		
		return (int) Math.floor(size*0.9); 
	}
	
	

	public void gameFinished() {
		Font font = new Font((int)Math.round(grid.getWidth()*0.07));
		
		winning = new Text("YAAY! U MADE IT! ");
		winning.setFont(font);
		winning.setFill(Color.BLUE);
		winning.setStroke(Color.AQUAMARINE);
		winning.setStrokeWidth(5);
		
		grid.add(winning, 0, 0,grid.getColumnCount(),grid.getRowCount()-1);
	}
	
	public void gameNoLongerFinished() {
		
		if (grid.getChildren().contains(winning))
			grid.getChildren().remove(winning);
		
	}
	
	public void switchOffTheLights() {
		
		if (darkShape == null) {
			Rectangle dark = new Rectangle();
			dark.setStyle("-fx-fill: darkslategray;");
			dark.widthProperty().bind(grid.widthProperty());
			dark.heightProperty().bind(grid.heightProperty());
			darkShape = dark;
		}else
			grid.getChildren().remove(darkShape);
			
		darkShape.prefWidth(grid.widthProperty().get());
		darkShape.prefHeight(grid.heightProperty().get());
		Circle light = new Circle();
		light.setRadius(getLowestRuteSize()*2);
		light.setCenterX(grid.getWidth()*model.getSpilleren().getPos()[0]/grid.getColumnCount());
		light.setCenterY(grid.getHeight()*model.getSpilleren().getPos()[1]/grid.getRowCount());
		darkShape =Shape.subtract(darkShape, light);
		grid.add(darkShape, 0, 0, grid.getColumnCount(), grid.getRowCount());
		
	}
	
	public void turnTheLightsOn() {
		if (darkShape != null)
			grid.getChildren().remove(darkShape);
	}
	
	public GridPane get() {
		return grid;
	}
	
	public Shape getSpillerDrawing() {
		return spillerDrawing;
	}

	public void setIsBlockAnimating(boolean blockAnimating) {
		this.blockAnimating=blockAnimating;
	}
	
	public boolean getIsBlockAnimating() {
		return blockAnimating;
	}
	
}
