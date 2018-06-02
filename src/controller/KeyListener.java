package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Spillet;

public class KeyListener implements EventHandler<KeyEvent>{
	private Spillet model;
	private boolean isActive;
	
	public KeyListener() {
		this.isActive = true;
	}

	/**
	 * Recognize moving input
	 * Can use either arrow keys or hjkl (like vim)
	 * calls model to move player until game is finished
	 */
	
	@Override
	public void handle(KeyEvent e) {
		
		// Naviger spill -------------------
		if (!isActive)
			return;
		
		if (e.getCode().equals(KeyCode.RIGHT) || e.getText().equals("l"))
			isActive = ! model.move(1, 0);
		else if (e.getCode().equals(KeyCode.LEFT) || e.getText().equals("h") && !e.isShortcutDown())
			isActive = ! model.move(-1, 0);
		else if (e.getCode().equals(KeyCode.UP) || e.getText().equals("k"))
			isActive = ! model.move(0, -1);
		else if (e.getCode().equals(KeyCode.DOWN) || e.getText().equals("j"))
			isActive = ! model.move(0, 1);
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void setModel(Spillet model) {
		this.model=model;
	}
	
	public boolean getIsActive() {
		return isActive;
	}

}
