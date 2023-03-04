package application.controller;

import application.model.*;
import application.model.Menu.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
 * This class, LoginController, handles when a user tries to login. If they are
 * successful it takes them to the next view, CreateOrder.
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */
public class EditMenuItemPriceController implements EventHandler<ActionEvent> {
	
	@FXML
	private ComboBox<String> item;
	
	@FXML
	private TextField price;
	
	@FXML
	private VBox priceHolder;

	private Menu menu = new Menu();
	
	private ArrayList<Integer> menuItemsId = new ArrayList<Integer>();
	
	private ArrayList<String> menuItems = new ArrayList<String>();
	
	public static double itemPrice;
	
	public static Label showPrice = new Label(String.format("$%.2f", itemPrice));

	/**
	 * Initializes the view.
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

		priceHolder.getChildren().add(showPrice);
	}
	
	/**
	 * Allows user to go back to the edit price screen.
	 * 
	 * @param event, ActionEvent
	 */
	@Override
	public void handle(ActionEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/EditPrice.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks the price of an item from the
	 * ComboBox. Automatically updates label.
	 * 
	 * @param event, ActionEvent
	 */
	public void checkPrice(ActionEvent event) {
		int index = menuItems.indexOf(item.getValue());
		
		itemPrice = menu.items.get(index).getSalePrice();
		
		showPrice.setText(String.format("$%.2f", itemPrice));
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
	 * Allows user to update the price of a menu item.   
	 *
	 * @param event, ActionEvent
	 */
	public void updatePrice(ActionEvent event) {
		int index = menuItems.indexOf(item.getValue());
		
		menu.items.get(index).changePrice(Float.parseFloat(price.getText()));
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Update Successful", ButtonType.OK);
		alert.showAndWait();
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/EditPrice.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
