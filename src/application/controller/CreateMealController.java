package application.controller;

import application.controller.CreateOrderController;
import application.model.*;
import application.model.MealPrice.MealType;
import application.model.Menu;
import application.model.Menu.Item;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.GroupLayout.Alignment;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *  A class that allows users to create a meal. Redirect from the createOrder page.
 * 
 * @author Joey Quismorio - 128008917 
 * 315 - Project 2
 *
 */

public class CreateMealController implements EventHandler<ActionEvent> {
	
	private int side;
	
	private Float sidePrice;
	
	private String sideName;
	
	private ArrayList<Integer> entrees = new ArrayList<Integer>();
	
	private ArrayList<String> entreeNames = new ArrayList<String>();
	
	private Float entreePrice;
 
	private int mealTypeId = 0;
	
	@FXML
	private VBox mealCart;
	
	@FXML
	private VBox sideList;
	
	@FXML
	private VBox entreeList;
	
	@FXML
	public GridPane sideGrid;
	
	@FXML
	public GridPane entreeGrid;
	
	private VBox sideHolder = new VBox();
	
	private VBox entreeHolder = new VBox();
	
	public static Menu menu = new Menu();
	
	public static MealPrice mealType = new MealPrice();
	
	/**
	 *   Initializes area for first view. No parameters.
	 * 
	 */
	
	@FXML
	public void initialize() {
		menu.items.clear();
		menu = new Menu();
		
		mealType.mealTypes.clear();
		mealType = new MealPrice();
		
		mealTypeId = CreateOrderController.mealId;
		mealCart.getChildren().add(new Label("Current Meal"));
		mealCart.setSpacing(10.0);
		
		sideHolder.getChildren().add(new Label("Sides"));
		sideHolder.setPrefWidth(400.0);
		sideHolder.setPrefHeight(70.0);
		sideHolder.setSpacing(10.0);
		
		entreeHolder.getChildren().add(new Label("Entrees"));
		entreeHolder.setPrefWidth(400.0);
		entreeHolder.setPrefHeight(300.0);
		entreeHolder.setSpacing(10.0);
		
		Separator separate1 = new Separator();
		separate1.setOrientation(Orientation.HORIZONTAL);
		Separator separate2 = new Separator();
		separate2.setOrientation(Orientation.HORIZONTAL);
		Separator separate3 = new Separator();
		separate3.setOrientation(Orientation.HORIZONTAL);
		
		mealCart.getChildren().add(separate1);
		mealCart.getChildren().add(sideHolder);
		mealCart.getChildren().add(separate2);
		mealCart.getChildren().add(entreeHolder);
		mealCart.getChildren().add(separate3);

		Button add = new Button("Add to Order");
		add.setStyle("-fx-padding:5px");
		add.setOnAction(e -> {
			handle(e);
		});
		
		mealCart.getChildren().add(add);
		
		ArrayList<Item> sidesList = menu.getAllSides();
		
		int currSidesRow = 1;
		
		for (Item i : sidesList) {
			if(sidesList.indexOf(i) !=  0 && sidesList.indexOf(i)%5 == 0) {
				currSidesRow += 1;
			}
			
			Button btn = new Button(i.getProductName());
			
			if (i.getCategory() == 3) {
				btn.setStyle("-fx-border-color: black; -fx-background-color: #FFFF8A");
			}

			btn.setAlignment(Pos.CENTER);
			btn.setContentDisplay(ContentDisplay.CENTER);
			btn.setPrefHeight(30);
			btn.setPrefWidth(80);
			String name = i.getProductName();
			btn.setOnAction(e -> {sideSelect(e, i.getMenuItemId(), i.getSalePrice(), name);});
			
			
			sideGrid.getChildren().add(btn);
			GridPane.setRowIndex(btn, currSidesRow);
			GridPane.setColumnIndex(btn, (sidesList.indexOf(i)%5));
		}
		
		ArrayList<Item> entreesList = menu.getAllEntrees();
		
		int currEntreesRow = 1;
		
		for (Item i : entreesList) {
			if(entreesList.indexOf(i) !=  0 && entreesList.indexOf(i)%4 == 0) {
				currEntreesRow += 1;
			}
			
			Button btn = new Button(i.getProductName());
			
			if (i.getCategory() == 4) {
				btn.setStyle("-fx-border-color: black; -fx-background-color: #FFFF8A; -fx-font-size:12");
			}else {
				btn.setStyle("-fx-font-size:12");
			}
			
			btn.setAlignment(Pos.CENTER);
			btn.setContentDisplay(ContentDisplay.CENTER);
			//btn.setStyle("-fx-font-size:12");
			btn.setPrefHeight(80);
			btn.setPrefWidth(110);
			String name = i.getProductName();
			btn.setOnAction(e -> {entreeSelect(e, i.getMenuItemId(), i.getSalePrice(), name);});
			
			
			entreeGrid.getChildren().add(btn);
			GridPane.setRowIndex(btn, currEntreesRow);
			GridPane.setColumnIndex(btn, (entreesList.indexOf(i)%4));
		}

	}
	
	/**
	 *   Creates view for side in side menu for
	 *   meal taking.
	 * 
	 * @param s, String
	 */
	
	private HBox sideView(String s){
		HBox layout = new HBox();
		layout.setAlignment(Pos.CENTER_LEFT);
		
		Label sideName = new Label(String.format("Side: %s", s));
		sideName.setPrefWidth(200);
		sideName.setStyle("-fx-font-size:10pt; -fx-padding:5px");

		Button delete = new Button("Delete");
		delete.setStyle("-fx-padding:5px");
		delete.setOnAction(e -> {
			sideHolder.getChildren().remove(layout);
			side = 0;
			if(mealTypeId == 4) {
				sidePrice = 0.0F;
			}
		});
				
		layout.getChildren().addAll(sideName, delete);
		
		return layout;
	}
	
	/**
	 *   Creates view for entree in side menu for
	 *   meal taking.
	 * 
	 * @param i, int
	 * @param s, String
	 */
	
	private HBox entreeView(int i, String s){
		entrees.add(i);
		
		HBox layout = new HBox();
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.setPrefWidth(400.0);
		
		Button delete = new Button("Delete");
		delete.setStyle("-fx-padding:5px");
		delete.setOnAction(e -> {
			entreeHolder.getChildren().remove(layout);
			entreeNames.remove((String) s);
			entrees.remove((Integer)i);
			if(mealTypeId == 4) {
				entreePrice = 0.0F;
			}
		});
		
		Label entreeName = new Label(String.format("Entree %d: %s", (entreeHolder.getChildren().size()), s));
		entreeName.setPrefWidth(200.0);
		entreeName.setStyle("-fx-font-size:10pt; -fx-padding:5px");
		
		layout.getChildren().addAll(entreeName, delete);
		
		return layout;
	}
	
	/**
	 *   Handles the adding of a meal to an
	 *   in-progress order.
	 * 
	 * @param event, ActionEvent
	 */
		
	@Override
	public void handle(ActionEvent event) {
		if (mealTypeId != 4 && (side == 0 || entrees.isEmpty())) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Send Out Empty Meal", ButtonType.OK);
			alert.showAndWait();
		}else if (mealTypeId != 4 && (entrees.size() != mealTypeId)) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Send Out Empty Meal", ButtonType.OK);
			alert.showAndWait();
		}else if (mealTypeId == 4 && (sideName == null && entrees.isEmpty())) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Send Out Empty Meal", ButtonType.OK);
			alert.showAndWait();
		}else {
			if (!(mealTypeId == 4)) {
				Meal meal = new Meal(side, entrees, mealTypeId);
				CreateOrderController.meals.add(meal);
			}else if (mealTypeId == 4 && entrees.isEmpty()) {
				CreateOrderController.addOns.add(side);
			}else if (mealTypeId == 4 && !entrees.isEmpty()) {
				CreateOrderController.addOns.add(entrees.get(0));
			}
			VBox mealContents = new VBox();
			
			mealContents.getChildren().add(new Label(sideName));
			
			for (String s : entreeNames) {
				mealContents.getChildren().add(new Label(s));
			}
			
			ArrayList<Float> mealPrices = new ArrayList<Float>();
			
			for (MealType m : mealType.mealTypes) {
				mealPrices.add(m.getPrice());
			}
			
			if (mealTypeId == 1) {
				CreateOrderController.mealHolder.getChildren().add(CreateOrderController.mealView(1, mealPrices.get(0), "Bowl", mealContents));
			}
			if (mealTypeId == 2) {
				CreateOrderController.mealHolder.getChildren().add(CreateOrderController.mealView(2, mealPrices.get(1), "Plate", mealContents));
			}
			if (mealTypeId == 3) {
				CreateOrderController.mealHolder.getChildren().add(CreateOrderController.mealView(3, mealPrices.get(2), "Bigger Plate", mealContents));
			}
			if (mealTypeId == 4) {
				if(entrees.isEmpty()) {
					CreateOrderController.mealHolder.getChildren().add(CreateOrderController.mealView(4, sidePrice, "Extra", mealContents));
				}else {
					CreateOrderController.mealHolder.getChildren().add(CreateOrderController.mealView(4, entreePrice, "Extra", mealContents));
				}
			}
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
	
	/**
	 *   Used in case a meal needs to be cancelled.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void cancelItem(ActionEvent event) {
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
	 *   Allows user to select a side and send it to
	 *   the current meal.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void sideSelect(ActionEvent event, int id, Float price, String name) {
		if(mealTypeId != 4 && sideHolder.getChildren().size() >= 2) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Side", ButtonType.OK);
			alert.showAndWait();
		}else if(mealTypeId == 4 && !entrees.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Side", ButtonType.OK);
			alert.showAndWait();
		}else if(mealTypeId == 4 && sideHolder.getChildren().size() >= 2) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Side", ButtonType.OK);
			alert.showAndWait();
		}else {
			side = id;
			sidePrice = price;
			sideName = name;
			sideHolder.getChildren().add(sideView(name));
		}
	}
	
	/**
	 *   Allows user to select an entree and send it to
	 *   the current meal.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void entreeSelect(ActionEvent event, int id, Float price, String name) {
		if (entreeHolder.getChildren().size() >= (mealTypeId + 1)) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Entree", ButtonType.OK);
			alert.showAndWait();
		}else if (mealTypeId == 4) {
			if (entreeHolder.getChildren().size() >= 2) {
				Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Entree", ButtonType.OK);
				alert.showAndWait();
			}else if (side > 0) {
				Alert alert = new Alert(AlertType.ERROR, "Cannot Add Another Entree", ButtonType.OK);
				alert.showAndWait();
			}else {
				entreeNames.add(name);
				entreeHolder.getChildren().add(entreeView(id, name));
				entreePrice = price;
			}
		}else{
			entreeNames.add(name);
			entreeHolder.getChildren().add(entreeView(id, name));
		}
	}
	
	/**
	 *    Logs the employee out by sending them to the CreateOrder view, 
	 * Login.fxml, which resets the application.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void PandaButton(MouseEvent event) {
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