package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Gang extends LabyrintRute{
	
	public Gang(int xPos, int yPos, boolean lights) {
		super(xPos, yPos, lights);
	}

	@Override
	public boolean moveHere(Spiller s) {
		s.setPos(this.getPos());
		return true;
	}

	@Override
	public boolean moveHere(Blokk b) {
		int nyXpos = b.getXpos();
		int nyYpos = b.getYpos();
		
		b.setPos(getXpos(), getYpos());
		this.setPos(nyXpos, nyYpos);
		
		return true;
	}
	
	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		if (isDiscovered()) shape.setStyle(" -fx-fill: burlywood;");
		else shape.setStyle(hiddenStyle);
		return shape;
	}

}
