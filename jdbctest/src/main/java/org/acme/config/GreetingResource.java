package org.acme.config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jdbctest")
public class GreetingResource {
    
    private Connection conn = null;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        testJDBC();
        System.out.println("******************************************************************************");
        System.out.println("*****************************************INIT*********************************");
        System.out.println("******************************************************************************");
        return "hello";
    }

    public void testJDBC() {
        Connection conn = null;
        Statement stmt = null;
        Driver driver;
        try {
            String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
            String DB_URL = "jdbc:oracle:thin:vsk_dev/1@192.168.100.159:1521:orcd";
            try {
                System.out.println("new driver");
                driver = new oracle.jdbc.driver.OracleDriver();
                DriverManager.registerDriver(driver);
                System.out.println("Connect");
                conn = driver.connect(DB_URL, null);
                System.out.println("after connection");
            } catch (Exception ex) {
                System.out.println("Error: unable to load driver class!");
                ex.printStackTrace();
                System.exit(1);
            }

//            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//            for (StackTraceElement element:stack){
//                System.out.println(element.toString());
//            }
            //Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
            //conn = DriverManager.getConnection(DB_URL);
            //conn = driver.connect(DB_URL,null);
            //STEP 4: Execute a query
    
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT name FROM ADOC_ADMORDERSTATE";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(String.format("name = %s\n", name));
                //Retrieve by column name

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

    }//end main 
}
