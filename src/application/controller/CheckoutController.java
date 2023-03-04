package application.controller;

import application.model.Order;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.Meal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * This class, LoginController, handles when a user tries to login. If they are
 * successful it takes them to the next view, CreateOrder.
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */


public class CheckoutController implements EventHandler<ActionEvent>, Initializable {
	

	@FXML
	private VBox mealCart;

	@Override
	public void handle(ActionEvent event) {}
	
	/**
     	* Initializes the view.
     	*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mealCart.getChildren().add(new Label("Order Total"));
		mealCart.setSpacing(10.0);
		
		CreateOrderController.drinkHolder.setPrefWidth(400.0);
		CreateOrderController.drinkHolder.setPrefHeight(70.0);
		CreateOrderController.drinkHolder.setSpacing(10.0);
		
		CreateOrderController.mealHolder.setPrefWidth(400.0);
		CreateOrderController.mealHolder.setPrefHeight(70.0);
		CreateOrderController.mealHolder.setSpacing(10.0);
		
		Separator separate1 = new Separator();
		separate1.setOrientation(Orientation.HORIZONTAL);
		Separator separate2 = new Separator();
		separate2.setOrientation(Orientation.HORIZONTAL);
		Separator separate3 = new Separator();
		separate3.setOrientation(Orientation.HORIZONTAL);
		
		mealCart.getChildren().add(separate1);
		mealCart.getChildren().add(new Label("Meals"));
		mealCart.getChildren().add(CreateOrderController.mealHolder);
		mealCart.getChildren().add(separate2);
		mealCart.getChildren().add(new Label("Sides"));
		mealCart.getChildren().add(CreateOrderController.drinkHolder);
		mealCart.getChildren().add(separate3);


		mealCart.getChildren().add(CreateOrderController.total);
	}
	
	/**
	 * Given an ActionEvent, checks out an order, sending an order containing meals and addOns to the backend
	 * 
	 * @param event, ActionEvent
	 * @return void
	 */
	public void checkout(ActionEvent event) {
		Order.sendOrder(CreateOrderController.meals, CreateOrderController.addOns);
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Order Successful", ButtonType.OK);
		alert.showAndWait();
		
		CreateOrderController.mealId = 0;
		CreateOrderController.drinkHolder.getChildren().clear();
		CreateOrderController.mealHolder.getChildren().clear();
		CreateOrderController.runningTotal = 0;
		CreateOrderController.addOns.clear();
		CreateOrderController.meals.clear();
		CreateOrderController.total.setText(String.format("$0.00")); 
		
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/CreateOrder.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 *    Goes back to CreateOrder page, CreateOrder.fxml, to make more changes to the order. 
	 * 
	 * @param event, MouseEvent
	 * @return void
	 */
	public void backClicked(MouseEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/CreateOrder.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
