package application;
	
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Spillet;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Stage owner;
	private BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			Scene scene = new Scene(root,600,400);
			root.prefWidthProperty().bind(scene.widthProperty());
			root.prefHeightProperty().bind(scene.heightProperty());
			this.owner = primaryStage;
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			//Last inn menuBar
			FXMLLoader menuLoader = new FXMLLoader();
			Controller menuController = new Controller(owner,this);
			menuLoader.setController(menuController);
			menuLoader.setLocation(getClass().getResource("/view/MenuBar.fxml"));
			MenuBar menuBar = menuLoader.load();
			root.setTop(menuBar);
		
			//Klar til aa vise vindu!:
			primaryStage.setTitle("Labyrint Spill / Maze Game");
			primaryStage.show();
			
			//Start spill(model)     Note: Spill setter selv opp view
			Spillet model = new Spillet(menuBar, this);
			menuController.setModel(model);
			
			//Sett opp lyttere ved lambda funksjoner
			configureListeners(model);

			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void configureListeners(Spillet model) {
		// Key Listener for Flytting av spiller
		owner.getScene().setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.RIGHT) || e.getText().equals("l")) model.move(1, 0);
			else if (e.getCode().equals(KeyCode.LEFT) || 
					e.getText().equals("h") && !e.isShortcutDown()) model.move(-1, 0);
			else if (e.getCode().equals(KeyCode.UP) || e.getText().equals("k")) model.move(0, -1);
			else if (e.getCode().equals(KeyCode.DOWN) || e.getText().equals("j")) model.move(0, 1);
		});

		// Automatisk resize
		root.widthProperty().addListener(e -> {
			model.getView().reSize();
		});
		root.heightProperty().addListener(e -> {
			model.getView().reSize();
		});
	}

	public Stage getOwner() {
		return owner;
	}
	public BorderPane getRoot() {
		return root;
	}
}
