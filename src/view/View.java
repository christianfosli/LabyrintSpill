package view;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

public interface View {

	void makeLab();
	
	void addRute(Shape shape, int[] pos);
	Node getNode(int[] pos);
	
	void movePlayer();
	
	void reSize(); 
	
	void finish(int steps, int rekord);
	
	Region getViewRegion();

	void swapNodes(int[] pos1, int[] pos2);
	void replaceNode(int[] pos, Shape replaceWith);

	
}
