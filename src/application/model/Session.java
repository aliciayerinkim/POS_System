package application.model;

// import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
// import java.text.SimpleDateFormat;
// import java.util.Date;
import java.sql.*;

/**
 *  An object class that to assist user login, checks user status before allowing access.
 * 
 * @author Aaron Su - 930006201
 * 315 - Project 2
 *
 */
public class Session
{  
    private static int employeeId;//data member (also instance variable)  
    private static String employeeName; //data member (also instance variable)  
    public static Boolean isManager;
    
    /** 
     * Gets current timestamp
     * 
     * @return Timestamp, formatted as YYYY-MM-DD HH:MM:SS
     */
    public static Timestamp timeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    /** 
     * Logs into database, returning true if the username and password match in the database. Return false otherwise. Updates session data.
     * 
     * @param username the username to log in with
     * @param password the password to log in with
     * @return Boolean true if username and password match in the database. false otherwise.
     */
    public static Boolean login(String username, String password) {
        if (DatabaseInterface.checkLogin(username, password)){
            ResultSet employeeInfo = DatabaseInterface.employeeInfo(username);
            try {
                employeeId = Integer.parseInt(employeeInfo.getString("employee_id"));
                isManager = employeeInfo.getString("is_manager").equals("t");
                employeeName = employeeInfo.getString("employee_name");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
            DatabaseInterface.connectToDB();
            return true;
        }
        else {
            System.out.printf("Could not log into POS system with username %s%n", username);
            return false;
        }
    }

    /**
     * logs out of the POS system, resetting Session data.
     * 
     * @return void
     */
    public static void logout() {
        employeeId = -1;
        employeeName = "";
        isManager = false;
        DatabaseInterface.disconnectToDB();
    }

    /** 
     * gets the employee id of the employee currently logged in
     * 
     * @return int the employee id
     */
    public static int getEmployeeId() {
        return employeeId;
    }

    /** 
     * gets the name of the employee currentl logged in
     * 
     * @return String name of employee
     */
    public static String getName() {
        return employeeName;
    }

    /** 
     * checks if the employee currently logged in is manager
     * 
     * @return Boolean true if the current employee has attribute is_manager
     */
    public static Boolean isManager() {
        return isManager;
    }



    // public static void main(String[] args) { // testing
    //     // Session.login("asu", "1234");
    //     // System.out.println(Session.getEmployeeId());
    //     // System.out.println(Session.getName());
    //     // System.out.println(Session.isManager());

    //     // Session.logout();

    //     Session.login("ymin", "1234");
    //     System.out.println(Session.getEmployeeId());
    //     System.out.println(Session.getName());
    //     System.out.println(Session.isManager());

    //     Session.logout();

    //     Session.login("jvannimwegen", "1234");
    //     System.out.println(Session.getEmployeeId());
    //     System.out.println(Session.getName());
    //     System.out.println(Session.isManager());

    //     Session.logout();

    //     Session.login("ymin", "1234");
    //     System.out.println(Session.getEmployeeId());
    //     System.out.println(Session.getName());
    //     System.out.println(Session.isManager());

    //     Session.logout();
    // }
}     