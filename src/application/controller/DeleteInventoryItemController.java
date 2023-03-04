package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import application.Main;
import application.model.DatabaseInterface;
import application.model.Inventory;
import application.model.Menu.Item;

import java.util.ArrayList;

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
 * This class, DeleteInventoryItemController, allow the manager to delete any item
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */
public class DeleteInventoryItemController implements EventHandler<ActionEvent> {
	
	@FXML
	private ComboBox<String> item;
	
	private Inventory inventory = new Inventory();
	
	private ArrayList<Integer> inventoryItemsId = new ArrayList<Integer>();
	
	private ArrayList<String> inventoryItems = new ArrayList<String>();
	
	
	@FXML
	/**
         * Initializes the view.
         */
	public void initialize() {
		inventory.items.clear();
		inventory = new Inventory();
				
		for (Inventory.Item i : inventory.items) {
			inventoryItems.add(i.getInventoryItemName());
			inventoryItemsId.add(i.getInventoryItemId());
		}
		
		for (String s : inventoryItems) {
			item.getItems().add(s);
		}
	}

	@Override
	/**
         * Lead the user to the page where they can choose whether to add item or delete item
	 * @param event, ActionEvent
         */
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
	 * Delete an item from the inventory and lead the user back to the page where they can add or delete item.
	 * 
	 * @param event, ActionEvent
	 */
	public void deleteInventoryItem(ActionEvent event) {
		int index = inventoryItems.indexOf(item.getValue());
		
		int itemId = inventory.items.get(index).getInventoryItemId();
		
		if(DatabaseInterface.checkIngredients(itemId) == false) {
			inventory.deleteInventoryItem(itemId);
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Deletion Successful", ButtonType.OK);
			alert.showAndWait();
		}else if (DatabaseInterface.checkIngredients(itemId) == true) {
			Alert alert = new Alert(AlertType.ERROR, "Failed To Delete Item", ButtonType.OK);
			alert.showAndWait();
		}
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
