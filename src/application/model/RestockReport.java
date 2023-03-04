package application.model;

import java.util.*;

/**
 *  An object class that to assist the restockReport() function from backend to frontend.
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class RestockReport {
    private String itemName;
    private int currentInventory;
    private int amountSold; 

    /** * Constructor for RestockReport, constructs RestockReport object
     *
     * @param int _itemName
     * @param int _currentInventory
     * @param int _amountSold
     * @return SellsTogther Object
    */
    public RestockReport(String _itemName, int _currentInventory, int _amountSold) {
        itemName = _itemName;
        currentInventory = _currentInventory;
        amountSold = _amountSold;
    } 

    /** * Getter string name
     *
     * @param none
     * @return String itemName
    */
    public String getItemName() {
        return itemName;
    }

    /** * Getter currentInventory
     *
     * @param none
     * @return int inventory
    */
    public int getCurrentInventory() {
        return currentInventory;
    }

    /** * Getter int amountSold
     *
     * @param none
     * @return int amountSold
    */
    public int getAmountSold() {
        return amountSold;
    }

}
