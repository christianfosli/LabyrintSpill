package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import view.Labview;

public class HeightListener implements ChangeListener<Number>{

	private Labview view;
	
	public HeightListener(Labview view) {
		this.view=view;
	}

	@Override
	public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		
		view.reSize();
		
	}

}
