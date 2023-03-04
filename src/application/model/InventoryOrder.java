package application.model;

import java.util.Hashtable;


/**
 *  An object class to contain inventory orders. Contains methods to send instructions to the database.
 * 
 * @author Aaron Su - 930006201
 * 315 - Project 2
 *
 */
public class InventoryOrder {
    private Hashtable<String, Integer> inventoryQuantities; // map each item in InventoryOrder with quantity Ordered
    
    /** * Constructor for inventoryOrder, constructs an inventory Order object 
     *
     * @param Hashtable of strings and integers
     * @return Inventory Order Object
    */
    public InventoryOrder(Hashtable<String, Integer> _inventoryQuantity) {
        inventoryQuantities = new Hashtable<String, Integer>(_inventoryQuantity);
    }
    
    /** * Getter for hastable of inventory quantities
     *
     * @param none
     * @return Hashtable of strings and integers
    */
    public Hashtable<String, Integer> getInventoryQuantities() {
        return inventoryQuantities;
    }

    /** * Sends given inventory order to the database interfact to be 
     * added to table. Constructs a inventoryorder object
     *
     * @param Hashtable<String, Integer> where the string is the item name and the integer is the quantity
     * @return void
    */
    public static void sendOrder(Hashtable<String, Integer> _inventoryQuantity) {
        InventoryOrder orderToSend = new InventoryOrder(_inventoryQuantity);
        DatabaseInterface.addInventoryOrderEntry(orderToSend);
    }
}
