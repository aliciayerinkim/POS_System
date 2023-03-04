package application.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.sql.Timestamp;
import java.time.*;

import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * This class, DateSelectRestockController, handles when a user sets the date for the restock report. 
 * Once the date is set and they hit the submit button, they will be directed to the restock report table view. 
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */

public class DateSelectRestockController implements EventHandler<ActionEvent>{
	
	@FXML
	private DatePicker startDateFXML;
	
	@FXML
	private DatePicker endDateFXML;
	
	public static Timestamp startDateRestock;
	public static Timestamp endDateRestock;
	
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
	
	/**
	 *  Redirects user to restock report table view if and only if they have set both dates.
	 * 
	 * @param event, ActionEvent
	 */
	public void submitButton(ActionEvent event) {
		if(startDateFXML.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Start Date Value is Empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}else if(endDateFXML.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR, "End Date Value is Empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}else {
			LocalDate x = startDateFXML.getValue();
			startDateRestock = Timestamp.valueOf(x.atStartOfDay());
			x = endDateFXML.getValue();
			endDateRestock = Timestamp.valueOf(x.atStartOfDay());
		}
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/RestockReport.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}