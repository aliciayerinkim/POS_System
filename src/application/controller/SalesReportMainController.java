package application.controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import application.model.*;
import application.model.Menu.Item;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * This class, SalesReportMainController, handles when a user tries to see the sales report of selected item from selected date to selected date.
 * 
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */

public class SalesReportMainController implements EventHandler<ActionEvent>{
	@FXML
	private Button submit;
	
	@FXML
	private DatePicker from;
	
	@FXML
	private DatePicker to;
	
	@FXML
	private ComboBox<String> menu_item;
	
	private Menu menu = new Menu();
	
	private ArrayList<Integer> menuItemsId = new ArrayList<Integer>();
	
	private ArrayList<String> menuItems = new ArrayList<String>();
	
	public static LocalDate fromDate;
	public static LocalDate toDate;
	public static int menu_selected;
	public static ArrayList<Order> ordersContainingItem;
	
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
			menu_item.getItems().add(s);
		}
	}
	
	@Override
	public void handle(ActionEvent event) {}
	
	/**
     * Leads the user to the page where there are all the buttons that only manager has access to.
	 * @param event, ActionEvent
     */
	public void goback(ActionEvent event) {
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
	 * Leads the user to the view where the sale report for the selected item is listed
	 * 
	 * @param event, ActionEvent
	 */
	public void salesreportlist(ActionEvent event) {
		try {
			if(from.getValue()==null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select a start date", ButtonType.OK);
				alert.showAndWait();
			}
			if(to.getValue()==null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select a end date", ButtonType.OK);
				alert.showAndWait();
			}
			if(menu_item.getValue()==null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select one menu item", ButtonType.OK);
				alert.showAndWait();
			}
			if(from.getValue()!=null&&to.getValue()!=null&&menu_item.getValue()!=null) {
				fromDate=from.getValue();
				
				toDate=to.getValue();
				
				int index = menuItems.indexOf(menu_item.getValue());
				
				int itemId = menu.items.get(index).getMenuItemId();
				
				menu_selected = itemId;
				
				Timestamp start = Timestamp.valueOf(fromDate.atStartOfDay());
				Timestamp end = Timestamp.valueOf(toDate.atStartOfDay());
				
				ordersContainingItem = DatabaseInterface.salesReport(menu_selected, start, end);			
				
				AnchorPane root = new AnchorPane();
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation( Main.class.getResource("view/SalesReportList.fxml") );
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
