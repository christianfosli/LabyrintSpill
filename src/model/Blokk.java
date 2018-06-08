package model;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import spiller.Spiller;
import view.ErrorMessage;

public class Blokk extends LabyrintRute {
	private Labyrint labyrinten;
	int previousXpos;
	int previousYpos;
	
	public Blokk(int xPos, int yPos, boolean lights, Labyrint labyrinten) {
		super(xPos, yPos, lights);
		this.labyrinten=labyrinten;
	}

	@Override
	public boolean moveHere(Spiller s) {
		previousXpos = this.getXpos();
		previousYpos = this.getYpos();
		LabyrintRute pushedIntoRute;
		
		try {
			pushedIntoRute = whatAmIPushedInto(s.getPos()[0], s.getPos()[1]);
		}catch (RuntimeException e) {
			new ErrorMessage("moving spiller and/or blokk", "Blokk at "+this.getXpos() +
					", " + this.getYpos() + ": " + e.getMessage());
			return false;
		}
		
		if (moveSelf(pushedIntoRute)) {
			s.setPos(new int[] {previousXpos, previousYpos});
			s.setPoeng(s.getPoeng()+1);//Used to count steps
			return true;
		}return false;
	}
	
	private boolean moveSelf(LabyrintRute pushedIntoRute) {
		boolean moved = pushedIntoRute.moveHere(this);
		if (moved) {
			if (pushedIntoRute instanceof Gang)
				labyrinten.swapRuter(this, pushedIntoRute);
			else
				labyrinten.replaceRute(this, new Gang(previousXpos, previousYpos, true));
			return true;
		}return false;
	}
	
	private LabyrintRute whatAmIPushedInto(int pushersXpos, int pushersYpos) {
		
		//Spiller -> Blokk
		if (pushersXpos < this.getXpos() && pushersYpos==this.getYpos())
			return labyrinten.getRute(this.getXpos()+1, this.getYpos());
			
		// Blokk <- Spiller
		else if (pushersXpos > this.getXpos() && pushersYpos==this.getYpos())
			return labyrinten.getRute(this.getXpos()-1, this.getYpos());
			
		// Spiller [ned] blokk
		else if (pushersXpos == this.getXpos() && pushersYpos < this.getYpos())
			return labyrinten.getRute(this.getXpos(), this.getYpos()+1);
			
		// Spiller [opp] Blokk
		else if (pushersXpos == this.getXpos() && pushersYpos > this.getYpos())
			return labyrinten.getRute(this.getXpos(), this.getYpos()-1);
		
		else throw new RuntimeException("blokk.whatAmIPushedInto Error - Could not find wereabouts");
	}
	
	@Override
	public boolean moveHere(Blokk b) {
		return false;
	}

	@Override
	public Shape draw() {
		Shape shape = new Rectangle();
		if (isDiscovered()) shape.setStyle(" -fx-fill: gray; -fx-stroke: darkgray; -fx-stroke-width: 1; -fx-stroke-type: inside;");
		else shape.setStyle(hiddenStyle);
		return shape;
	}
}
