package main;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Note JavaDoc files for this application are located in the folder titled "C482PAJavaDocFiles" in the C482 folder. 
 * 
 * The Main class launches the application and loads the main stage and screen.
 * 
 * @author John
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene inventory = new Scene(root, 1200, 500, Color.WHITESMOKE);
		stage.setScene(inventory);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}