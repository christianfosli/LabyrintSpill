package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Vegg extends LabyrintRute {

	public Vegg(int xPos, int yPos, boolean lights) {
		super(xPos, yPos, lights);
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
		if (isDiscovered()) shape.setStyle(" -fx-fill: darkgreen; -fx-stroke: olive; -fx-stroke-width: 1; -fx-stroke-type: inside; ");
		else shape.setStyle(hiddenStyle);
		return shape;
	}

}
