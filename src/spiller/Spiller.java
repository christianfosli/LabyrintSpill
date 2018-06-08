	package spiller;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class Spiller implements Serializable{
	private static final long serialVersionUID = 1L;
	private String navn;
	private final int id;
	private static int nextId = 1;
	private int poeng;
	private int[] posisjon; //lagt til for lab spill, posisjon[0] = xPos, posisjon[y] = yPos
	private boolean vilFortsette = true; // Lagt til for 21 spill

	public Spiller() {
		this.id = nextId;
		nextId ++;
		this.navn="NONAME";
		this.poeng = 0;
		this.posisjon = new int[] {0, 0};
	}
	
	public Spiller(String navn) throws IllegalArgumentException {
		setNavn(navn);
		this.id = nextId;
		nextId ++;
		this.poeng = 0;
		this.posisjon = new int[] {0, 0};
	}
	
	public void setNavn(String navn) throws IllegalArgumentException{
		if (navn.equals(""))
			throw new IllegalArgumentException("Ugyldig input\tNavnet kan ikke vaere tomt!");
		navn = OppgAStorBokstav.makeStorBokstav(navn);		
		this.navn = navn;
	}
	public void setFortsette(boolean vilFortsette) {
		this.vilFortsette = vilFortsette;
	}
	public void setPoeng(int poeng) {
		if (poeng < 0) {
			String errorMessage = String.format("Ugyldig input %d\t-poeng må være >0%n",
					poeng);
			throw new IllegalArgumentException(errorMessage);
		}else this.poeng = poeng;
	}
	public void setPos(int[] posisjon) {
		this.posisjon = posisjon;
	}

	public String getNavn() {
		return navn;
	}
	public int getId() {
		return id;
	}
	public int getPoeng() {
		return poeng;
	}
	public int[] getPos() {
		return this.posisjon;
	}
	public boolean getVilFortsette() {
		return vilFortsette;
	}
	
	public Shape draw(int size) {
		Shape shape = drawPersonSVG(size);
		return shape;
	}
	
	private static SVGPath drawPersonSVG(int size) {
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
	
	//Kaller Poeng for 'Steps' for aa tilpasse LabyrintSPill
	public String toString() {
		return String.format("\tName: %10s\tSteps: %d%n", navn, poeng);
	}
}
