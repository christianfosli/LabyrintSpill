package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Gang extends LabyrintRute{
	
	public Gang(int xPos, int yPos) {
		super(xPos, yPos);
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
		shape.setStyle(" -fx-fill: burlywood;");
		return shape;
	}
	
	

}
