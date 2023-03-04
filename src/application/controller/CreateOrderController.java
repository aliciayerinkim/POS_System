package application.controller;

import application.Main;
import application.model.Meal;
import application.model.MealPrice;
import application.model.MealPrice.MealType;
import application.model.Menu;
import application.model.Menu.Item;

//import application.controller.LoginController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class, createOrder, handles when a user is in the process of 
 * creating an order. You are able to check the contents of the order in the
 * side-view order ticket.
 * 
 * @author Molly Frost - 731002331 
 * @author Joey Quismorio - 128008917 
 * 315 - Project 2
 *
 */

public class CreateOrderController implements EventHandler<ActionEvent>, Initializable {
	
	@FXML
	private Button managerButton;
	
	@FXML
	private ScrollPane scroller;
	
	@FXML
	private VBox mealCart;
	
	@FXML
	private GridPane mealPane;
	
	@FXML
	private GridPane sidePane;
	
	public static VBox drinkHolder = new VBox();
	
	public static VBox mealHolder = new VBox();
	
	public static ArrayList<Meal> meals = new ArrayList<Meal>();
	
	public static ArrayList<Integer> addOns = new ArrayList<Integer>();
	
	public static int mealId;
	
	public static double runningTotal;
	
	public static Label total = new Label(String.format("$%.2f", runningTotal));
	
	public static Menu menu = new Menu();
	
	public static MealPrice mealPrice = new MealPrice();
	
	/**
	 * Initializes the view.
	 * 
	 * @param location, URL
	 * @param resources, ResourceBundle
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menu.items.clear();
		menu = new Menu();
		
		mealPrice.mealTypes.clear();
		mealPrice = new MealPrice();
		
		if(!LoginController.isManager){//if employee is not a manager
			managerButton.setDisable(true);
			ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.0);

            managerButton.setEffect(colorAdjust);
		}
		scroller.setFitToWidth(true);
		scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mealCart.getChildren().add(new Label("Current Order"));
		mealCart.setSpacing(10.0);
		
		drinkHolder.setPrefWidth(400.0);
		drinkHolder.setPrefHeight(70.0);
		drinkHolder.setSpacing(10.0);
		
		mealHolder.setPrefWidth(400.0);
		mealHolder.setPrefHeight(70.0);
		mealHolder.setSpacing(10.0);
		
		Separator separate1 = new Separator();
		separate1.setOrientation(Orientation.HORIZONTAL);
		Separator separate2 = new Separator();
		separate2.setOrientation(Orientation.HORIZONTAL);
		Separator separate3 = new Separator();
		separate3.setOrientation(Orientation.HORIZONTAL);
		
		mealCart.getChildren().add(separate1);
		mealCart.getChildren().add(new Label("Meals"));
		mealCart.getChildren().add(mealHolder);
		mealCart.getChildren().add(separate2);
		mealCart.getChildren().add(new Label("Sides"));
		mealCart.getChildren().add(drinkHolder);
		mealCart.getChildren().add(separate3);

		Button checkOut = new Button("Checkout");
		checkOut.setStyle("-fx-padding:5px");
		checkOut.setOnAction(e -> {
			checkout(e);
		});
		
		ArrayList<MealType> mealsList = mealPrice.getMealTypes();

		
		for (MealType i : mealsList) {
			Button btn = new Button(i.getMealName());
			btn.setAlignment(Pos.CENTER);
			btn.setContentDisplay(ContentDisplay.CENTER);
			btn.setPrefHeight(50.0);
			btn.setPrefWidth(150.0);
			String name = i.getMealName();
			btn.setOnAction(e -> {move(e, i.getMealTypeId());});
			
			
			mealPane.getChildren().add(btn);
			GridPane.setRowIndex(btn, 1);
			GridPane.setColumnIndex(btn, (mealsList.indexOf(i)%3));
		}
		
		ArrayList<Item> sidesList = menu.getAllDrinks();
		
		int currSidesRow = 1;
		
		for (Item i : sidesList) {
			if(sidesList.indexOf(i) !=  0 && sidesList.indexOf(i)%2 == 0) {
				currSidesRow += 1;
			}
			Button btn = new Button(i.getProductName());
			
			if (i.getCategory() == 5) {
				btn.setStyle("-fx-border-color: black; -fx-background-color: #FFFF8A");
			}
			
			btn.setAlignment(Pos.CENTER);
			btn.setContentDisplay(ContentDisplay.CENTER);
			btn.setPrefHeight(50.0);
			btn.setPrefWidth(150.0);
			String name = i.getProductName();
			btn.setOnAction(e -> {addDrink(e, i.getMenuItemId(), i.getSalePrice(), name);});
			
			
			sidePane.getChildren().add(btn);
			GridPane.setRowIndex(btn, currSidesRow);
			GridPane.setColumnIndex(btn, (sidesList.indexOf(i)%2) + 1);
		}
		
		mealCart.getChildren().add(checkOut);
		mealCart.getChildren().add(total);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}
	
	/**
	 * Creates HBox element for use in
	 * creating order ticket. Takes parameters:
	 * 
	 * @param i, int - Representative of the drink type ID.
	 * @param price, double - Representative of the drink price.
	 * @param s, String - Representative of the drink name.
	 */
	
	private HBox drinkView(int i, double price, String s){
		runningTotal += price;
		HBox layout = new HBox();
		layout.setAlignment(Pos.CENTER_LEFT);
		
		Label drinkName = new Label(String.format("%s", s));
		drinkName.setPrefWidth(200);
		drinkName.setStyle("-fx-font-size:10pt; -fx-padding:5px");
		
		Label p = new Label(String.format("$%.2f", price));
		drinkName.setPrefWidth(200);
		drinkName.setStyle("-fx-font-size:10pt; -fx-padding:5px");

		Button delete = new Button("Delete");
		delete.setStyle("-fx-padding:5px");
		delete.setOnAction(e -> {
			drinkHolder.getChildren().remove(layout);
			addOns.remove((Integer)i);
			runningTotal -= price;
			this.total.setText(String.format("$%.2f", runningTotal));
		});
				
		layout.getChildren().addAll(drinkName, p, delete);
		
		this.total.setText(String.format("$%.2f", runningTotal));
		
		return layout;
	}
	
	/**
	 * Creates VBox element for use in
	 * creating order ticket. Takes parameters:
	 * 
	 * @param i, int - Representative of the meal type ID.
	 * @param price, double - Representative of the meal price.
	 * @param s, String - Representative of the meal name.
	 * @param list, Vbox - Holds contents of the meal, including side and entrees.
	 */
	
	public static VBox mealView(int i, double price, String s, VBox list){
		VBox layout = new VBox();
		runningTotal += price;
		HBox item = new HBox();
		layout.setAlignment(Pos.CENTER_LEFT);
		
		Label drinkName = new Label(String.format("%s", s));
		drinkName.setPrefWidth(200);
		drinkName.setStyle("-fx-font-size:10pt; -fx-padding:5px");
		
		Label p = new Label(String.format("$%.2f", price));
		drinkName.setPrefWidth(200);
		drinkName.setStyle("-fx-font-size:10pt; -fx-padding:5px");

		Button delete = new Button("Delete");
		delete.setStyle("-fx-padding:5px");
		delete.setOnAction(e -> {
			mealHolder.getChildren().remove(layout);
			addOns.remove((Integer)i);
			runningTotal -= price;
			total.setText(String.format("$%.2f", runningTotal));
		});
				
		item.getChildren().addAll(drinkName, p, delete);
		
		total.setText(String.format("$%.2f", runningTotal));
		
		layout.getChildren().addAll(item, list);
		
		return layout;
	}
	
	/**
	 *    Handles the transition from 
	 *    createOrder to checkout.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void checkout(ActionEvent event) {
	if (meals.isEmpty() && addOns.isEmpty()) {
		Alert alert = new Alert(AlertType.ERROR, "Cannot Send Out Empty Order", ButtonType.OK);
		alert.showAndWait();
	}else {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/Checkout.fxml") );
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
	 *    Handles the transition from 
	 *    createOrder to createMeal.
	 *    Sets mealId for createMeal to
	 *    base size of meal on.
	 * 
	 * @param event, ActionEvent
	 */
	

	public void move(ActionEvent event, int id) {
		mealId = id;
		moveToCreateMeal();
	}
	
	public void moveSides(ActionEvent event) {
		mealId = 4;
		moveToCreateMeal();
	}
	
	/**
	 *   Helper function used to help
	 *   transition screens from createOrder
	 *   to createMeal.
	 */
	
	
	public void moveToCreateMeal() {
		try {
			AnchorPane root = new AnchorPane();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/CreateMeal.fxml") );
			root = (AnchorPane) loader.load();
	
			Scene scene = new Scene( root );
			Main.stage.setScene( scene );
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *    Allows for the addition of 
	 *    drinks to an order.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void addDrink(ActionEvent event, int id, Float price, String name) {
		addOns.add(id);
		drinkHolder.getChildren().add(drinkView(id, price, name));
	}
	
	/**
	 *    If the user is a manager, will allow
	 *    them to view the Inventory screen.
	 * 
	 * @param event, ActionEvent
	 */
	
	public void managerClicked(ActionEvent event) {
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
			addOns.clear();
			meals.clear();
			mealHolder.getChildren().clear();
			drinkHolder.getChildren().clear();
			runningTotal = 0.0F;
			total.setText(String.format("$%.2f", runningTotal));
			LoginController.isManager = false;
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
