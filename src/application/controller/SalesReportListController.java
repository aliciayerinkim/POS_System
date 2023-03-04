package application.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import application.controller.SalesReportMainController;
import application.model.DatabaseInterface;
import application.model.Inventory;
import application.model.Inventory.Item;
import application.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.awt.ScrollPane;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.model.SalesReportItem;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class, SalesReportListController, is where user can view the sales report of an menu item and can select one specific order to see its detail.
 * 
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */

public class SalesReportListController implements EventHandler<ActionEvent>, Initializable{
	@FXML
	private TableView<SalesReportItem> tableView;
	
	@FXML
	private TableColumn<SalesReportItem,Timestamp> time;
	
	@FXML
	private TableColumn<SalesReportItem,Integer> orderID;
	
	@FXML
	private TableColumn<SalesReportItem,Float> money;
	
	
	private ObservableList<SalesReportItem> data = FXCollections.observableArrayList();   
	
	/**
     * Initializes the view.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ArrayList<Order> ordersContainingItem = SalesReportMainController.ordersContainingItem;
		
		for(Order o : ordersContainingItem) {
			data.add(new SalesReportItem(o.getTimestamp(), o.getOrderId(), o.getOrderTotal()));
		}
		
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        money.setCellValueFactory(new PropertyValueFactory<>("money"));
		
		tableView.setItems(data);
	}
	
	@Override
	public void handle(ActionEvent event) {}
	
	/**
     * Leads the user to the page where they can select dates and menu item to see its sales history.
	 * @param event, ActionEvent
     */
	public void goback(ActionEvent event) {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/SalesReportMain.fxml") );
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

}
