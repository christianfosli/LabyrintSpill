	package spiller;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

// Oppgave B -------------------------------
public class Spiller {
	private String navn;
	private int id;
	private static int nextId = 1;
	private int poeng;
	private int[] posisjon; //lagt til for lab spill, posisjon[0] = xPos, posisjon[y] = yPos
	private boolean vilFortsette = true; // Lagt til for 21 spill


	public Spiller() throws IllegalArgumentException {
		this.id = nextId;
		nextId ++;
		this.poeng = 0;
		this.posisjon = new int[2];
		this.posisjon[0] = 0;
		this.posisjon[1] = 0;
	}
	
	
	public Spiller(String navn) throws IllegalArgumentException {
		setNavn(navn);
		this.id = nextId;
		nextId ++;
		this.poeng = 0;
		this.posisjon = new int[2];
		this.posisjon[0] = 0;
		this.posisjon[1] = 0;
	}
	

	// Oppgave D ------------------------------

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) throws IllegalArgumentException{
		
		if (navn.equals(""))
			throw new IllegalArgumentException("Ugyldig input\tNavnet kan ikke vaere tomt!");
		
		navn = OppgAStorBokstav.makeStorBokstav(navn);		
		this.navn = navn;
	}

	public int getId() {
		return id;
	}

	public int getPoeng() {
		return poeng;
	}

	public void setPoeng(int poeng) {
		if (poeng < 0) {
			String errorMessage = String.format("Ugyldig input %d\t-poeng må være >0%n",poeng);
			throw new IllegalArgumentException(errorMessage);
		}else
			this.poeng = poeng;
	}
	
	public void setFortsette(boolean vilFortsette) {
		this.vilFortsette = vilFortsette;
	}
	
	public boolean getVilFortsette() {
		return vilFortsette;
	}

	public void setPos(int[] posisjon) {
		this.posisjon = posisjon;
	}
	
	public int[] getPos() {
		return this.posisjon;
	}
	
	public Shape draw(int size) {
		Shape shape = drawPersonSVG(size);
		return shape;
	}
	
	public static SVGPath drawPersonSVG(int size) {
		int xLeft = Math.round(size*1/6);
		int xRight = Math.round(size - xLeft);
		int yHalfHead = Math.round(size*1/6);
		int yArm = Math.round(size*2/6);
		int xMiddle = Math.round(size/2);
		int yLegs = Math.round(size*4/6);
		
		SVGPath shape = new SVGPath();
		
		shape.setContent(
				"M" + xMiddle + " " + yArm + //Hode
				" S" + xRight + " " + yHalfHead + " " + xMiddle + " " + 0 +
				" S" + xLeft + " " + yHalfHead + " " + xMiddle + " " + yArm +
				" M" + xMiddle + " " + yArm + //Mage
				" L" + xLeft + " " + size*0.5 +
				" L" + xLeft + " " + yLegs +
				" L" + xRight + " " + yLegs +
				" L" + xRight + " " + size*0.5 +
				" L" + xMiddle + " " + yArm +
				" M" + 0 + " " + yArm + //Armer
				" L" + size + " " + yArm +
				" M" + xMiddle + " " + yLegs + //Bein
				" L" + xLeft + " " + size +
				" L" + xLeft + " " + yLegs +
				" L" + xRight + " " + yLegs +
				" L" + xRight + " " + size +
				" L" + xMiddle + " " + yLegs + " Z"
				);
		
		shape.maxWidth(size);
		shape.maxHeight(size);
	
		shape.setStroke(Color.BLACK);
		shape.setFill(Color.ANTIQUEWHITE);
		
		return shape;
	}
	
	// Oppgave E -------------------------
	public String toString() {
		return String.format("\tNavn: %10s,\tId: %d,\tPoeng: %d%n", navn, id, poeng);
	}

}
