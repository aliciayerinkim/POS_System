package application.controller;

import application.model.DatabaseInterface;
import application.model.SellsTogether;
import application.controller.DateSelectRestockController;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * This class, WhatSellsController, display a list of pairs of entree items that sell together often, popular or not. 
 * Ingredients are only displayed from the given time window that was set on the previous view, DateSelectWhatSells.fxml
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */

public class WhatSellsController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	private TableView table;
	
	@FXML
	private TableColumn rankColumn;
	
	@FXML
	private TableColumn entree1Column;
	
	@FXML
	private TableColumn entree2Column;
	
	@FXML
	private TableColumn percentColumn;
	
	/**
	 * Initializes sets up the columns for the table view in the what sells together view.
	 * The dates of the what sells together item are then sent to the backend where an ArrayList of SellsTogether Items are returned
	 * The table is then loaded with all the SellsTogether items returned from the ArrayList.
	 * This includes the rank of the item combo, the first entree item, the second entree item and the percentage they correlate.
	 * 
	 * @param location, URL
	 * @param resources, ResourceBundle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        
		ArrayList<SellsTogether> whatSells;
		whatSells = DatabaseInterface.whatSellsTogether(DateSelectWhatSellsController.startDateWhatSells, DateSelectWhatSellsController.endDateWhatSells);
		Collections.sort(whatSells, new Comparator<SellsTogether>() {
		    @Override
		    public int compare(SellsTogether z1, SellsTogether z2) {
		        if (z1.getRank() < z2.getRank())
		            return 1;
		        if (z1.getRank() > z2.getRank())
		            return -1;
		        return 0;
		    }
		});
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		entree1Column.setCellValueFactory(new PropertyValueFactory<>("firstMenuItemName"));
		entree2Column.setCellValueFactory(new PropertyValueFactory<>("secondMenuItemName"));
		percentColumn.setCellValueFactory(new PropertyValueFactory<>("porportionPercent"));
        for (SellsTogether x : whatSells) {
			table.getItems().add(x);
		}

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