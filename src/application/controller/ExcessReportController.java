package application.controller;

import application.model.DatabaseInterface;
import application.model.ExcessReport;
import application.model.SellsTogether;
import application.controller.ExcessReportMainController;
import application.model.Session;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
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
 * This class, ExcessReportController, let the user see a list of items that sold less than 10% of their inventory
 * in the time frame they have selected in ExcessReportMainController.java
 * 
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */

public class ExcessReportController implements EventHandler<ActionEvent>, Initializable{
	
	//@FXML
	//TableView table;
	
	@FXML
	private TableView table;
	
	@FXML
	private TableColumn nameColumn;
	
	@FXML
	private TableColumn pctColumn;
	
	/**
	 * Initializes the view.
	 * 
	 * @param location, URL
	 * @param resources, ResourceBundle
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*TableColumn name = new TableColumn("Name");
        TableColumn currInvent = new TableColumn("Current Inventory");
        TableColumn amtSold = new TableColumn("Amount Sold");
        
        table.getColumns().addAll(name, currInvent, amtSold);*/
		Map<String, Float> er = DatabaseInterface.excessReport(ExcessReportMainController.startDate, ExcessReportMainController.endDate);
		
		ArrayList<ExcessReport> erList = new ArrayList<ExcessReport>();
		
        for (Map.Entry<String, Float> e : er.entrySet()) {
        	ExcessReport standIn = new ExcessReport(e.getKey(), e.getValue());
        	erList.add(standIn);
        }
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		pctColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

		//System.out.println(erList.size());
        for (ExcessReport x : erList) {
			table.getItems().add(x);
		}
        
	}
	
	
	@Override
	/**
         * Leads the user to the page where they can create order
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
