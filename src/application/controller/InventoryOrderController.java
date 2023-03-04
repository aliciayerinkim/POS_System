package application.controller;

import java.util.Hashtable;

import application.Main;
import application.model.Inventory;
import application.model.InventoryOrder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.ObservableList; 

/**
 * This class, InventoryOrderController, handles when a manager wants to restock or take out the quantity of any items.
 * Items can be chosen from the drop down box.
 * Item will be restocked when positive integer is entered.
 * Item will be taken out when negative integer is entered.
 * 
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */

public class InventoryOrderController implements EventHandler<ActionEvent> {
	
	@FXML
	private VBox page;
	private HBox function = new HBox();
	public ComboBox<String> orderBox;
	public TextField quantity;
	public Button confirm;
	
	public Hashtable<String, Integer> order = new Hashtable<String, Integer>();
	
	
	@FXML
	/**
	 * Initializes the page layout with all the items in the database be in the dropdown choices.
	 * 
	 * @param 
	 */
	public void initialize() {
		new Inventory();
		Label title = new Label("Restock/Takeout Inventory Item");
		Label info = new Label("Type positive number to restock / negative number to take out");
		
		ObservableList<String> items = orderBox.getItems();
		for(int i=0;i<Inventory.items.size();++i) {
			items.add(Inventory.items.get(i).getInventoryItemName());
		}		
		
		title.setFont(Font.font("Apple Symbols",FontWeight.EXTRA_BOLD,36));
		info.setFont(Font.font("Apple Symbols",FontWeight.MEDIUM,15));
		
		orderBox.setPrefWidth(250);
		quantity.setPrefWidth(150);
		
		page.setMargin(title, new Insets(120,10,10,10));
		page.setMargin(info, new Insets(20,10,10,10));
		
		
		function.setMargin(orderBox, new Insets(10,10,10,10));
		function.setMargin(quantity, new Insets(10,10,10,10));
		function.setMargin(confirm, new Insets(10,10,10,10));
		
		page.setMargin(function, new Insets(30,10,10,10));
		
		ObservableList list1 = function.getChildren();
		
		list1.addAll(orderBox,quantity,confirm);
		
		ObservableList list = page.getChildren();
		
		list.addAll(title,info,function);
		
		page.setAlignment(Pos.TOP_CENTER);
		function.setAlignment(Pos.CENTER);
		
		
	}
	@Override
	/**
	 * Go back to manager dashboard when clicked
	 * 
	 * @param even, MouseEvent
	 */
	public void handle(ActionEvent event) {
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
	 * Checks whether the manager has entered an integer in quantity textfield.
	 * 
	 * @param input, String
	 */
	public boolean isInteger(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    }
	    catch( NumberFormatException e ) {
	        return false;
	    }
	}
	
	/**
	 * Gives error message when no item is selected, no quantity entered, invalid quantity entered
	 * If item selected with valid quantity, make the item and quantity into hashtable and send the order
	 * 
	 * @param even, MouseEvent
	 */
	public void send_order(MouseEvent event) {
		try {
			if(orderBox.getValue()==null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select one of the items", ButtonType.OK);
				alert.showAndWait();				
			}
			if(quantity.getText()==""||isInteger(quantity.getText())==false) {
				Alert alert = new Alert(AlertType.ERROR, "Please enter an integer", ButtonType.OK);
				alert.showAndWait();			
			}
			if (orderBox.getValue()!=null&&quantity.getText()!=""&&isInteger(quantity.getText())==true) {
				order.put(orderBox.getValue(), Integer.parseInt(quantity.getText()));
				InventoryOrder.sendOrder(order);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Changes made successfully", ButtonType.OK);
				alert.showAndWait();
				orderBox.setValue(null);
				quantity.clear();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Go back to the login page when clicked
	 * 
	 * @param even, MouseEvent
	 */
	public void PandaButton(MouseEvent event) {
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