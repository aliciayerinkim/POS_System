package application.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *  An object class to contain the time, orderID, and money, which will be used for listing out orders corresponding to Sales Report
 * 
 * @author Alicia Kim - 131003034 315 - Project 2
 *
 */
public class SalesReportItem {
	Timestamp time;
	Integer orderID;
	Float money;
	
	/** * Constructor for SalesReportItem
         *
         * @param Timestamp of tim
         * @param orderID of orderID
         * @param float of moeny
        */
	public SalesReportItem(Timestamp time, Integer orderID, Float money) {
		super();
		this.time=time;
		this.orderID=orderID;
		this.money=money;
	}
	
	/** * Returns a time when the order was made string value
         *
         * @param none
         * @return time
        */
	public Timestamp getTime() {
	  return time;
	 }
	
	/** * Returns an order ID Integer value
         *
         * @param none
         * @return orderID
        */
	public int getOrderID() {
	  return orderID;
	}
	
	/** * Returns total price of order Float value
         *
         * @param none
         * @return money
        */
	public Float getMoney() {
	  return money;
	}
}
