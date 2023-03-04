package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import application.Main;
import application.controller.*;
import application.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/**
 * This class, LoginController, handles when a user tries to login. If they are
 * successful it takes them to the next view, CreateOrder.
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */
public class AddInventoryItemController implements EventHandler<ActionEvent> {
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField price;
	
	@FXML
	private ComboBox<String> category;
	
	@FXML
	private TextField ingredients;
	
	private Inventory inventory = new Inventory();

	@FXML
	public void initialize() {
		category.getItems().addAll("Ingredient", "Drink", "Misc.");
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/EditInventoryItem.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *    Logs the employee out by sending them to the login view, 
	 * Login.fxml, which resets the application.
	 * 
	 * @param event, ActionEvent
	 */
	public void signout(MouseEvent event) {
		try {
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
	
	/**
	 *    Logs the employee out by sending them to the login view, 
	 * Login.fxml, which resets the application.
	 * 
	 * @param event, ActionEvent
	 */
	public void addInventoryItem(ActionEvent event) {
		int itemType = 0;
		if (category.getValue().equals("Ingredient")) {
			itemType = 0;
		}
		if (category.getValue().equals("Drink")) {
			itemType = 1;
		}
		if (category.getValue().equals("Misc.")) {
			itemType = 2;
		}
		
		inventory.addInventoryItem(name.getText(), Float.parseFloat(price.getText()), 0, itemType);
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Addition Successful", ButtonType.OK);
		alert.showAndWait();
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/EditInventoryItem.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}