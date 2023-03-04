package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 *  A class that creates the primary stage and sets the scene for the Javafx application.
 * 
 * @author Molly Frost - 731002331
 * 315 - Project 2
 *
 */

public class Main extends Application {
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Main.stage = primaryStage;
			AnchorPane root = new AnchorPane();
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/Login.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}