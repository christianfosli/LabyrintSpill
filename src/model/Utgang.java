package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Utgang extends LabyrintRute{

	public Utgang(int xPos, int yPos) {
		super(xPos, yPos, true);
	}

	@Override
	public boolean moveHere(Spiller s) {
		s.setPos(this.getPos());
		s.setPoeng(s.getPoeng()+1);
		s.setFortsette(false);
		return true;
	}

	@Override
	public boolean moveHere(Blokk b) {
		return false;
	}

	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		//shape.setFill(Color.RED);
		shape.setStyle(" -fx-fill: red; -fx-stroke: white; -fx-stroke-width: 1; -fx-stroke-type: inside; ");
		return shape;
	}
}
