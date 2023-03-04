

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
import javafx.scene.control.ComboBoxBase;

/**
 * This class, ExcessReportMainController, lets the user choose a timeframe that will be used to calculate what items sold less than 10% of their inventory
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */

public class ExcessReportMainController implements EventHandler<ActionEvent>{
	
	@FXML
	private DatePicker startDateFXML;
	
	@FXML
	private DatePicker endDateFXML;
	
	public static Timestamp startDate;
	public static Timestamp endDate;
	
	@Override
	/**
         * Leads the user to manager dashboard
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
	
	/**
	 * Leads the user to the view where the ingredients that sold less than 10% of their inventory is listed
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
			startDate = Timestamp.valueOf(x.atStartOfDay());
			x = endDateFXML.getValue();
			endDate = Timestamp.valueOf(x.atStartOfDay());
		}
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/ExcessReportList.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
