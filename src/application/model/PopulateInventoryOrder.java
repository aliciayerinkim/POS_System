package application.model;

import java.sql.*;

public class PopulateInventoryOrder {
    static Connection conn = null;
    static String teamNumber = "81";
    static String sectionNumber = "908";
    static String dbName = "csce315" + "_" + sectionNumber + "_" + teamNumber;
    static String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    static login myCredentials = new login(); 
    static boolean isOpen = false;

//        public static void main(String args[]) { // testing
//        System.out.println("Testing");
        

//        DatabaseInterface.disconnectToDB();
//        isOpen = false;
//    }
  
}
