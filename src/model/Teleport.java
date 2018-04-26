package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Teleport extends LabyrintRute{
	int [] teleportToPos;
	
	public Teleport(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	public void setTeleportPos(int[] teleportToPos) {
		this.teleportToPos = teleportToPos;
	}

	@Override
	public boolean moveHere(Spiller s) {
		s.setPos(teleportToPos);
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
		shape.setStyle("-fx-fill: transparent; -fx-stroke: darkviolet; -fx-stroke-width: 3; -fx-stroke-type: inside; -fx-stroke-dash-array: 10 10");
		return shape;
	}

}
