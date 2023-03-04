package application.model;

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 *  An object class to contain Orders, will be used with order to contain meals, addOns, 
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class Order {
    private ArrayList<Meal> meals;
    private ArrayList<Integer> addOnIds;
    private ArrayList<String> miscItemIds;
    private Timestamp timestamp;
    private Float orderTotal;
    private int orderId;

    /** 
     * Constructor for order, constructs an order object 
     *
     * @param _meals arraylist of meals
     * @param _addonIds arraylist of integers
     * @return Order Object
    */
    public Order(ArrayList<Meal> _meals, ArrayList<Integer> _addonIds) {
        meals = new ArrayList<Meal>(_meals);
        addOnIds = new ArrayList<Integer>(_addonIds);
        miscItemIds = new ArrayList<String>();
        addMiscItems(); // populate miscItemIds
    }
    
    /**
     * Cosntructor for order
     * 
     * @param _timestamp the timestamp of the prder
     * @param _orderId the order id
     * @param _orderTotal the order total, in dollars
     */
    public Order(Timestamp _timestamp, int _orderId, Float _orderTotal) {
        timestamp = _timestamp;
        orderId = _orderId;
        orderTotal = _orderTotal;
    }


    /**
     * Constructor for order
     * 
     * @param _timestamp the timestamp of the prder
     * @param _orderId the order id
     * @param _orderTotal the order total, in dollars
     * @param _meals arraylist of meals
     * @param _addonIds arraylist of integers
     */
    public Order(Timestamp _timestamp, int _orderId, Float _orderTotal, ArrayList<Meal> _meals, ArrayList<Integer> _addonIds) {
        timestamp = _timestamp;
        orderId = _orderId;
        orderTotal = _orderTotal;
        meals = new ArrayList<Meal>(_meals);
        addOnIds = new ArrayList<Integer>(_addonIds);

        miscItemIds = new ArrayList<String>();
        addMiscItems(); // populate miscItemIds

    }

    /** 
     * Calculates the misc values of an order to be refelected in inventory
     *
     * @param none
     * @return none
    */
    private void addMiscItems() {
        for (Meal m : meals) {
            if (m.getMealTypeId() == 3) { // bowl --> bowls, misc_item_id = 3
                miscItemIds.add("Bowls");
            }
            else if (m.getMealTypeId() == 2 || m.getMealTypeId() == 1) { // plate --> boxes, misc_item_id = 2, bigger plate --> boxes, misc_item_id = 2
                miscItemIds.add("Boxes");
            }
            miscItemIds.add("Silverware"); // add silverware and napkin for each meal          
            miscItemIds.add("Napkins");  
        }
        for (Integer i : addOnIds) {
            if (i == 19) {
                miscItemIds.add("Cups"); // add cup for each fountain drink
            }
            else if (1 <= i && i <= 18) {
                miscItemIds.add("Bowl");
            }
        }

        if (meals.size()/3 == 0) { // if less than 3 meals, add single bag
            miscItemIds.add("Bags");
        }
        else { // otherwise, add a bag for every three meals
            for (int i = 0; i < meals.size()/3; i++) {
                miscItemIds.add("Bags");
            }
        }
    }

    /** 
     * returns ArrayList of the meals within the order
     *
     * @param none
     * @return ArrayList<Meal>
    */
    public ArrayList<Meal> getMeals() {
        return meals;
    }

    /** 
     * returns ArrayList of the addOn id integers within the order
     *
     * @param none
     * @return ArrayList<Integer>
    */
    public ArrayList<Integer> getAddOnIds() {
        return addOnIds;
    }

    /** 
     * returns ArrayList of the miscItem names within the order
     *
     * @param none
     * @return ArrayList<String>
    */
    public ArrayList<String> getMiscItemIds() {
        return miscItemIds;
    }

    /**
     * Gets order id
     * 
     * @return order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Gets timestamp of order
     * 
     * @return timestamp of order
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Gets order total
     * 
     * @return order total dollar amount
     */
    public Float getOrderTotal() {
        return orderTotal;
    }
    
    /**  
     * Called by front end containing meals and addOns, constructs order object to be sent to the 
     * backend for record input
     *
     * @param ArrayList of meals in the order
     * @param ArrayList of integer of addonIds
     * @return void
    */
    public static void sendOrder(ArrayList<Meal> _meals,  ArrayList<Integer> _addonIds) {
        Order orderToSend = new Order(_meals, _addonIds);     // create an order
        orderToSend.addMiscItems();
        DatabaseInterface.addOrderEntry(orderToSend);         // send order to DBInterface
    }
}