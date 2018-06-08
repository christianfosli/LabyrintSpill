package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Teleport extends LabyrintRute{
	int [] teleportToPos;
	
	public Teleport(int xPos, int yPos, boolean lights) {
		super(xPos, yPos, lights);
	}
	
	public void setTeleportPos(int[] teleportToPos) {
		this.teleportToPos = teleportToPos;
	}

	@Override
	public boolean moveHere(Spiller s) {
		s.setPos(teleportToPos);
		s.setPoeng(s.getPoeng()+1);//Used to count steps
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
		if (isDiscovered()) shape.setStyle("-fx-fill: transparent; -fx-stroke: darkviolet; -fx-stroke-width: 3; -fx-stroke-type: inside; -fx-stroke-dash-array: 10 10");
		else shape.setStyle(hiddenStyle);
		return shape;
	}

}
