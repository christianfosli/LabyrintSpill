package view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Spillet;

public class LabViewGrid implements View{

	private GridPane grid;
	private BorderPane root;
	private Spillet model;
	private Shape spiller;
	private Text exitSign;
	private Text winning;
	private Text subMessage;
	
	public LabViewGrid(Spillet model, BorderPane root) {
		this.root = root;
		this.model = model;
		grid = new GridPane();
		
		//Dynamic resizing of GridPane:
		grid.prefWidthProperty().bind(this.root.widthProperty());
		grid.prefHeightProperty().bind(this.root.heightProperty().subtract(this.model.getMenuBar().heightProperty()));
		
		//Redraw items which do not scale automatically when window changes size
		root.widthProperty().addListener(e -> {
			reSize();
		});
		root.heightProperty().addListener(e -> {
			reSize();
		});
	}

	@Override
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
		drawSpiller();
	}
	
	/**
	 * Redraw elements which do not scale dynamically:
	 */
	@Override
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
		
		if (spiller != null)
			grid.getChildren().remove(spiller);
		
		spiller = model.getSpilleren().draw(size);
		grid.add(spiller, xPos, yPos);
	}
	
	private int getLowestRuteSize() {
		double size;
		
		if (grid.getPrefHeight() < grid.getPrefWidth())
			size = grid.getPrefHeight() / grid.getRowCount();
		else
			size = grid.getPrefWidth() / grid.getColumnCount();
		
		return (int) Math.floor(size*0.9); 
	}
	
	@Override
	public void addRute(Shape shape, int[] pos) {
		Rectangle rute =(Rectangle)shape; 
		//Dynamisk binde storrelse:
		rute.heightProperty().bind(grid.prefHeightProperty().divide(model.getLabyrinten().getHeight()));
		rute.widthProperty().bind(grid.prefWidthProperty().divide(model.getLabyrinten().getWidth()));
		//Legge til i grid:
		grid.add(rute, pos[0], pos[1]);
	}

	@Override
	public Node getNode(int[] pos) throws IllegalArgumentException{
		for (Node node : grid.getChildren())
			if (GridPane.getColumnIndex(node) == pos[0]
					&& GridPane.getRowIndex(node) == pos[1])
				return node;
		throw new IllegalArgumentException("No node at position " + pos[0] + ", " + pos[1]);
	}

	@Override
	public void movePlayer() {
		GridPane.setColumnIndex(spiller, model.getSpilleren().getPos()[0]);
		GridPane.setRowIndex(spiller, model.getSpilleren().getPos()[1]);
	}
	
	@Override
	public void swapNodes(int[] pos1, int[] pos2) {
		Node node1 = getNode(pos1);
		Node node2 = getNode(pos2);
		
		GridPane.setColumnIndex(node1, pos2[0]);
		GridPane.setRowIndex(node1, pos2[1]);
		
		GridPane.setColumnIndex(node2, pos1[0]);
		GridPane.setRowIndex(node2, pos1[1]);
	}
	
	@Override
	public void replaceNode(int[] pos, Shape replaceWith) {
		Node toBeRemoved = getNode(pos);
		addRute(replaceWith, pos);
		grid.getChildren().remove(toBeRemoved);
		drawSpiller();
	}
	
	@Override
	public void finish(int steps, int rekord) {
		Font winFont = new Font((int)Math.round(grid.getWidth()*0.07));
		Font subFont = new Font((int)Math.round(grid.getWidth()*0.05));
		
		winning = new Text("YAAY! U MADE IT! ");
		winning.setFont(winFont);
		winning.setFill(Color.BLUE);
		winning.setStroke(Color.AQUAMARINE);
		winning.setStrokeWidth(5);
		
		subMessage = new Text(String.format("You used %d steps. Record is %d",
				steps,rekord));
		subMessage.setFont(subFont);
		subMessage.setFill(Color.BISQUE);
		subMessage.setStroke(Color.BLACK);
		subMessage.setStrokeWidth(1);
		
		grid.add(winning, 0, 0,grid.getColumnCount(),grid.getRowCount()-1);
		grid.add(subMessage, 0, (int)(grid.getRowCount()/2),
				grid.getColumnCount(),grid.getRowCount()-1);
	}

	@Override
	public Region getViewRegion() {
		return grid;
	}
}
