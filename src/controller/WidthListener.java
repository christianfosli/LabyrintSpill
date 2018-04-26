package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import view.Labview;

public class WidthListener implements ChangeListener<Number>{

	private Labview view;
	
	public WidthListener(Labview view) {
		this.view = view;
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		
		view.reSize();
		
	}

}
