package application.model;

import java.util.*;
import application.model.Menu;

/**
 *  An object class that to assist the whatSellstogether() function from backend to frontend.
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class SellsTogether {
    private int firstMenuItemId;
    private int secondMenuItemId;
    private String firstMenuItemName;
    private String secondMenuItemName;
    private int rank; // Actuall rank starts at 1
    private double porportionPercent;

    /** * Constructor for Sellstogether, constructs SellsTogether object
     *
     * @param int _firstMenuItemId
     * @param int _secondMenuItemId
     * @return SellsTogther Object
    */
    public SellsTogether(int _firstMenuItemId, int _secondMenuItemId) {
        firstMenuItemId = _firstMenuItemId;
        secondMenuItemId = _secondMenuItemId;
        firstMenuItemName = getName(_firstMenuItemId);
        secondMenuItemName = getName(_secondMenuItemId);
        rank = 0;
    } 

    /** * Constructor for Sellstogether, constructs SellsTogether object
     *
     * @param int _firstMenuItemId
     * @param int _secondMenuItemId
     * @param int _rank
     * @param double porportion percent
     * @return SellsTogther Object
    */
    public SellsTogether(int _firstMenuItem, int _secondMenuItem, int _rank, double _porportionPercent) {
        firstMenuItemId = _firstMenuItem;
        secondMenuItemId = _secondMenuItem;
        rank = _rank;
        porportionPercent = _porportionPercent;
        firstMenuItemName = getName(_firstMenuItem);
        secondMenuItemName = getName(_secondMenuItem);
    } 
    
    /** * Getter name, given menuID
     *
     * @param int menuID
     * @return String name
    */
    public String getName(int menuID) {
    	Menu menu = new Menu();
    	Menu.Item item = menu.getMenuItem(menuID);
    	return item.getProductName();
    } 

    /** * Getter forString menuItem name
     *
     * @param none
     * @return String First Name
    */
    public String getFirstMenuItemName() {
		return firstMenuItemName;
	}

    /** * Getter forString menuItem name
     *
     * @param none
     * @return String Second Name
    */
	public String getSecondMenuItemName() {
		return secondMenuItemName;
	}

    /** * Getter for int menuItem id
     *
     * @param none
     * @return String First id
    */
	public int getFirstMenuItem() {
        return firstMenuItemId;
    }

    /** * Getter for int menuItem id
     *
     * @param none
     * @return String second id
    */
    public int getSecondMenuItem() {
        return secondMenuItemId;
    }

    /** * Getter for int rank of porportion
     *
     * @param none
     * @return int rank
    */
    public int getRank() {
        return rank;
    }

    /** * Getter double porportion percent
     *
     * @param none
     * @return double porportion percent
    */
    public double getPorportionPercent() {
        return porportionPercent;
    }

    /** * Setter for int rank
     *
     * @param int rank
     * @return void
    */
    public void setRank(int _rank) {
        rank = _rank;
    }
}
