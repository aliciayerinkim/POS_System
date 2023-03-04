package application.model;

import java.util.ArrayList;
import java.sql.*;

/**
 *  An object class that pulls from the database and compiles intevetory into an object list of Items, containing the relevant information
 * 
 * @author Aaron Su - 930006201
 * 315 - Project 2
 *
 */
public class Inventory{
    public static ArrayList<Item> items = new ArrayList<Item>();

    public class Item {
        private int inventoryItemId;
        private String inventoryItemName;
        private Float unitPrice;
        private int quantity;
        private int category;
        /**
         * This item constructor is referenced by DatabaseInterface
         * 
         * @param _id the inventory item id
         * @param _name the inventory item name
         * @param _unit_price the inventory item unit price
         * @param _quantity the inventory item quantity
         * @param _category the inventory item category
         * @return a constructed Item
         */
        public Item(int _id, String _name, Float _unit_price, int _quantity, int _category) {
            this.inventoryItemId = _id;
            this.inventoryItemName = _name;
            this.unitPrice = _unit_price;
            this.quantity = _quantity;
            this.category = _category;
        }

        /**
         * This item constructor is referenced by Frontend and automatically populates the id
         * 
         * @param _name the inventory item name
         * @param _unit_price the inventory item unit price
         * @param _quantity the inventory item quantity
         * @param _category the inventory item category
         * @return a constructed Item
         */
        public Item(String _name, Float _unit_price, int _quantity, int _category) {
            this.inventoryItemId = DatabaseInterface.findNextInventoryId();
            this.inventoryItemName = _name;
            this.unitPrice = _unit_price;
            this.quantity = _quantity;
            this.category = _category;
        }
        
        /**
         * This gets the inventory item id
         * 
         * @return
         */
        public int getInventoryItemId() {
            return inventoryItemId;
        }
        
        /**
         * This gets the inventory item name
         * 
         * @return
         */
        public String getInventoryItemName() {
            return inventoryItemName;
        }
        
        /**
         * This gets the unit price
         * 
         * @return
         */
        public Float getUnitPrice() {
            return unitPrice;
        }
        
        /**
         * This gets the inventory item quantity
         * 
         * @return
         */
        public int getItemQuanity() {
            return quantity;
        }
        
        /**
         * This gets the inventory item category
         * 
         * @return 
         */
        public int getCategory() {
            return category;
        }
        
        /**
         * This sets the quantity to a specified quantity
         * 
         * @param newQuantity the new quantity to be set
         * @return void
         */
        public void setItemQuantity(int newQuantity) {
            quantity = newQuantity;
        }
        
        /**
         * This prints the item's data
         * 
         * @return void
         */
        public void print() {
            String s = String.format("%d, %s, %f, %d, %d", inventoryItemId, inventoryItemName, unitPrice, quantity, category);
            System.out.println(s);
        }
    }
    
    /**
     * This is the Inventory constructor, which automatically populates the inventory from the database
     * 
     * @return constructed Inventory
     */
    public Inventory() {
        refreshInventory();
    }

    /**
     * This automatically populates the inventory from the database
     * 
     * @return void
     */
    public void refreshInventory() {
        ResultSet result = DatabaseInterface.populateInventory();
        
        items = new ArrayList<Item>();
        
        int InventoryItemId;
        String InventoryItemName;
        Float unitPrice;
        int quantity;
        int category;
        try {
            do {         
                InventoryItemId = Integer.parseInt(result.getString("inventory_id"));
                InventoryItemName = result.getString("inventory_name");
                unitPrice = Float.parseFloat(result.getString("unit_purchase_price"));
                quantity = Integer.parseInt(result.getString("quantity"));
                category = Integer.parseInt(result.getString("item_type"));
                Inventory.Item inventoryItem = new Inventory.Item(InventoryItemId, InventoryItemName, unitPrice, quantity, category);
                items.add(inventoryItem);
            }while(result.next());
    
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    } 
   
    /** 
     * This gets the ArrayList<Item> items from Inventory
     * 
     * @return ArrayList<Item> a copy of all items in Inventory
     */
    public static ArrayList<Item> getInventory() {
        return items;
    }
    
    /** 
     * This searches through the Inventory for the item with the specified Id, returning a copy of that item
     * 
     * @param _inventoryItemId the inventory id to look up in the inventory
     * @return Item the item with the corresponds id, or null if not found
     */
    public static Item getInventoryItem(int _inventoryItemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInventoryItemId() == _inventoryItemId) {
                return items.get(i);
            } 
        }
        return null;
    }

    /** 
     * Searches through the Inventory for the item with the specified name, returning a copy of that item
     * 
     * @param _inventoryItemName the inventory id to look up in the inventory
     * @return Item the item with the corresponds name, or null if not found
     */
    public static Item getInventoryItem(String _inventoryItemName) { 
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInventoryItemName().replaceAll("\\s", "").equals(_inventoryItemName)) {
                return items.get(i);
            } 
        }
        return null;
    }
    
    /** 
     * Constructs and adds an inventory item to the inventory
     * 
     * @param _name the name of the item
     * @param _unitPrice the price of the item
     * @param _quantity the quantity of the item
     * @param _category the category of the item
     * @return void
     */
    public void addInventoryItem(String _name, Float _unitPrice, int _quantity, int _category) {
        int id = DatabaseInterface.findNextInventoryId();
        Item newItem = new Item(_name, _unitPrice, _quantity, _category);
        items.add(newItem);
        String sqlInsertLine = String.format("INSERT INTO inventory(inventory_id, inventory_name, item_type, unit_purchase_price, quantity) VALUES ('%d', '%s', '%d', '%f', '%d')", id, _name, _category, _unitPrice, _quantity);
        DatabaseInterface.insertIntoTable(sqlInsertLine);
    }
    
    /** 
     * Deletes the inventory item with the specified inventory item id
     * 
     * @param _inventoryItemId
     * @return void
     */
    public void deleteInventoryItem(int _inventoryItemId) {
        Item toBeDeleted = getInventoryItem(_inventoryItemId);
        if (toBeDeleted != null) {
            String sqlDeleteLine = String.format("DELETE FROM inventory WHERE inventory_id = %d", _inventoryItemId);
            DatabaseInterface.insertIntoTable(sqlDeleteLine);
            items.remove(toBeDeleted);
            return;
        } 
        else {
            throw new IllegalArgumentException("item does not exist in Inventory.items");
        }
    }
 
    /** 
     * Given a comma separated string, return true if all elements from the string, split by commas, exists in the inventory. Otherwise, return False.
     * 
     * @param itemNames the comma separated string to check inventory with
     * @return Boolean checks if all elements exist in the inventory
     */
    public static Boolean checkInventory(String itemNames) {
        //input is a string of item names seperated by ,
        String str[] = itemNames.split(",");
        for(int i =0 ; i < str.length; i++) {
            if (getInventoryItem(str[i]) == null) {
                return false;
            }
        }
        return true;
    }

    public void printInventoryItems() {
        for (Item i : Inventory.getInventory()) {
            i.print();
        }
    }

    /** 
     * Prints all items in inventory
     * 
     * @param InventoryItemId
     * @param 0
     */
    public void addOrSubtractQuantity(int InventoryItemId, int quantityToAdd) { // I know the name is the same as the Item function but idk what to call it
        Item i = getInventoryItem(InventoryItemId);
        int newQuantity = i.getItemQuanity() + quantityToAdd;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("new quantity cannot be less than 0");
        }
        i.setItemQuantity(newQuantity);
        items.set(items.indexOf(i), i);
        System.out.println("new quantity: " + (getInventoryItem(InventoryItemId).getItemQuanity()));
        String sqlUpdateTableString = String.format("UPDATE inventory SET quantity = %d WHERE inventory_id = %d", newQuantity, InventoryItemId);
        System.out.println(sqlUpdateTableString);
        DatabaseInterface.insertIntoTable(sqlUpdateTableString);
    }



    //    public static void main(String args[]) {
//        Inventory inv = new Inventory(); // check constructor
//
//        // Inventory.printInventoryItems();
//
//        // Item i1 = getInventoryItem(1); // should return item @ id 1
//        // i1.print();
//        // Item i2 = getInventoryItem(99); // should return null. 
//
//        // Item i3 = getInventoryItem("Chicken"); // FIXME String not matching
//        // i3.print();
//        
//        // System.out.println(Inventory.checkInventory("cups,chicken, celery")); // test once getInventoryItem(String) works
//        // System.out.println(Inventory.checkInventory("cups,chicken,celery,chair")); 
//        // Item newItem = new Item("NEWITEM", (float)99.99, 500, 1);
//        // inv.addInventoryItem("NEWITEM", 99.00f, 500, 1);
//        // inv.deleteInventoryItem(43); // deletes 43rd entry
//        // inv.refreshInventory();
//
//        // change quantity of NEWITEM (subtract 10, add 20)
//        // inv.addOrSubtractQuantity(42, -10);
//        // inv.addOrSubtractQuantity(42, 20);
//
//        // inv.printInventoryItems();
//    }
}