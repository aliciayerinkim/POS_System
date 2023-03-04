package application.controller;

import application.model.RestockReport;
import application.model.DatabaseInterface;
import application.controller.DateSelectRestockController;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class, RestockReportController, displays a list of ingredients whose current inventory is less than the amount sold. 
 * Ingredients are only displayed from the given time window that was set on the previous view, DateSelectRestock.fxml
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */

public class RestockReportController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	TableView table;
	
	@FXML
	TableColumn nameColumn;
	
	@FXML
	TableColumn currentInventoryColumn;
	
	//@FXML
	//TableColumn amountSoldColumn;
	
	/**
	 * Initializes sets up the columns for the table view is the restock report view.
	 * The dates of the restock report are then sent to the backend where an ArrayList of Restock Items are returned
	 * The table is then loaded with all the restock items returned from the ArrayList.
	 * This includes the name of item, the current inventory amount, and the current amount sold of the item.
	 * 
	 * @param location, URL
	 * @param resources, ResourceBundle
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
       
		ArrayList<RestockReport> restock;
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		currentInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("currentInventory"));
		//amountSoldColumn.setCellValueFactory(new PropertyValueFactory<>("amountSold"));
        restock = DatabaseInterface.restockReport2();
		for (RestockReport x : restock) {
			table.getItems().add(x);
		}

//        <TableColumn fx:id="nameColumn" prefWidth="227.0" text="Name" />
//        <TableColumn fx:id="currentInventoryColumn" prefWidth="255.0" text="Current Inventory Amount" />
//          <TableColumn fx:id="amountSoldColumn" prefWidth="231.0" text="Amount Sold" />
	}
	
	
	@Override
	/**
     * Handle is called when the back button is selected. 
     * It leads the user back to the Manager Dashboard page.
     * 
	 * @param event, ActionEvent
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
	 * Signout is called when the signout button or the panda logo is selected. 
	 * It signs the user out and leads them back to the Login page.
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
}
