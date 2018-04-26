package model;

import javafx.scene.shape.Shape;
import spiller.Spiller;

public abstract class LabyrintRute {

	private int xPos;
	private int yPos;
	
	
	public LabyrintRute(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract boolean moveHere(Spiller s);
	
	public abstract boolean moveHere(Blokk b);
	
	public int getXpos() {
		return this.xPos;
	}
	
	public int getYpos() {
		return this.yPos;
	}
	
	public int[] getPos() {
		int[] position = new int[2];
		position[0]=xPos;
		position[1]=yPos;
		return position;
	}
	
	public void setPos(int[] pos) {
		xPos = pos[0];
		yPos = pos[1];
	}
	
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
	public abstract Shape draw();
}
