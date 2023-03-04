package application.model;

// import application.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.sound.sampled.TargetDataLine;

/**
 *  A class that allows JDBC connection to the SQL databases. Will be called by object and controller functions. 
 * 
 * @author Justin Van Nimwegen - 930005547 
 * @author Aaron Su - 930006201
 * 315 - Project 2
 *
 */
public class DatabaseInterface {

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  //MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE

    //Building the connection with your credentials
    static Connection conn = null;
    static String teamNumber = "81";
    static String sectionNumber = "908";
    static String dbName = "csce315" + "_" + sectionNumber + "_" + teamNumber;
    static String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    static login myCredentials = new login(); 
    static boolean isOpen = false;

    /** * Connects through JDBC to data base
     * @return void
    */
    public static void connectToDB() {
        try {
            conn = DriverManager.getConnection(dbConnectionString, login.user, login.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        isOpen = true;
    }

    /** * disonnects through JDBC to data base
     * 
     * @return void
    */
    public static void disconnectToDB() {
        try {
            conn.close();
            System.out.println("Connection Closed.");
          } catch(Exception e) {
            System.out.println("Connection NOT Closed.");
          }
          isOpen = false;
    }

    /** * Checks if the given username and password are of a registered user
     * Connects to the database and queries the username and password into employee to check if it is a user 
     * 
     *
     * @param username string of the user-supplied username
     * @param password string of the user-supplied password
     * @return void
    */
    public static boolean checkLogin(String username, String password) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = String.format("SELECT * FROM employee WHERE username = '%s' AND password = '%s'", username, password);
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            if (!result.next()) {
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /** * Returns a ResultSet of the employee information from employee database
     * of given username
     *
     * @param username - string of the user-supplied username
     * @return ResultSet the sql generated info from the data base
    */
    public static ResultSet employeeInfo(String username) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = String.format("SELECT * FROM employee WHERE username = '%s'", username);
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            if (!result.next()) {
                return null;
            }else {
                return result;
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in employeeInfo");
        return null;
    }
    /** * Returns an int value of the next orderId that is to be a record of 
     * the Orders table.
     *
     * @return int value of next available orderId
    */
    private static int findNextOrderId() {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int next = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                next = Integer.parseInt(result.getString("order_id")) + 1;
            }
            return next;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in findNextOrderId");
        return next;
    }

    /** * Returns an int value of the next mealId that is to be a record of 
     * the Meal table.
     *
     * @return int value of next available mealId
    */
    private static int findNextMealId() {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int next = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = "SELECT meal_id FROM meal ORDER BY meal_id DESC LIMIT 1;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                next = Integer.parseInt(result.getString("meal_id")) + 1;
            }
            return next;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in findNextMealId");
        return 0;
    }

    /** * Returns an int value of the next inventoryId that is to be a record of 
     * the Inventory table.
     *
     * @return int value of next available inventoryId
    */
    public static int findNextInventoryId() {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int next = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                next = Integer.parseInt(result.getString("inventory_id")) + 1;
            }
            return next;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in findNextInventoryId");
        return 0;
    }

    /** * Returns an int value of the next menuId that is to be a record of 
     * the Menu_item table.
     *
     * @return int value of next available menuId
    */
    public static int findNextMenuId() {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int next = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = "SELECT menu_item_id FROM menu_item ORDER BY menu_item_id DESC LIMIT 1;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                next = Integer.parseInt(result.getString("menu_item_id")) + 1;
            }
            return next;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in findNextMenuId");
        return 0;
    }

    /** * Returns an int value of the next inventoryOrderId that is to be a record of 
     * the inventory_order table.
     *
     * @return int value of next available inventoryOrderId
    */
    public static int findNextInventoryOrderId() {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int next = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = "SELECT inventory_order_id FROM inventory_order ORDER BY inventory_order_id DESC LIMIT 1;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                next = Integer.parseInt(result.getString("inventory_order_id")) + 1;
            }
            return next;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in findNextInventoryOrderId");
        return 0;
    }

    /** 
     * * Sends given sql command to database to be inserted, updated, or deleted from database
     *
     * @param command string sql command to be sent to database
     * @return int value of next available orderId
    */
    public static void insertIntoTable(String command) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(command);

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Queries databas
     * 
     * @param command
     * @return ResultSet of whatever is queried
     */
    public static ResultSet queryFromTable(String command) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(command);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }

    /** * 
     * Returns an int value of the next orderId that is to be a record of 
     * the Orders table.
     *
     * @param itemid, itemId for either meal or menu
     * @param mealOrMenu, 0 for meal & 1 for menu, identifies what table to get price from
     * @return int value of next available orderId
    */
    private static float getItemPrice(int itemId, int mealOrMenu) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        float price = 0;
        try{

            Statement stmt = conn.createStatement();
            
            String sqlStatement = "";
            if (mealOrMenu == 0) {
                sqlStatement = String.format("SELECT (SELECT meal_price FROM meal_price WHERE meal_type_id = '%d') as price;", itemId);
            }else if (mealOrMenu == 1) {
                sqlStatement = String.format("SELECT (SELECT sale_price FROM menu_item WHERE menu_item_id = '%d') as price;", itemId);
            }else {
                throw new Exception("Invalid mealOrMenu");
            }
            
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                price = Float.parseFloat(result.getString("price"));
            }
            return price;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in getItemPrice");
        return 0;
    }

    /** * 
     * Returns a float value of the inventory unit price (restock price)
     * of a given itemId
     *
     * @param itemId of od inventory item to get price of
     * @return float value of price
    */
    private static float getUnitPrice(int itemId) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        float price = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            
            String sqlStatement = "";
            sqlStatement = String.format("SELECT (SELECT unit_purchase_price FROM inventory WHERE inventory_id = '%d') as price;", itemId);

            
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                price = Float.parseFloat(result.getString("price"));
            }
            return price;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("FAIL in getUnitPrice");
        return 0;
    }

    /** 
     * Returns an int value of the quantity of a given
     * inventory item id. 
     *
     * @param inventory_id of requested inventory item
     * @return int value of specific incentory quantity
    */
    public static int getInventoryQuanity(int itemId) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int quantity = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            
            String sqlStatement = String.format("SELECT quantity FROM inventory WHERE inventory_id = '%d';", itemId);

            
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                quantity = Integer.parseInt(result.getString("quantity"));
            }
            return quantity;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return 0;
    }

    /** 
     * Returns an int value of the quantity of a given
     * inventory item name. 
     *
     * @param inventory_name of requested inventory item
     * @return int value of specific incentory quantity
    */
    public static int getInventoryQuanity(String itemName) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        int quantity = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            
            String sqlStatement = "";
            sqlStatement = String.format("SELECT quantity FROM inventory WHERE inventory_name = '%s';", itemName);

            
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                quantity = Integer.parseInt(result.getString("quantity"));
            }
            return quantity;

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return 0;
    }

    /**
     * Returns an ArrayList of integers conraininf the ingredientIDlist of a given menuItem
     *
     * @param menuItemId of requested menu item
     * @return ArrayList of integers of the ingredient Ids
    */
    public static ArrayList<Integer> getIngredientIdList(int menuItemId) {
        ArrayList<Integer> ingredients = new ArrayList<Integer>();
        int id = 0;
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            
            String sqlStatement = String.format("SELECT inventory_order_id FROM ingredient_bridge WHERE menu_item_id = '%d'", menuItemId);
            
            
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                id = Integer.parseInt(result.getString("inventory_order_id"));
                ingredients.add(id);
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return ingredients;
    }

    /**
     * Constucts and sends relevant meal records of an order's meal
     *
     * @param meal to be inported
     * @param orderId that the meal belongs to
     * @return void
    */
    public static void constructMealCommands(Meal meal, int orderId) {
        int mealId = findNextMealId();
        int mealTypeId = meal.getMealTypeId();
        int sideId = meal.getSideId();
        ArrayList<Integer> entrees = meal.getEntrees(); 

        // order_meals_bridge table insert
        String command  = String.format("INSERT INTO order_meals_bridge(order_id, meal_id) VALUES ('%d', '%d')", orderId, mealId);
        insertIntoTable(command);

        // meal table insert
        command  = String.format("INSERT INTO meal(meal_id, meal_type_id, side_id) VALUES ('%d', '%d', '%d')", mealId, mealTypeId, sideId);
        insertIntoTable(command);

        // meal_items_bridge table insert && deincrement menu_item
        for (int e: entrees) {
            command  = String.format("INSERT INTO meal_items_bridge(meal_id, menu_item_id) VALUES ('%d', '%d')", mealId, e);
            insertIntoTable(command);
            
            ArrayList<Integer> ingredientList = getIngredientIdList(e);
            for(int i = 0; i < ingredientList.size(); i++) {
                int currentQuanity = getInventoryQuanity(ingredientList.get(i));
                command  = String.format("UPDATE inventory SET quantity = '%d' WHERE inventory_id = '%d';", (currentQuanity - 1), ingredientList.get(i));
                insertIntoTable(command);
            }
        }   

        //Updating side ID inventory
        int currentQuanity = getInventoryQuanity(sideId);
        command  = String.format("UPDATE inventory SET quantity = '%d' WHERE inventory_id = '%d';", (currentQuanity - 1), sideId);
        insertIntoTable(command);

    }

    /**
     * Called from oder, addOrderEntry sends the order object and all of its
     * relevant information to the database.
     *
     * @param order object of the new order
     * @return void
    */
    public static void addOrderEntry(Order orderToAdd) {
        ArrayList<Meal> meals = orderToAdd.getMeals();
        ArrayList<Integer> addOnIds = orderToAdd.getAddOnIds();
        ArrayList<String> miscItems = orderToAdd.getMiscItemIds();
        int orderId = findNextOrderId();

        //Calls current time and formats to string 
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        
        //other 
        int employeeID = Session.getEmployeeId();
        float mealPrice = 0;        

        //Parse through meals, add rows to meal, meal_items_bridge, order_meals_bridge
        //also deincrement menu_item
        for (Meal meal : meals) {
            constructMealCommands(meal, orderId);
            mealPrice += getItemPrice(meal.getMealTypeId(), 0);
        }

        //parse though addOns, add rows to order_addon_items_bridge
        for (int addOn : addOnIds) {
            String command  = String.format("INSERT INTO order_addon_items_bridge(order_id, addon_item_id) VALUES ('%d', '%d')", orderId, addOn);
            insertIntoTable(command);
            mealPrice += getItemPrice(addOn, 1);

            ArrayList<Integer> ingredientList = getIngredientIdList(addOn);
            for(int i = 0; i < ingredientList.size(); i++) {
                int currentQuanity = getInventoryQuanity(ingredientList.get(i));
                command  = String.format("UPDATE inventory SET quantity = '%d' WHERE inventory_id = '%d';", (currentQuanity - 1), ingredientList.get(i));
                insertIntoTable(command);
            }
            
        }
                
        //add row to orders 
        String command  = String.format("INSERT INTO orders(order_id, time_stamp, employee_id, order_total) VALUES ('%d', '%s', '%d', '%f')", orderId, formattedDate, employeeID, mealPrice);
        insertIntoTable(command);



        //parse misc_items
        //deincrement and misc_item
        for (String miscItem : miscItems) {
            int currentQuanity = getInventoryQuanity(miscItem);
            command  = String.format("UPDATE inventory SET quantity = '%d' WHERE inventory_name = '%s'", (currentQuanity - 1), miscItem);
            insertIntoTable(command);
        }
    }

    /**
     * Adds a record of a inventory order to the database
     *
     * @param InventiryOrder object of an inventory order to be sent to the database
     * @return void
    */
    public static void addInventoryOrderEntry(InventoryOrder orderToAdd) {
        Hashtable<String, Integer> inventoryQuantity = orderToAdd.getInventoryQuantities();

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        int inventoryOrderId = 0;

        //Looping through menuItems, adding records to inventory_order_menu_items_bridge & updading quantity
        float orderPrice = 0;

        for(Map.Entry<String, Integer> entry : inventoryQuantity.entrySet()) {     
            inventoryOrderId = findNextInventoryOrderId();
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Inventory.Item item = Inventory.getInventoryItem(productName); 

            int itemId = item.getInventoryItemId();

            int currentQuanity = getInventoryQuanity(itemId);
            String command  = String.format("UPDATE inventory SET quantity = '%d' WHERE inventory_id = '%d';", (currentQuanity + quantity), itemId);
            insertIntoTable(command);

            
            orderPrice = getUnitPrice(itemId) * quantity;
            command  = String.format("INSERT INTO inventory_order(inventory_order_id, inventory_item_id, inventory_quantity, time_stamp, inventory_order_price, quantity_snapshot) VALUES ('%d', '%d', '%d', '%s', '%f', '%d')", inventoryOrderId, itemId, quantity, formattedDate, orderPrice, (currentQuanity + quantity));
            insertIntoTable(command);

            
        }

    }

    /**
     * Returns a sql result set conaint all the information from the menu_item table
     *
     * @param none
     * @return Result set of menu_item
    */
    public static ResultSet populateMenu() {
        // ArrayList<Menu.Item> menu = new ArrayList<Menu.Item>();
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = String.format("SELECT * FROM menu_item ORDER BY menu_item_id");
            ResultSet result = stmt.executeQuery(sqlStatement);

            if (!result.next()) {
                return null;
            }else {
                return result;
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }

     /**
      * Returns a sql result set conaint all the information from the inventory table
     *
     * @param none
     * @return Result set of inventory
    */
    public static ResultSet populateInventory() {
        ArrayList<Menu.Item> menu = new ArrayList<Menu.Item>();
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = String.format("SELECT * FROM inventory ORDER BY inventory_id;");
            ResultSet result = stmt.executeQuery(sqlStatement);

            if (!result.next()) {
                return null;
            }else {
                return result;
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }

     /**
      * Returns a sql result set conaint all the information from the meal_price table
     *
     * @param none
     * @return Result set of meal_price
    */
    public static ResultSet populateMealPrice() {
        ArrayList<Menu.Item> menu = new ArrayList<Menu.Item>();
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
     
            String sqlStatement = String.format("SELECT * FROM meal_price ORDER BY meal_type_id;");
            ResultSet result = stmt.executeQuery(sqlStatement);

            if (!result.next()) {
                return null;
            }else {
                return result;
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }

    /**
     * Gets Inventory Quantity for specified InventoryItemId at the given time
     * 
     * @param InventoryItemId The id to get quantity for
     * @param targetDate The date to get quantity at
     * @return int
     */
    public static int getInventoryQuantityAtTime(int InventoryItemId, Timestamp targetDate) {
        ResultSet minDateRs = queryFromTable("SELECT time_stamp FROM inventory_order ORDER BY time_stamp LIMIT 1");

        try{    
            minDateRs.next();
            if (targetDate.compareTo(minDateRs.getTimestamp("time_stamp")) < 0) {
                // System.err.println("targetDate: " + targetDate);
                // System.err.println("minDate: " + minDateRs.getTimestamp("time_stamp"));
                System.err.println("targetDate too early. Setting targetDate to minDate...\n");
                targetDate = minDateRs.getTimestamp("time_stamp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        String InventoryOrderSQLStr = String.format("SELECT "
        + "time_stamp, quantity_snapshot "
        + "FROM inventory_order "
        + "WHERE time_stamp <= '%s' "
        + "ORDER BY time_stamp DESC "
        + "LIMIT 1", targetDate);


        ResultSet startResultSet = queryFromTable(InventoryOrderSQLStr); // get startQuantity from inventory_order
        try {
            Timestamp startDate = null;
            int startQuantity = 0;
            while (startResultSet.next()) {
                startQuantity = startResultSet.getInt("quantity_snapshot");
                startDate = startResultSet.getTimestamp("time_stamp"); // parse this to timestamp
            }
            // String formattedStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate);
            // String formattedTargetDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(targetDate);

            String orderJoinSQLStr1 = String.format("SELECT "
                + "COUNT(addon_item_id) AS quantity "
                + "FROM orders o "
                + "INNER JOIN order_addon_items_bridge oai "
                + "ON o.order_id = oai.order_id "
                + "WHERE oai.addon_item_id = %d "
                + "AND '%s' <= o.time_stamp AND o.time_stamp <= '%s'",
                InventoryItemId, startDate, targetDate);

            String orderJoinSQLStr2 = String.format("SELECT "
                + "COUNT(*) AS quantity "
                + "FROM (orders o "
                + "INNER JOIN order_meals_bridge om "
                + "ON o.order_id = om.order_id) "
                + "INNER JOIN meal_items_bridge mi "
                + "ON om.meal_id = mi.meal_id "
                + "WHERE mi.menu_item_id = %d "
                + "AND '%s' <= o.time_stamp AND o.time_stamp <= '%s'",
                InventoryItemId, startDate, targetDate);

            int orderQuantity_Order_and_Order_addon_BRIDGE = -1;
            ResultSet rsJoin1 = queryFromTable(orderJoinSQLStr1);
            while (rsJoin1.next()) {
                orderQuantity_Order_and_Order_addon_BRIDGE = rsJoin1.getInt("quantity");
            }
            
            int orderQuantity_Order_and_Meal_and_Meal_items_BRIDGE = -1;
            ResultSet rsJoin2 = queryFromTable(orderJoinSQLStr2);
            while (rsJoin2.next()) {
                orderQuantity_Order_and_Meal_and_Meal_items_BRIDGE = rsJoin2.getInt("quantity");
            }
            // System.out.println("startQuantity " + startQuantity);
            // System.out.println("orderQuantity_Order_and_Order_addon_BRIDGE " + orderQuantity_Order_and_Order_addon_BRIDGE);
            // System.out.println("orderQuantity_Order_and_Meal_and_Meal_items_BRIDGE " + orderQuantity_Order_and_Meal_and_Meal_items_BRIDGE);

            return (startQuantity - orderQuantity_Order_and_Order_addon_BRIDGE - orderQuantity_Order_and_Meal_and_Meal_items_BRIDGE);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    return -1; 
    }

    /**
     * Gets the sum of all restocks within the given timespan for the specified inventoryItemId
     * 
     * @param inventoryItemId The id to get restock amount for
     * @param begin The beginning of the timespan
     * @param end The end of the timespan
     * @return int the sum of all restocks in the timespan
     */
    public static int getRestockAmount(int inventoryItemId, Timestamp begin, Timestamp end) {
        String restockAmountSQLStr = String.format("SELECT SUM(inventory_quantity) FROM inventory_order WHERE '%s' <= time_stamp AND time_stamp <= '%s' AND inventory_item_id = %d", begin, end, inventoryItemId);
        ResultSet rs = queryFromTable(restockAmountSQLStr);
        int restockAmount = -1;
        try{
            rs.next();
            restockAmount = rs.getInt("sum");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return restockAmount;
    }
    
    /**
     * Gets the amount sold over a timespan for the specified inventoryItemId
     * 
     * @param inventoryItemId The id to get amount sold for
     * @param begin The beginning of the timespan
     * @param end The end of the timespan
     * @return int the amount sold over the timespan
     */
    public static int getAmountSold(int inventoryItemId, Timestamp begin, Timestamp end) {
        int quantityBefore = getInventoryQuantityAtTime(inventoryItemId, begin);
        int quantityAfter = getInventoryQuantityAtTime(inventoryItemId, end);
        int restockAmount = getRestockAmount(inventoryItemId, begin, end);
        int numSales = quantityBefore - quantityAfter + restockAmount;
        // System.out.println("quantityBefore: " + quantityBefore);
        // System.out.println("quantityAfter: " + quantityAfter);
        // System.out.println("restockAmount: " + restockAmount);
        // System.out.println("numSales: " + numSales);
        return numSales;
    }
    
    /**  
    * Given a startTime and endTime, display the sales by item (menu_item) from the order history. 
    *
    * @param menuItemId, int
    * @param start, Timestamp
    * @param end, Timestamp
    * @return ArrayList
   */

    /**
     * Gets all Orders over a timespan containing the specified inventoryItemId
     * 
     * @param inventoryItemId The id to get the sales data for
     * @param begin The beginning of the timespan
     * @param end The end of the timespan
     * @return ArrayList<Order> containing all orders in the timespan, containing order id, time stamp, and order total
     */
    public static ArrayList<Order> salesReport(int inventoryItemId, Timestamp begin, Timestamp end) {
        String salesReportSQLStr = String.format("SELECT DISTINCT o.order_id, o.time_stamp, o.order_total FROM ORDERS o INNER JOIN order_addon_items_bridge oai ON o.order_id = oai.order_id INNER JOIN order_meals_bridge om ON o.order_id = om.order_id INNER JOIN meal_items_bridge mi ON om.meal_id = mi.meal_id WHERE (menu_item_id = %d OR addon_item_id = %d) AND '%s' <= time_stamp AND time_stamp <= '%s' ORDER BY o.order_id", inventoryItemId, inventoryItemId, begin, end);
        ResultSet rs = queryFromTable(salesReportSQLStr);
        ArrayList<Order> ordersContainingItem = new ArrayList<Order>();
        try {
            while (rs.next()) {
                Order orderToAdd = new Order(rs.getTimestamp("time_stamp"), rs.getInt("order_id"), rs.getFloat("order_total"));
                ordersContainingItem.add(orderToAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return ordersContainingItem;
    }
    
    /**
    *  Given a startTime and endTime, display the list of items (ingredients) that only sold less than 10% of their inventory
    *
    * @param start, Timestamp
    * @param end, Timestamp
    * @return int value of next available orderId
   */
    
    public static Map<String, Float> excessReport(Timestamp start, Timestamp end) {
        Map<String,Float> er = new HashMap<String,Float>();
        ResultSet rs = queryFromTable(String.format("SELECT inventory_id, inventory_name FROM inventory"));
        Map<Integer, String> ingredientIdName = new HashMap<>();
        try{
            while (rs.next()) {
                ingredientIdName.put(rs.getInt(1), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        for (int i : ingredientIdName.keySet()) {
            int startInventory = getInventoryQuantityAtTime(i, start);
            int amtSold = getAmountSold(i, start, end); 
            //System.out.println("startInventory: " + startInventory);
            //System.out.println("amtSold: " + amtSold);
            Float percentSold = (float)amtSold/startInventory; 
            if (percentSold <= .10) {
                //System.out.println("case1: excess with percentSold = " + percentSold);
                er.put(ingredientIdName.get(i), percentSold);
            }
            else {
                //System.out.println("case2: no excess with percentSold = " + percentSold);
            }
        }
        return er;
    }

    /**
    * Given a startTime and endTime, parses through the orders over that time and computes what entrees sell well together
    *
    * @param startTime, Timestamp
    * @param endTime, Timestamp
    * @return ArrayList
    */
    public static ArrayList<SellsTogether> whatSellsTogether(Timestamp startTime, Timestamp endTime) {

        Map<Integer, Map<Integer, Integer>> menuItemPairsMap = new HashMap<Integer, Map<Integer, Integer>>();
        Map<Integer, Map<Integer, Double>> parrallelPorpotions = new HashMap<Integer, Map<Integer, Double>>();   
        ArrayList<SellsTogether> top10 = new ArrayList<SellsTogether>();

        //"SELECT mib.meal_id, mib.menu_item_id FROM ((orders o INNER JOIN order_meals_bridge omb ON o.order_id = omb.order_id) INNER JOIN meal_items_bridge mib ON omb.meal_id = mib.meal_ID) WHERE o.time_stamp > 'startTime' AND o.time_stamp < 'endTime'; "
        
        String mealAndEntreesQuery = String.format(
            "SELECT mib.meal_id, mib.menu_item_id " +  
            "FROM ((orders o INNER JOIN order_meals_bridge omb ON o.order_id = omb.order_id) " +
            "INNER JOIN meal_items_bridge mib ON omb.meal_id = mib.meal_ID) " +
            "WHERE o.time_stamp > '%s' AND o.time_stamp < '%s';",
            startTime, endTime);

        ResultSet result = queryFromTable(mealAndEntreesQuery);
        try {
            int currMeal = 0;
            ArrayList<Integer> menuIds = new ArrayList<Integer>();
            
            while (result.next()) {
                int mealId = Integer.parseInt(result.getString("meal_id"));
                int menuItemId = Integer.parseInt(result.getString("menu_item_id"));

                if (currMeal == mealId) {
                    menuIds.add(menuItemId);
                }else {
                    //menuIds increment eachother
                    if (menuIds.size() > 1) {
                        for(int i = 0; i < menuIds.size(); i++) { //todo change this
                            for(int incrementMenuId : menuIds.subList(1, menuIds.size())) {
                                //stared line in psuedoCode


                                Map<Integer, Integer> currentIndexedMap = menuItemPairsMap.get(menuIds.get(0));

                                if (currentIndexedMap == null) {
                                    currentIndexedMap = new HashMap<Integer, Integer>();
                                    currentIndexedMap.put(incrementMenuId, 0);
                                    menuItemPairsMap.put(menuIds.get(0), currentIndexedMap);
         
                                }
                                if (currentIndexedMap.get(incrementMenuId) == null) {
                                    menuItemPairsMap.get(menuIds.get(0)).put(incrementMenuId, 0);
                                }

                                currentIndexedMap.put(incrementMenuId, currentIndexedMap.get(incrementMenuId) + 1); //increments the sub loop e value by 1
                                menuItemPairsMap.put(menuIds.get(0), currentIndexedMap); // updates the origional 2d map with the new incremented subMap 
                            }
                        }
                        int tempId = menuIds.get(0);
                        menuIds.remove(0);
                        menuIds.add(tempId);
                    }

                    //reset variables
                    menuIds.clear();

                    //set new variables
                    currMeal = mealId;
                    menuIds.add(menuItemId);
                }
            } 
            // process last meal
            if (menuIds.size() != 1) {
                for(int i = 0; i < menuIds.size(); i++) {
                    
                    for(int incrementMenuId : menuIds.subList(1, menuIds.size())) {
                        //stared line in psuedoCode:
                        Map<Integer, Integer> currentIndexedMap = menuItemPairsMap.get(menuIds.get(0));

                        if (currentIndexedMap == null) {
                            currentIndexedMap = new HashMap<Integer, Integer>();
                            currentIndexedMap.put(incrementMenuId, 0);
                        } 
                        if (currentIndexedMap.get(incrementMenuId) == null) {
                            menuItemPairsMap.get(menuIds.get(0)).put(incrementMenuId, 0);
                        }

                        currentIndexedMap.put(incrementMenuId, currentIndexedMap.get(incrementMenuId) + 1); //increments the sub loop e value by 1
                        menuItemPairsMap.put(menuIds.get(0), currentIndexedMap); // updates the origional 2d map with the new incremented subMap 
                    }
                }
                int tempId = menuIds.get(0);
                menuIds.remove(0);
                menuIds.add(tempId);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //run parallel loop
        for(Map.Entry<Integer, Map<Integer, Integer>> entry : menuItemPairsMap.entrySet()) {
            int menuId = entry.getKey();
            Map<Integer, Integer> subMap = entry.getValue();

            parrallelPorpotions.put(menuId, null);

            double sum = subMap.values().stream().mapToInt(Integer::intValue).sum();
            
            for (Map.Entry<Integer, Integer> entry2 : subMap.entrySet()) {
                Map<Integer, Double> newMap = parrallelPorpotions.get(menuId);

                if (newMap == null) {
                    newMap = new HashMap<Integer, Double>();
                } 

                newMap.put(entry2.getKey(), (((double) entry2.getValue())/(sum)));
                parrallelPorpotions.put(menuId, newMap);
            }
        }
        //find top 10
        for (int i =0; i < 10; i++) {
            //Find max porportion
            double currentMax = 0;
            int menuIdex1 = 0;
            int menuIdex2 = 0;


            for(Map.Entry<Integer, Map<Integer, Double>> entry : parrallelPorpotions.entrySet()) {
                int menuId = entry.getKey();
                Map<Integer, Double> subMap = entry.getValue();

                for (Map.Entry<Integer, Double> entry2 : subMap.entrySet()) {
                    double currPorportion = entry2.getValue();
                    if (currPorportion > currentMax) {
                        currentMax = currPorportion;
                        menuIdex1 = menuId;
                        menuIdex2 = entry2.getKey();
                    }
                }
            }

            // Adding largest to arraylist and setting value in porportions to 0
            parrallelPorpotions.get(menuIdex1).put(menuIdex2, 0.0);
            top10.add(new SellsTogether(menuIdex1, menuIdex2, i + 1, (double)Math.round(currentMax*1000)/10));
        }
        return top10;
    }
    
    /** 
    * Given an ingredient ID, parses through the ingredients bridge to see what menu items still require it
    *
    * @param itemId, int
    * @return if ingredient still used, boolean
   */

    /**
     * Checks if ingredient exists within database
     * 
     * @param itemId ingredient to check
     * @return boolean 
     */
    public static boolean checkIngredients(int itemId) {
        if (!isOpen) {
            isOpen = true;
            connectToDB();
        }
        try{
            //create a statement object
            Statement stmt = conn.createStatement();
            
            String query = String.format("SELECT * FROM ingredient_bridge WHERE inventory_order_id = %d", itemId);
           ResultSet menuItems = stmt.executeQuery(query);
           if(!menuItems.next()) {
               return false;
           }else {
               return true;
           }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return true;
   }

    /** 
    * Given a startTime and endTime, parses through the inventory orders to see the list of ingredients whose current inventory is less than the amount sold over the timespan
    *
    * @param begin, Timestamp
    * @param end, Timestamp
    * @return lowStockItems, ArrayList
    */
    public static ArrayList<RestockReport> restockReport(Timestamp begin, Timestamp end){
    	Inventory inv = new Inventory();

    	ArrayList<RestockReport> lowStockItems = new ArrayList<RestockReport>();
    	for (Inventory.Item i : inv.items) {
    		int startQuantity = getInventoryQuantityAtTime(i.getInventoryItemId(), begin);
    		//System.out.println(startQuantity);
    		int endQuantity = getInventoryQuantityAtTime(i.getInventoryItemId(), end);
    		//System.out.println(endQuantity);
    		int amountSold = startQuantity + getRestockAmount(i.getInventoryItemId(), begin, end) - endQuantity;
    		//System.out.println(amountSold);
    		//System.out.println(i.getItemQuanity());

    		if (amountSold < i.getItemQuanity()) {
    			String name = i.getInventoryItemName();
    			int endQuant = i.getItemQuanity();
    			int endAmountSold= amountSold;

                RestockReport currRestock = new RestockReport(name, endQuant, endAmountSold);
                lowStockItems.add(currRestock);
    		}
    		
    	}
    	return lowStockItems;
    }
    
    /**
     * Parses through the inventory orders to see the list of ingredients whose current inventory is less than a threshold
     * 
     * @return lowStockItems, ArrayList
     */
    public static ArrayList<RestockReport> restockReport2(){
    	Inventory inv = new Inventory();

    	ArrayList<RestockReport> lowStockItems = new ArrayList<RestockReport>();
    	for (Inventory.Item i : inv.items) {

    		if (i.getItemQuanity() < 500) {
    			String name = i.getInventoryItemName();
    			int endQuant = i.getItemQuanity();
    			int endAmountSold = 100;

                RestockReport currRestock = new RestockReport(name, endQuant, endAmountSold);
                lowStockItems.add(currRestock);
    		}
    		
    	}
    	return lowStockItems;
    }

    /** * Given a orderID, returns the orderObject from database
    *
    * @param int, OrderID
    * @param Order, orderObject for given id
    * @return lowStockItems, ArrayList
   */
    public static Order getOrderObject(int orderID) {
        //Info from Order Table
        Timestamp timestamp = Timestamp.valueOf("2022-09-04 00:00:00");
        int employeeId = -1;
        float orderTotal = 0.f;
        ResultSet query1 = queryFromTable(String.format("SELECT * FROM orders WHERE order_id = '%d';", orderID));
        

        //From Order_meals_bridge,meal, mealitems bridge (should already have something that does part of this)
        ArrayList<Meal> meals = new ArrayList<Meal>();
        ResultSet query2 = queryFromTable(String.format("SELECT m.meal_id, m.meal_type_id, m.side_id from meal m INNER JOIN order_meals_bridge omb on omb.meal_id = m.meal_id WHERE omb.order_id = '%d';", orderID));

        //order addOn bridge
        ArrayList<Integer> addOnIds = new ArrayList<Integer>();
        ResultSet query4 = queryFromTable(String.format("SELECT oaib.addon_item_id FROM order_addon_items_bridge oaib INNER JOIN orders o on o.order_id = oaib.order_id WHERE o.order_id = '%d';", orderID));


        try{
            while (query1.next()) {
                timestamp = (query1.getTimestamp("time_stamp"));
                employeeId = Integer.parseInt(query1.getString("employee_id"));
                orderTotal = Float.parseFloat(query1.getString("order_total"));
            }
            if (employeeId == -1) {
                return null;
            }

            while (query2.next()) {
                int sideId = Integer.parseInt(query2.getString("side_id"));
                int mealType = Integer.parseInt(query2.getString("meal_type_id"));
                int mealId = Integer.parseInt(query2.getString("meal_id"));
                ArrayList<Integer> entrees = new ArrayList<Integer>();

                ResultSet query3 = queryFromTable(String.format("SELECT m.meal_id, mib.menu_item_id FROM meal m INNER JOIN meal_items_bridge mib on mib.meal_id = m.meal_id WHERE m.meal_id = '%d';", mealId));
                
                while (query3.next()) {
                    entrees.add(Integer.parseInt(query3.getString("menu_item_id")));
                }
                Meal newMeal = new Meal(sideId, entrees, mealType);
                meals.add(newMeal);
            }

            while (query4.next()) {
                addOnIds.add(Integer.parseInt(query4.getString("addon_item_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        Order newOrder = new Order(timestamp, orderID, orderTotal, meals, addOnIds);

        return newOrder;
    }
}
