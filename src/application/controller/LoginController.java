package application.controller;

import application.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class, LoginController, handles when a user tries to login. If they are
 * successful it takes them to the next view, CreateOrder.
 * 
 * @author Molly Frost - 731002331 315 - Project 2
 *
 */
public class LoginController implements EventHandler<ActionEvent> {

	@FXML
	private TextField username;
	@FXML
	private Label error;
	@FXML
	private PasswordField password;
	public static Boolean isManager = false;

	/**
	 * Sends the user to CreateOrder view, IF they enter a valid login and password.
	 * If they enter an invalid username or password it will say "Invalid Username
	 * or password."
	 * 
	 * @param event, ActionEvent
	 */
	@Override
	public void handle(ActionEvent event) {
		error.setVisible(false);
		if (!(Session.login(username.getText(), password.getText()))) {
			error.setText("Invalid Username or Password");
			error.setVisible(true);
		} else {
			isManager = Session.isManager();
			try {
				AnchorPane root = new AnchorPane();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("view/CreateOrder.fxml"));
				root = (AnchorPane) loader.load();

				Scene scene = new Scene(root);
				Main.stage.setScene(scene);
				Main.stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}