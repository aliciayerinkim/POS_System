package application.controller;

import application.Main;
import application.model.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class InventoryController implements EventHandler<ActionEvent>, Initializable  {
	@FXML
    private TextArea inventoryField;


    String menuValues[] = {"White Rice", "Brown Rice", "Fried Rice", "Chow Mein", "Mixed Vegetables", "Honey Sesame Chicken", "Orange Chicken", "Black Pepper Angus","String Bean Chicken Breast","SweetFire Chicken Breast","Kung Pao Chicken","Mushroom Chicken", "Black Pepper Chicken","Grilled Teriyaki Chicken", "Brococoli Beef","Beijing Beef","Honey Walnut Shrimp","Eggplant Tofu","Fountain Drink","Bottled Water"};

    @Override
    public void handle(ActionEvent event) {}

    @Override
    /**
     * Initializes the view.
     * @param location, URL, resources, ResourceBundle
     */
    public void initialize(URL location, ResourceBundle resources) {
	Inventory inventory = new Inventory();

	for  (Inventory.Item i : inventory.items) {
	    int quantity = i.getItemQuanity();
	    String name = i.getInventoryItemName() ;
	    inventoryField.appendText("Item: " + name + "\n" + "     Quantity: " + quantity + "\n");
	}

	/*map = DatabaseInterface.checkMiscInventory();
	for (HashMap.Entry<Integer, Integer> entry2 : map.entrySet()) {
	    int key2 = entry2.getKey();
	    int value2 = entry2.getValue();
	    String name2 = menuValues[key2-1];
	    inventoryField.appendText("Item: " + name2 + "\n" + "     Quantity: " + value2 + "\n");
	}*/
    }

	/**
	 *Leads the user back to the dashboard with other buttons managers have access
	 * 
	 * @param event, MouseEvent
	 */
	public void pandaClicked(MouseEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/ManagerDashboard.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Leads the user to page where manager can restock and takeout item quantity when clicked.
	 * 
	 * @param event, ActionEvent
	 */
	public void manageInventoryClicked(ActionEvent event) {
		try {
			AnchorPane root = new AnchorPane();
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/ManagerDashboard.fxml") );
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
	 * @param event, MouseEvent
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
}
