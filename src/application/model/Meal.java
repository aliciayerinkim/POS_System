package application.model;

import java.util.ArrayList;

/**
 *  An object class to contain meals, will be used with order to cainin entrees and side items.
 * 
 * @author Aaron Su - 930006201
 * 315 - Project 2
 *
 */
public class Meal {
    private ArrayList<Integer> entrees;
    private int sideId;
    private int mealTypeId;

    /** * Constructor for meal, constructs an Meal object 
     *
     * @param int of sideId
     * @param of ArrayList of integers
     * @param mealtypeId, int
     * @return Inventory Order Object
    */
    public Meal(int _sideId, ArrayList<Integer> _entrees, int _mealTypeId) {
        sideId = _sideId;
        entrees = new ArrayList<Integer>(_entrees);
        mealTypeId = _mealTypeId;
    }

    /** * rerturns meal sideId
     *
     * @param noen
     * @return side id int value
    */
    public int getSideId() {
        return sideId;
    }

    /** * rerturns ArrayList of the meals entrees menuIds
     *
     * @param none
     * @return ArrayList of entree menuIDs
    */
    public ArrayList<Integer> getEntrees() {
        return entrees;
    }

    /** * rerturns meal typeId
     *
     * @param noen
     * @return mealType Int value
    */
    public int getMealTypeId() {
        return mealTypeId;
    }



    // public static void main(String args[]) { // testing
    //     ArrayList<Integer> entreeIds = new ArrayList<Integer>();
    //     entreeIds.add(6);
    //     entreeIds.add(7);
    //     entreeIds.add(8);
    //     Meal myMeal = new Meal(5, entreeIds, 2);
    //     System.out.println(myMeal.getSideId());
    //     System.out.println(myMeal.getEntrees());
    // }
}

