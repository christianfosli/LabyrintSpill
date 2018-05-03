package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Vegg extends LabyrintRute {

	public Vegg(int xPos, int yPos) {
		super(xPos, yPos);
	}

	@Override
	public boolean moveHere(Spiller s) {
		return false;
	}

	@Override
	public boolean moveHere(Blokk b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		shape.setStyle(" -fx-fill: darkgreen; -fx-stroke: olive; -fx-stroke-width: 1; -fx-stroke-type: inside; ");
		return shape;
	}

}
