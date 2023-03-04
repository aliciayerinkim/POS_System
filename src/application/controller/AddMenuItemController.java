package application.controller;

import application.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

import application.Main;
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
 * This class, AddMenuItemController, handles when a user tries to add a new
 * menu item. If at least one of the ingredients do not exist, throws an error
 * telling the user that adding the menu item is required.
 * 
 * @author Joey Quismorio - 128008917 
 * 315 - Project 2
 *
 */
public class AddMenuItemController implements EventHandler<ActionEvent> {
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField price;
	
	@FXML
	private ComboBox<String> category;
	
	@FXML
	private TextField ingredients;
	
	private Menu menu = new Menu();
	
	private Inventory inv = new Inventory();
	
	/**
	 * Initializes the view.
	 */

	@FXML
	public void initialize() {
		inv.items.clear();
		inv = new Inventory();
		
		category.getItems().addAll("Side", "Entree", "Drink", "Seasonal Side", "Seasonal Entree", "Seasonal Drink");
	}
	
	/**
	 * Allows user to go back to the edit menu item view.
	 * 
	 * @param event, ActionEvent
	 */
	@Override
	public void handle(ActionEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/EditMenuItem.fxml") );
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
	 * Allows user to add a menu item to the database.
	 * 
	 * @param event, ActionEvent
	 */
	public void addMenuItem(ActionEvent event) {
		try {
			int itemType = 0;
			if (category.getValue().equals("Side")) {
				itemType = 0;
			}
			if (category.getValue().equals("Entree")) {
				itemType = 1;
			}
			if (category.getValue().equals("Drink")) {
				itemType = 2;
			}
			if (category.getValue().equals("Seasonal Side")) {
				itemType = 3;
			}
			if (category.getValue().equals("Seasonal Entree")) {
				itemType = 4;
			}
			if (category.getValue().equals("Seasonal Drink")) {
				itemType = 5;
			}
			
			ArrayList<Inventory.Item> vals = new ArrayList<Inventory.Item>();
			
			for (String s : ingredients.getText().split(",")) {
				vals.add(Inventory.getInventoryItem(s));
			}
			
			if(vals.contains(null)) {
				Alert alert = new Alert(AlertType.ERROR, "Make sure all ingredients exist in inventory", ButtonType.OK);
				alert.showAndWait();
			}else if (!vals.contains(null)){
				menu.addMenuItem(name.getText(), Float.parseFloat(price.getText()), itemType, ingredients.getText());
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Addition Successful", ButtonType.OK);
				alert.showAndWait();
			
				AnchorPane root = new AnchorPane();
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation( Main.class.getResource("view/EditMenuItem.fxml") );
				root = (AnchorPane) loader.load();
		
				Scene scene = new Scene( root );
				Main.stage.setScene( scene );
				Main.stage.show();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}