package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;

public class Blokk extends LabyrintRute {

	private Labyrint labyrinten;
	
	public Blokk(int xPos, int yPos, Labyrint labyrinten) {
		super(xPos, yPos);
		this.labyrinten=labyrinten;
	}

	@Override
	public boolean moveHere(Spiller s) {
		
		int oldXPosSelf = this.getXpos();
		int oldYPosSelf = this.getYpos();
		
		int xPosSelf;
		int yPosSelf;
		
		if (s.getPos()[0] < this.getXpos() && s.getPos()[1]==this.getYpos()) {
			// Spiller --> Blokk
			xPosSelf = this.getXpos()+1;
			yPosSelf = this.getYpos();
			
		}else if (s.getPos()[0] > this.getXpos() && s.getPos()[1]==this.getYpos()) {
			// Blokk <-- Spiller
			xPosSelf = this.getXpos()-1;
			yPosSelf = this.getYpos();
			
		}else if (s.getPos()[0] == this.getXpos() && s.getPos()[1] < this.getYpos()) {
			// Spiller 
			// \> Blokk
			xPosSelf = this.getXpos();
			yPosSelf = this.getYpos()+1;
			
		}else if (s.getPos()[0] == this.getXpos() && s.getPos()[1] > this.getYpos()) {
			// Blokk
			// ^Spiller
			xPosSelf = this.getXpos();
			yPosSelf = this.getYpos()-1;
		}else
			return false;
		
		if (labyrinten.getRute(xPosSelf,yPosSelf).moveHere(this)) {
			s.setPos(new int[] {oldXPosSelf,oldYPosSelf});
			if (labyrinten.getRute(xPosSelf, yPosSelf) instanceof Fallgrav ||
					labyrinten.getRute(xPosSelf,yPosSelf) instanceof Teleport)
				labyrinten.reDraw(this, new Gang(this.getXpos(),this.getYpos()));
			labyrinten.reDraw(this, labyrinten.getRute(this.getXpos(),this.getYpos()));
			return true;
		}return false;//TODO tror dette blir riktig.....
	}
	
	@Override
	public boolean moveHere(Blokk b) {
	
		return false;
		
	}

	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		shape.setStyle(" -fx-fill: gray; -fx-stroke: darkgray; -fx-stroke-width: 1; -fx-stroke-type: inside;");
		return shape;
	}

	
}
