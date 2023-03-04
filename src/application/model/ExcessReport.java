package application.model;

import java.util.*;

/**
 *  An object class that to assist the restockReport() function from backend to frontend.
 * 
 * @author Justin Van Nimwegen - 930005547
 * 315 - Project 2
 *
 */
public class ExcessReport {
    private String itemName;
    private Float percentage;

    /** * Constructor for ExcessReport
     *
     * @param int _itemName
     * @param int _percentage
    */
    public ExcessReport(String _itemName, Float _percentage) {
        itemName = _itemName;
        percentage = _percentage;
    } 

    /** * Getter getItemName
     *
     * @return String itemName
    */
    public String getItemName() {
        return itemName;
    }

    /** * Getter getPercentage
     *
     * @return Flaot percentage
    */
    public Float getPercentage() {
        return percentage;
    }
    
}