package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Fallgrav extends LabyrintRute {

	private int[] startPos;
	
	public Fallgrav(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	public void setStartPos(int[] startPos) {
		this.startPos=startPos;
	}

	@Override
	public boolean moveHere(Spiller s) {
		s.setPos(startPos);
		return true;
	}

	@Override
	public boolean moveHere(Blokk b) {
		//haandteres i blokk
		
		return true;
	}
	
	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		shape.setStyle("-fx-fill: transparent; -fx-stroke: red; -fx-stroke-width: 3; -fx-stroke-type: inside; -fx-stroke-dash-array: 10 10");
		return shape;
	}

	
}
