package application.controller;

import application.model.*;
import application.model.Menu.Item;
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
 * This class, DeleteMenuItemController, handles when a user tries to delete an
 * existing menu item.
 * 
 * @author Joey Quismorio - 128008917 
 * 315 - Project 2
 *
 */
public class DeleteMenuItemController implements EventHandler<ActionEvent> {
	
	@FXML
	private ComboBox<String> item;
	
	private Menu menu = new Menu();
	
	private ArrayList<Integer> menuItemsId = new ArrayList<Integer>();
	
	private ArrayList<String> menuItems = new ArrayList<String>();
	
	
	/**
	 * Initializes the view.
	 * 
	 */
	@FXML
	public void initialize() {
		menu.items.clear();
		menu = new Menu();
				
		for (Item i : menu.items) {
			menuItems.add(i.getProductName());
			menuItemsId.add(i.getMenuItemId());
		}
		
		for (String s : menuItems) {
			item.getItems().add(s);
		}
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
	 * Allows user to delete a menu item from the database.
	 * 
	 * @param event, ActionEvent
	 */
	public void deleteMenuItem(ActionEvent event) {
		int index = menuItems.indexOf(item.getValue());
		
		int itemId = menu.items.get(index).getMenuItemId();
		
		menu.deleteMenuItem(itemId);
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Deletion Successful", ButtonType.OK);
		alert.showAndWait();
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
}