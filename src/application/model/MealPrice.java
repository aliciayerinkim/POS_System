package application.model;

import java.util.ArrayList;

import java.sql.*;


/**
 *  An object class to contain mealPrice, will be used with order to contain mealType prices
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class MealPrice {
    public static ArrayList<MealType> mealTypes = new ArrayList<MealType>();
    
    public class MealType {
        private int id;
        private String name;
        private float price;

        public MealType(int _id, String _name, Float _price) { 
            id = _id;
            name = _name;
            price = _price;
        }

        /**
         * Gets meal type id
         * 
         * @return
         */
        public int getMealTypeId() {
            return id;
        }
        
        /**
         * Gets meal name of meal type
         * 
         * @return
         */
        public String getMealName() {
            return name;
        }
        
        /**
         * Gets price of meal type
         * 
         * @return
         */
        public Float getPrice() {
            return price;
        }

        /**
         * changes price of meal type to specified price
         * 
         * @param newPrice
         */
        public void changePrice(Float newPrice) {
            String insertLine = String.format("UPDATE meal_price SET meal_price = '%f' WHERE meal_type_id = '%d';", newPrice, this.id);
            DatabaseInterface.insertIntoTable(insertLine);
            refreshMealPrice();
        }
    }

    /**
     * Empty constructor for MealPrice
     * 
     * @return constructed mealPrice
     */
    public MealPrice() {
        refreshMealPrice();
    }
    
    /**
     * Refreshes meal prices from database
     * 
     * @return void
     */
    public void refreshMealPrice() {
        ResultSet result = DatabaseInterface.populateMealPrice();
        mealTypes.clear();

        int id = 0;
        String name = "";
        float price = 0.f;
        
        try {
            do {
                id = Integer.parseInt(result.getString("meal_type_id"));
                name = result.getString("meal_name");
                price = Float.parseFloat(result.getString("meal_price"));
                MealPrice.MealType mealType = new MealPrice.MealType(id, name, price);
                mealTypes.add(mealType);
            }while(result.next());
    
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /** 
     * Gets an ArrayList of MealTypes from MealPrice
     * 
     * @return ArrayList<MealType> all MealTypes from MealPrice
     */
    public ArrayList<MealType> getMealTypes() {
        return mealTypes;
    }
    
}