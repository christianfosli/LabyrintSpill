package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Labyrint {
	
	private Spillet model;
	private LabyrintRute[][] labyrinten;
	private int width;
	private int height;
	private int[] startPoint; 
	private int[] sluttPoint; 

	private List<LabyrintRute> teleportTo;
	
	public Labyrint(File fileRef, Spillet model) {
		this.model=model;
		loadFile(fileRef);
	}
	
	public LabyrintRute[][] getRuter(){
		return labyrinten;
	}
	
	public LabyrintRute getRute(int[]posisjon) {
		return labyrinten[posisjon[0]][posisjon[1]];
	}
	
	public LabyrintRute getRute(int xPos, int yPos) {
		return labyrinten[xPos][yPos];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getStartPoint() {
		return startPoint;
	}
	
	public int[] getSluttPoint() {
		return sluttPoint;
	}
	
	/**
	 * Swaps 2 ruter in labyrinten array.
	 * Note!!! Before calling this method set the new position in the applicable rute
	 * @param rute1
	 * @param rute2
	 */
	public void swapRuter(LabyrintRute rute1, LabyrintRute rute2) {
		labyrinten[rute1.getXpos()][rute1.getYpos()] = rute1;
		labyrinten[rute2.getXpos()][rute2.getYpos()] = rute2;
		
		model.getView().swapNodes(rute1.getPos(), rute2.getPos());
	}
	
	public void replaceRute(LabyrintRute replacedRute, LabyrintRute newRute) {
		labyrinten[replacedRute.getXpos()][replacedRute.getYpos()] = newRute;
		model.getView().replaceNode(replacedRute.getPos(), newRute.draw());
	}
	
	public void loadFile(File fileRef) throws IllegalArgumentException {
		teleportTo = new ArrayList<LabyrintRute>();
		
		try (Scanner inn = new Scanner(fileRef)){	
			
			width = Integer.parseInt(inn.nextLine());
			height = Integer.parseInt(inn.nextLine());
			char[] txtline = new char[width];
			
			labyrinten = new LabyrintRute[width][height];
			
			// Gaa gjennom tekstfil.
			for (int y = 0; y < height; y++) {
				txtline = inn.nextLine().toCharArray();
				for (int x = 0; x<width; x++) 
						labyrinten[x][y] = makeRute(txtline[x],x,y);
			}
			
			giveRuterNeededInfo();
			
			System.out.println("Labyrint loaded from file");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Errors trying to open the specified file "+e.getMessage());
		} catch (NumberFormatException | NoSuchElementException e) {
			throw new IllegalArgumentException("Error converting file into labyrint. Please ensure proper format."
					+ "See 'help' section for format requirements. \n Details: "+e.getMessage());
		} 
	}
	/**
	 * Fallgrav trenger start pos
	 * Teleport ruter trenger hvor de skal teleporteres til
	 */
	public void giveRuterNeededInfo() {
		for (LabyrintRute[] rArray : labyrinten)
			for (LabyrintRute r : rArray) {
				if (r instanceof Fallgrav)
					((Fallgrav) r).setStartPos(startPoint);
				else if(r instanceof Teleport) {
					((Teleport) r).setTeleportPos(teleportTo.get(teleportTo.size()-1).getPos());
					teleportTo.remove(teleportTo.get(teleportTo.size()-1));
				}
			}
	}

	/**
	 * KONVERTERER tegn til Ruter!
	 * @param c - char som skal konverteres
	 * 		Vegg: '#'	Gang: ' '	Utgang: '-'	Start: '*'	Blokk: '+'	Fallgrav: 'x'
	 * 		Teleporter fra: 't'		Teleporter til: 'h'
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private LabyrintRute makeRute(char c, int xPos, int yPos) {
		LabyrintRute rute;
		
		if (c == '#')
			rute = new Vegg(xPos, yPos);
		else if (c == ' ')
			rute = new Gang(xPos, yPos);
		else if (c == '-' ) {
			rute = new Utgang(xPos, yPos);
			this.sluttPoint = new int[]{xPos,yPos};
		}
		else if (c == '*') {
			rute = new Gang(xPos, yPos);
			this.startPoint = new int[]{xPos,yPos};
		}else if (c == '+')
			rute = new Blokk(xPos, yPos, this);
		else if (c == 'x')
			rute = new Fallgrav(xPos,yPos);
		else if (c == 't')
			rute = new Teleport(xPos, yPos);
		else if (c == 'h') {
			rute = new Gang(xPos,yPos);
			this.teleportTo.add(rute);
		}
		else {
			String feilmelding = String.format("Char %c cannot be converted to a rute", c);
			throw new IllegalArgumentException(feilmelding);
		}
		
		return rute;
	}

	public Spillet getModel() {
		return model;
	}
}