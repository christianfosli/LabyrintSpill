package model;

import javafx.scene.shape.Shape;
import spiller.Spiller;

public abstract class LabyrintRute {
	private int xPos;
	private int yPos;
	private boolean discovered;
	protected String hiddenStyle = "-fx-fill: rgba(0,0,0,0.6); -fx-stroke: rgba(0,0,0,0.7);"
			+ " -fx-stroke-width: 1; -fx-stroke-type: inside;";
	
	public LabyrintRute(int xPos, int yPos, boolean lights) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.discovered = lights;
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
	public boolean isDiscovered() {
		return discovered;
	}
	
	public void setPos(int[] pos) {
		xPos = pos[0];
		yPos = pos[1];
	}
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public void discover() {
		discovered = true;
	}
	
	public abstract Shape draw();
}
