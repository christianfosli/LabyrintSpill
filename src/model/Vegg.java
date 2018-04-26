package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Vegg extends LabyrintRute {

	public Vegg(int xPos, int yPos) {
		super(xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean moveHere(Spiller s) {
		System.out.println("Du traff en vegg ved pos "+ s.getPos()[0] + "," + s.getPos()[1]);
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
