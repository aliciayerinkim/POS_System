package application.controller;

import application.model.*;
import application.model.MealPrice.MealType;
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
 * This class, EditMealPriceController, allows the user to
 * edit the price of a meal. This updated price will be reflected
 * in all future orders.
 * 
 * @author Joey Quismorio - 128008917 
 * 315 - Project 2
 *
 */
public class EditMealPriceController implements EventHandler<ActionEvent> {
	
	@FXML
	private ComboBox<String> item;
	
	@FXML
	private TextField price;
	
	@FXML
	private VBox priceHolder;

	private MealPrice meals = new MealPrice();
	
	private ArrayList<Integer> mealItemsId = new ArrayList<Integer>();
	
	private ArrayList<String> mealItems = new ArrayList<String>();
	
	public static double itemPrice;
	
	public static Label showPrice = new Label(String.format("$%.2f", itemPrice));

	
	/**
	 * Initializes the view.
	 * 
	 */
	
	@FXML
	public void initialize() {
		meals.mealTypes.clear();
		meals = new MealPrice();
				
		for (MealType i : meals.mealTypes) {
			mealItems.add(i.getMealName());
			mealItemsId.add(i.getMealTypeId());
		}
		
		for (String s : mealItems) {
			item.getItems().add(s);
		}

		priceHolder.getChildren().add(showPrice);
	}
	
	/**
	 * Allows user to go back to the edit price menu.
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
	 *    Checks the price automatically.
	 * 
	 * @param event, ActionEvent
	 */
	
	
	public void checkPrice(ActionEvent event) {
		int index = mealItems.indexOf(item.getValue());
		
		itemPrice = meals.mealTypes.get(index).getPrice();
		
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
	 *    Handles the updating of prices in the database.
	 * 
	 * @param event, ActionEvent
	 */
	public void updatePrice(ActionEvent event) {
		int index = mealItems.indexOf(item.getValue());
		
		meals.mealTypes.get(index).changePrice(Float.parseFloat(price.getText()));
		
//		System.out.println(index);
		
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