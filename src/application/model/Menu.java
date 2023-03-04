package application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.crypto.Data;


import java.sql.*;

/**
 *  An object class that pulls from the database and compiles menu_item into an object list of Items, containing the relevant information.
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class Menu {
    public static ArrayList<Item> items = new ArrayList<Item>();

    
    public class Item {
        private int menuItemId;
        private String productName;
        private Float salePrice;
        // 0 - Sides | 1 - Entrees | 2 - Drinks | 3 - Seasonal Sides | 4 - Seasonal Entrees | 5 - Seasonal Drinks
        private int category;
        
        /** * Constructor for item, constructs an Meal.item object 
         *
         * @param int of id
         * @param of string of name
         * @param float sale price
         * @param category, int
         * @return Item object
        */
        public Item(int _id, String _name, Float _salePrice, int _category) { 
            menuItemId = _id;
            productName = _name;
            salePrice = _salePrice;
            category = _category;
        }

        /** * Returns an Item menuItemid int value
         *
         * @param none
         * @return menuItemId
        */
        public int getMenuItemId() {
            return menuItemId;
        }

        /** * Returns an Item menu product name string value
         *
         * @param none
         * @return proiductname
        */
        public String getProductName() {
            return productName;
        }

        /** * Returns an Item sale price float value
         *
         * @param none
         * @return sale price
        */
        public Float getSalePrice() {
            return salePrice;
        }

        /** * Returns an Item category int value
         *
         * @param none
         * @return category
        */
        public int getCategory() {
            return category;
        }

         /** * Updates the price of an item to a given price from the manager
         *
         * @param float of new price
         * @return void
        */
        public void changePrice(Float newPrice) {
        	String insertLine = String.format("UPDATE menu_item SET sale_price = '%f' WHERE menu_item_id = '%d';", newPrice, this.menuItemId);
        	DatabaseInterface.insertIntoTable(insertLine);
            refreshMenu();
        }
        
    }
    
    public Menu() {
        refreshMenu();
    }

    /** * Updates the static varible items of the current populated menu
     *
     * @param noen
     * @return void
    */
    public void refreshMenu() {
        ResultSet result = DatabaseInterface.populateMenu();

        int menuItemId = 0;
        String productName = "";
        Float salePrice = 0.f;
        int category = 0;
        items = new ArrayList<Item>();

        try {
            do {
                menuItemId = Integer.parseInt(result.getString("menu_item_id"));
                productName = result.getString("product_name");
                salePrice = Float.parseFloat(result.getString("sale_price"));
                category = Integer.parseInt(result.getString("category"));
                Menu.Item menuItem = new Menu.Item(menuItemId, productName, salePrice, category);
                items.add(menuItem);
            }while(result.next());
    
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /** * Returns an arraylist of items of all the side items
     * loops through item to parse all the side menu items to display
     * 0 is sides, 3 is seasonal sides
     *
     * @param noen
     * @return ArrayList of items
    */
    public ArrayList<Item> getAllSides() {
        ArrayList<Item> sideItems = new ArrayList<Item>();

        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getCategory() == 0 || items.get(i).getCategory() == 3) {
                sideItems.add(items.get(i));
            } 
        }
        return sideItems;
    }

    /** * Returns an arraylist of items of all the entree items
     * loops through item to parse all the entree menu items to display
     * 1 is entrees, 4 is seasonal entrees
     *
     * @param noen
     * @return ArrayList of entrees
    */
    public ArrayList<Item> getAllEntrees() {
        ArrayList<Item> entrees = new ArrayList<Item>();

        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getCategory() == 1 || items.get(i).getCategory() == 4) {
                entrees.add(items.get(i));
            } 
        }
        return entrees;
    }

    /** * Returns an arraylist of items of all the drink items
     * loops through item to parse all the drink menu items to display
     * 2 is drinks, 5 is seasonal drinks
     *
     * @param noen
     * @return ArrayList of drink
    */
    public ArrayList<Item> getAllDrinks() {
        ArrayList<Item> drinks = new ArrayList<Item>();

        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getCategory() == 2 || items.get(i).getCategory() == 5) {
                drinks.add(items.get(i));
            } 
        }
        return drinks;
    }
    
    /** * Returns an Item object of the item correlating to the given menuId
     *
     * @param menuItemId of requested item
     * @return Item object
    */
    public Item getMenuItem(int _menuItemId) {
        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getMenuItemId() == _menuItemId) {
                return items.get(i);
            } 
        }
        return null;
    }

    /** * Returns an Item object of the item correlating to the given item name
     *
     * @param itemName of requested item
     * @return Item object
    */
    public Item getMenuItem(String itemName) {
        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductName().equals(itemName)) {
                return items.get(i);
            } 
        }
        return null;
    }
    
    /** * Takes in menu item parameters to construct a item object and adds record to database
     *
     * @param menuItem name
     * @param menuitem sale price
     * @param menu item categoty
     * @param ingeredient name of menuItem
     * @return void
    */
    public void addMenuItem(String _name, Float _salePrice, int _category, String itemNames) { 
        int id = DatabaseInterface.findNextMenuId();
        Item newItem = new Item(id, _name, _salePrice, _category); 
        items.add(newItem);
        String insertLine = String.format("INSERT INTO menu_item(menu_item_id, product_name, sale_price, category) VALUES ('%d', '%s', '%f', '%d')", id, _name, _salePrice, _category);
        DatabaseInterface.insertIntoTable(insertLine);

        String str[] = itemNames.split(",");
        for(int i =0 ; i < str.length; i++) {
            int invId = Inventory.getInventoryItem(str[i]).getInventoryItemId();
            String ingBr = String.format("INSERT INTO ingredient_bridge(menu_item_id, inventory_order_id) VALUES ('%d', '%d')", id, invId);
            DatabaseInterface.insertIntoTable(ingBr);
        }
    }  


    /** * Given a menyId, this will will delete the menu object from the local values and database
     *
     * @param menuItemId of requested item
     * @return void
    */
    public void deleteMenuItem(int _menuItemId) {
        Item toBeDeleted = getMenuItem(_menuItemId);
        if (toBeDeleted != null) {
        	String insertLine = String.format("DELETE FROM menu_item WHERE menu_item_id  = '%d'", _menuItemId);
        	DatabaseInterface.insertIntoTable(insertLine);

            insertLine = String.format("DELETE FROM ingredient_bridge WHERE menu_item_id = '%d'", _menuItemId);
            DatabaseInterface.insertIntoTable(insertLine);
        }else {
            throw new IllegalArgumentException("item does not exist in menuItem");
        }
    }

 }