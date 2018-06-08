package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import spiller.Spiller;
import view.ErrorMessage;

public class Labyrint {
	private Spillet model;
	private LabyrintRute[][] labyrinten;
	private int width;
	private int height;
	private int[] startPoint; 
	private int[] sluttPoint; 
	private Spiller[] winners;
	private File winnerFile;
	private boolean lights;
	private List<LabyrintRute> teleportTo;
	
	public Labyrint(File fileRef, Spillet model) {
		this.model=model;
		loadFile(fileRef);
		loadWinners(fileRef);
	}
	
	public void discoverRuter() {
		int xPos = model.getSpilleren().getPos()[0];
		int yPos = model.getSpilleren().getPos()[1];
		// Don't go out of bounds:
		if (xPos == 0) xPos = 1;
		else if (xPos == width-1) xPos = width-2;
		if (yPos == 0) yPos = 1;
		else if (yPos == height-1) yPos = height-2;
		
		// Discover ruter:
		for (int y = yPos-1;y<=yPos+1;y++)
			for (int x = xPos-1;x<=xPos+1;x++) {
				LabyrintRute ruten = labyrinten[x][y];
				if (!ruten.isDiscovered()) {
					ruten.discover();
					model.getView().replaceNode(new int[] {x,y}, ruten.draw());
				}
			}
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
			
			String lightsStr = inn.nextLine();
			if (lightsStr.equals("lights:on")) lights = true;
			else if (lightsStr.equals("lights:off")) lights = false;
			else throw new IllegalArgumentException(String.format("Labyrint.loadFile err-"
					+ " line 3 should be lights:off/on, but is %s",lightsStr));
			
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

	public void addWinner(Spiller s) throws IllegalArgumentException{
		if (s.getPoeng() > getMaxStepsForScoreList())
			throw new IllegalArgumentException("Too many steps for ScoreList");
		winners[getScoreListIndex(s.getPoeng())] = s;
		writeWinners();
	}
	
	private int getScoreListIndex(int steps) {
		for (int i = 0; i < winners.length; i++) {
			try {
				if (steps < winners[i].getPoeng()) {
					pushScoreListDown(i);
					return i;
				}
			} catch (NullPointerException e) {
				return i;
			}
		}throw new IllegalArgumentException("Too many steps for ScoreList");
	}
	
	private void pushScoreListDown(int index) {
		for (int i = winners.length-1; i > index; i--) {
			winners[i] = winners[i-1];
		}
	}

	/**
	 * Fallgrav trenger start pos
	 * Teleport ruter trenger hvor de skal teleporteres til
	 */
	private void giveRuterNeededInfo() {
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
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private LabyrintRute makeRute(char c, int xPos, int yPos) {
		LabyrintRute rute;
		
		if (c == ' ') rute = new Gang(xPos, yPos, lights);
		else if (c == '#') rute = new Vegg(xPos, yPos, lights);
		else if (c == '+') rute = new Blokk(xPos, yPos, lights, this);
		else if (c == 'x') rute = new Fallgrav(xPos,yPos, lights);
		else if (c == 't') rute = new Teleport(xPos, yPos, lights);
		else if (c == '-' ) {
			rute = new Utgang(xPos, yPos);
			this.sluttPoint = new int[]{xPos,yPos};
		}else if (c == '*') {
			rute = new Gang(xPos, yPos, true);
			this.startPoint = new int[]{xPos,yPos};
		}else if (c == 'h') {
			rute = new Gang(xPos,yPos, lights);
			this.teleportTo.add(rute);
		}else throw new IllegalArgumentException(String.format("Labyrint.makeRute - "
					+ "illegal char: %c in at pos %d, %d", c,xPos,yPos));
		return rute;
	}
	
	private void loadWinners(File levelFile) {
		winnerFile = getWinnerFile(levelFile);
		
		if (winnerFile.exists()){
			try (FileInputStream inn = new FileInputStream(winnerFile);
					ObjectInputStream reader = new ObjectInputStream(inn);){
				Object o = reader.readObject();
				if (o instanceof Spiller[]) winners = (Spiller[]) o;
			}catch (IOException e) {
				new ErrorMessage("loading winner-scores",e.getMessage());
				winners = new Spiller[10];
			}catch (ClassNotFoundException e) {
				new ErrorMessage("loading winner-scores",e.getMessage());
				winners = new Spiller[10];
			}
		}else winners = new Spiller[10];
	}
	
	private void writeWinners() {
		try (FileOutputStream out = new FileOutputStream(winnerFile);
				ObjectOutputStream writer = new ObjectOutputStream(out);){
			writer.writeObject(winners);
		} catch (FileNotFoundException e) {
			try {
				// If file doesnt exist, create it, and try again:
				if (winnerFile.createNewFile()) writeWinners();
				else new ErrorMessage("saving winners to file",e.getMessage());
			} catch (IOException e1) {
				new ErrorMessage("saving winners to file",e.getMessage());
			}
		} catch (IOException e) {
			new ErrorMessage("saving winners to file",e.getMessage());
		}
	}
	
	private File getWinnerFile(File levelFile) {
		String str = levelFile.toString();
		char level = str.charAt(str.length()-5);
		String winPath = levelFile.getParent() + File.separator + "winners_L" + level;
		return new File(winPath);
	}
	
	public boolean spillerAtExit() {
		if (getRute(model.getSpilleren().getPos()) instanceof Utgang) return true;
		return false;
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
	public Spillet getModel() {
		return model;
	}
	public boolean hasLights() {
		return lights;
	}
	public Spiller[] getWinners() {
		return winners;
	}
	public Spiller getWinner() {
		if (winners[0] == null) {
			Spiller noWinnerYet = new Spiller();
			noWinnerYet.setPoeng(999);
			return noWinnerYet;
		}
		return winners[0];
	}
	public int getMaxStepsForScoreList() {
		if (winners[winners.length-1] == null) return 999;//Listen ikke full
		return winners[winners.length-1].getPoeng()-1;
	}
}